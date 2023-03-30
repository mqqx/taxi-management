import { Pipe, PipeTransform } from '@angular/core';
import { Driver } from '../gen';

@Pipe({
  name: 'driverToName',
})
export class DriverToNamePipe implements PipeTransform {
  transform(value: Driver): string {
    return value.lastName + ' ' + value.firstName;
  }
}
