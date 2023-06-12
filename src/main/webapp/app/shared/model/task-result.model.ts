import dayjs from 'dayjs';
import { ITask } from 'app/shared/model/task.model';

export interface ITaskResult {
  id?: number;
  embeddeddataContentType?: string | null;
  embeddeddata?: string | null;
  added?: string | null;
  reviewed?: boolean | null;
  ipAddress?: string;
  headers?: string | null;
  url?: string | null;
  task?: ITask | null;
}

export const defaultValue: Readonly<ITaskResult> = {
  reviewed: false,
};
