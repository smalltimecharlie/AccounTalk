import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PreviousAccountant from './previous-accountant';
import PreviousAccountantDetail from './previous-accountant-detail';
import PreviousAccountantUpdate from './previous-accountant-update';
import PreviousAccountantDeleteDialog from './previous-accountant-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PreviousAccountantUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PreviousAccountantUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PreviousAccountantDetail} />
      <ErrorBoundaryRoute path={match.url} component={PreviousAccountant} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={PreviousAccountantDeleteDialog} />
  </>
);

export default Routes;
