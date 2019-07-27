import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Business from './business';
import BusinessDetail from './business-detail';
import BusinessUpdate from './business-update';
import BusinessDeleteDialog from './business-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BusinessUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BusinessUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BusinessDetail} />
      <ErrorBoundaryRoute path={match.url} component={Business} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={BusinessDeleteDialog} />
  </>
);

export default Routes;
