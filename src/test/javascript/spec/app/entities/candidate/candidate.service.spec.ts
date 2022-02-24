import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CandidateService } from 'app/entities/candidate/candidate.service';
import { ICandidate, Candidate } from 'app/shared/model/candidate.model';

describe('Service Tests', () => {
  describe('Candidate Service', () => {
    let injector: TestBed;
    let service: CandidateService;
    let httpMock: HttpTestingController;
    let elemDefault: ICandidate;
    let expectedResult: ICandidate | ICandidate[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CandidateService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Candidate(0, 'AAAAAAA', 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Candidate', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Candidate()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Candidate', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            type: 1,
            politicalParty: 'BBBBBB',
            description: 'BBBBBB',
            imageUrl: 'BBBBBB',
            cikCandidateId: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Candidate', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            type: 1,
            politicalParty: 'BBBBBB',
            description: 'BBBBBB',
            imageUrl: 'BBBBBB',
            cikCandidateId: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Candidate', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
