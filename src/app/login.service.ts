import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private _http: HttpClient) { }
  
  get authoriztion() {
    let user = localStorage.getItem('user');
    let parsed = JSON.parse(user);
    return parsed.auth;
  }

  
}
