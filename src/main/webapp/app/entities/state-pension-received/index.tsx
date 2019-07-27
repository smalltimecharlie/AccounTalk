import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import StatePensionReceived from './state-pension-received';
import StatePensionReceivedDetail from './state-pension-received-detail';
import StatePensionReceivedUpdate from './state-pension-received-update';
import StatePensionReceivedDeleteDialog from './state-pension-received-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={StatePensionReceivedUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={StatePensionReceivedUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={StatePensionReceivedDetail} />
      <ErrorBoundaryRoute path={match.url} component={StatePensionReceived} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={StatePensionReceivedDeleteDialog} />
  </>
);

export default Routes;
