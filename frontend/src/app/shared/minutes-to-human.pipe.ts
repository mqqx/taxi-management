import { Pipe, PipeTransform } from '@angular/core';
import { Duration } from 'luxon';

@Pipe({
  name: 'minutesToHuman',
})
export class MinutesToHumanPipe implements PipeTransform {
  transform(value: number): string {
    return Duration.fromObject({ minutes: value }).toISOTime({
      suppressSeconds: true,
    });
  }
}
