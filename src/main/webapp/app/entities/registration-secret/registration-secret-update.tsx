import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRegistrationEvent } from 'app/shared/model/registration-event.model';
import { getEntities as getRegistrationEvents } from 'app/entities/registration-event/registration-event.reducer';
import { IRegistrationSecret } from 'app/shared/model/registration-secret.model';
import { getEntity, updateEntity, createEntity, reset } from './registration-secret.reducer';

export const RegistrationSecretUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const registrationEvents = useAppSelector(state => state.registrationEvent.entities);
  const registrationSecretEntity = useAppSelector(state => state.registrationSecret.entity);
  const loading = useAppSelector(state => state.registrationSecret.loading);
  const updating = useAppSelector(state => state.registrationSecret.updating);
  const updateSuccess = useAppSelector(state => state.registrationSecret.updateSuccess);

  const handleClose = () => {
    navigate('/registration-secret');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getRegistrationEvents({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...registrationSecretEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...registrationSecretEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="keyStoneApp.registrationSecret.home.createOrEditLabel" data-cy="RegistrationSecretCreateUpdateHeading">
            <Translate contentKey="keyStoneApp.registrationSecret.home.createOrEditLabel">Create or edit a RegistrationSecret</Translate>
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
                  id="registration-secret-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('keyStoneApp.registrationSecret.uniqueValue')}
                id="registration-secret-uniqueValue"
                name="uniqueValue"
                data-cy="uniqueValue"
                type="text"
              />
              <ValidatedField
                label={translate('keyStoneApp.registrationSecret.numericalValue')}
                id="registration-secret-numericalValue"
                name="numericalValue"
                data-cy="numericalValue"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/registration-secret" replace color="info">
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

export default RegistrationSecretUpdate;
