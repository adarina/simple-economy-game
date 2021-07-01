import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegisterComponent } from './register/register.component';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { LoginComponent } from './login/login.component';
import { BuildingsComponent } from './buildings/buildings.component';
import { ResourcesComponent } from './resources/resources.component';
import { BuildingsManagementComponent } from './buildings-management/buildings-management.component';
import { UnitsManagementComponent } from './units-management/units-management.component';
import { BuildingComponent } from './building/building.component';
import { ResourceComponent } from './resource/resource.component';
import { UnitComponent } from './unit/unit.component';
import { UnitsComponent } from './units/units.component';

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    FooterComponent,
    HeaderComponent,
    LoginComponent,
    BuildingsComponent,
    ResourcesComponent,
    BuildingsManagementComponent,
    UnitsManagementComponent,
    BuildingComponent,
    ResourceComponent,
    UnitComponent,
    UnitsComponent
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
