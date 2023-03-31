import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DarkModeToggleComponent } from './dark-mode-toggle.component';
import { MatIconModule } from '@angular/material/icon';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { StyleManager } from '../shared/style-manager';
import { ThemeStorage } from './theme-storage/theme-storage';

describe('DarkModeToggleComponent', () => {
  let component: DarkModeToggleComponent;
  let fixture: ComponentFixture<DarkModeToggleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DarkModeToggleComponent],
      imports: [MatIconModule, MatSlideToggleModule],
      providers: [StyleManager, ThemeStorage],
    }).compileComponents();

    fixture = TestBed.createComponent(DarkModeToggleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
