import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Task from './task';
import RegistrationEvent from './registration-event';
import Agent from './agent';
import CallBack from './call-back';
import Platform from './platform';
import TaskResult from './task-result';
import RegistrationSecret from './registration-secret';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="task/*" element={<Task />} />
        <Route path="registration-event/*" element={<RegistrationEvent />} />
        <Route path="agent/*" element={<Agent />} />
        <Route path="call-back/*" element={<CallBack />} />
        <Route path="platform/*" element={<Platform />} />
        <Route path="task-result/*" element={<TaskResult />} />
        <Route path="registration-secret/*" element={<RegistrationSecret />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
