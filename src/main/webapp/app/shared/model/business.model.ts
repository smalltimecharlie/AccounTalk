import { IClient } from 'app/shared/model/client.model';

export const enum BusinessType {
  SOLE_TRADE = 'SOLE_TRADE',
  PARTNERSHIP = 'PARTNERSHIP',
  RENTAL_PROPERTY = 'RENTAL_PROPERTY'
}

export interface IBusiness {
  id?: number;
  businessName?: string;
  businessDescription?: string;
  businessType?: BusinessType;
  client?: IClient;
}

export const defaultValue: Readonly<IBusiness> = {};
