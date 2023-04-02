import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DriversComponent } from './drivers.component';
import { MockProvider } from 'ng-mocks';
import { EMPTY } from 'rxjs';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTableModule } from '@angular/material/table';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MatDialog } from '@angular/material/dialog';
import { Store } from '@ngxs/store';
import { MatInputModule } from '@angular/material/input';
import { SearchFilterComponent } from '../shared/search-filter/search-filter.component';

describe('DriversComponent', () => {
  let component: DriversComponent;
  let fixture: ComponentFixture<DriversComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DriversComponent, SearchFilterComponent],
      imports: [
        MatPaginatorModule,
        MatTableModule,
        MatInputModule,
        NoopAnimationsModule,
      ],
      providers: [
        [MockProvider(Store, { select: () => EMPTY })],
        { provide: MatDialog, useValue: {} },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(DriversComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
