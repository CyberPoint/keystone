import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAgent } from 'app/shared/model/agent.model';
import { getEntities as getAgents } from 'app/entities/agent/agent.reducer';
import { ICallBack } from 'app/shared/model/call-back.model';
import { getEntity, updateEntity, createEntity, reset } from './call-back.reducer';

export const CallBackUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const agents = useAppSelector(state => state.agent.entities);
  const callBackEntity = useAppSelector(state => state.callBack.entity);
  const loading = useAppSelector(state => state.callBack.loading);
  const updating = useAppSelector(state => state.callBack.updating);
  const updateSuccess = useAppSelector(state => state.callBack.updateSuccess);

  const handleClose = () => {
    navigate('/call-back');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getAgents({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.timestamp = convertDateTimeToServer(values.timestamp);

    const entity = {
      ...callBackEntity,
      ...values,
      agent: agents.find(it => it.id.toString() === values.agent.toString()),
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
          timestamp: displayDefaultDateTime(),
        }
      : {
          ...callBackEntity,
          timestamp: convertDateTimeFromServer(callBackEntity.timestamp),
          agent: callBackEntity?.agent?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="keyStoneApp.callBack.home.createOrEditLabel" data-cy="CallBackCreateUpdateHeading">
            <Translate contentKey="keyStoneApp.callBack.home.createOrEditLabel">Create or edit a CallBack</Translate>
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
                  id="call-back-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('keyStoneApp.callBack.ipAddress')}
                id="call-back-ipAddress"
                name="ipAddress"
                data-cy="ipAddress"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField label={translate('keyStoneApp.callBack.url')} id="call-back-url" name="url" data-cy="url" type="text" />
              <ValidatedField
                label={translate('keyStoneApp.callBack.remotePort')}
                id="call-back-remotePort"
                name="remotePort"
                data-cy="remotePort"
                type="text"
              />
              <ValidatedField
                label={translate('keyStoneApp.callBack.timestamp')}
                id="call-back-timestamp"
                name="timestamp"
                data-cy="timestamp"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('keyStoneApp.callBack.buffer')}
                id="call-back-buffer"
                name="buffer"
                data-cy="buffer"
                type="text"
              />
              <ValidatedBlobField
                label={translate('keyStoneApp.callBack.rawcontents')}
                id="call-back-rawcontents"
                name="rawcontents"
                data-cy="rawcontents"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedField
                id="call-back-agent"
                name="agent"
                data-cy="agent"
                label={translate('keyStoneApp.callBack.agent')}
                type="select"
              >
                <option value="" key="0" />
                {agents
                  ? agents.map(otherEntity => (
                      <option value={otherEntity.name} key={otherEntity.name}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/call-back" replace color="info">
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

export default CallBackUpdate;
