import { Shift, ShiftService } from '../../gen';
import { Action, Selector, State, StateContext } from '@ngxs/store';
import { Injectable } from '@angular/core';
import { GetShiftsByPeriod } from './shift.actions';
import { tap } from 'rxjs';

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
}
