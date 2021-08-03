import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { GetUnitResponse } from '../dto/get-unit-response';
import { Unit } from '../model/unit';

@Injectable({
  providedIn: 'root'
})
export class UnitService {

  constructor(private _http: HttpClient) { }

  getUnits(name: string): Observable<Array<Unit>> {
    let user = JSON.parse(localStorage.getItem('user'));
    let headers = new HttpHeaders();
    headers = headers.set('Data-Type', 'json');
    return this._http.get<GetUnitResponse>('http://localhost:8080/api/users/' + user.id + '/units', { headers }).
      pipe(map(value => {
        let units = new Array<Unit>();
        value.units.forEach(unit => {
          units.push(new Unit(unit.id, unit.type, unit.amount, unit.active));
        })
        return units;
      }))
  }

  getUnit(id: number): Observable<Unit> {
    let user = JSON.parse(localStorage.getItem('user'));
    let headers = new HttpHeaders();
    headers = headers.set('Data-Type', 'json');
    return this._http.get<Unit>('http://localhost:8080/api/users/' + user.id + '/units/' + id, { headers })
  }

  updateUnit(id: number, type: string, amount: number) {
   
    let user = JSON.parse(localStorage.getItem('user'));
    let unit: Unit = new Unit(id, type, amount, true)
    let headers = new HttpHeaders();
    headers = headers.set('Content-Type', 'application/json');

    return this._http.put('http://localhost:8080/api/users/' + user.id + '/units/' + unit.id, unit, { headers });
  }
}