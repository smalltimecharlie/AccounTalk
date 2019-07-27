import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import GiftAidDonations from './gift-aid-donations';
import GiftAidDonationsDetail from './gift-aid-donations-detail';
import GiftAidDonationsUpdate from './gift-aid-donations-update';
import GiftAidDonationsDeleteDialog from './gift-aid-donations-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GiftAidDonationsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GiftAidDonationsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GiftAidDonationsDetail} />
      <ErrorBoundaryRoute path={match.url} component={GiftAidDonations} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={GiftAidDonationsDeleteDialog} />
  </>
);

export default Routes;
