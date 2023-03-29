import { Driver, DriverService } from '../../gen';
import { Action, Selector, State, StateContext } from '@ngxs/store';
import { Injectable } from '@angular/core';
import { AddDriver, GetDrivers, UpdateDriver } from './driver.actions';
import { tap } from 'rxjs';
import { patch, updateItem } from '@ngxs/store/operators';
import { DriverStateModel } from './driver.model';

@State<DriverStateModel>({
  name: 'drivers',
  defaults: {
    drivers: [],
    isDirty: true,
  },
})
@Injectable()
export class DriversState {
  constructor(private driverService: DriverService) {}

  @Selector()
  public static drivers(state: DriverStateModel) {
    return state.drivers;
  }

  @Selector()
  public static activeDrivers(state: DriverStateModel) {
    return state.drivers.filter((driver: Driver) => driver.active);
  }

  @Action(GetDrivers)
  public getDrivers(ctx: StateContext<DriverStateModel>) {
    const state = ctx.getState();
    if (!state.isDirty) {
      return;
    }

    return this.driverService.getDrivers().pipe(
      tap((drivers: Driver[]) => {
        ctx.setState({
          drivers: drivers,
          isDirty: false,
        });
      })
    );
  }

  @Action(AddDriver)
  public addDriver(ctx: StateContext<DriverStateModel>, payload: AddDriver) {
    this.driverService
      .addDriver(payload.driver)
      .pipe(
        tap(() => {
          ctx.setState({
            drivers: [payload.driver, ...ctx.getState().drivers],
            isDirty: true,
          });
        })
      )
      .subscribe();
  }

  @Action(UpdateDriver)
  public updateDriver(
    ctx: StateContext<DriverStateModel>,
    payload: UpdateDriver
  ) {
    this.driverService
      .updateDriver(payload.id, payload.driver)
      .pipe(
        tap(() => {
          ctx.setState(
            patch({
              drivers: updateItem(
                (driver) => driver?.id === payload.id,
                payload.driver
              ),
              isDirty: true,
            })
          );
        })
      )
      .subscribe();
  }
}
