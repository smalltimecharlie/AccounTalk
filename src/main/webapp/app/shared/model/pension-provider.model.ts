import { IClient } from 'app/shared/model/client.model';

export interface IPensionProvider {
  id?: number;
  nameOfProvider?: string;
  membershipNumber?: string;
  payeReference?: string;
  client?: IClient;
}

export const defaultValue: Readonly<IPensionProvider> = {};
