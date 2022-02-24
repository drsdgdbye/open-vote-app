export interface IUser {
  id?: number;
}

export class User implements IUser {
  constructor(public id?: number) {}
}
