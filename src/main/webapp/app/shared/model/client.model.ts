import { IAddress } from 'app/shared/model/address.model';
import { IBusiness } from 'app/shared/model/business.model';
import { IPreviousAccountant } from 'app/shared/model/previous-accountant.model';
import { ITaxReturn } from 'app/shared/model/tax-return.model';
import { IBankDetails } from 'app/shared/model/bank-details.model';
import { IShares } from 'app/shared/model/shares.model';
import { IPensionProvider } from 'app/shared/model/pension-provider.model';
import { IEmployment } from 'app/shared/model/employment.model';

export const enum Gender {
  MALE = 'MALE',
  FEMALE = 'FEMALE',
  OTHER = 'OTHER'
}

export interface IClient {
  id?: number;
  title?: string;
  forename?: string;
  surname?: string;
  dateOfBirth?: string;
  phoneNumber?: string;
  email?: string;
  selfAssesmentUtrNo?: string;
  nationality?: string;
  gender?: Gender;
  studentLoan?: boolean;
  childBenefit?: boolean;
  spouse?: boolean;
  findOutAboutUs?: string;
  additionalInformation?: string;
  addresses?: IAddress[];
  businesses?: IBusiness[];
  previousAccountants?: IPreviousAccountant[];
  taxReturns?: ITaxReturn[];
  bankDetails?: IBankDetails[];
  shares?: IShares[];
  pensionProviders?: IPensionProvider[];
  employments?: IEmployment[];
}

export const defaultValue: Readonly<IClient> = {
  studentLoan: false,
  childBenefit: false,
  spouse: false
};
