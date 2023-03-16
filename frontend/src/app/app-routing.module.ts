import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DriversComponent } from './drivers/drivers.component';
import { CustomersComponent } from './customers/customers.component';
import { TaxisComponent } from './taxis/taxis.component';
import { LocationsComponent } from './locations/locations.component';

const routes: Routes = [
  {
    path: 'drivers',
    component: DriversComponent,
  },
  {
    path: 'taxis',
    component: TaxisComponent,
  },
  {
    path: 'customers',
    component: CustomersComponent,
  },
  {
    path: 'locations',
    component: LocationsComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
