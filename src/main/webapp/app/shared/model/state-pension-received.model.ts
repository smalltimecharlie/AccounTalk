export interface IStatePensionReceived {
  id?: number;
  grossPensionReceived?: number;
  taxDeducted?: number;
}

export const defaultValue: Readonly<IStatePensionReceived> = {};
