import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PvformComponent } from './pvform.component';

describe('PvformComponent', () => {
  let component: PvformComponent;
  let fixture: ComponentFixture<PvformComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PvformComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PvformComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
