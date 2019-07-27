import { Moment } from 'moment';
import { IClient } from 'app/shared/model/client.model';

export interface IEmployment {
  id?: number;
  businessName?: string;
  payeReference?: string;
  employmentEndDate?: Moment;
  client?: IClient;
}

export const defaultValue: Readonly<IEmployment> = {};
