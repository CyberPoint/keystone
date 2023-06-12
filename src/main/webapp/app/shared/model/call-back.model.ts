import dayjs from 'dayjs';
import { IAgent } from 'app/shared/model/agent.model';

export interface ICallBack {
  id?: number;
  ipAddress?: string;
  url?: string | null;
  remotePort?: number | null;
  timestamp?: string | null;
  buffer?: string | null;
  rawcontentsContentType?: string | null;
  rawcontents?: string | null;
  agent?: IAgent | null;
}

export const defaultValue: Readonly<ICallBack> = {};
