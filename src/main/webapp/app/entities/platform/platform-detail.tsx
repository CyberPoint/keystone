import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './platform.reducer';

export const PlatformDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const platformEntity = useAppSelector(state => state.platform.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="platformDetailsHeading">
          <Translate contentKey="keyStoneApp.platform.detail.title">Platform</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{platformEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="keyStoneApp.platform.name">Name</Translate>
            </span>
          </dt>
          <dd>{platformEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="keyStoneApp.platform.description">Description</Translate>
            </span>
          </dt>
          <dd>{platformEntity.description}</dd>
          <dt>
            <span id="accessLevel">
              <Translate contentKey="keyStoneApp.platform.accessLevel">Access Level</Translate>
            </span>
          </dt>
          <dd>{platformEntity.accessLevel}</dd>
          <dt>
            <span id="version">
              <Translate contentKey="keyStoneApp.platform.version">Version</Translate>
            </span>
          </dt>
          <dd>{platformEntity.version}</dd>
          <dt>
            <span id="os">
              <Translate contentKey="keyStoneApp.platform.os">Os</Translate>
            </span>
          </dt>
          <dd>
            {platformEntity.os ? (
              <div>
                {platformEntity.osContentType ? (
                  <a onClick={openFile(platformEntity.osContentType, platformEntity.os)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {platformEntity.osContentType}, {byteSize(platformEntity.os)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="added">
              <Translate contentKey="keyStoneApp.platform.added">Added</Translate>
            </span>
          </dt>
          <dd>{platformEntity.added ? <TextFormat value={platformEntity.added} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updated">
              <Translate contentKey="keyStoneApp.platform.updated">Updated</Translate>
            </span>
          </dt>
          <dd>{platformEntity.updated ? <TextFormat value={platformEntity.updated} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="active">
              <Translate contentKey="keyStoneApp.platform.active">Active</Translate>
            </span>
          </dt>
          <dd>{platformEntity.active ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/platform" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/platform/${platformEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PlatformDetail;
