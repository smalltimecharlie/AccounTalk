import { IClient } from 'app/shared/model/client.model';
import { IPreviousAccountant } from 'app/shared/model/previous-accountant.model';

export interface IAddress {
  id?: number;
  address1?: string;
  address2?: string;
  address3?: string;
  town?: string;
  county?: string;
  country?: string;
  postcode?: string;
  client?: IClient;
  previousAccountant?: IPreviousAccountant;
}

export const defaultValue: Readonly<IAddress> = {};
