import { Taxi, TaxiService } from '../../gen';
import { Action, Selector, State, StateContext } from '@ngxs/store';
import { Injectable } from '@angular/core';
import { AddTaxi, GetTaxis, UpdateTaxi } from './taxi.actions';
import { tap } from 'rxjs';
import { updateItem } from '@ngxs/store/operators';

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

  @Action(AddTaxi)
  public addTaxi(ctx: StateContext<Taxi[]>, payload: AddTaxi) {
    this.taxiService
      .addTaxi(payload.taxi)
      .pipe(
        tap(() => {
          ctx.setState([payload.taxi, ...ctx.getState()]);
        })
      )
      .subscribe();
  }

  @Action(UpdateTaxi)
  public updateTaxi(ctx: StateContext<Taxi[]>, payload: UpdateTaxi) {
    this.taxiService
      .updateTaxi(payload.id, payload.taxi)
      .pipe(
        tap(() => {
          ctx.setState(
            updateItem((taxi) => taxi?.id === payload.id, payload.taxi)
          );
        })
      )
      .subscribe();
  }
}
