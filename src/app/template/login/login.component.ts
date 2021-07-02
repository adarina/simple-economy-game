import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../../user/model/user';
import { LoginService } from '../../user/service/login.service';
//import { LoginService } from '../login.service';
//import { User } from '../model/user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  private _users: Array<User>;

  public _login: string;

  public _password: string;

  public _id: number;

  constructor(private _loginService: LoginService, private _route: ActivatedRoute, private _router: Router) { }

  ngOnInit(): void {
    this.getUsers();
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
    this.checkUser();
    this._router.navigateByUrl('/buildings');
  }

  getUsers(): void {
    if (this._route.snapshot.paramMap) {
      this._loginService.getUsers(this._route.snapshot.paramMap.get('users')).subscribe(value => { 
        this._users = value;
      },
      error => {
        console.log(error);
        console.log(error.status);
        console.log(error.error);
      });
    }
  }

  checkUser(): void {
    console.log(this._users)
    this._users.forEach(current => {
     if(current.username === this._login) {
        let user = {
          auth: btoa(current.id + ":" + this._password),
          id: current.id,
          login: current.username
        }
        localStorage.setItem('user', JSON.stringify(user));
      }
    })
  }

  onLogout(): void {
    localStorage.removeItem('user');
    this._router.navigateByUrl('/');
  }
}
