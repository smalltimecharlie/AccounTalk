import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Client from './client';
import Address from './address';
import Employment from './employment';
import PreviousAccountant from './previous-accountant';
import Business from './business';
import TaxReturn from './tax-return';
import PensionProvider from './pension-provider';
import PensionReceived from './pension-received';
import BankInterest from './bank-interest';
import PerPensionContributions from './per-pension-contributions';
import StatePensionReceived from './state-pension-received';
import GiftAidDonations from './gift-aid-donations';
import BankDetails from './bank-details';
import DividendsReceived from './dividends-received';
import Shares from './shares';
import EmpPensionContributions from './emp-pension-contributions';
import EmploymentDetails from './employment-details';
import BusinessProfit from './business-profit';
import Earnings from './earnings';
import Benefits from './benefits';
import Expenses from './expenses';
import EisInvestments from './eis-investments';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/client`} component={Client} />
      <ErrorBoundaryRoute path={`${match.url}/address`} component={Address} />
      <ErrorBoundaryRoute path={`${match.url}/employment`} component={Employment} />
      <ErrorBoundaryRoute path={`${match.url}/previous-accountant`} component={PreviousAccountant} />
      <ErrorBoundaryRoute path={`${match.url}/business`} component={Business} />
      <ErrorBoundaryRoute path={`${match.url}/tax-return`} component={TaxReturn} />
      <ErrorBoundaryRoute path={`${match.url}/pension-provider`} component={PensionProvider} />
      <ErrorBoundaryRoute path={`${match.url}/pension-received`} component={PensionReceived} />
      <ErrorBoundaryRoute path={`${match.url}/bank-interest`} component={BankInterest} />
      <ErrorBoundaryRoute path={`${match.url}/per-pension-contributions`} component={PerPensionContributions} />
      <ErrorBoundaryRoute path={`${match.url}/state-pension-received`} component={StatePensionReceived} />
      <ErrorBoundaryRoute path={`${match.url}/gift-aid-donations`} component={GiftAidDonations} />
      <ErrorBoundaryRoute path={`${match.url}/bank-details`} component={BankDetails} />
      <ErrorBoundaryRoute path={`${match.url}/dividends-received`} component={DividendsReceived} />
      <ErrorBoundaryRoute path={`${match.url}/shares`} component={Shares} />
      <ErrorBoundaryRoute path={`${match.url}/emp-pension-contributions`} component={EmpPensionContributions} />
      <ErrorBoundaryRoute path={`${match.url}/employment-details`} component={EmploymentDetails} />
      <ErrorBoundaryRoute path={`${match.url}/business-profit`} component={BusinessProfit} />
      <ErrorBoundaryRoute path={`${match.url}/earnings`} component={Earnings} />
      <ErrorBoundaryRoute path={`${match.url}/benefits`} component={Benefits} />
      <ErrorBoundaryRoute path={`${match.url}/expenses`} component={Expenses} />
      <ErrorBoundaryRoute path={`${match.url}/eis-investments`} component={EisInvestments} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
