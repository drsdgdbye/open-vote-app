export interface IVoteCount {
  candidateId?: number;
  candidateName?: string;
  candidateParty?: string;
  candidateCount?: number;
}

export class Votecount implements IVoteCount {
  constructor(public candidateId?: number, public candidateName?: string, public candidateParty?: string, public candidateCount?: number) {}
}
