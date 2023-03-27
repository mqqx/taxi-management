import { Shift } from '../../gen';

export class GetShiftsByPeriod {
  static readonly type = '[Shifts] Fetch';
  constructor(public from?: Date, public to?: Date) {}
}

export class AddShift {
  static readonly type = '[Shift] Add';
  constructor(public shift: Shift) {}
}

export class UpdateShift {
  static readonly type = '[Shift] Update';
  constructor(public id: number, public shift: Shift) {}
}
