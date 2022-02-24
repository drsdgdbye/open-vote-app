import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { VoteplaceService } from '../../../../../../main/webapp/app/entities/voteplace/voteplace.service';
import { IVotePlace, VotePlace } from '../../../../../../main/webapp/app/shared/model/voteplace.model';

describe('Service Tests', () => {
  describe('VotePlace Service', () => {
    let injector: TestBed;
    let service: VoteplaceService;
    let httpMock: HttpTestingController;
    let elemDefault: IVotePlace;
    let expectedResult: IVotePlace | IVotePlace[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(VoteplaceService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new VotePlace(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a VotePlace', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new VotePlace()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a VotePlace', () => {
        const returnedFromService = Object.assign(
          {
            vrn: 'BBBBBB',
            name: 'BBBBBB',
            subjCode: 'BBBBBB',
            numKsa: 'BBBBBB',
            vid: 'BBBBBB',
            address: 'BBBBBB',
            descr: 'BBBBBB',
            phone: 'BBBBBB',
            lat: 'BBBBBB',
            lon: 'BBBBBB',
            votingAddress: 'BBBBBB',
            votingDescr: 'BBBBBB',
            votingPhone: 'BBBBBB',
            votingLat: 'BBBBBB',
            votingLon: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of VotePlace', () => {
        const returnedFromService = Object.assign(
          {
            vrn: 'BBBBBB',
            name: 'BBBBBB',
            subjCode: 'BBBBBB',
            numKsa: 'BBBBBB',
            vid: 'BBBBBB',
            address: 'BBBBBB',
            descr: 'BBBBBB',
            phone: 'BBBBBB',
            lat: 'BBBBBB',
            lon: 'BBBBBB',
            votingAddress: 'BBBBBB',
            votingDescr: 'BBBBBB',
            votingPhone: 'BBBBBB',
            votingLat: 'BBBBBB',
            votingLon: 'BBBBBB',
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

      it('should delete a VotePlace', () => {
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
