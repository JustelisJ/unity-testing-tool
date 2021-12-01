import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlayrunsComponent } from './playruns.component';

describe('PlayrunsComponent', () => {
  let component: PlayrunsComponent;
  let fixture: ComponentFixture<PlayrunsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PlayrunsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PlayrunsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
