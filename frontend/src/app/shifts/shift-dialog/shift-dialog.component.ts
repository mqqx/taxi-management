import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Shift } from '../../gen';
import { FormControl, Validators } from '@angular/forms';
import { Duration } from 'luxon';

@Component({
  selector: 'tm-shift-dialog',
  templateUrl: './shift-dialog.component.html',
  styleUrls: ['./shift-dialog.component.scss'],
})
export class ShiftDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<ShiftDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Shift
  ) {}

  formControl = new FormControl('', [Validators.required]);

  getErrorMessage() {
    return this.formControl.hasError('required') ? 'Pflichtfeld' : '';
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onDurationChange($event: string): void {
    this.data.duration =
      Duration.fromISOTime($event).shiftTo('minutes').minutes;
  }
}
