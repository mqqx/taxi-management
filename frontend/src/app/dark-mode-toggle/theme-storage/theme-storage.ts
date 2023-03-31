import { Injectable, EventEmitter } from '@angular/core';

export interface DocsSiteTheme {
  name: string;
  displayName: string;
  isDark: boolean;
  isDefault?: boolean;
}

@Injectable()
export class ThemeStorage {
  static storageKey = 'TAXI_MANAGEMENT_THEME';

  onThemeUpdate: EventEmitter<DocsSiteTheme> =
    new EventEmitter<DocsSiteTheme>();

  storeTheme(theme: DocsSiteTheme) {
    try {
      window.localStorage[ThemeStorage.storageKey] = theme.name;
    } catch {
      /* empty */
    }

    this.onThemeUpdate.emit(theme);
  }

  getStoredThemeName(): string | null {
    try {
      return window.localStorage[ThemeStorage.storageKey] || null;
    } catch {
      return null;
    }
  }

  clearStorage() {
    try {
      window.localStorage.removeItem(ThemeStorage.storageKey);
    } catch {
      /* empty */
    }
  }
}
