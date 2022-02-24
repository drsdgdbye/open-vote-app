export interface IVoting {
  id?: number;
  name?: string;
  date?: string;
  cikVotingId?: string;
}

export class Voting implements IVoting {
  constructor(public id?: number, public name?: string, public date?: string, public cikVotingId?: string) {}
}
