import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CallBack from './call-back';
import CallBackDetail from './call-back-detail';
import CallBackUpdate from './call-back-update';
import CallBackDeleteDialog from './call-back-delete-dialog';

const CallBackRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CallBack />} />
    <Route path="new" element={<CallBackUpdate />} />
    <Route path=":id">
      <Route index element={<CallBackDetail />} />
      <Route path="edit" element={<CallBackUpdate />} />
      <Route path="delete" element={<CallBackDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CallBackRoutes;
