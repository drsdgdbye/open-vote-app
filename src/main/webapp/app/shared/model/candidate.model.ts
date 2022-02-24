export interface ICandidate {
  id?: number;
  name?: string;
  type?: number;
  politicalParty?: string;
  description?: string;
  imageUrl?: string;
  cikCandidateId?: string;
}

export class Candidate implements ICandidate {
  constructor(
    public id?: number,
    public name?: string,
    public type?: number,
    public politicalParty?: string,
    public description?: string,
    public imageUrl?: string,
    public cikCandidateId?: string
  ) {}
}
