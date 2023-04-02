import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Location } from '../gen';
import { Observable } from 'rxjs';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { map } from 'rxjs/operators';
import { Store } from '@ngxs/store';
import { GetLocations } from './store/location.actions';
import { LocationsState } from './store/location.state';

@Component({
  selector: 'tm-locations',
  templateUrl: './locations.component.html',
  styleUrls: ['./locations.component.scss'],
})
export class LocationsComponent implements OnInit, AfterViewInit {
  dataSource: MatTableDataSource<Location> = new MatTableDataSource<Location>(
    []
  );
  columnKeys: string[] = ['description'];

  locations$?: Observable<MatTableDataSource<Location>>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private store: Store) {}

  ngOnInit(): void {
    this.store.dispatch(new GetLocations());
    this.refresh();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  private refresh() {
    this.locations$ = this.store.select(LocationsState.locations).pipe(
      map((locations: Location[]) => {
        this.dataSource.data = locations;
        setTimeout(() => {
          this.dataSource.sort = this.sort;
        });
        return this.dataSource;
      })
    );
  }
}
