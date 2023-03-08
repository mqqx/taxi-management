import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Driver, DriverService } from '../gen';
import { Observable } from 'rxjs';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { map } from 'rxjs/operators';

@Component({
  selector: 'tm-drivers',
  templateUrl: './drivers.component.html',
  styleUrls: ['./drivers.component.scss']
})
export class DriversComponent implements AfterViewInit {
  private dataSource: MatTableDataSource<Driver> = new MatTableDataSource<Driver>([]);
  columnKeys: string[] = ['lastName',
    'firstName',
    'pLicenceDate',
    'birthdate',
    'address',
    'active'];

  drivers$: Observable<any>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private driverService: DriverService) {
    this.drivers$ = this.findDrivers();
  }

  private findDrivers() {
    return this.driverService.getDrivers().pipe(
      map(customers => {
        this.dataSource.data = customers;
        return this.dataSource;
      }));
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }
}
