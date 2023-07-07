import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { Row, Col, Alert, Card, CardBody, CardTitle, CardText } from 'reactstrap';

import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <Row>
      <Col md="9">
        <h2>
          <Translate contentKey="home.title">KeyStone</Translate>
        </h2>
        <p className="lead">
          <Translate contentKey="home.subtitle">CyberPoint</Translate>
        </p>

        {account?.login ? (
          <>
            <Card>
              <CardBody>
                <CardTitle tag="h5">Welcome Back!</CardTitle>
                <CardText>You are logged in as {account.login}.</CardText>
              </CardBody>
            </Card>

            <Card>
              <CardBody>
                <CardTitle tag="h5">Your Dashboard</CardTitle>
                <CardText>Here is some information about your account...</CardText>
              </CardBody>
            </Card>
          </>
        ) : (
          <>
            <Card>
              <CardBody>
                <CardTitle tag="h5">About This Website</CardTitle>
                <CardText>This website is designed to provide...</CardText>
              </CardBody>
            </Card>

            <Card>
              <CardBody>
                <CardTitle tag="h5">Features</CardTitle>
                <CardText>Our website offers a range of features including...</CardText>
              </CardBody>
            </Card>

            <Alert color="warning">
              <Translate contentKey="global.messages.info.register.noaccount">You do not have an account yet?</Translate>&nbsp;
              <Link to="/account/register" className="alert-link">
                <Translate contentKey="global.messages.info.register.link">Register a new account</Translate>
              </Link>
            </Alert>
          </>
        )}
      </Col>
    </Row>
  );
};

export default Home;
