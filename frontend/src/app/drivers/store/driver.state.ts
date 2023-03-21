import { Driver, DriverService } from '../../gen';
import { Action, Selector, State, StateContext } from '@ngxs/store';
import { Injectable } from '@angular/core';
import { AddDriver, GetDrivers, UpdateDriver } from './driver.actions';
import { tap } from 'rxjs';
import { updateItem } from '@ngxs/store/operators';

@State<Driver[]>({
  name: 'drivers',
  defaults: [],
})
@Injectable()
export class DriversState {
  constructor(private driverService: DriverService) {}

  @Selector()
  public static drivers(state: Driver[]) {
    return state;
  }

  @Action(GetDrivers)
  public getDrivers(ctx: StateContext<Driver[]>) {
    const drivers = ctx.getState();
    if (drivers.length > 0) {
      console.log(drivers);
      return;
    }

    return this.driverService.getDrivers().pipe(
      tap((drivers: Driver[]) => {
        ctx.setState(drivers);
      })
    );
  }

  @Action(AddDriver)
  public addDriver(ctx: StateContext<Driver[]>, payload: AddDriver) {
    this.driverService
      .addDriver(payload.driver)
      .pipe(
        tap(() => {
          ctx.setState([payload.driver, ...ctx.getState()]);
        })
      )
      .subscribe();
  }

  @Action(UpdateDriver)
  public updateDriver(ctx: StateContext<Driver[]>, payload: UpdateDriver) {
    this.driverService
      .updateDriver(payload.id, payload.driver)
      .pipe(
        tap(() => {
          ctx.setState(
            updateItem((driver) => driver?.id === payload.id, payload.driver)
          );
        })
      )
      .subscribe();
  }
}
