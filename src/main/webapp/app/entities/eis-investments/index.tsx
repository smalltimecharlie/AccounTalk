import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EisInvestments from './eis-investments';
import EisInvestmentsDetail from './eis-investments-detail';
import EisInvestmentsUpdate from './eis-investments-update';
import EisInvestmentsDeleteDialog from './eis-investments-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EisInvestmentsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EisInvestmentsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EisInvestmentsDetail} />
      <ErrorBoundaryRoute path={match.url} component={EisInvestments} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={EisInvestmentsDeleteDialog} />
  </>
);

export default Routes;
