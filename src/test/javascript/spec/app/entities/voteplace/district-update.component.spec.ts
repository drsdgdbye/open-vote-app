import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { OpenVoteBackTestModule } from '../../../test.module';
import { VoteplaceUpdateComponent } from '../../../../../../main/webapp/app/entities/voteplace/voteplace-update.component';
import { VoteplaceService } from '../../../../../../main/webapp/app/entities/voteplace/voteplace.service';
import { VotePlace } from '../../../../../../main/webapp/app/shared/model/voteplace.model';

describe('Component Tests', () => {
  describe('VotePlace Management Update Component', () => {
    let comp: VoteplaceUpdateComponent;
    let fixture: ComponentFixture<VoteplaceUpdateComponent>;
    let service: VoteplaceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OpenVoteBackTestModule],
        declarations: [VoteplaceUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(VoteplaceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VoteplaceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VoteplaceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new VotePlace(123);
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
        const entity = new VotePlace();
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
