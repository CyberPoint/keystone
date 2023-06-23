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

import { getEntities, reset } from './call-back.reducer';

export const CallBack = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const callBackList = useAppSelector(state => state.callBack.entities);
  const loading = useAppSelector(state => state.callBack.loading);
  const links = useAppSelector(state => state.callBack.links);
  const updateSuccess = useAppSelector(state => state.callBack.updateSuccess);

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
      <h2 id="call-back-heading" data-cy="CallBackHeading">
        <Translate contentKey="keyStoneApp.callBack.home.title">Call Backs</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="keyStoneApp.callBack.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/call-back/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="keyStoneApp.callBack.home.createLabel">Create new Call Back</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={callBackList ? callBackList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {callBackList && callBackList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="keyStoneApp.callBack.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('ipAddress')}>
                    <Translate contentKey="keyStoneApp.callBack.ipAddress">Ip Address</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('ipAddress')} />
                  </th>
                  <th className="hand" onClick={sort('url')}>
                    <Translate contentKey="keyStoneApp.callBack.url">Url</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('url')} />
                  </th>
                  <th className="hand" onClick={sort('remotePort')}>
                    <Translate contentKey="keyStoneApp.callBack.remotePort">Remote Port</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('remotePort')} />
                  </th>
                  <th className="hand" onClick={sort('timestamp')}>
                    <Translate contentKey="keyStoneApp.callBack.timestamp">Timestamp</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('timestamp')} />
                  </th>
                  <th className="hand" onClick={sort('buffer')}>
                    <Translate contentKey="keyStoneApp.callBack.buffer">Buffer</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('buffer')} />
                  </th>
                  <th className="hand" onClick={sort('rawcontents')}>
                    <Translate contentKey="keyStoneApp.callBack.rawcontents">Rawcontents</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('rawcontents')} />
                  </th>
                  <th>
                    <Translate contentKey="keyStoneApp.callBack.agent">Agent</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {callBackList.map((callBack, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/call-back/${callBack.id}`} color="link" size="sm">
                        {callBack.id}
                      </Button>
                    </td>
                    <td>{callBack.ipAddress}</td>
                    <td>{callBack.url}</td>
                    <td>{callBack.remotePort}</td>
                    <td>{callBack.timestamp ? <TextFormat type="date" value={callBack.timestamp} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{callBack.buffer}</td>
                    <td>
                      {callBack.rawcontents ? (
                        <div>
                          {callBack.rawcontentsContentType ? (
                            <a onClick={openFile(callBack.rawcontentsContentType, callBack.rawcontents)}>
                              <Translate contentKey="entity.action.open">Open</Translate>
                              &nbsp;
                            </a>
                          ) : null}
                          <span>
                            {callBack.rawcontentsContentType}, {byteSize(callBack.rawcontents)}
                          </span>
                        </div>
                      ) : null}
                    </td>
                    <td>{callBack.agent ? <Link to={`/agent/${callBack.agent.id}`}>{callBack.agent.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/call-back/${callBack.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/call-back/${callBack.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/call-back/${callBack.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
                <Translate contentKey="keyStoneApp.callBack.home.notFound">No Call Backs found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default CallBack;
