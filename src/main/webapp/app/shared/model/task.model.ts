import dayjs from 'dayjs';
import { ITaskResult } from 'app/shared/model/task-result.model';

export interface ITask {
  id?: number;
  command?: string | null;
  implantTaskId?: number | null;
  submittedBy?: string | null;
  description?: string | null;
  added?: string | null;
  updated?: string | null;
  retrieved?: boolean | null;
  failure?: boolean | null;
  approved?: boolean | null;
  results?: ITaskResult[] | null;
}

export const defaultValue: Readonly<ITask> = {
  retrieved: false,
  failure: false,
  approved: false,
};
