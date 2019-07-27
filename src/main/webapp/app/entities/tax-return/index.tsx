import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TaxReturn from './tax-return';
import TaxReturnDetail from './tax-return-detail';
import TaxReturnUpdate from './tax-return-update';
import TaxReturnDeleteDialog from './tax-return-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TaxReturnUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TaxReturnUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TaxReturnDetail} />
      <ErrorBoundaryRoute path={match.url} component={TaxReturn} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={TaxReturnDeleteDialog} />
  </>
);

export default Routes;
