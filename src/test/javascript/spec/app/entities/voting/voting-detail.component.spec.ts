import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OpenVoteBackTestModule } from '../../../test.module';
import { VotingDetailComponent } from 'app/entities/voting/voting-detail.component';
import { Voting } from 'app/shared/model/voting.model';

describe('Component Tests', () => {
  describe('Voting Management Detail Component', () => {
    let comp: VotingDetailComponent;
    let fixture: ComponentFixture<VotingDetailComponent>;
    const route = ({ data: of({ voting: new Voting(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OpenVoteBackTestModule],
        declarations: [VotingDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(VotingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VotingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load voting on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.voting).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
