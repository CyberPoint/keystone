import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './task.reducer';

export const TaskDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const taskEntity = useAppSelector(state => state.task.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="taskDetailsHeading">
          <Translate contentKey="keyStoneApp.task.detail.title">Task</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{taskEntity.id}</dd>
          <dt>
            <span id="command">
              <Translate contentKey="keyStoneApp.task.command">Command</Translate>
            </span>
          </dt>
          <dd>{taskEntity.command}</dd>
          <dt>
            <span id="implantTaskId">
              <Translate contentKey="keyStoneApp.task.implantTaskId">Implant Task Id</Translate>
            </span>
          </dt>
          <dd>{taskEntity.implantTaskId}</dd>
          <dt>
            <span id="submittedBy">
              <Translate contentKey="keyStoneApp.task.submittedBy">Submitted By</Translate>
            </span>
          </dt>
          <dd>{taskEntity.submittedBy}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="keyStoneApp.task.description">Description</Translate>
            </span>
          </dt>
          <dd>{taskEntity.description}</dd>
          <dt>
            <span id="added">
              <Translate contentKey="keyStoneApp.task.added">Added</Translate>
            </span>
          </dt>
          <dd>{taskEntity.added ? <TextFormat value={taskEntity.added} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updated">
              <Translate contentKey="keyStoneApp.task.updated">Updated</Translate>
            </span>
          </dt>
          <dd>{taskEntity.updated ? <TextFormat value={taskEntity.updated} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="retrieved">
              <Translate contentKey="keyStoneApp.task.retrieved">Retrieved</Translate>
            </span>
          </dt>
          <dd>{taskEntity.retrieved ? 'true' : 'false'}</dd>
          <dt>
            <span id="failure">
              <Translate contentKey="keyStoneApp.task.failure">Failure</Translate>
            </span>
          </dt>
          <dd>{taskEntity.failure ? 'true' : 'false'}</dd>
          <dt>
            <span id="approved">
              <Translate contentKey="keyStoneApp.task.approved">Approved</Translate>
            </span>
          </dt>
          <dd>{taskEntity.approved ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="keyStoneApp.task.agent">Agent</Translate>
          </dt>
          <dd>{taskEntity.agent ? taskEntity.agent.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/task" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/task/${taskEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TaskDetail;
