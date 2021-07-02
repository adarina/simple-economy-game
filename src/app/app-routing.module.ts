import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
//import { BuildingsManagementComponent } from './building/buildings-management/buildings-management.component';
import { RegisterManagementComponent } from './user/component/register-management/register-management.component';
import { UnitsManagementComponent } from './unit/component/units-management/units-management.component';
import { BuildingManagementComponent } from './building/component/building-management/building-management.component';


const routes: Routes = [
  {path: '', component: RegisterManagementComponent},
  {path: 'buildings', component: BuildingManagementComponent},
  {path: 'units', component: UnitsManagementComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
