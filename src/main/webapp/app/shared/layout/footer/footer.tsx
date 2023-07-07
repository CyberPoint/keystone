import './footer.scss';

import React from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';

const Footer = props => (
  <div className="footer page-content">
    <Row>
      <Col md="4">
        <h5>About Us</h5>
        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque auctor lectus nec nunc commodo, at tempus turpis bibendum.</p>
      </Col>
      <Col md="4">
        <h5>Contact Us</h5>
        <p>
          <strong>Email:</strong> info@example.com<br/>
          <strong>Phone:</strong> +1 234 567 8901
        </p>
      </Col>
      <Col md="4">
        <h5>Follow Us</h5>
        <a href="https://www.facebook.com" target="_blank" rel="noopener noreferrer">Facebook</a><br/>
        <a href="https://www.twitter.com" target="_blank" rel="noopener noreferrer">Twitter</a><br/>
        <a href="https://www.instagram.com" target="_blank" rel="noopener noreferrer">Instagram</a>
      </Col>
    </Row>
  </div>
);

export default Footer;