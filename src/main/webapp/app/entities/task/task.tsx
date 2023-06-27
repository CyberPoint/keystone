import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getPaginationState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities, reset } from './task.reducer';

export const Task = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const taskList = useAppSelector(state => state.task.entities);
  const loading = useAppSelector(state => state.task.loading);
  const links = useAppSelector(state => state.task.links);
  const updateSuccess = useAppSelector(state => state.task.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="task-heading" data-cy="TaskHeading">
        <Translate contentKey="keyStoneApp.task.home.title">Tasks</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="keyStoneApp.task.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/task/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="keyStoneApp.task.home.createLabel">Create new Task</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={taskList ? taskList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {taskList && taskList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="keyStoneApp.task.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('command')}>
                    <Translate contentKey="keyStoneApp.task.command">Command</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('command')} />
                  </th>
                  <th className="hand" onClick={sort('implantTaskId')}>
                    <Translate contentKey="keyStoneApp.task.implantTaskId">Implant Task Id</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('implantTaskId')} />
                  </th>
                  <th className="hand" onClick={sort('submittedBy')}>
                    <Translate contentKey="keyStoneApp.task.submittedBy">Submitted By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('submittedBy')} />
                  </th>
                  <th className="hand" onClick={sort('description')}>
                    <Translate contentKey="keyStoneApp.task.description">Description</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('description')} />
                  </th>
                  <th className="hand" onClick={sort('added')}>
                    <Translate contentKey="keyStoneApp.task.added">Added</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('added')} />
                  </th>
                  <th className="hand" onClick={sort('updated')}>
                    <Translate contentKey="keyStoneApp.task.updated">Updated</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updated')} />
                  </th>
                  <th className="hand" onClick={sort('retrieved')}>
                    <Translate contentKey="keyStoneApp.task.retrieved">Retrieved</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('retrieved')} />
                  </th>
                  <th className="hand" onClick={sort('failure')}>
                    <Translate contentKey="keyStoneApp.task.failure">Failure</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('failure')} />
                  </th>
                  <th className="hand" onClick={sort('approved')}>
                    <Translate contentKey="keyStoneApp.task.approved">Approved</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('approved')} />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {taskList.map((task, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/task/${task.id}`} color="link" size="sm">
                        {task.id}
                      </Button>
                    </td>
                    <td>{task.command}</td>
                    <td>{task.implantTaskId}</td>
                    <td>{task.submittedBy}</td>
                    <td>{task.description}</td>
                    <td>{task.added ? <TextFormat type="date" value={task.added} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{task.updated ? <TextFormat type="date" value={task.updated} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{task.retrieved ? 'true' : 'false'}</td>
                    <td>{task.failure ? 'true' : 'false'}</td>
                    <td>{task.approved ? 'true' : 'false'}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/task/${task.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/task/${task.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/task/${task.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="keyStoneApp.task.home.notFound">No Tasks found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Task;
