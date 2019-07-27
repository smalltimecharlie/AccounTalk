import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Shares from './shares';
import SharesDetail from './shares-detail';
import SharesUpdate from './shares-update';
import SharesDeleteDialog from './shares-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SharesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SharesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SharesDetail} />
      <ErrorBoundaryRoute path={match.url} component={Shares} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={SharesDeleteDialog} />
  </>
);

export default Routes;
