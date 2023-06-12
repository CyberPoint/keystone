import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './call-back.reducer';

export const CallBackDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const callBackEntity = useAppSelector(state => state.callBack.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="callBackDetailsHeading">
          <Translate contentKey="keyStoneApp.callBack.detail.title">CallBack</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{callBackEntity.id}</dd>
          <dt>
            <span id="ipAddress">
              <Translate contentKey="keyStoneApp.callBack.ipAddress">Ip Address</Translate>
            </span>
          </dt>
          <dd>{callBackEntity.ipAddress}</dd>
          <dt>
            <span id="url">
              <Translate contentKey="keyStoneApp.callBack.url">Url</Translate>
            </span>
          </dt>
          <dd>{callBackEntity.url}</dd>
          <dt>
            <span id="remotePort">
              <Translate contentKey="keyStoneApp.callBack.remotePort">Remote Port</Translate>
            </span>
          </dt>
          <dd>{callBackEntity.remotePort}</dd>
          <dt>
            <span id="timestamp">
              <Translate contentKey="keyStoneApp.callBack.timestamp">Timestamp</Translate>
            </span>
          </dt>
          <dd>{callBackEntity.timestamp ? <TextFormat value={callBackEntity.timestamp} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="buffer">
              <Translate contentKey="keyStoneApp.callBack.buffer">Buffer</Translate>
            </span>
          </dt>
          <dd>{callBackEntity.buffer}</dd>
          <dt>
            <span id="rawcontents">
              <Translate contentKey="keyStoneApp.callBack.rawcontents">Rawcontents</Translate>
            </span>
          </dt>
          <dd>
            {callBackEntity.rawcontents ? (
              <div>
                {callBackEntity.rawcontentsContentType ? (
                  <a onClick={openFile(callBackEntity.rawcontentsContentType, callBackEntity.rawcontents)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {callBackEntity.rawcontentsContentType}, {byteSize(callBackEntity.rawcontents)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="keyStoneApp.callBack.agent">Agent</Translate>
          </dt>
          <dd>{callBackEntity.agent ? callBackEntity.agent.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/call-back" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/call-back/${callBackEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CallBackDetail;
