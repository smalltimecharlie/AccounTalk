import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Benefits from './benefits';
import BenefitsDetail from './benefits-detail';
import BenefitsUpdate from './benefits-update';
import BenefitsDeleteDialog from './benefits-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BenefitsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BenefitsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BenefitsDetail} />
      <ErrorBoundaryRoute path={match.url} component={Benefits} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={BenefitsDeleteDialog} />
  </>
);

export default Routes;
