import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BuildingsManagementComponent } from './buildings-management/buildings-management.component';
import { RegisterManagementComponent } from './register-management/register-management.component';
import { UnitsManagementComponent } from './units-management/units-management.component';


const routes: Routes = [
  {path: '', component: RegisterManagementComponent},
  {path: 'buildings', component: BuildingsManagementComponent},
  {path: 'units', component: UnitsManagementComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
