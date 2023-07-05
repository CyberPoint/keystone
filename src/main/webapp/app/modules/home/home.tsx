import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { Row, Col, Alert, Card, CardBody, CardTitle, CardText, Button } from 'reactstrap';

import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <Row>
      <Col md="9">
        <h2>
          <Translate contentKey="home.title">KeyStone</Translate>
        </h2>


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

        {account?.login ? (
          <Alert color="success">
            <Translate contentKey="home.logged.message" interpolate={{ username: account.login }}>
              You are logged in as user {account.login}.
            </Translate>
          </Alert>
        ) : (
          <Card>
            <CardBody>
              <CardTitle tag="h5">Existing User? Sign In Here</CardTitle>
              <Button tag={Link} to="/login" color="primary">Sign In</Button>
            </CardBody>
          </Card>
        )}

        {!account?.login && (
          <Alert color="warning">
            <Translate contentKey="global.messages.info.register.noaccount">You do not have an account yet?</Translate>&nbsp;
            <Link to="/account/register" className="alert-link">
              <Translate contentKey="global.messages.info.register.link">Register a new account</Translate>
            </Link>
          </Alert>
        )}
      </Col>
    </Row>
  );
};

export default Home;
