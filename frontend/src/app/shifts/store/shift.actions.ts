export class GetShiftsByPeriod {
  static readonly type = '[Shifts] Fetch';
  constructor(public from?: Date, public to?: Date) {}
}
