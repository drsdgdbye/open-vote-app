import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OpenVoteBackTestModule } from '../../../test.module';
import { VoteplaceDetailComponent } from '../../../../../../main/webapp/app/entities/voteplace/voteplace-detail.component';
import { VotePlace } from '../../../../../../main/webapp/app/shared/model/voteplace.model';

describe('Component Tests', () => {
  describe('VotePlace Management Detail Component', () => {
    let comp: VoteplaceDetailComponent;
    let fixture: ComponentFixture<VoteplaceDetailComponent>;
    const route = ({ data: of({ votePlace: new VotePlace(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OpenVoteBackTestModule],
        declarations: [VoteplaceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(VoteplaceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VoteplaceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load votePlace on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.votePlace).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
