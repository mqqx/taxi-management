import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Taxi, TaxiService } from '../gen';
import { Observable } from 'rxjs';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatDialog } from '@angular/material/dialog';
import { map } from 'rxjs/operators';
import { MatSlideToggleChange } from '@angular/material/slide-toggle';
import { TaxiDialogComponent } from './taxi-dialog/taxi-dialog.component';

@Component({
  selector: 'tm-taxis',
  templateUrl: './taxis.component.html',
  styleUrls: ['./taxis.component.scss'],
})
export class TaxisComponent implements AfterViewInit {
  private dataSource = new MatTableDataSource<Taxi>();
  columnKeys: string[] = [
    'description',
    'mileage',
    'numberPlate',
    'registrationDate',
    'fin',
    'concessionNumber',
    'concessionDate',
    'active',
    'actions',
  ];

  taxis$?: Observable<MatTableDataSource<Taxi>>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(public dialog: MatDialog, private taxiService: TaxiService) {}

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.refresh();
  }

  private refresh() {
    this.taxis$ = this.taxiService.getTaxis().pipe(
      map((taxis: Taxi[]) => {
        const dataSource = this.dataSource;
        dataSource.data = taxis;
        return this.dataSource;
      })
    );
  }

  toggleActive($event: MatSlideToggleChange, taxi: Taxi) {
    this.updateIfHasId(taxi);
  }

  add() {
    const taxi = { active: true } as Taxi;

    this.handleDialog(taxi, () =>
      this.taxiService.addTaxi(taxi).subscribe(() => {
        this.refresh();
      })
    );
  }

  edit(taxi: Taxi) {
    // clone to prevent changes in current model if edit gets cancelled
    const clonedTaxi = structuredClone(taxi);

    this.handleDialog(clonedTaxi, () => {
      this.updateIfHasId(clonedTaxi, () =>
        // assign changes after taxi was updated successfully in backend
        Object.assign(taxi, clonedTaxi)
      );
    });
  }

  private handleDialog(taxi: Taxi, callback: () => void) {
    const dialogRef = this.dialog.open(TaxiDialogComponent, {
      data: taxi,
      maxWidth: '400px',
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result === 1) {
        callback();
      }
    });
  }

  private updateIfHasId(taxi: Taxi, callback?: () => void) {
    if (taxi.id) {
      this.taxiService.updateTaxi(taxi.id, taxi).subscribe(() => {
        if (callback) {
          callback();
        }
      });
    }
  }
}
