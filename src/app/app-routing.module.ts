import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BuildingsComponent } from './buildings/buildings.component';
import { RegisterComponent } from './register/register.component';


const routes: Routes = [
  {path: '', component: RegisterComponent},
  {path: 'buildings', component: BuildingsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
