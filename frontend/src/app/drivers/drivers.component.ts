import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Driver, DriverService } from '../gen';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { MatSlideToggleChange } from '@angular/material/slide-toggle';
import { DriverDialogComponent } from './driver-dialog/driver-dialog.component';
import { MatDialog } from '@angular/material/dialog';

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
    'actions',
  ];

  drivers$?: Observable<MatTableDataSource<Driver>>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(public dialog: MatDialog, private driverService: DriverService) {}

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.refresh();
  }

  private refresh() {
    this.drivers$ = this.driverService.getDrivers().pipe(
      map((drivers: Driver[]) => {
        const dataSource = this.dataSource;
        dataSource.data = drivers;
        return this.dataSource;
      })
    );
  }

  toggleActive($event: MatSlideToggleChange, driver: Driver) {
    this.updateIfHasId(driver);
  }

  add() {
    const driver = { active: true } as Driver;

    this.handleDialog(driver, () =>
      this.driverService.addDriver(driver).subscribe(() => {
        this.refresh();
      })
    );
  }

  edit(driver: Driver) {
    // clone to prevent changes in current model if edit gets cancelled
    const clonedDriver = structuredClone(driver);

    this.handleDialog(clonedDriver, () => {
      this.updateIfHasId(clonedDriver, () =>
        // assign changes after driver was updated successfully in backend
        Object.assign(driver, clonedDriver)
      );
    });
  }

  private handleDialog(driver: Driver, callback: () => void) {
    const dialogRef = this.dialog.open(DriverDialogComponent, {
      data: driver,
      maxWidth: '400px',
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result === 1) {
        callback();
      }
    });
  }

  private updateIfHasId(driver: Driver, callback?: () => void) {
    if (driver.id) {
      this.driverService.updateDriver(driver.id, driver).subscribe(() => {
        if (callback) {
          callback();
        }
      });
    }
  }
}
