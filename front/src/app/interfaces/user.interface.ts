import { Subject } from './subject.interface';

export interface User {
  id: number;
  email: string;
  username: string;
  admin: boolean;
  password: string;
  createdAt: Date;
  updatedAt?: Date;
  subscriptions?: Subject[];
}
