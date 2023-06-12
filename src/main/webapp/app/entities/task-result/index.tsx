import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TaskResult from './task-result';
import TaskResultDetail from './task-result-detail';
import TaskResultUpdate from './task-result-update';
import TaskResultDeleteDialog from './task-result-delete-dialog';

const TaskResultRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TaskResult />} />
    <Route path="new" element={<TaskResultUpdate />} />
    <Route path=":id">
      <Route index element={<TaskResultDetail />} />
      <Route path="edit" element={<TaskResultUpdate />} />
      <Route path="delete" element={<TaskResultDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TaskResultRoutes;
