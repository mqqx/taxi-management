import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomersComponent } from './customers.component';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTableModule } from '@angular/material/table';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MockProvider } from 'ng-mocks';
import { EMPTY } from 'rxjs';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatLuxonDateModule } from '@angular/material-luxon-adapter';
import { ReactiveFormsModule } from '@angular/forms';
import { Store } from '@ngxs/store';

describe('CustomersComponent', () => {
  let component: CustomersComponent;
  let fixture: ComponentFixture<CustomersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CustomersComponent],
      imports: [
        MatPaginatorModule,
        MatTableModule,
        MatInputModule,
        ReactiveFormsModule,
        MatDatepickerModule,
        NoopAnimationsModule,
        MatLuxonDateModule,
      ],
      providers: [MockProvider(Store, { select: () => EMPTY })],
    }).compileComponents();

    fixture = TestBed.createComponent(CustomersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
