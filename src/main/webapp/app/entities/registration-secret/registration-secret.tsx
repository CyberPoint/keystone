import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './registration-secret.reducer';

export const RegistrationSecret = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(location, 'id'), location.search));

  const registrationSecretList = useAppSelector(state => state.registrationSecret.entities);
  const loading = useAppSelector(state => state.registrationSecret.loading);

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
      <h2 id="registration-secret-heading" data-cy="RegistrationSecretHeading">
        <Translate contentKey="keyStoneApp.registrationSecret.home.title">Registration Secrets</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="keyStoneApp.registrationSecret.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/registration-secret/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="keyStoneApp.registrationSecret.home.createLabel">Create new Registration Secret</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {registrationSecretList && registrationSecretList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="keyStoneApp.registrationSecret.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('uniqueValue')}>
                  <Translate contentKey="keyStoneApp.registrationSecret.uniqueValue">Unique Value</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('uniqueValue')} />
                </th>
                <th className="hand" onClick={sort('numericalValue')}>
                  <Translate contentKey="keyStoneApp.registrationSecret.numericalValue">Numerical Value</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('numericalValue')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {registrationSecretList.map((registrationSecret, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/registration-secret/${registrationSecret.id}`} color="link" size="sm">
                      {registrationSecret.id}
                    </Button>
                  </td>
                  <td>{registrationSecret.uniqueValue}</td>
                  <td>{registrationSecret.numericalValue}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/registration-secret/${registrationSecret.id}`}
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
                        to={`/registration-secret/${registrationSecret.id}/edit`}
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
                        to={`/registration-secret/${registrationSecret.id}/delete`}
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
              <Translate contentKey="keyStoneApp.registrationSecret.home.notFound">No Registration Secrets found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default RegistrationSecret;
