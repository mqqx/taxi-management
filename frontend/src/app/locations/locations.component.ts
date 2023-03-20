import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Location, LocationService } from '../gen';
import { Observable } from 'rxjs';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { map } from 'rxjs/operators';

@Component({
  selector: 'tm-locations',
  templateUrl: './locations.component.html',
  styleUrls: ['./locations.component.scss'],
})
export class LocationsComponent implements AfterViewInit {
  private dataSource: MatTableDataSource<Location> =
    new MatTableDataSource<Location>([]);
  columnKeys: string[] = ['description'];

  locations$?: Observable<MatTableDataSource<Location>>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private locationService: LocationService) {}

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.locations$ = this.locationService.getLocations().pipe(
      map((locations: Location[]) => {
        this.dataSource.data = locations;
        setTimeout(() => {
          this.dataSource.sort = this.sort;
        });
        return this.dataSource;
      })
    );
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
}
