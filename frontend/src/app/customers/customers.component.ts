import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { Customer } from '../gen';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { FormControl, FormGroup } from '@angular/forms';
import { DateTime } from 'luxon';
import { Store } from '@ngxs/store';
import { CustomersState } from './store/customer.state';
import { GetCustomersByPeriod } from './store/customer.actions';

@Component({
  selector: 'tm-customers',
  templateUrl: './customers.component.html',
  styleUrls: ['./customers.component.scss'],
})
export class CustomersComponent implements OnInit, AfterViewInit {
  private dataSource: MatTableDataSource<Customer> =
    new MatTableDataSource<Customer>([]);
  columnKeys: string[] = ['name', 'count'];
  customerRange = new FormGroup({
    start: new FormControl(DateTime.now().toUTC().startOf('year')),
    end: new FormControl(DateTime.now().toUTC().endOf('year')),
  });

  customers$?: Observable<MatTableDataSource<Customer>>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private store: Store) {}

  ngOnInit(): void {
    this.customerRangeChange();
    this.customers$ = this.store.select(CustomersState.customers).pipe(
      map((customers: Customer[]) => {
        this.dataSource.data = customers;
        setTimeout(() => {
          this.dataSource.sort = this.sort;
        });
        return this.dataSource;
      })
    );
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  customerRangeChange() {
    this.store.dispatch(
      new GetCustomersByPeriod(
        this.customerRange.controls.start.value?.toJSDate(),
        this.customerRange.controls.end.value?.toJSDate()
      )
    );
  }
}
