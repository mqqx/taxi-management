export class GetCustomersByPeriod {
  static readonly type = '[Customers] Fetch';
  constructor(public from?: Date, public to?: Date) {}
}
