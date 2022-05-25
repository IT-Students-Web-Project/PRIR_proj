import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FileDatabaseViewComponent } from './file-database-view.component';

describe('FileDatabaseViewComponent', () => {
  let component: FileDatabaseViewComponent;
  let fixture: ComponentFixture<FileDatabaseViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FileDatabaseViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FileDatabaseViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
