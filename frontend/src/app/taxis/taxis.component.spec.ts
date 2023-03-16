import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaxisComponent } from './taxis.component';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTableModule } from '@angular/material/table';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MockProvider } from 'ng-mocks';
import { TaxiService } from '../gen';
import { EMPTY } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';

describe('TaxisComponent', () => {
  let component: TaxisComponent;
  let fixture: ComponentFixture<TaxisComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TaxisComponent],
      imports: [MatPaginatorModule, MatTableModule, NoopAnimationsModule],
      providers: [
        MockProvider(TaxiService, { getTaxis: () => EMPTY }),
        { provide: MatDialog, useValue: {} },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(TaxisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
