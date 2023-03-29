import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Driver, Shift } from '../gen';
import { Observable } from 'rxjs';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { map } from 'rxjs/operators';
import { Store } from '@ngxs/store';
import {
  AddShift,
  GetShiftsByFilter,
  UpdateShift,
} from './store/shift.actions';
import { ShiftsState } from './store/shift.state';
import { ShiftDialogComponent } from './shift-dialog/shift-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { FormControl, FormGroup } from '@angular/forms';
import { DriversState } from '../drivers/store/driver.state';
import { GetDrivers } from '../drivers/store/driver.actions';
import { MatSelectChange } from '@angular/material/select';

@Component({
  selector: 'tm-shifts',
  templateUrl: './shifts.component.html',
  styleUrls: ['./shifts.component.scss'],
})
export class ShiftsComponent implements OnInit, AfterViewInit {
  private dataSource = new MatTableDataSource<Shift>();
  drivers$: Observable<Driver[]>;
  driver: Driver | undefined;
  columnKeys: string[] = [
    'date',
    'driver',
    'taxi',
    'startMileage',
    'endMileage',
    'duration',
    'actions',
  ];
  shiftRange = new FormGroup({
    start: new FormControl(),
    end: new FormControl(),
  });

  shifts$?: Observable<MatTableDataSource<Shift>>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(public dialog: MatDialog, private store: Store) {
    this.drivers$ = this.store.select(DriversState.drivers);
  }

  // TODO: fix sorting for nested types like shift or driver
  // ngOnInit(): void {
  // this.dataSource.sortingDataAccessor = (item, property: string) => {
  //   if (property === 'shift') {
  //     return item.shift.description;
  //   } else {
  //     return item[property as keyof Shift];
  //   }
  // };
  // }

  ngOnInit(): void {
    this.store.dispatch(new GetDrivers());
    this.store.dispatch(new GetShiftsByFilter());
    this.shifts$ = this.store.select(ShiftsState.shifts).pipe(
      map((shifts: Shift[]) => {
        this.dataSource.data = shifts;
        setTimeout(() => {
          this.dataSource.sort = this.sort;
        });
        return this.dataSource;
      })
    );
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  shiftRangeChange() {
    this.refresh();
  }

  private refresh() {
    this.store.dispatch(
      new GetShiftsByFilter(
        this.shiftRange.controls.start.value?.toJSDate(),
        this.shiftRange.controls.end.value?.toJSDate(),
        this.driver?.id
      )
    );
  }

  onDriverChange(driver: Driver) {
    this.driver = driver;
    this.refresh();
  }

  add() {
    const shift = {} as Shift;

    this.handleDialog(shift, () => this.store.dispatch(new AddShift(shift)));
  }

  edit(shift: Shift) {
    // clone to prevent changes in current model if edit gets cancelled
    const clonedShift = structuredClone(shift);

    this.handleDialog(clonedShift, () => {
      this.updateIfHasId(clonedShift);
    });
  }

  private handleDialog(shift: Shift, callback: () => void) {
    const dialogRef = this.dialog.open(ShiftDialogComponent, {
      data: shift,
      maxWidth: '400px',
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result === 1) {
        callback();
      }
    });
  }

  private updateIfHasId(shift: Shift) {
    if (shift.id) {
      this.store.dispatch(new UpdateShift(shift.id, shift));
    }
  }
}
