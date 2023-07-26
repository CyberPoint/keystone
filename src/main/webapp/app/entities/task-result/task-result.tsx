import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat, getPaginationState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities, reset } from './task-result.reducer';

export const TaskResult = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const taskResultList = useAppSelector(state => state.taskResult.entities);
  const loading = useAppSelector(state => state.taskResult.loading);
  const links = useAppSelector(state => state.taskResult.links);
  const updateSuccess = useAppSelector(state => state.taskResult.updateSuccess);

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
      <h2 id="task-result-heading" data-cy="TaskResultHeading">
        <Translate contentKey="keyStoneApp.taskResult.home.title">Task Results</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="keyStoneApp.taskResult.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/task-result/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="keyStoneApp.taskResult.home.createLabel">Create new Task Result</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={taskResultList ? taskResultList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {taskResultList && taskResultList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="keyStoneApp.taskResult.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('embeddeddata')}>
                    <Translate contentKey="keyStoneApp.taskResult.embeddeddata">Embeddeddata</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('embeddeddata')} />
                  </th>
                  <th className="hand" onClick={sort('added')}>
                    <Translate contentKey="keyStoneApp.taskResult.added">Added</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('added')} />
                  </th>
                  <th className="hand" onClick={sort('reviewed')}>
                    <Translate contentKey="keyStoneApp.taskResult.reviewed">Reviewed</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('reviewed')} />
                  </th>
                  <th className="hand" onClick={sort('ipAddress')}>
                    <Translate contentKey="keyStoneApp.taskResult.ipAddress">Ip Address</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('ipAddress')} />
                  </th>
                  <th className="hand" onClick={sort('headers')}>
                    <Translate contentKey="keyStoneApp.taskResult.headers">Headers</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('headers')} />
                  </th>
                  <th className="hand" onClick={sort('url')}>
                    <Translate contentKey="keyStoneApp.taskResult.url">Url</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('url')} />
                  </th>
                  <th className="hand" onClick={sort('content')}>
                    <Translate contentKey="keyStoneApp.taskResult.content">Content</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('content')} />
                  </th>
                  <th>
                    <Translate contentKey="keyStoneApp.taskResult.task">Task</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {taskResultList.map((taskResult, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/task-result/${taskResult.id}`} color="link" size="sm">
                        {taskResult.id}
                      </Button>
                    </td>
                    <td>
                      {taskResult.embeddeddata ? (
                        <div>
                          {taskResult.embeddeddataContentType ? (
                            <a onClick={openFile(taskResult.embeddeddataContentType, taskResult.embeddeddata)}>
                              <Translate contentKey="entity.action.open">Open</Translate>
                              &nbsp;
                            </a>
                          ) : null}
                          <span>
                            {taskResult.embeddeddataContentType}, {byteSize(taskResult.embeddeddata)}
                          </span>
                        </div>
                      ) : null}
                    </td>
                    <td>{taskResult.added ? <TextFormat type="date" value={taskResult.added} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{taskResult.reviewed ? 'true' : 'false'}</td>
                    <td>{taskResult.ipAddress}</td>
                    <td>{taskResult.headers}</td>
                    <td>{taskResult.url}</td>
                    <td>{taskResult.content}</td>
                    <td>{taskResult.task ? <Link to={`/task/${taskResult.task.id}`}>{taskResult.task.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/task-result/${taskResult.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/task-result/${taskResult.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/task-result/${taskResult.id}/delete`}
                          color="danger"
                          size="sm"
                          data-cy="entityDeleteButton"
                        >
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
                <Translate contentKey="keyStoneApp.taskResult.home.notFound">No Task Results found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default TaskResult;
