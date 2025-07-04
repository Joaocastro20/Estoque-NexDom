import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Movimento } from './movimento';

describe('Movimento', () => {
  let component: Movimento;
  let fixture: ComponentFixture<Movimento>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Movimento]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Movimento);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
