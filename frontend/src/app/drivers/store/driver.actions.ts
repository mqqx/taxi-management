import { Driver } from '../../gen';

export class GetDrivers {
  static readonly type = '[Drivers] Fetch';
}

export class AddDriver {
  static readonly type = '[Driver] Add';
  constructor(public driver: Driver) {}
}

export class UpdateDriver {
  static readonly type = '[Driver] Update';
  constructor(public id: number, public driver: Driver) {}
}
