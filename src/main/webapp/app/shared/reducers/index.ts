import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import client, {
  ClientState
} from 'app/entities/client/client.reducer';
// prettier-ignore
import address, {
  AddressState
} from 'app/entities/address/address.reducer';
// prettier-ignore
import employment, {
  EmploymentState
} from 'app/entities/employment/employment.reducer';
// prettier-ignore
import previousAccountant, {
  PreviousAccountantState
} from 'app/entities/previous-accountant/previous-accountant.reducer';
// prettier-ignore
import business, {
  BusinessState
} from 'app/entities/business/business.reducer';
// prettier-ignore
import taxReturn, {
  TaxReturnState
} from 'app/entities/tax-return/tax-return.reducer';
// prettier-ignore
import pensionProvider, {
  PensionProviderState
} from 'app/entities/pension-provider/pension-provider.reducer';
// prettier-ignore
import pensionReceived, {
  PensionReceivedState
} from 'app/entities/pension-received/pension-received.reducer';
// prettier-ignore
import bankInterest, {
  BankInterestState
} from 'app/entities/bank-interest/bank-interest.reducer';
// prettier-ignore
import perPensionContributions, {
  PerPensionContributionsState
} from 'app/entities/per-pension-contributions/per-pension-contributions.reducer';
// prettier-ignore
import statePensionReceived, {
  StatePensionReceivedState
} from 'app/entities/state-pension-received/state-pension-received.reducer';
// prettier-ignore
import giftAidDonations, {
  GiftAidDonationsState
} from 'app/entities/gift-aid-donations/gift-aid-donations.reducer';
// prettier-ignore
import bankDetails, {
  BankDetailsState
} from 'app/entities/bank-details/bank-details.reducer';
// prettier-ignore
import dividendsReceived, {
  DividendsReceivedState
} from 'app/entities/dividends-received/dividends-received.reducer';
// prettier-ignore
import shares, {
  SharesState
} from 'app/entities/shares/shares.reducer';
// prettier-ignore
import empPensionContributions, {
  EmpPensionContributionsState
} from 'app/entities/emp-pension-contributions/emp-pension-contributions.reducer';
// prettier-ignore
import employmentDetails, {
  EmploymentDetailsState
} from 'app/entities/employment-details/employment-details.reducer';
// prettier-ignore
import businessProfit, {
  BusinessProfitState
} from 'app/entities/business-profit/business-profit.reducer';
// prettier-ignore
import earnings, {
  EarningsState
} from 'app/entities/earnings/earnings.reducer';
// prettier-ignore
import benefits, {
  BenefitsState
} from 'app/entities/benefits/benefits.reducer';
// prettier-ignore
import expenses, {
  ExpensesState
} from 'app/entities/expenses/expenses.reducer';
// prettier-ignore
import eisInvestments, {
  EisInvestmentsState
} from 'app/entities/eis-investments/eis-investments.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly client: ClientState;
  readonly address: AddressState;
  readonly employment: EmploymentState;
  readonly previousAccountant: PreviousAccountantState;
  readonly business: BusinessState;
  readonly taxReturn: TaxReturnState;
  readonly pensionProvider: PensionProviderState;
  readonly pensionReceived: PensionReceivedState;
  readonly bankInterest: BankInterestState;
  readonly perPensionContributions: PerPensionContributionsState;
  readonly statePensionReceived: StatePensionReceivedState;
  readonly giftAidDonations: GiftAidDonationsState;
  readonly bankDetails: BankDetailsState;
  readonly dividendsReceived: DividendsReceivedState;
  readonly shares: SharesState;
  readonly empPensionContributions: EmpPensionContributionsState;
  readonly employmentDetails: EmploymentDetailsState;
  readonly businessProfit: BusinessProfitState;
  readonly earnings: EarningsState;
  readonly benefits: BenefitsState;
  readonly expenses: ExpensesState;
  readonly eisInvestments: EisInvestmentsState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  client,
  address,
  employment,
  previousAccountant,
  business,
  taxReturn,
  pensionProvider,
  pensionReceived,
  bankInterest,
  perPensionContributions,
  statePensionReceived,
  giftAidDonations,
  bankDetails,
  dividendsReceived,
  shares,
  empPensionContributions,
  employmentDetails,
  businessProfit,
  earnings,
  benefits,
  expenses,
  eisInvestments,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
