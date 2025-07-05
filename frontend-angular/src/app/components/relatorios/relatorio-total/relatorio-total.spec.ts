import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RelatorioTotal } from './relatorio-total';

describe('RelatorioTotal', () => {
  let component: RelatorioTotal;
  let fixture: ComponentFixture<RelatorioTotal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RelatorioTotal]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RelatorioTotal);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
