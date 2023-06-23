import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAgent } from 'app/shared/model/agent.model';
import { getEntities as getAgents } from 'app/entities/agent/agent.reducer';
import { IRegistrationSecret } from 'app/shared/model/registration-secret.model';
import { getEntities as getRegistrationSecrets } from 'app/entities/registration-secret/registration-secret.reducer';
import { IRegistrationEvent } from 'app/shared/model/registration-event.model';
import { getEntity, updateEntity, createEntity, reset } from './registration-event.reducer';

export const RegistrationEventUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const agents = useAppSelector(state => state.agent.entities);
  const registrationSecrets = useAppSelector(state => state.registrationSecret.entities);
  const registrationEventEntity = useAppSelector(state => state.registrationEvent.entity);
  const loading = useAppSelector(state => state.registrationEvent.loading);
  const updating = useAppSelector(state => state.registrationEvent.updating);
  const updateSuccess = useAppSelector(state => state.registrationEvent.updateSuccess);

  const handleClose = () => {
    navigate('/registration-event');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAgents({}));
    dispatch(getRegistrationSecrets({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.registrationDate = convertDateTimeToServer(values.registrationDate);

    const entity = {
      ...registrationEventEntity,
      ...values,
      agent: agents.find(it => it.id.toString() === values.agent.toString()),
      secret: registrationSecrets.find(it => it.id.toString() === values.secret.toString()),
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
          registrationDate: displayDefaultDateTime(),
        }
      : {
          ...registrationEventEntity,
          registrationDate: convertDateTimeFromServer(registrationEventEntity.registrationDate),
          agent: registrationEventEntity?.agent?.id,
          secret: registrationEventEntity?.secret?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="keyStoneApp.registrationEvent.home.createOrEditLabel" data-cy="RegistrationEventCreateUpdateHeading">
            <Translate contentKey="keyStoneApp.registrationEvent.home.createOrEditLabel">Create or edit a RegistrationEvent</Translate>
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
                  id="registration-event-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('keyStoneApp.registrationEvent.ipAddress')}
                id="registration-event-ipAddress"
                name="ipAddress"
                data-cy="ipAddress"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('keyStoneApp.registrationEvent.rawContents')}
                id="registration-event-rawContents"
                name="rawContents"
                data-cy="rawContents"
                type="text"
              />
              <ValidatedField
                label={translate('keyStoneApp.registrationEvent.remotePort')}
                id="registration-event-remotePort"
                name="remotePort"
                data-cy="remotePort"
                type="text"
              />
              <ValidatedField
                label={translate('keyStoneApp.registrationEvent.name')}
                id="registration-event-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('keyStoneApp.registrationEvent.approved')}
                id="registration-event-approved"
                name="approved"
                data-cy="approved"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('keyStoneApp.registrationEvent.registrationDate')}
                id="registration-event-registrationDate"
                name="registrationDate"
                data-cy="registrationDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="registration-event-agent"
                name="agent"
                data-cy="agent"
                label={translate('keyStoneApp.registrationEvent.agent')}
                type="select"
              >
                <option value="" key="0" />
                {agents
                  ? agents.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="registration-event-secret"
                name="secret"
                data-cy="secret"
                label={translate('keyStoneApp.registrationEvent.secret')}
                type="select"
              >
                <option value="" key="0" />
                {registrationSecrets
                  ? registrationSecrets.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/registration-event" replace color="info">
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

export default RegistrationEventUpdate;
