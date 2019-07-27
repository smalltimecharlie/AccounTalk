import { Moment } from 'moment';
import { ITaxReturn } from 'app/shared/model/tax-return.model';

export const enum InvestmentScheme {
  EIS = 'EIS',
  SEIS = 'SEIS'
}

export interface IEisInvestments {
  id?: number;
  investmentScheme?: InvestmentScheme;
  dateInvested?: Moment;
  amountPaid?: number;
  taxReturn?: ITaxReturn;
}

export const defaultValue: Readonly<IEisInvestments> = {};
