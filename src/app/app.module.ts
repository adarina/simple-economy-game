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
import { ResourceListComponent } from './resource/component/resource-list/resource-list.component';
import { BuildingSingleComponent } from './building/component/building-single/building-single.component';
import { ResourceSingleComponent } from './resource/component/resource-single/resource-single.component';
import { NavigationComponent } from './template/navigation/navigation.component';
import { RegisterManagementComponent } from './user/component/register-management/register-management.component';
import { BuildingListComponent } from './building/component/building-list/building-list.component';
import { BuildingManagementComponent } from './building/component/building-management/building-management.component';
import { UnitSingleComponent } from './unit/component/unit-single/unit-single.component';
import { UnitListComponent } from './unit/component/unit-list/unit-list.component';
import { UnitManagementComponent } from './unit/component/unit-management/unit-management.component';

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    FooterComponent,
    HeaderComponent,
    LoginComponent,
    BuildingListComponent,
    ResourceListComponent,
    UnitManagementComponent,
    BuildingSingleComponent,
    ResourceSingleComponent,
    UnitSingleComponent,
    UnitListComponent,
    NavigationComponent,
    RegisterManagementComponent,
    BuildingManagementComponent
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
