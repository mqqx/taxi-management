import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Driver, DriverService } from '../gen';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { MatSlideToggleChange } from '@angular/material/slide-toggle';
import { DriverDialogComponent } from './driver-dialog/driver-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { AddDriver, GetDrivers, UpdateDriver } from './store/driver.actions';
import { Store } from '@ngxs/store';
import { DriversState } from './store/driver.state';

@Component({
  selector: 'tm-drivers',
  templateUrl: './drivers.component.html',
  styleUrls: ['./drivers.component.scss'],
})
export class DriversComponent implements OnInit, AfterViewInit {
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

  constructor(
    public dialog: MatDialog,
    private driverService: DriverService,
    private store: Store
  ) {}

  ngOnInit(): void {
    this.store.dispatch(new GetDrivers());
    this.refresh();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  private refresh() {
    this.drivers$ = this.store.select(DriversState.drivers).pipe(
      map((drivers: Driver[]) => {
        this.dataSource.data = drivers;
        setTimeout(() => {
          this.dataSource.sort = this.sort;
        });
        return this.dataSource;
      })
    );
  }

  toggleActive($event: MatSlideToggleChange, driver: Driver) {
    this.updateIfHasId(driver);
  }

  add() {
    const driver = { active: true } as Driver;

    this.handleDialog(driver, () => this.store.dispatch(new AddDriver(driver)));
  }

  edit(driver: Driver) {
    // clone to prevent changes in current model if edit gets cancelled
    const clonedDriver = structuredClone(driver);

    this.handleDialog(clonedDriver, () => {
      this.updateIfHasId(clonedDriver);
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

  private updateIfHasId(driver: Driver) {
    if (driver.id) {
      this.store.dispatch(new UpdateDriver(driver.id, driver));
    }
  }
}
