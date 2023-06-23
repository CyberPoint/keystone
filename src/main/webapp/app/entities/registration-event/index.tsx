import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import RegistrationEvent from './registration-event';
import RegistrationEventDetail from './registration-event-detail';
import RegistrationEventUpdate from './registration-event-update';
import RegistrationEventDeleteDialog from './registration-event-delete-dialog';

const RegistrationEventRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<RegistrationEvent />} />
    <Route path="new" element={<RegistrationEventUpdate />} />
    <Route path=":id">
      <Route index element={<RegistrationEventDetail />} />
      <Route path="edit" element={<RegistrationEventUpdate />} />
      <Route path="delete" element={<RegistrationEventDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RegistrationEventRoutes;
