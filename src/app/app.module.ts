import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegisterComponent } from './user/component/register/register.component';
import { FooterComponent } from './template/footer/footer.component';
import { HeaderComponent } from './template/header/header.component';
import { LoginComponent } from './template/login/login.component';
//import { BuildingsListComponent } from './building/buildings-list/buildings-list.component';
import { ResourcesComponent } from './resource/component/resources/resources.component';
//import { BuildingsManagementComponent } from './building/buildings-management/buildings-management.component';
import { UnitsManagementComponent } from './unit/component/units-management/units-management.component';
import { BuildingSingleComponent } from './building/component/building-single/building-single.component';
import { ResourceComponent } from './resource/component/resource/resource.component';
import { UnitComponent } from './unit/component/unit/unit.component';
import { UnitsComponent } from './unit/component/units/units.component';
import { NavigationComponent } from './template/navigation/navigation.component';
import { RegisterManagementComponent } from './user/component/register-management/register-management.component';
//import { BuildingsListComponent } from './building/component/building-list/building-list.component';
import { BuildingManagementComponent } from './building/component/building-management/building-management.component';
import { BuildingListComponent } from './building/component/building-list/building-list.component';
//import { RegisterManagementComponent } from './register-management/register-management.component';

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    FooterComponent,
    HeaderComponent,
    LoginComponent,
    BuildingListComponent,
    ResourcesComponent,
    BuildingManagementComponent,
    UnitsManagementComponent,
    BuildingSingleComponent,
    ResourceComponent,
    UnitComponent,
    UnitsComponent,
    NavigationComponent,
    RegisterManagementComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
