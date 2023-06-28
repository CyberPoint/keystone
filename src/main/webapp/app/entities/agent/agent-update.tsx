import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPlatform } from 'app/shared/model/platform.model';
import { getEntities as getPlatforms } from 'app/entities/platform/platform.reducer';
import { IRegistrationEvent } from 'app/shared/model/registration-event.model';
import { getEntities as getRegistrationEvents } from 'app/entities/registration-event/registration-event.reducer';
import { IAgent } from 'app/shared/model/agent.model';
import { getEntity, updateEntity, createEntity, reset } from './agent.reducer';

export const AgentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const platforms = useAppSelector(state => state.platform.entities);
  const registrationEvents = useAppSelector(state => state.registrationEvent.entities);
  const agentEntity = useAppSelector(state => state.agent.entity);
  const loading = useAppSelector(state => state.agent.loading);
  const updating = useAppSelector(state => state.agent.updating);
  const updateSuccess = useAppSelector(state => state.agent.updateSuccess);

  const handleClose = () => {
    navigate('/agent' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getPlatforms({}));
    dispatch(getRegistrationEvents({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.installedOn = convertDateTimeToServer(values.installedOn);
    values.uninstallDate = convertDateTimeToServer(values.uninstallDate);

    const entity = {
      ...agentEntity,
      ...values,
      platform: platforms.find(it => it.id.toString() === values.platform.toString()),
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
          installedOn: displayDefaultDateTime(),
          uninstallDate: displayDefaultDateTime(),
        }
      : {
          ...agentEntity,
          installedOn: convertDateTimeFromServer(agentEntity.installedOn),
          uninstallDate: convertDateTimeFromServer(agentEntity.uninstallDate),
          platform: agentEntity?.platform?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="keyStoneApp.agent.home.createOrEditLabel" data-cy="AgentCreateUpdateHeading">
            <Translate contentKey="keyStoneApp.agent.home.createOrEditLabel">Create or edit a Agent</Translate>
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
                  id="agent-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('keyStoneApp.agent.name')}
                id="agent-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('keyStoneApp.agent.classification')}
                id="agent-classification"
                name="classification"
                data-cy="classification"
                type="text"
              />
              <ValidatedField
                label={translate('keyStoneApp.agent.description')}
                id="agent-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('keyStoneApp.agent.installedOn')}
                id="agent-installedOn"
                name="installedOn"
                data-cy="installedOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('keyStoneApp.agent.uninstallDate')}
                id="agent-uninstallDate"
                name="uninstallDate"
                data-cy="uninstallDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('keyStoneApp.agent.active')}
                id="agent-active"
                name="active"
                data-cy="active"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('keyStoneApp.agent.deactivate')}
                id="agent-deactivate"
                name="deactivate"
                data-cy="deactivate"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('keyStoneApp.agent.autoRegistered')}
                id="agent-autoRegistered"
                name="autoRegistered"
                data-cy="autoRegistered"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('keyStoneApp.agent.approved')}
                id="agent-approved"
                name="approved"
                data-cy="approved"
                check
                type="checkbox"
              />
              <ValidatedField
                id="agent-platform"
                name="platform"
                data-cy="platform"
                label={translate('keyStoneApp.agent.platform')}
                type="select"
              >
                <option value="" key="0" />
                {platforms
                  ? platforms.map(otherEntity => (
                      <option value={otherEntity.name} key={otherEntity.name}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/agent" replace color="info">
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

export default AgentUpdate;
