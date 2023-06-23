import dayjs from 'dayjs';

export interface IPlatform {
  id?: number;
  name?: string;
  description?: string | null;
  accessLevel?: number;
  version?: string | null;
  contentsContentType?: string | null;
  contents?: string | null;
  added?: string | null;
  updated?: string | null;
  active?: boolean | null;
}

export const defaultValue: Readonly<IPlatform> = {
  active: false,
};
