import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PensionProvider from './pension-provider';
import PensionProviderDetail from './pension-provider-detail';
import PensionProviderUpdate from './pension-provider-update';
import PensionProviderDeleteDialog from './pension-provider-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PensionProviderUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PensionProviderUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PensionProviderDetail} />
      <ErrorBoundaryRoute path={match.url} component={PensionProvider} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={PensionProviderDeleteDialog} />
  </>
);

export default Routes;
