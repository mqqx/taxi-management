import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Driver, Shift, Taxi } from '../../gen';
import { FormControl, Validators } from '@angular/forms';
import { DateTime, Duration } from 'luxon';
import { TaxisState } from '../../taxis/store/taxi.state';
import { Observable } from 'rxjs';
import { Store } from '@ngxs/store';
import { GetTaxis } from '../../taxis/store/taxi.actions';
import { DriversState } from '../../drivers/store/driver.state';
import { GetDrivers } from '../../drivers/store/driver.actions';

@Component({
  selector: 'tm-shift-dialog',
  templateUrl: './shift-dialog.component.html',
  styleUrls: ['./shift-dialog.component.scss'],
})
export class ShiftDialogComponent implements OnInit {
  taxis$: Observable<Taxi[]>;
  drivers$: Observable<Driver[]>;
  formControl = new FormControl('', [Validators.required]);
  constructor(
    public dialogRef: MatDialogRef<ShiftDialogComponent>,
    private store: Store,
    @Inject(MAT_DIALOG_DATA) public data: Shift
  ) {
    this.taxis$ = this.store.select(TaxisState.taxis);
    this.drivers$ = this.store.select(DriversState.drivers);
  }

  ngOnInit(): void {
    this.store.dispatch(new GetTaxis());
    this.store.dispatch(new GetDrivers());
  }

  getErrorMessage() {
    return this.formControl.hasError('required') ? 'Pflichtfeld' : '';
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onDateChange($event: DateTime): void {
    this.data.date = $event.toJSDate();
  }

  onDurationChange($event: string): void {
    this.data.duration =
      Duration.fromISOTime($event).shiftTo('minutes').minutes;
  }

  onTaxiChange($event: Taxi): void {
    this.data.startMileage = $event.mileage;
  }

  compareTaxis(o1: Taxi, o2: Taxi): boolean {
    return o1 && o2 && o1.id === o2.id;
  }

  compareDrivers(o1: Driver, o2: Driver): boolean {
    return o1 && o2 && o1.id === o2.id;
  }
}
