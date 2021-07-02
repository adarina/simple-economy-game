import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ResourceSingleComponent } from './resource-single.component';

describe('ResourceSingleComponent', () => {
  let component: ResourceSingleComponent;
  let fixture: ComponentFixture<ResourceSingleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ResourceSingleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResourceSingleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
