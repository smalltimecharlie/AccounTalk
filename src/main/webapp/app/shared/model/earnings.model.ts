import { IEmploymentDetails } from 'app/shared/model/employment-details.model';

export interface IEarnings {
  id?: number;
  grossPay?: number;
  taxDeducted?: number;
  studentLoanDeductions?: number;
  employmentDetails?: IEmploymentDetails;
}

export const defaultValue: Readonly<IEarnings> = {};
