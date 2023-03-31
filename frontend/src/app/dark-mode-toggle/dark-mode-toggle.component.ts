import { Component } from '@angular/core';
import { DocsSiteTheme, ThemeStorage } from './theme-storage/theme-storage';
import { StyleManager } from '../shared/style-manager';
import { LiveAnnouncer } from '@angular/cdk/a11y';

@Component({
  selector: 'tm-dark-mode-toggle',
  templateUrl: './dark-mode-toggle.component.html',
  styleUrls: ['./dark-mode-toggle.component.scss'],
})
export class DarkModeToggleComponent {
  _isDark = true;
  currentTheme: DocsSiteTheme | undefined;

  // The below themes need to align with the bundled themes in angular.json
  themes: DocsSiteTheme[] = [
    {
      displayName: 'Indigo & Pink',
      name: 'light-theme',
      isDark: false,
    },
    {
      displayName: 'Pink & Blue-grey',
      name: 'dark-theme',
      isDefault: true,
      isDark: true,
    },
  ];

  constructor(
    public styleManager: StyleManager,
    private _themeStorage: ThemeStorage,
    private liveAnnouncer: LiveAnnouncer
  ) {
    const themeName = this._themeStorage.getStoredThemeName();
    if (themeName) {
      this.selectTheme(themeName);
    } else {
      this.themes.find((themes) => {
        if (themes.isDefault === true) {
          this.selectTheme(themes.name);
        }
      });
    }
  }

  selectTheme(themeName: string) {
    const theme = this.themes.find(
      (currentTheme) => currentTheme.name === themeName
    );

    if (!theme) {
      return;
    }

    this.currentTheme = theme;
    this._isDark = this.currentTheme.isDark;

    this.styleManager.setStyle('theme', `${theme.name}.css`);

    if (this.currentTheme) {
      this.liveAnnouncer.announce(
        `${theme.displayName} theme selected.`,
        'polite',
        3000
      );
      this._themeStorage.storeTheme(this.currentTheme);
    }
  }

  get isDark(): boolean {
    return this._isDark;
  }

  set isDark(value: boolean) {
    // Noop if the value is the same as is already set.
    if (value !== this._isDark) {
      this._isDark = value;
      this.selectTheme(this._isDark ? 'dark-theme' : 'light-theme');
    }
  }
}
