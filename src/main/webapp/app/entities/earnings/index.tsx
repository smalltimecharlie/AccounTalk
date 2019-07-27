import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Earnings from './earnings';
import EarningsDetail from './earnings-detail';
import EarningsUpdate from './earnings-update';
import EarningsDeleteDialog from './earnings-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EarningsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EarningsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EarningsDetail} />
      <ErrorBoundaryRoute path={match.url} component={Earnings} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={EarningsDeleteDialog} />
  </>
);

export default Routes;
