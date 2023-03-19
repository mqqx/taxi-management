import { MinutesToHumanPipe } from './minutes-to-human.pipe';

describe('MinutesToHumanPipe', () => {
  it('create an instance', () => {
    const pipe = new MinutesToHumanPipe();
    expect(pipe).toBeTruthy();
  });
  it('should transform minutes to proper HH:mm format', () => {
    const pipe = new MinutesToHumanPipe();
    expect(pipe.transform(123)).toEqual('02:03');
  });
});
