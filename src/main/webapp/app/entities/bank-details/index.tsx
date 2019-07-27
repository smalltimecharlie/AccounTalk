import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BankDetails from './bank-details';
import BankDetailsDetail from './bank-details-detail';
import BankDetailsUpdate from './bank-details-update';
import BankDetailsDeleteDialog from './bank-details-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BankDetailsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BankDetailsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BankDetailsDetail} />
      <ErrorBoundaryRoute path={match.url} component={BankDetails} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={BankDetailsDeleteDialog} />
  </>
);

export default Routes;
