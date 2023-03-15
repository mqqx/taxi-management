import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Driver } from '../../gen';
import { FormControl, Validators } from '@angular/forms';
import { DateTime } from 'luxon';

@Component({
  selector: 'tm-driver-dialog',
  templateUrl: './driver-dialog.component.html',
  styleUrls: ['./driver-dialog.component.scss'],
})
export class DriverDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<DriverDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Driver
  ) {}

  maxDate = DateTime.now().minus({ years: 18 }).toJSDate();

  formControl = new FormControl('', [Validators.required]);

  getErrorMessage() {
    return this.formControl.hasError('required') ? 'Pflichtfeld' : '';
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
