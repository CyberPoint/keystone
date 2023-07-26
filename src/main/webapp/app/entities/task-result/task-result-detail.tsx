import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './task-result.reducer';

export const TaskResultDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const taskResultEntity = useAppSelector(state => state.taskResult.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="taskResultDetailsHeading">
          <Translate contentKey="keyStoneApp.taskResult.detail.title">TaskResult</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{taskResultEntity.id}</dd>
          <dt>
            <span id="embeddeddata">
              <Translate contentKey="keyStoneApp.taskResult.embeddeddata">Embeddeddata</Translate>
            </span>
            <UncontrolledTooltip target="embeddeddata">
              <Translate contentKey="keyStoneApp.taskResult.help.embeddeddata" />
            </UncontrolledTooltip>
          </dt>
          <dd>
            {taskResultEntity.embeddeddata ? (
              <div>
                {taskResultEntity.embeddeddataContentType ? (
                  <a onClick={openFile(taskResultEntity.embeddeddataContentType, taskResultEntity.embeddeddata)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {taskResultEntity.embeddeddataContentType}, {byteSize(taskResultEntity.embeddeddata)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="added">
              <Translate contentKey="keyStoneApp.taskResult.added">Added</Translate>
            </span>
          </dt>
          <dd>{taskResultEntity.added ? <TextFormat value={taskResultEntity.added} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="reviewed">
              <Translate contentKey="keyStoneApp.taskResult.reviewed">Reviewed</Translate>
            </span>
          </dt>
          <dd>{taskResultEntity.reviewed ? 'true' : 'false'}</dd>
          <dt>
            <span id="ipAddress">
              <Translate contentKey="keyStoneApp.taskResult.ipAddress">Ip Address</Translate>
            </span>
          </dt>
          <dd>{taskResultEntity.ipAddress}</dd>
          <dt>
            <span id="headers">
              <Translate contentKey="keyStoneApp.taskResult.headers">Headers</Translate>
            </span>
          </dt>
          <dd>{taskResultEntity.headers}</dd>
          <dt>
            <span id="url">
              <Translate contentKey="keyStoneApp.taskResult.url">Url</Translate>
            </span>
          </dt>
          <dd>{taskResultEntity.url}</dd>
          <dt>
            <span id="content">
              <Translate contentKey="keyStoneApp.taskResult.content">Content</Translate>
            </span>
          </dt>
          <dd>{taskResultEntity.content}</dd>
          <dt>
            <Translate contentKey="keyStoneApp.taskResult.task">Task</Translate>
          </dt>
          <dd>{taskResultEntity.task ? taskResultEntity.task.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/task-result" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/task-result/${taskResultEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TaskResultDetail;
