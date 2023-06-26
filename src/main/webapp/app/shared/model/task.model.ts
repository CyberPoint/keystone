import dayjs from 'dayjs';
import { ITaskResult } from 'app/shared/model/task-result.model';
import { IAgent } from 'app/shared/model/agent.model';

export interface ITask {
  id?: number;
  command?: string | null;
  formattedCommand?: string | null;
  submittedBy?: string | null;
  description?: string | null;
  added?: string | null;
  updated?: string | null;
  retrieved?: boolean | null;
  failure?: boolean | null;
  approved?: boolean | null;
  results?: ITaskResult[] | null;
  agent?: IAgent | null;
}

export const defaultValue: Readonly<ITask> = {
  retrieved: false,
  failure: false,
  approved: false,
};
