export class Account {
  constructor(
    public activated: boolean,
    public authorities: string[],
    public email: string,
    public firstName: string,
    public langKey: string,
    public lastName: string,
    public login: string,
    public imageUrl: string,
    public middleName: string,
    public passport: string,
    public issuedBy: string,
    public issuedDate: string,
    public birthday: string,
    public phone: string
  ) {}
}
