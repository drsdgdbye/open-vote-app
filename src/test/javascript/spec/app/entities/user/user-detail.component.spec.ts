import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OpenVoteBackTestModule } from '../../../test.module';
import { UserDetailComponent } from 'app/entities/user/user-detail.component';
import { User } from 'app/shared/model/user.model';

describe('Component Tests', () => {
  describe('User Management Detail Component', () => {
    let comp: UserDetailComponent;
    let fixture: ComponentFixture<UserDetailComponent>;
    const route = ({ data: of({ user: new User(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OpenVoteBackTestModule],
        declarations: [UserDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load user on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.user).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
