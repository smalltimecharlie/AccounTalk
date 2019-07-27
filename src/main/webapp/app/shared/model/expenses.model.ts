import { IEmploymentDetails } from 'app/shared/model/employment-details.model';

export const enum ExpenseType {
  UNSPECIFIED = 'UNSPECIFIED',
  SUBSCRIPTIONS = 'SUBSCRIPTIONS',
  MOTOR_EXPENSE_IN_OWN_CAR = 'MOTOR_EXPENSE_IN_OWN_CAR'
}

export interface IExpenses {
  id?: number;
  expenseType?: ExpenseType;
  description?: string;
  value?: number;
  employmentDetails?: IEmploymentDetails;
}

export const defaultValue: Readonly<IExpenses> = {};
