import { Moment } from 'moment';
import { ITaxReturn } from 'app/shared/model/tax-return.model';
import { IClient } from 'app/shared/model/client.model';

export interface IBankDetails {
  id?: number;
  accountHolderName?: string;
  accountNumber?: string;
  sortCode?: string;
  jointAccount?: boolean;
  bankName?: string;
  openingDate?: Moment;
  closedDate?: Moment;
  taxReturn?: ITaxReturn;
  client?: IClient;
}

export const defaultValue: Readonly<IBankDetails> = {
  jointAccount: false
};
