import { Shift, ShiftService } from '../../gen';
import { Action, Selector, State, StateContext } from '@ngxs/store';
import { Injectable } from '@angular/core';
import { GetShiftsByFilter, AddShift, UpdateShift } from './shift.actions';
import { tap } from 'rxjs';
import { updateItem } from '@ngxs/store/operators';

@State<Shift[]>({
  name: 'shifts',
  defaults: [],
})
@Injectable()
export class ShiftsState {
  constructor(private shiftService: ShiftService) {}

  @Selector()
  public static shifts(state: Shift[]) {
    return state;
  }

  @Action(GetShiftsByFilter)
  public getShifts(ctx: StateContext<Shift[]>, parameters: GetShiftsByFilter) {
    return this.shiftService
      .getShiftsByFilter(parameters.from, parameters.to, parameters.driverId)
      .pipe(
        tap((shifts: Shift[]) => {
          ctx.setState(shifts);
        })
      );
  }

  @Action(AddShift)
  public addShift(ctx: StateContext<Shift[]>, payload: AddShift) {
    this.shiftService
      .addShift(payload.shift)
      .pipe(
        tap(() => {
          ctx.setState([payload.shift, ...ctx.getState()]);
        })
      )
      .subscribe();
  }

  @Action(UpdateShift)
  public updateShift(ctx: StateContext<Shift[]>, payload: UpdateShift) {
    this.shiftService
      .updateShift(payload.id, payload.shift)
      .pipe(
        tap(() => {
          // date ist kaputt wenn es hier reingeschrieben wird
          console.log(payload.shift);
          ctx.setState(
            updateItem((shift) => shift?.id === payload.id, payload.shift)
          );
        })
      )
      .subscribe();
  }
}
