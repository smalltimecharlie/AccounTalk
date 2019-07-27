import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PerPensionContributions from './per-pension-contributions';
import PerPensionContributionsDetail from './per-pension-contributions-detail';
import PerPensionContributionsUpdate from './per-pension-contributions-update';
import PerPensionContributionsDeleteDialog from './per-pension-contributions-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PerPensionContributionsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PerPensionContributionsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PerPensionContributionsDetail} />
      <ErrorBoundaryRoute path={match.url} component={PerPensionContributions} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={PerPensionContributionsDeleteDialog} />
  </>
);

export default Routes;
