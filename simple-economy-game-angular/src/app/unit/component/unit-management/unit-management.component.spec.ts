import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitManagementComponent } from './unit-management.component';

describe('UnitManagementComponent', () => {
  let component: UnitManagementComponent;
  let fixture: ComponentFixture<UnitManagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UnitManagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UnitManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
