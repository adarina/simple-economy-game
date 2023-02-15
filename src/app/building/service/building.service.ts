import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { AppConstants } from 'src/app/common/app.constants';
import { GetBuildingResponse } from '../dto/get-building-response';
import { Building } from '../model/building';

@Injectable({
  providedIn: 'root'
})
export class BuildingService {

  constructor(private _http: HttpClient) { }

  getBuildings(name: string): Observable<Array<Building>> {
    let user = JSON.parse(localStorage.getItem('user'));
    let headers = new HttpHeaders();
    headers = headers.set('Authorization', 'Bearer ' + user.token);
    return this._http.get<GetBuildingResponse>(AppConstants.API_BASE_URL + user.id + '/buildings', { headers }).
      pipe(map(value => {
        let buildings = new Array<Building>();
        value.buildings.forEach(building => {
          buildings.push(new Building(building.id, building.type));
        })
        return buildings;
      }))
  }

  addBuilding(type: string) {
    let user = JSON.parse(localStorage.getItem('user'));
    let headers = new HttpHeaders();
    headers = headers.set('Authorization', 'Bearer ' + user.token);
    let building: Building = new Building(null, type);
    return this._http.post(AppConstants.API_BASE_URL + user.id + '/buildings', building, { headers });
  }

  getBuilding(id: number): Observable<Building> {
    let user = JSON.parse(localStorage.getItem('user'));
    let headers = new HttpHeaders();
    headers = headers.set('Authorization', 'Bearer ' + user.token);
    return this._http.get<Building>(AppConstants.API_BASE_URL + user.id + '/buildings/' + id, { headers })
  }
}
