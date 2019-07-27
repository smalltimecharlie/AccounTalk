import { ITaxReturn } from 'app/shared/model/tax-return.model';
import { IBusiness } from 'app/shared/model/business.model';

export interface IBusinessProfit {
  id?: number;
  turnover?: number;
  expenses?: number;
  capitalAllowances?: number;
  profit?: number;
  cisTaxDeducted?: number;
  taxReturn?: ITaxReturn;
  business?: IBusiness;
}

export const defaultValue: Readonly<IBusinessProfit> = {};
