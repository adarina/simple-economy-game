import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BuildingsManagementComponent } from './buildings-management.component';

describe('BuildingsManagementComponent', () => {
  let component: BuildingsManagementComponent;
  let fixture: ComponentFixture<BuildingsManagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BuildingsManagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BuildingsManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
