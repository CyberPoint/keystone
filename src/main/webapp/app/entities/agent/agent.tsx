import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './agent.reducer';

export const Agent = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const agentList = useAppSelector(state => state.agent.entities);
  const loading = useAppSelector(state => state.agent.loading);
  const totalItems = useAppSelector(state => state.agent.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (location.search !== endURL) {
      navigate(`${location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
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
      <h2 id="agent-heading" data-cy="AgentHeading">
        <Translate contentKey="keyStoneApp.agent.home.title">Agents</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="keyStoneApp.agent.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/agent/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="keyStoneApp.agent.home.createLabel">Create new Agent</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {agentList && agentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="keyStoneApp.agent.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="keyStoneApp.agent.name">Name</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('name')} />
                </th>
                <th className="hand" onClick={sort('classification')}>
                  <Translate contentKey="keyStoneApp.agent.classification">Classification</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('classification')} />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="keyStoneApp.agent.description">Description</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('description')} />
                </th>
                <th className="hand" onClick={sort('installedOn')}>
                  <Translate contentKey="keyStoneApp.agent.installedOn">Installed On</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('installedOn')} />
                </th>
                <th className="hand" onClick={sort('uninstallDate')}>
                  <Translate contentKey="keyStoneApp.agent.uninstallDate">Uninstall Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('uninstallDate')} />
                </th>
                <th className="hand" onClick={sort('active')}>
                  <Translate contentKey="keyStoneApp.agent.active">Active</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('active')} />
                </th>
                <th className="hand" onClick={sort('deactivate')}>
                  <Translate contentKey="keyStoneApp.agent.deactivate">Deactivate</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('deactivate')} />
                </th>
                <th className="hand" onClick={sort('autoRegistered')}>
                  <Translate contentKey="keyStoneApp.agent.autoRegistered">Auto Registered</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('autoRegistered')} />
                </th>
                <th className="hand" onClick={sort('approved')}>
                  <Translate contentKey="keyStoneApp.agent.approved">Approved</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('approved')} />
                </th>
                <th>
                  <Translate contentKey="keyStoneApp.agent.platform">Platform</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {agentList.map((agent, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/agent/${agent.id}`} color="link" size="sm">
                      {agent.id}
                    </Button>
                  </td>
                  <td>{agent.name}</td>
                  <td>{agent.classification}</td>
                  <td>{agent.description}</td>
                  <td>{agent.installedOn ? <TextFormat type="date" value={agent.installedOn} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{agent.uninstallDate ? <TextFormat type="date" value={agent.uninstallDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{agent.active ? 'true' : 'false'}</td>
                  <td>{agent.deactivate ? 'true' : 'false'}</td>
                  <td>{agent.autoRegistered ? 'true' : 'false'}</td>
                  <td>{agent.approved ? 'true' : 'false'}</td>
                  <td>{agent.platform ? <Link to={`/platform/${agent.platform.id}`}>{agent.platform.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/agent/${agent.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/agent/${agent.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/agent/${agent.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="keyStoneApp.agent.home.notFound">No Agents found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={agentList && agentList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default Agent;
