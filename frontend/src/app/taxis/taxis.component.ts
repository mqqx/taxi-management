import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Taxi, TaxiService } from '../gen';
import { Observable } from 'rxjs';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatDialog } from '@angular/material/dialog';
import { map } from 'rxjs/operators';
import { MatSlideToggleChange } from '@angular/material/slide-toggle';
import { TaxiDialogComponent } from './taxi-dialog/taxi-dialog.component';
import { Store } from '@ngxs/store';
import { AddTaxi, GetTaxis, UpdateTaxi } from './store/taxi.actions';
import { TaxisState } from './store/taxi.state';

@Component({
  selector: 'tm-taxis',
  templateUrl: './taxis.component.html',
  styleUrls: ['./taxis.component.scss'],
})
export class TaxisComponent implements OnInit, AfterViewInit {
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

  constructor(
    public dialog: MatDialog,
    private taxiService: TaxiService,
    private store: Store
  ) {}

  ngOnInit(): void {
    this.store.dispatch(new GetTaxis());
    this.refresh();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  private refresh() {
    this.taxis$ = this.store.select(TaxisState.taxis).pipe(
      map((taxis: Taxi[]) => {
        this.dataSource.data = taxis;
        setTimeout(() => {
          this.dataSource.sort = this.sort;
        });
        return this.dataSource;
      })
    );
  }

  toggleActive($event: MatSlideToggleChange, taxi: Taxi) {
    this.updateIfHasId(taxi);
  }

  add() {
    const taxi = { active: true } as Taxi;

    this.handleDialog(taxi, () => this.store.dispatch(new AddTaxi(taxi)));
  }

  edit(taxi: Taxi) {
    // clone to prevent changes in current model if edit gets cancelled
    const clonedTaxi = structuredClone(taxi);

    this.handleDialog(clonedTaxi, () => {
      this.updateIfHasId(clonedTaxi);
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

  private updateIfHasId(taxi: Taxi) {
    if (taxi.id) {
      this.store.dispatch(new UpdateTaxi(taxi.id, taxi));
    }
  }
}
