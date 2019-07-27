import { ITaxReturn } from 'app/shared/model/tax-return.model';
import { IEmployment } from 'app/shared/model/employment.model';
import { IEarnings } from 'app/shared/model/earnings.model';
import { IBenefits } from 'app/shared/model/benefits.model';
import { IExpenses } from 'app/shared/model/expenses.model';
import { IEmpPensionContributions } from 'app/shared/model/emp-pension-contributions.model';

export interface IEmploymentDetails {
  id?: number;
  taxReturn?: ITaxReturn;
  employment?: IEmployment;
  earnings?: IEarnings[];
  benefits?: IBenefits[];
  expenses?: IExpenses[];
  employmentPensionContributions?: IEmpPensionContributions[];
}

export const defaultValue: Readonly<IEmploymentDetails> = {};
