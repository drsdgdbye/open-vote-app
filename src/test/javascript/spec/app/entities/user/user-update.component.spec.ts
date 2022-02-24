import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { OpenVoteBackTestModule } from '../../../test.module';
import { UserUpdateComponent } from 'app/entities/user/user-update.component';
import { UserService } from 'app/entities/user/user.service';
import { User } from 'app/shared/model/user.model';

describe('Component Tests', () => {
  describe('User Management Update Component', () => {
    let comp: UserUpdateComponent;
    let fixture: ComponentFixture<UserUpdateComponent>;
    let service: UserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OpenVoteBackTestModule],
        declarations: [UserUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new User(123);
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
        const entity = new User();
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
