import { ITaxReturn } from 'app/shared/model/tax-return.model';
import { IPensionProvider } from 'app/shared/model/pension-provider.model';

export interface IPensionReceived {
  id?: number;
  grossPensionReceived?: number;
  taxDeducted?: number;
  taxReturn?: ITaxReturn;
  pensionProvider?: IPensionProvider;
}

export const defaultValue: Readonly<IPensionReceived> = {};
