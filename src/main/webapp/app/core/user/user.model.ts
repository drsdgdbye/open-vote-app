export interface IUser {
  id?: any;
  login?: string;
  firstName?: string;
  lastName?: string;
  email?: string;
  activated?: boolean;
  langKey?: string;
  authorities?: string[];
  createdBy?: string;
  createdDate?: Date;
  lastModifiedBy?: string;
  lastModifiedDate?: Date;
  password?: string;
  middleName?: string;
  region?: string;
  city?: string;
  address?: string;
  fbId?: string;
  twitterId?: string;
  votePlaceId?: number;
  passport?: string;
  issuedBy?: string;
  issuedDate?: string;
  birthday?: string;
  phone?: string;
  pushToken?: string;
}

export class User implements IUser {
  constructor(
    public id?: any,
    public login?: string,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public activated?: boolean,
    public langKey?: string,
    public authorities?: string[],
    public createdBy?: string,
    public createdDate?: Date,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Date,
    public password?: string,
    public middleName?: string,
    public region?: string,
    public city?: string,
    public address?: string,
    public fbId?: string,
    public twitterId?: string,
    public votePlaceId?: number,
    public passport?: string,
    public issuedBy?: string,
    public issuedDate?: string,
    public birthday?: string,
    public phone?: string,
    public pushToken?: string
  ) {}
}
