import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, timer } from 'rxjs';
import { debounce, map } from 'rxjs/operators';
import { GetResourceResponse } from '../dto/get-resource-response';
import { Resource } from '../model/resource';
//import { GetResourceResponse } from './dto/get-resource-response';
//import { ResourceDTO } from './dto/resource-dto';
//import { Resource } from './model/resource';


@Injectable({
  providedIn: 'root'
})
export class ResourceService {

  get authoriztion() {
    let user = localStorage.getItem('user');
    let parsed = JSON.parse(user);
    return parsed.auth;
  }

  constructor(private _http: HttpClient) { }

  getResources(name: string): Observable<Array<Resource>> {
    let user = JSON.parse(localStorage.getItem('user'));
    if(user !== null) {
    let headers = new HttpHeaders();
    headers = headers.set('Data-Type', 'json');
    return this._http.get<GetResourceResponse>('http://localhost:8080/api/users/'+ user.id +'/resources', {headers}).
        pipe(map(value => {
          let resources = new Array<Resource>();
          value.resources.forEach(resource => {
              resources.push(new Resource(resource.id, resource.type, resource.amount)); 
          })
          return resources;
        }))
  } return null;
  }
}
