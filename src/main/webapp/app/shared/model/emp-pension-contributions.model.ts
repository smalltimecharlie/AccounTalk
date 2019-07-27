import { IEmploymentDetails } from 'app/shared/model/employment-details.model';

export interface IEmpPensionContributions {
  id?: number;
  amountPaid?: number;
  employmentDetails?: IEmploymentDetails;
}

export const defaultValue: Readonly<IEmpPensionContributions> = {};
