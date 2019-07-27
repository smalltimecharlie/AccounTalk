import { Moment } from 'moment';
import { IBankDetails } from 'app/shared/model/bank-details.model';
import { IStatePensionReceived } from 'app/shared/model/state-pension-received.model';
import { IPensionReceived } from 'app/shared/model/pension-received.model';
import { IPerPensionContributions } from 'app/shared/model/per-pension-contributions.model';
import { IGiftAidDonations } from 'app/shared/model/gift-aid-donations.model';
import { IBankInterest } from 'app/shared/model/bank-interest.model';
import { IDividendsReceived } from 'app/shared/model/dividends-received.model';
import { IEmploymentDetails } from 'app/shared/model/employment-details.model';
import { IEisInvestments } from 'app/shared/model/eis-investments.model';
import { IBusinessProfit } from 'app/shared/model/business-profit.model';
import { IClient } from 'app/shared/model/client.model';

export const enum StudentLoan {
  NONE = 'NONE',
  PLAN1 = 'PLAN1',
  PLAN2 = 'PLAN2'
}

export interface ITaxReturn {
  id?: number;
  year?: Moment;
  studentLoan?: StudentLoan;
  bankRefundDetails?: IBankDetails;
  statePensionDetails?: IStatePensionReceived;
  pensionReceiveds?: IPensionReceived[];
  personalPensionContributions?: IPerPensionContributions[];
  giftAidDonations?: IGiftAidDonations[];
  bankInterests?: IBankInterest[];
  dividendsReceiveds?: IDividendsReceived[];
  employmentDetails?: IEmploymentDetails[];
  eisInvestments?: IEisInvestments[];
  businessProfits?: IBusinessProfit[];
  client?: IClient;
}

export const defaultValue: Readonly<ITaxReturn> = {};
