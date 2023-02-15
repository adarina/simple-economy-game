import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RegisterManagementComponent } from './user/component/register-management/register-management.component';
import { BuildingManagementComponent } from './building/component/building-management/building-management.component';
import { UnitManagementComponent } from './unit/component/unit-management/unit-management.component';

const routes: Routes = [
  {path: '', component: RegisterManagementComponent},
  {path: 'buildings', component: BuildingManagementComponent},
  {path: 'units', component: UnitManagementComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
