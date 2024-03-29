import {
  AfterViewInit,
  Component,
  HostListener,
  OnInit,
  ViewChild,
} from '@angular/core';
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
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { FormControl, FormGroup } from '@angular/forms';
import { DriversState } from '../drivers/store/driver.state';
import { GetDrivers } from '../drivers/store/driver.actions';
import { _isNumberValue } from '@angular/cdk/coercion';

/**
 * Corresponds to `Number.MAX_SAFE_INTEGER`. Moved out into a variable here due to
 * flaky browser support and the value not being defined in Closure's typings.
 */
const MAX_SAFE_INTEGER = 9007199254740991;

@Component({
  selector: 'tm-shifts',
  templateUrl: './shifts.component.html',
  styleUrls: ['./shifts.component.scss'],
})
export class ShiftsComponent implements OnInit, AfterViewInit {
  private dataSource = new MatTableDataSource<Shift>();
  private dialogRef: MatDialogRef<ShiftDialogComponent> | null = null;
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

    // custom copy of angular sortingDataAccessor https://github.com/angular/components/blob/main/src/material/table/table-data-source.ts#L161
    this.dataSource.sortingDataAccessor = (
      data: Shift,
      sortHeaderId: string
    ): string | number => {
      const value = (data as unknown as Record<string, never>)[sortHeaderId];

      if (_isNumberValue(value)) {
        const numberValue = Number(value);

        // Numbers beyond `MAX_SAFE_INTEGER` can't be compared reliably, so we
        // leave them as strings. For more info: https://goo.gl/y5vbSg
        return numberValue < MAX_SAFE_INTEGER ? numberValue : value;
      }

      switch (sortHeaderId) {
        case 'taxi':
          return data.taxi.description;
        case 'driver':
          return data.driver.lastName + data.driver.firstName;
        default:
          return value;
      }
    };
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

  @HostListener('window:keydown.enter')
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
    if (this.dialogRef) {
      return;
    }

    this.dialogRef = this.dialog.open(ShiftDialogComponent, {
      data: shift,
      maxWidth: '400px',
    });

    this.dialogRef.afterClosed().subscribe((result: number) => {
      if (result === 1) {
        callback();
      }
      this.dialogRef = null;
    });
  }

  private updateIfHasId(shift: Shift) {
    if (shift.id) {
      this.store.dispatch(new UpdateShift(shift.id, shift));
    }
  }
}
