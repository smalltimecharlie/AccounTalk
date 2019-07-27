import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import DividendsReceived from './dividends-received';
import DividendsReceivedDetail from './dividends-received-detail';
import DividendsReceivedUpdate from './dividends-received-update';
import DividendsReceivedDeleteDialog from './dividends-received-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DividendsReceivedUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DividendsReceivedUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DividendsReceivedDetail} />
      <ErrorBoundaryRoute path={match.url} component={DividendsReceived} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={DividendsReceivedDeleteDialog} />
  </>
);

export default Routes;
