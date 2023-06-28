import dayjs from 'dayjs';
import { ITask } from 'app/shared/model/task.model';
import { IPlatform } from 'app/shared/model/platform.model';
import { IRegistrationEvent } from 'app/shared/model/registration-event.model';

export interface IAgent {
  id?: number;
  name?: string;
  classification?: string | null;
  description?: string | null;
  installedOn?: string | null;
  uninstallDate?: string | null;
  active?: boolean | null;
  deactivate?: boolean | null;
  autoRegistered?: boolean | null;
  approved?: boolean | null;
  tasks?: ITask[] | null;
  platform?: IPlatform | null;
  registrationEvent?: IRegistrationEvent | null;
}

export const defaultValue: Readonly<IAgent> = {
  active: false,
  deactivate: false,
  autoRegistered: false,
  approved: false,
};
