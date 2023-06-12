import dayjs from 'dayjs';
import { IAgent } from 'app/shared/model/agent.model';
import { IRegistrationSecret } from 'app/shared/model/registration-secret.model';

export interface IRegistrationEvent {
  id?: number;
  ipAddress?: string;
  rawContents?: string | null;
  remotePort?: number | null;
  name?: string | null;
  approved?: boolean | null;
  registrationDate?: string | null;
  agent?: IAgent | null;
  secret?: IRegistrationSecret | null;
}

export const defaultValue: Readonly<IRegistrationEvent> = {
  approved: false,
};
