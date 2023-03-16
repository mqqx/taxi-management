import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Taxi } from '../../gen';
import { FormControl, Validators } from '@angular/forms';

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

  formControl = new FormControl('', [Validators.required]);

  getErrorMessage() {
    return this.formControl.hasError('required') ? 'Pflichtfeld' : '';
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
