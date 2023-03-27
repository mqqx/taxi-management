import { Shift, ShiftService } from '../../gen';
import { Action, Selector, State, StateContext } from '@ngxs/store';
import { Injectable } from '@angular/core';
import { GetShiftsByPeriod, AddShift, UpdateShift } from './shift.actions';
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

  @Action(GetShiftsByPeriod)
  public getShifts(ctx: StateContext<Shift[]>, parameters: GetShiftsByPeriod) {
    const shifts = ctx.getState();

    // TODO remove when shifts by period parameters will be implemented
    if (shifts.length > 0) {
      return;
    }

    return this.shiftService
      .getShiftsByPeriod(parameters.from, parameters.to)
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
