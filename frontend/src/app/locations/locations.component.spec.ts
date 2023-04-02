import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LocationsComponent } from './locations.component';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTableModule } from '@angular/material/table';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MockProvider } from 'ng-mocks';
import { EMPTY } from 'rxjs';
import { Store } from '@ngxs/store';
import { SearchFilterComponent } from '../shared/search-filter/search-filter.component';

describe('LocationsComponent', () => {
  let component: LocationsComponent;
  let fixture: ComponentFixture<LocationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LocationsComponent, SearchFilterComponent],
      imports: [
        MatPaginatorModule,
        MatTableModule,
        MatInputModule,
        ReactiveFormsModule,
        NoopAnimationsModule,
      ],
      providers: [MockProvider(Store, { select: () => EMPTY })],
    }).compileComponents();

    fixture = TestBed.createComponent(LocationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
