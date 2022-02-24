import { Moment } from 'moment';

export interface IVote {
  id?: number;
  userId?: number;
  createDate?: Moment;
  candidateId?: number;
  votingName?: string;
  votingId?: number;
  candidateName?: string;
}

export class Vote implements IVote {
  constructor(
    public id?: number,
    public userId?: number,
    public createDate?: Moment,
    public candidateId?: number,
    public votingName?: string,
    public votingId?: number,
    public candidateName?: string
  ) {}
}
