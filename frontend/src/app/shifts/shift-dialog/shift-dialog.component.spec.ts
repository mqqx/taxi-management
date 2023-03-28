import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShiftDialogComponent } from './shift-dialog.component';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MatLuxonDateModule } from '@angular/material-luxon-adapter';
import {
  MAT_DIALOG_DATA,
  MatDialogModule,
  MatDialogRef,
} from '@angular/material/dialog';
import { MockProvider } from 'ng-mocks';
import { Store } from '@ngxs/store';
import { EMPTY } from 'rxjs';
import { MinutesToHumanPipe } from '../../shared/minutes-to-human.pipe';
import { MatSelectModule } from '@angular/material/select';

describe('ShiftDialogComponent', () => {
  let component: ShiftDialogComponent;
  let fixture: ComponentFixture<ShiftDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ShiftDialogComponent, MinutesToHumanPipe],
      imports: [
        FormsModule,
        MatFormFieldModule,
        MatInputModule,
        MatDatepickerModule,
        NoopAnimationsModule,
        MatLuxonDateModule,
        MatDialogModule,
        MatSelectModule,
      ],
      providers: [
        { provide: MAT_DIALOG_DATA, useValue: {} },
        { provide: MatDialogRef, useValue: {} },
        MockProvider(Store, { select: () => EMPTY }),
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(ShiftDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
