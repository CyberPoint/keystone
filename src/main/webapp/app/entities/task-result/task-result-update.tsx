import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText, UncontrolledTooltip } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITask } from 'app/shared/model/task.model';
import { getEntities as getTasks } from 'app/entities/task/task.reducer';
import { ITaskResult } from 'app/shared/model/task-result.model';
import { getEntity, updateEntity, createEntity, reset } from './task-result.reducer';

export const TaskResultUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tasks = useAppSelector(state => state.task.entities);
  const taskResultEntity = useAppSelector(state => state.taskResult.entity);
  const loading = useAppSelector(state => state.taskResult.loading);
  const updating = useAppSelector(state => state.taskResult.updating);
  const updateSuccess = useAppSelector(state => state.taskResult.updateSuccess);

  const handleClose = () => {
    navigate('/task-result');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getTasks({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.added = convertDateTimeToServer(values.added);

    const entity = {
      ...taskResultEntity,
      ...values,
      task: tasks.find(it => it.id.toString() === values.task.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          added: displayDefaultDateTime(),
        }
      : {
          ...taskResultEntity,
          added: convertDateTimeFromServer(taskResultEntity.added),
          task: taskResultEntity?.task?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="keyStoneApp.taskResult.home.createOrEditLabel" data-cy="TaskResultCreateUpdateHeading">
            <Translate contentKey="keyStoneApp.taskResult.home.createOrEditLabel">Create or edit a TaskResult</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="task-result-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedBlobField
                label={translate('keyStoneApp.taskResult.embeddeddata')}
                id="task-result-embeddeddata"
                name="embeddeddata"
                data-cy="embeddeddata"
                openActionLabel={translate('entity.action.open')}
              />
              <UncontrolledTooltip target="embeddeddataLabel">
                <Translate contentKey="keyStoneApp.taskResult.help.embeddeddata" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('keyStoneApp.taskResult.added')}
                id="task-result-added"
                name="added"
                data-cy="added"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('keyStoneApp.taskResult.reviewed')}
                id="task-result-reviewed"
                name="reviewed"
                data-cy="reviewed"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('keyStoneApp.taskResult.ipAddress')}
                id="task-result-ipAddress"
                name="ipAddress"
                data-cy="ipAddress"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('keyStoneApp.taskResult.headers')}
                id="task-result-headers"
                name="headers"
                data-cy="headers"
                type="text"
              />
              <ValidatedField label={translate('keyStoneApp.taskResult.url')} id="task-result-url" name="url" data-cy="url" type="text" />
              <ValidatedField
                label={translate('keyStoneApp.taskResult.content')}
                id="task-result-content"
                name="content"
                data-cy="content"
                type="text"
              />
              <ValidatedField
                id="task-result-task"
                name="task"
                data-cy="task"
                label={translate('keyStoneApp.taskResult.task')}
                type="select"
              >
                <option value="" key="0" />
                {tasks
                  ? tasks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/task-result" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default TaskResultUpdate;
