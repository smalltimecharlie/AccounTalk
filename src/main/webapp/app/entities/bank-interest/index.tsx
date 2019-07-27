import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BankInterest from './bank-interest';
import BankInterestDetail from './bank-interest-detail';
import BankInterestUpdate from './bank-interest-update';
import BankInterestDeleteDialog from './bank-interest-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BankInterestUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BankInterestUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BankInterestDetail} />
      <ErrorBoundaryRoute path={match.url} component={BankInterest} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={BankInterestDeleteDialog} />
  </>
);

export default Routes;
