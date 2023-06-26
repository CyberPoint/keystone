import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './registration-event.reducer';

export const RegistrationEvent = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(location, 'id'), location.search));

  const registrationEventList = useAppSelector(state => state.registrationEvent.entities);
  const loading = useAppSelector(state => state.registrationEvent.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (location.search !== endURL) {
      navigate(`${location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="registration-event-heading" data-cy="RegistrationEventHeading">
        <Translate contentKey="keyStoneApp.registrationEvent.home.title">Registration Events</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="keyStoneApp.registrationEvent.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/registration-event/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="keyStoneApp.registrationEvent.home.createLabel">Create new Registration Event</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {registrationEventList && registrationEventList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="keyStoneApp.registrationEvent.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('ipAddress')}>
                  <Translate contentKey="keyStoneApp.registrationEvent.ipAddress">Ip Address</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('ipAddress')} />
                </th>
                <th className="hand" onClick={sort('rawContents')}>
                  <Translate contentKey="keyStoneApp.registrationEvent.rawContents">Raw Contents</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('rawContents')} />
                </th>
                <th className="hand" onClick={sort('remotePort')}>
                  <Translate contentKey="keyStoneApp.registrationEvent.remotePort">Remote Port</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('remotePort')} />
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="keyStoneApp.registrationEvent.name">Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('name')} />
                </th>
                <th className="hand" onClick={sort('approved')}>
                  <Translate contentKey="keyStoneApp.registrationEvent.approved">Approved</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('approved')} />
                </th>
                <th className="hand" onClick={sort('registrationDate')}>
                  <Translate contentKey="keyStoneApp.registrationEvent.registrationDate">Registration Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('registrationDate')} />
                </th>
                <th>
                  <Translate contentKey="keyStoneApp.registrationEvent.agent">Agent</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="keyStoneApp.registrationEvent.secret">Secret</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {registrationEventList.map((registrationEvent, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/registration-event/${registrationEvent.id}`} color="link" size="sm">
                      {registrationEvent.id}
                    </Button>
                  </td>
                  <td>{registrationEvent.ipAddress}</td>
                  <td>{registrationEvent.rawContents}</td>
                  <td>{registrationEvent.remotePort}</td>
                  <td>{registrationEvent.name}</td>
                  <td>{registrationEvent.approved ? 'true' : 'false'}</td>
                  <td>
                    {registrationEvent.registrationDate ? (
                      <TextFormat type="date" value={registrationEvent.registrationDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {registrationEvent.agent ? <Link to={`/agent/${registrationEvent.agent.id}`}>{registrationEvent.agent.id}</Link> : ''}
                  </td>
                  <td>
                    {registrationEvent.secret ? (
                      <Link to={`/registration-secret/${registrationEvent.secret.id}`}>{registrationEvent.secret.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/registration-event/${registrationEvent.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/registration-event/${registrationEvent.id}/edit`}
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
                        to={`/registration-event/${registrationEvent.id}/delete`}
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
              <Translate contentKey="keyStoneApp.registrationEvent.home.notFound">No Registration Events found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default RegistrationEvent;
