import { IRegistrationEvent } from 'app/shared/model/registration-event.model';

export interface IRegistrationSecret {
  id?: number;
  uniqueValue?: string | null;
  numericalValue?: number | null;
  registrationEvent?: IRegistrationEvent | null;
}

export const defaultValue: Readonly<IRegistrationSecret> = {};
