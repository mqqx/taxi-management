import { Taxi, TaxiService } from '../../gen';
import { Action, Selector, State, StateContext } from '@ngxs/store';
import { Injectable } from '@angular/core';
import { GetTaxis } from './taxi.actions';
import { tap } from 'rxjs';

@State<Taxi[]>({
  name: 'taxis',
  defaults: [],
})
@Injectable()
export class TaxisState {
  constructor(private taxiService: TaxiService) {}

  @Selector()
  public static taxis(state: Taxi[]) {
    return state;
  }

  @Action(GetTaxis)
  public getTaxis(ctx: StateContext<Taxi[]>) {
    const taxis = ctx.getState();
    if (taxis.length > 0) {
      return;
    }

    return this.taxiService.getTaxis().pipe(
      tap((taxis: Taxi[]) => {
        ctx.setState(taxis);
      })
    );
  }
}
