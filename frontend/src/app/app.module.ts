import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { HeaderComponent } from './header/header.component';
import { DarkModeToggleComponent } from './dark-mode-toggle/dark-mode-toggle.component';
import { DriversComponent } from './drivers/drivers.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MAT_DATE_LOCALE } from '@angular/material/core';
import {
  MatPaginatorIntl,
  MatPaginatorModule,
} from '@angular/material/paginator';
import { getGermanPaginatorIntl } from './shared/german-paginator-intl';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { HttpClientModule } from '@angular/common/http';
import { CustomersComponent } from './customers/customers.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import {
  MAT_LUXON_DATE_ADAPTER_OPTIONS,
  MatLuxonDateModule,
} from '@angular/material-luxon-adapter';
import { MatInputModule } from '@angular/material/input';
import { DriverDialogComponent } from './drivers/driver-dialog/driver-dialog.component';
import { MatDialogModule } from '@angular/material/dialog';
import { TaxisComponent } from './taxis/taxis.component';
import { TaxiDialogComponent } from './taxis/taxi-dialog/taxi-dialog.component';
import { LocationsComponent } from './locations/locations.component';
import { ShiftsComponent } from './shifts/shifts.component';
import { DATE_PIPE_DEFAULT_OPTIONS } from '@angular/common';
import { MinutesToHumanPipe } from './shared/minutes-to-human.pipe';
import { NgxsModule } from '@ngxs/store';
import { TaxisState } from './taxis/store/taxi.state';
import { NgxsReduxDevtoolsPluginModule } from '@ngxs/devtools-plugin';
import { NgxsLoggerPluginModule } from '@ngxs/logger-plugin';
import { environment } from '../environments/environment';
import { DriversState } from './drivers/store/driver.state';
import { LocationsState } from './locations/store/location.state';
import { CustomersState } from './customers/store/customer.state';
import { ShiftsState } from './shifts/store/shift.state';
import { ShiftDialogComponent } from './shifts/shift-dialog/shift-dialog.component';
import { MatSelectModule } from '@angular/material/select';
import { DriverToNamePipe } from './drivers/driver-to-name.pipe';
import { StyleManager } from './shared/style-manager';
import { ThemeStorage } from './dark-mode-toggle/theme-storage/theme-storage';

const dev = environment.production
  ? []
  : [NgxsReduxDevtoolsPluginModule.forRoot(), NgxsLoggerPluginModule.forRoot()];

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    DarkModeToggleComponent,
    DriversComponent,
    DriverDialogComponent,
    CustomersComponent,
    TaxisComponent,
    TaxiDialogComponent,
    LocationsComponent,
    ShiftsComponent,
    ShiftDialogComponent,
    MinutesToHumanPipe,
    DriverToNamePipe,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatSlideToggleModule,
    MatPaginatorModule,
    MatIconModule,
    MatTableModule,
    MatSortModule,
    FormsModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatInputModule,
    MatDatepickerModule,
    MatLuxonDateModule,
    MatDialogModule,
    MatSelectModule,
    NgxsModule.forRoot([
      TaxisState,
      DriversState,
      LocationsState,
      CustomersState,
      ShiftsState,
    ]),
    ...dev,
  ],
  providers: [
    {
      provide: MAT_DATE_LOCALE,
      useValue: 'de-DE',
    },
    {
      provide: MatPaginatorIntl,
      useValue: getGermanPaginatorIntl(),
    },
    {
      provide: MAT_LUXON_DATE_ADAPTER_OPTIONS,
      useValue: { useUtc: true, firstDayOfWeek: 1 },
    },
    {
      provide: DATE_PIPE_DEFAULT_OPTIONS,
      useValue: { dateFormat: 'dd.MM.yy' },
    },
    StyleManager,
    ThemeStorage,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
