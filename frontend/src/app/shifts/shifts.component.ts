import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Shift, ShiftService } from '../gen';
import { Observable } from 'rxjs';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { map } from 'rxjs/operators';
import { Store } from '@ngxs/store';
import { GetShiftsByPeriod } from './store/shift.actions';
import { ShiftsState } from './store/shift.state';

@Component({
  selector: 'tm-shifts',
  templateUrl: './shifts.component.html',
  styleUrls: ['./shifts.component.scss'],
})
export class ShiftsComponent implements OnInit, AfterViewInit {
  private dataSource = new MatTableDataSource<Shift>();
  columnKeys: string[] = [
    'date',
    'driver',
    'taxi',
    'startMileage',
    'endMileage',
    'duration',
  ];

  shifts$?: Observable<MatTableDataSource<Shift>>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private shiftService: ShiftService, private store: Store) {}

  // TODO: fix sorting for nested types like taxi or driver
  // ngOnInit(): void {
  // this.dataSource.sortingDataAccessor = (item, property: string) => {
  //   if (property === 'taxi') {
  //     return item.taxi.description;
  //   } else {
  //     return item[property as keyof Shift];
  //   }
  // };
  // }

  ngOnInit(): void {
    this.store.dispatch(new GetShiftsByPeriod());
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
}
