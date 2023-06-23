import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './agent.reducer';

export const AgentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const agentEntity = useAppSelector(state => state.agent.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="agentDetailsHeading">
          <Translate contentKey="keyStoneApp.agent.detail.title">Agent</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{agentEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="keyStoneApp.agent.name">Name</Translate>
            </span>
          </dt>
          <dd>{agentEntity.name}</dd>
          <dt>
            <span id="classification">
              <Translate contentKey="keyStoneApp.agent.classification">Classification</Translate>
            </span>
          </dt>
          <dd>{agentEntity.classification}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="keyStoneApp.agent.description">Description</Translate>
            </span>
          </dt>
          <dd>{agentEntity.description}</dd>
          <dt>
            <span id="installedOn">
              <Translate contentKey="keyStoneApp.agent.installedOn">Installed On</Translate>
            </span>
          </dt>
          <dd>{agentEntity.installedOn ? <TextFormat value={agentEntity.installedOn} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="uninstallDate">
              <Translate contentKey="keyStoneApp.agent.uninstallDate">Uninstall Date</Translate>
            </span>
          </dt>
          <dd>
            {agentEntity.uninstallDate ? <TextFormat value={agentEntity.uninstallDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="active">
              <Translate contentKey="keyStoneApp.agent.active">Active</Translate>
            </span>
          </dt>
          <dd>{agentEntity.active ? 'true' : 'false'}</dd>
          <dt>
            <span id="deactivate">
              <Translate contentKey="keyStoneApp.agent.deactivate">Deactivate</Translate>
            </span>
          </dt>
          <dd>{agentEntity.deactivate ? 'true' : 'false'}</dd>
          <dt>
            <span id="autoRegistered">
              <Translate contentKey="keyStoneApp.agent.autoRegistered">Auto Registered</Translate>
            </span>
          </dt>
          <dd>{agentEntity.autoRegistered ? 'true' : 'false'}</dd>
          <dt>
            <span id="approved">
              <Translate contentKey="keyStoneApp.agent.approved">Approved</Translate>
            </span>
          </dt>
          <dd>{agentEntity.approved ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="keyStoneApp.agent.platform">Platform</Translate>
          </dt>
          <dd>{agentEntity.platform ? agentEntity.platform.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/agent" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/agent/${agentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AgentDetail;
