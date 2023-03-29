import { Taxi, TaxiService } from '../../gen';
import { Action, Selector, State, StateContext } from '@ngxs/store';
import { Injectable } from '@angular/core';
import { AddTaxi, GetTaxis, UpdateTaxi } from './taxi.actions';
import { tap } from 'rxjs';
import { patch, updateItem } from '@ngxs/store/operators';
import { TaxiStateModel } from './taxi.model';

@State<TaxiStateModel>({
  name: 'taxis',
  defaults: {
    taxis: [],
    isDirty: true,
  },
})
@Injectable()
export class TaxisState {
  constructor(private taxiService: TaxiService) {}

  @Selector()
  public static taxis(state: TaxiStateModel) {
    return state.taxis;
  }

  @Selector()
  public static activeTaxis(state: TaxiStateModel) {
    return state.taxis.filter((taxi: Taxi) => taxi.active);
  }

  @Action(GetTaxis)
  public getTaxis(ctx: StateContext<TaxiStateModel>) {
    const state = ctx.getState();
    if (!state.isDirty) {
      return;
    }

    return this.taxiService.getTaxis().pipe(
      tap((taxis: Taxi[]) => {
        ctx.setState({
          taxis: taxis,
          isDirty: false,
        });
      })
    );
  }

  @Action(AddTaxi)
  public addTaxi(ctx: StateContext<TaxiStateModel>, payload: AddTaxi) {
    this.taxiService
      .addTaxi(payload.taxi)
      .pipe(
        tap(() => {
          ctx.setState({
            taxis: [payload.taxi, ...ctx.getState().taxis],
            isDirty: true,
          });
        })
      )
      .subscribe();
  }

  @Action(UpdateTaxi)
  public updateTaxi(ctx: StateContext<TaxiStateModel>, payload: UpdateTaxi) {
    this.taxiService
      .updateTaxi(payload.id, payload.taxi)
      .pipe(
        tap(() => {
          ctx.setState(
            patch({
              taxis: updateItem(
                (taxi) => taxi?.id === payload.id,
                payload.taxi
              ),
              isDirty: true,
            })
          );
        })
      )
      .subscribe();
  }
}
