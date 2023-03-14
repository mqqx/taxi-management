import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { Customer, CustomerService } from '../gen';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { FormControl, FormGroup } from '@angular/forms';
import { DateTime } from 'luxon';

@Component({
  selector: 'tm-customers',
  templateUrl: './customers.component.html',
  styleUrls: ['./customers.component.scss'],
})
export class CustomersComponent implements AfterViewInit {
  private dataSource: MatTableDataSource<Customer> =
    new MatTableDataSource<Customer>([]);
  columnKeys: string[] = ['name', 'count'];
  customerRange = new FormGroup({
    start: new FormControl(DateTime.now().startOf('year')),
    end: new FormControl(DateTime.now().endOf('year')),
  });

  customers$?: Observable<MatTableDataSource<Customer>>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private customerService: CustomerService) {}

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;

    this.customerRangeChange();
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  customerRangeChange() {
    this.customers$ = this.customerService
      .getCustomersByPeriod(
        this.customerRange.controls.start.value?.toJSDate(),
        this.customerRange.controls.end.value?.toJSDate()
      )
      .pipe(
        map((customers) => {
          this.dataSource.data = customers;
          return this.dataSource;
        })
      );
  }
}
