import { Taxi } from '../../gen';

export class GetTaxis {
  static readonly type = '[Taxis] Fetch';
}

export class AddTaxi {
  static readonly type = '[Taxi] Add';
  constructor(public taxi: Taxi) {}
}

export class UpdateTaxi {
  static readonly type = '[Taxi] Update';
  constructor(public id: number, public taxi: Taxi) {}
}
