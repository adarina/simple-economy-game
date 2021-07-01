import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { GetUnitResponse } from './dto/get-unit-response';
import { UnitDTO } from './dto/unit-dto';
import { Unit } from './model/unit';

@Injectable({
  providedIn: 'root'
})
export class UnitService {

  constructor(private _http: HttpClient) { }

  getUnits(name: string): Observable<Array<Unit>> {
    let headers = new HttpHeaders();
    headers = headers.set('Data-Type', 'json');
    return this._http.get<GetUnitResponse>('http://localhost:8080/api/users/1/units', {headers}).
        pipe(map(value => {
          let units = new Array<Unit>();
          value.units.forEach(unit => {
              units.push(new Unit(unit.id, unit.type, unit.amount, unit.active)); 
          })
          return units;
        }))
  }

  getUnit(id: number): Observable<Unit> {
    let headers = new HttpHeaders();
    headers = headers.set('Data-Type', 'json');
    return this._http.get<Unit>('http://localhost:8080/api/users/1/units/'+ id, {headers})
  }

  addUnit(type: string, amount: number) {
    let headers = new HttpHeaders();
    headers = headers.set('Content-Type', 'application/json');
    let unit: Unit = new Unit(null, type, amount, null);
    console.log(unit);
    return this._http.put('http://localhost:8080/api/users/1/units/1/', unit, {headers});
  }
}