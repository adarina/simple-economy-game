import { Component, OnInit } from '@angular/core';
import { ResourceDTO } from '../dto/resource-dto';
import { UserDTO } from '../dto/user-dto';
import { ResourceService } from '../resource.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  users: UserDTO[] = [];

  public _login: string;

  public _password: string;

  constructor() { }

  ngOnInit(): void {
    
  }

  get user(): string {
    let user = localStorage.getItem('user');
    if (user != null) {
      user = localStorage.getItem('user');
      let parsed = JSON.parse(user);
      user = parsed.login;
    } 
    return user;
  }

  set login(login: string) {
    this._login = login;
  }

  get login(): string {
    return this._login;
  }

  set password(password: string) {
    this._password = password;
  }

  get password(): string {
    return this._password;
  }

  onLogin(): void {
    let user = {
      auth: btoa(this._login + ":" + this._password),
      login: this._login
    }
    localStorage.setItem('user', JSON.stringify(user));
  }

  onLogout(): void {
    localStorage.removeItem('user');
  }
}
