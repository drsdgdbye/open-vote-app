import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { OpenVoteBackTestModule } from '../../../test.module';
import { VotingUpdateComponent } from 'app/entities/voting/voting-update.component';
import { VotingService } from 'app/entities/voting/voting.service';
import { Voting } from 'app/shared/model/voting.model';

describe('Component Tests', () => {
  describe('Voting Management Update Component', () => {
    let comp: VotingUpdateComponent;
    let fixture: ComponentFixture<VotingUpdateComponent>;
    let service: VotingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OpenVoteBackTestModule],
        declarations: [VotingUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(VotingUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VotingUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VotingService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Voting(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Voting();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
