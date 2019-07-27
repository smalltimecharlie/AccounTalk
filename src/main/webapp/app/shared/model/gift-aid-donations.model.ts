import { Moment } from 'moment';
import { ITaxReturn } from 'app/shared/model/tax-return.model';

export interface IGiftAidDonations {
  id?: number;
  charityName?: string;
  donationDate?: Moment;
  donationAmount?: number;
  giftAidClaimed?: boolean;
  taxReturn?: ITaxReturn;
}

export const defaultValue: Readonly<IGiftAidDonations> = {
  giftAidClaimed: false
};
