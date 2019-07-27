import { Moment } from 'moment';
import { ITaxReturn } from 'app/shared/model/tax-return.model';
import { IShares } from 'app/shared/model/shares.model';

export interface IDividendsReceived {
  id?: number;
  paymentDate?: Moment;
  amountReceived?: number;
  taxReturn?: ITaxReturn;
  shares?: IShares;
}

export const defaultValue: Readonly<IDividendsReceived> = {};
