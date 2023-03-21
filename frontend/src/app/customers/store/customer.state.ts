import { Customer, CustomerService } from '../../gen';
import { Action, Selector, State, StateContext } from '@ngxs/store';
import { Injectable } from '@angular/core';
import { GetCustomersByPeriod } from './customer.actions';
import { tap } from 'rxjs';

@State<Customer[]>({
  name: 'customers',
  defaults: [],
})
@Injectable()
export class CustomersState {
  constructor(private customerService: CustomerService) {}

  @Selector()
  public static customers(state: Customer[]) {
    return state;
  }

  @Action(GetCustomersByPeriod)
  public getCustomers(
    ctx: StateContext<Customer[]>,
    parameters: GetCustomersByPeriod
  ) {
    return this.customerService
      .getCustomersByPeriod(parameters.from, parameters.to)
      .pipe(
        tap((customers: Customer[]) => {
          ctx.setState(customers);
        })
      );
  }
}
