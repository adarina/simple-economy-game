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
          value.users.forEach(user => {
              users.push(new User(user.id, user.username, user.password)); 
          })
          return users;
        }))
  }

  addUser(username: string, password: string) {
    let headers = new HttpHeaders();
    headers = headers.set('Data-Type', 'json');
    let newUser: User = new User(null, username, password);
    console.log(newUser)
    return this._http.post('http://localhost:8080/api/users', newUser, {headers});
  }
}
