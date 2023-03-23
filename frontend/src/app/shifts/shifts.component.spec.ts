import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShiftsComponent } from './shifts.component';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTableModule } from '@angular/material/table';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MockProvider } from 'ng-mocks';
import { EMPTY } from 'rxjs';
import { Store } from '@ngxs/store';

describe('ShiftsComponent', () => {
  let component: ShiftsComponent;
  let fixture: ComponentFixture<ShiftsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ShiftsComponent],
      imports: [
        MatPaginatorModule,
        MatTableModule,
        MatInputModule,
        ReactiveFormsModule,
        NoopAnimationsModule,
      ],
      providers: [[MockProvider(Store, { select: () => EMPTY })]],
    }).compileComponents();

    fixture = TestBed.createComponent(ShiftsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
