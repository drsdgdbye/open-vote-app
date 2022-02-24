import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { OpenVoteBackTestModule } from '../../../test.module';
import { CandidateUpdateComponent } from 'app/entities/candidate/candidate-update.component';
import { CandidateService } from 'app/entities/candidate/candidate.service';
import { Candidate } from 'app/shared/model/candidate.model';

describe('Component Tests', () => {
  describe('Candidate Management Update Component', () => {
    let comp: CandidateUpdateComponent;
    let fixture: ComponentFixture<CandidateUpdateComponent>;
    let service: CandidateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OpenVoteBackTestModule],
        declarations: [CandidateUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CandidateUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CandidateUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CandidateService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Candidate(123);
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
        const entity = new Candidate();
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
