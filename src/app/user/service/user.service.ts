import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { User } from '../model/user';
import { GetUserResponse } from '../dto/get-user-response';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private _http: HttpClient) { }
  
  get authoriztion() {
    let user = localStorage.getItem('user');
    let parsed = JSON.parse(user);
    return parsed.auth;
  }

  getUsers(name: string): Observable<Array<User>> {
    let headers = new HttpHeaders();
    headers = headers.set('Data-Type', 'json');
    return this._http.get<GetUserResponse>('http://localhost:8080/api/users', {headers}).
        pipe(map(value => {
          let users = new Array<User>();
          value.users.forEach(resource => {
              users.push(new User(resource.id, resource.username)); 
          })
          //console.log(users)
          return users;
        }))
  }
  
}
