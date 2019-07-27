import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EmpPensionContributions from './emp-pension-contributions';
import EmpPensionContributionsDetail from './emp-pension-contributions-detail';
import EmpPensionContributionsUpdate from './emp-pension-contributions-update';
import EmpPensionContributionsDeleteDialog from './emp-pension-contributions-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EmpPensionContributionsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EmpPensionContributionsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EmpPensionContributionsDetail} />
      <ErrorBoundaryRoute path={match.url} component={EmpPensionContributions} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={EmpPensionContributionsDeleteDialog} />
  </>
);

export default Routes;
