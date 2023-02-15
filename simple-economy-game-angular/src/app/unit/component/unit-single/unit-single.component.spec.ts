import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitSingleComponent } from './unit-single.component';

describe('UnitSingleComponent', () => {
  let component: UnitSingleComponent;
  let fixture: ComponentFixture<UnitSingleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UnitSingleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UnitSingleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
