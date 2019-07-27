import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BusinessProfit from './business-profit';
import BusinessProfitDetail from './business-profit-detail';
import BusinessProfitUpdate from './business-profit-update';
import BusinessProfitDeleteDialog from './business-profit-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BusinessProfitUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BusinessProfitUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BusinessProfitDetail} />
      <ErrorBoundaryRoute path={match.url} component={BusinessProfit} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={BusinessProfitDeleteDialog} />
  </>
);

export default Routes;
