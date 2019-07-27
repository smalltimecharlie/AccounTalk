import { IClient } from 'app/shared/model/client.model';
import { IAddress } from 'app/shared/model/address.model';

export interface IPreviousAccountant {
  id?: number;
  name?: string;
  email?: string;
  phoneNumber?: string;
  client?: IClient;
  addresses?: IAddress[];
}

export const defaultValue: Readonly<IPreviousAccountant> = {};
