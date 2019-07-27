import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PensionReceived from './pension-received';
import PensionReceivedDetail from './pension-received-detail';
import PensionReceivedUpdate from './pension-received-update';
import PensionReceivedDeleteDialog from './pension-received-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PensionReceivedUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PensionReceivedUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PensionReceivedDetail} />
      <ErrorBoundaryRoute path={match.url} component={PensionReceived} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={PensionReceivedDeleteDialog} />
  </>
);

export default Routes;
