import { IEmploymentDetails } from 'app/shared/model/employment-details.model';

export const enum BenefitType {
  UNSPECIFIED = 'UNSPECIFIED',
  COMPANY_VAN = 'COMPANY_VAN',
  FUEL_VAN = 'FUEL_VAN',
  PRIVATE_MEDICAL_DENTAL_INSURANCE = 'PRIVATE_MEDICAL_DENTAL_INSURANCE',
  COMPANY_CAR = 'COMPANY_CAR',
  FUEL_CAR = 'FUEL_CAR'
}

export interface IBenefits {
  id?: number;
  benefitType?: BenefitType;
  description?: string;
  value?: number;
  employmentDetails?: IEmploymentDetails;
}

export const defaultValue: Readonly<IBenefits> = {};
