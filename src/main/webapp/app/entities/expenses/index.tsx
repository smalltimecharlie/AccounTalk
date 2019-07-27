import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Expenses from './expenses';
import ExpensesDetail from './expenses-detail';
import ExpensesUpdate from './expenses-update';
import ExpensesDeleteDialog from './expenses-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ExpensesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ExpensesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ExpensesDetail} />
      <ErrorBoundaryRoute path={match.url} component={Expenses} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ExpensesDeleteDialog} />
  </>
);

export default Routes;
