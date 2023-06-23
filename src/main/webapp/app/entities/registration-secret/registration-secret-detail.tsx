import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './registration-secret.reducer';

export const RegistrationSecretDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const registrationSecretEntity = useAppSelector(state => state.registrationSecret.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="registrationSecretDetailsHeading">
          <Translate contentKey="keyStoneApp.registrationSecret.detail.title">RegistrationSecret</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{registrationSecretEntity.id}</dd>
          <dt>
            <span id="uniqueValue">
              <Translate contentKey="keyStoneApp.registrationSecret.uniqueValue">Unique Value</Translate>
            </span>
          </dt>
          <dd>{registrationSecretEntity.uniqueValue}</dd>
          <dt>
            <span id="numericalValue">
              <Translate contentKey="keyStoneApp.registrationSecret.numericalValue">Numerical Value</Translate>
            </span>
          </dt>
          <dd>{registrationSecretEntity.numericalValue}</dd>
        </dl>
        <Button tag={Link} to="/registration-secret" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/registration-secret/${registrationSecretEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RegistrationSecretDetail;
