import { Taxi } from '../../gen';

export interface TaxiStateModel {
  taxis: Taxi[];
  isDirty: boolean;
}
