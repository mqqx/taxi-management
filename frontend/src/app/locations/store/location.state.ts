import { Location, LocationService } from '../../gen';
import { Action, Selector, State, StateContext } from '@ngxs/store';
import { Injectable } from '@angular/core';
import { GetLocations } from './location.actions';
import { tap } from 'rxjs';

@State<Location[]>({
  name: 'locations',
  defaults: [],
})
@Injectable()
export class LocationsState {
  constructor(private locationService: LocationService) {}

  @Selector()
  public static locations(state: Location[]) {
    return state;
  }

  @Action(GetLocations)
  public getLocations(ctx: StateContext<Location[]>) {
    const locations = ctx.getState();
    if (locations.length > 0) {
      return;
    }

    return this.locationService.getLocations().pipe(
      tap((locations: Location[]) => {
        ctx.setState(locations);
      })
    );
  }
}
