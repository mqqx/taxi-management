import { DriverToNamePipe } from './driver-to-name.pipe';
import { Driver } from '../gen';

describe('DriverToNamePipe', () => {
  it('create an instance', () => {
    const pipe = new DriverToNamePipe();
    expect(pipe).toBeTruthy();
  });
  it('should return the drivers full name', () => {
    const pipe = new DriverToNamePipe();
    expect(
      pipe.transform({ lastName: 'Müller', firstName: 'Hans' } as Driver)
    ).toEqual('Müller Hans');
  });
});
