import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Shift, ShiftService } from '../gen';
import { Observable } from 'rxjs';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatDialog } from '@angular/material/dialog';
import { map } from 'rxjs/operators';

@Component({
  selector: 'tm-shifts',
  templateUrl: './shifts.component.html',
  styleUrls: ['./shifts.component.scss'],
})
export class ShiftsComponent implements AfterViewInit {
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

  constructor(private shiftService: ShiftService) {}

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

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.refresh();
  }

  private refresh() {
    this.shifts$ = this.shiftService.getShiftsByPeriod().pipe(
      map((shifts: Shift[]) => {
        this.dataSource.data = shifts;
        setTimeout(() => {
          this.dataSource.sort = this.sort;
        });
        return this.dataSource;
      })
    );
  }
}
