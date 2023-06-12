import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Platform from './platform';
import PlatformDetail from './platform-detail';
import PlatformUpdate from './platform-update';
import PlatformDeleteDialog from './platform-delete-dialog';

const PlatformRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Platform />} />
    <Route path="new" element={<PlatformUpdate />} />
    <Route path=":id">
      <Route index element={<PlatformDetail />} />
      <Route path="edit" element={<PlatformUpdate />} />
      <Route path="delete" element={<PlatformDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PlatformRoutes;
