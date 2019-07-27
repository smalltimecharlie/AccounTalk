import { ITaxReturn } from 'app/shared/model/tax-return.model';
import { IBankDetails } from 'app/shared/model/bank-details.model';

export interface IBankInterest {
  id?: number;
  netInterest?: number;
  taxDeducted?: number;
  taxReturn?: ITaxReturn;
  bankdetails?: IBankDetails;
}

export const defaultValue: Readonly<IBankInterest> = {};
