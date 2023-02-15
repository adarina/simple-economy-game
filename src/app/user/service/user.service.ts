import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { AppConstants } from 'src/app/common/app.constants';
import { GetUserResponse } from '../dto/get-user-response';
import { User } from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private _http: HttpClient) { }

  loginUser(payload: any) {
    return this._http.post(AppConstants.API_BASE_URL + 'login', payload)
  }

  getUsers(name: string): Observable<Array<User>> {
    let user = JSON.parse(localStorage.getItem('user'));
    let headers = new HttpHeaders({
      'Authorization': 'Bearer ' + user.token
    });
    return this._http.get<GetUserResponse>(AppConstants.API_BASE_URL + 'all', { headers }).
      pipe(map(value => {
        let users = new Array<User>();
        value.users.forEach(user => {
          users.push(new User(user.id, user.username, user.password, user.role));
        })
        return users;
      }))
  }

  addUser(username: string, password: string) {
    let headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    let newUser: User = new User(null, username, password, "ROLE_USER");
    return this._http.post(AppConstants.API_BASE_URL + 'register', newUser, { headers });
  }

  logoutUser() {
    let user = JSON.parse(localStorage.getItem('user'));
    let headers = new HttpHeaders({
      'Authorization': 'Bearer ' + user.token
    });
    return this._http.get(AppConstants.API_BASE_URL + 'logout', { headers });
  }

  deleteUser() {
    let user = JSON.parse(localStorage.getItem('user'));
    let headers = new HttpHeaders({
      'Authorization': 'Bearer ' + user.token
    });
    return this._http.delete(AppConstants.API_BASE_URL + 'delete/' + user.id, { headers });
  }

  deleteUserById(id: number) {
    let user = JSON.parse(localStorage.getItem('user'));
    let headers = new HttpHeaders({
      'Authorization': 'Bearer ' + user.token
    });
    return this._http.delete(AppConstants.API_BASE_URL + 'delete/' + id, { headers });
  }
}
