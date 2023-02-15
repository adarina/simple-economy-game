import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BuildingSingleComponent } from './building-single.component';

describe('BuildingSingleComponent', () => {
  let component: BuildingSingleComponent;
  let fixture: ComponentFixture<BuildingSingleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BuildingSingleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BuildingSingleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
