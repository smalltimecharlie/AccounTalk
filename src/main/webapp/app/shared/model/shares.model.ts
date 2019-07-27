import { IClient } from 'app/shared/model/client.model';

export interface IShares {
  id?: number;
  companyName?: string;
  numberOfShares?: number;
  client?: IClient;
}

export const defaultValue: Readonly<IShares> = {};
