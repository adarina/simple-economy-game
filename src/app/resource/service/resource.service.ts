import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { AppConstants } from 'src/app/common/app.constants';
import { GetResourceResponse } from '../dto/get-resource-response';
import { Resource } from '../model/resource';

@Injectable({
  providedIn: 'root'
})
export class ResourceService {

  constructor(private _http: HttpClient) { }

  getResources(name: string): Observable<Array<Resource>> {
    let user = JSON.parse(localStorage.getItem('user'));
    if (user !== null) {
      let headers = new HttpHeaders();
      headers = headers.set('Authorization', 'Bearer ' + user.token);
      return this._http.get<GetResourceResponse>(AppConstants.API_BASE_URL + user.id + '/resources', { headers }).
        pipe(map(value => {
          let resources = new Array<Resource>();
          value.resources.forEach(resource => {
            resources.push(new Resource(resource.id, resource.type, resource.amount));
          })
          return resources;
        }))
    }
    return null;
  }
}
