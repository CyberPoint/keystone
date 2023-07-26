import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPlatform } from 'app/shared/model/platform.model';
import { getEntity, updateEntity, createEntity, reset } from './platform.reducer';

export const PlatformUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const platformEntity = useAppSelector(state => state.platform.entity);
  const loading = useAppSelector(state => state.platform.loading);
  const updating = useAppSelector(state => state.platform.updating);
  const updateSuccess = useAppSelector(state => state.platform.updateSuccess);

  const handleClose = () => {
    navigate('/platform' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.added = convertDateTimeToServer(values.added);
    values.updated = convertDateTimeToServer(values.updated);

    const entity = {
      ...platformEntity,
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
      ? {
          added: displayDefaultDateTime(),
          updated: displayDefaultDateTime(),
        }
      : {
          ...platformEntity,
          added: convertDateTimeFromServer(platformEntity.added),
          updated: convertDateTimeFromServer(platformEntity.updated),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="keyStoneApp.platform.home.createOrEditLabel" data-cy="PlatformCreateUpdateHeading">
            <Translate contentKey="keyStoneApp.platform.home.createOrEditLabel">Create or edit a Platform</Translate>
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
                  id="platform-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('keyStoneApp.platform.name')}
                id="platform-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('keyStoneApp.platform.description')}
                id="platform-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('keyStoneApp.platform.accessLevel')}
                id="platform-accessLevel"
                name="accessLevel"
                data-cy="accessLevel"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 1, message: translate('entity.validation.min', { min: 1 }) },
                  max: { value: 10, message: translate('entity.validation.max', { max: 10 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('keyStoneApp.platform.version')}
                id="platform-version"
                name="version"
                data-cy="version"
                type="text"
              />
              <ValidatedBlobField
                label={translate('keyStoneApp.platform.os')}
                id="platform-os"
                name="os"
                data-cy="os"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedField
                label={translate('keyStoneApp.platform.added')}
                id="platform-added"
                name="added"
                data-cy="added"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('keyStoneApp.platform.updated')}
                id="platform-updated"
                name="updated"
                data-cy="updated"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('keyStoneApp.platform.active')}
                id="platform-active"
                name="active"
                data-cy="active"
                check
                type="checkbox"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/platform" replace color="info">
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

export default PlatformUpdate;
