import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import RegistrationSecret from './registration-secret';
import RegistrationSecretDetail from './registration-secret-detail';
import RegistrationSecretUpdate from './registration-secret-update';
import RegistrationSecretDeleteDialog from './registration-secret-delete-dialog';

const RegistrationSecretRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<RegistrationSecret />} />
    <Route path="new" element={<RegistrationSecretUpdate />} />
    <Route path=":id">
      <Route index element={<RegistrationSecretDetail />} />
      <Route path="edit" element={<RegistrationSecretUpdate />} />
      <Route path="delete" element={<RegistrationSecretDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RegistrationSecretRoutes;
