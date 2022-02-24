export interface IVotePlace {
  id?: number;
  vrn?: string;
  name?: string;
  subjCode?: string;
  numKsa?: string;
  vid?: string;
  address?: string;
  descr?: string;
  phone?: string;
  lat?: string;
  lon?: string;
  votingAddress?: string;
  votingDescr?: string;
  votingPhone?: string;
  votingLat?: string;
  votingLon?: string;
}

export class VotePlace implements IVotePlace {
  constructor(
    public id?: number,
    public vrn?: string,
    public name?: string,
    public subjCode?: string,
    public numKsa?: string,
    public vid?: string,
    public address?: string,
    public descr?: string,
    public phone?: string,
    public lat?: string,
    public lon?: string,
    public votingAddress?: string,
    public votingDescr?: string,
    public votingPhone?: string,
    public votingLat?: string,
    public votingLon?: string
  ) {}
}
