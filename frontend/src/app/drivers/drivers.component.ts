import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Driver, DriverService } from '../gen';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs';

@Component({
  selector: 'tm-drivers',
  templateUrl: './drivers.component.html',
  styleUrls: ['./drivers.component.scss'],
})
export class DriversComponent implements AfterViewInit {
  private dataSource = new MatTableDataSource<Driver>();
  columnKeys: string[] = [
    'lastName',
    'firstName',
    'pLicenceDate',
    'birthdate',
    'address',
    'active',
  ];

  drivers$?: Observable<any>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private driverService: DriverService) {}

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;

    this.drivers$ = this.driverService.getDrivers().pipe(
      map((drivers: Driver[]) => {
        const dataSource = this.dataSource;
        dataSource.data = drivers;
        return this.dataSource;
      })
    );
  }
}
