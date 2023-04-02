import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchFilterComponent } from './search-filter.component';
import { Customer } from '../../gen';

describe('SearchFilterComponent', () => {
  let component: SearchFilterComponent<Customer>;
  let fixture: ComponentFixture<SearchFilterComponent<Customer>>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SearchFilterComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(SearchFilterComponent<Customer>);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
