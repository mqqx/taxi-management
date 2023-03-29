import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Taxi } from '../../gen';
import { DateTime } from 'luxon';

@Component({
  selector: 'tm-taxi-dialog',
  templateUrl: './taxi-dialog.component.html',
  styleUrls: ['./taxi-dialog.component.scss'],
})
export class TaxiDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<TaxiDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Taxi
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  onRegistrationDateChange($event: DateTime): void {
    this.data.registrationDate = $event?.toJSDate();
  }

  onConcessionDateChange($event: DateTime): void {
    this.data.concessionDate = $event?.toJSDate();
  }
}
