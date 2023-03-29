import { Shift } from '../../gen';

export class GetShiftsByFilter {
  static readonly type = '[Shifts] Fetch';
  constructor(public from?: Date, public to?: Date, public driverId?: number) {}
}

export class AddShift {
  static readonly type = '[Shift] Add';
  constructor(public shift: Shift) {}
}

export class UpdateShift {
  static readonly type = '[Shift] Update';
  constructor(public id: number, public shift: Shift) {}
}
