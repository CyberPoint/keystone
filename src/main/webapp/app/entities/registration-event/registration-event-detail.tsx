import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './registration-event.reducer';

export const RegistrationEventDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const registrationEventEntity = useAppSelector(state => state.registrationEvent.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="registrationEventDetailsHeading">
          <Translate contentKey="keyStoneApp.registrationEvent.detail.title">RegistrationEvent</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{registrationEventEntity.id}</dd>
          <dt>
            <span id="ipAddress">
              <Translate contentKey="keyStoneApp.registrationEvent.ipAddress">Ip Address</Translate>
            </span>
          </dt>
          <dd>{registrationEventEntity.ipAddress}</dd>
          <dt>
            <span id="rawContents">
              <Translate contentKey="keyStoneApp.registrationEvent.rawContents">Raw Contents</Translate>
            </span>
          </dt>
          <dd>{registrationEventEntity.rawContents}</dd>
          <dt>
            <span id="remotePort">
              <Translate contentKey="keyStoneApp.registrationEvent.remotePort">Remote Port</Translate>
            </span>
          </dt>
          <dd>{registrationEventEntity.remotePort}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="keyStoneApp.registrationEvent.name">Name</Translate>
            </span>
          </dt>
          <dd>{registrationEventEntity.name}</dd>
          <dt>
            <span id="approved">
              <Translate contentKey="keyStoneApp.registrationEvent.approved">Approved</Translate>
            </span>
          </dt>
          <dd>{registrationEventEntity.approved ? 'true' : 'false'}</dd>
          <dt>
            <span id="registrationDate">
              <Translate contentKey="keyStoneApp.registrationEvent.registrationDate">Registration Date</Translate>
            </span>
          </dt>
          <dd>
            {registrationEventEntity.registrationDate ? (
              <TextFormat value={registrationEventEntity.registrationDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="keyStoneApp.registrationEvent.agent">Agent</Translate>
          </dt>
          <dd>{registrationEventEntity.agent ? registrationEventEntity.agent.id : ''}</dd>
          <dt>
            <Translate contentKey="keyStoneApp.registrationEvent.secret">Secret</Translate>
          </dt>
          <dd>{registrationEventEntity.secret ? registrationEventEntity.secret.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/registration-event" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/registration-event/${registrationEventEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RegistrationEventDetail;
