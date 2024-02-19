import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PvApplicationsViewComponent } from './pv-applications-view.component';

describe('PvApplicationsViewComponent', () => {
  let component: PvApplicationsViewComponent;
  let fixture: ComponentFixture<PvApplicationsViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PvApplicationsViewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PvApplicationsViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
