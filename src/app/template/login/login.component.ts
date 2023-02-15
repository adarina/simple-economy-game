import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/user/service/user.service';
import { User } from '../../user/model/user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})


export class LoginComponent implements OnInit {

  private _users: Array<User>;

  public _login: string;

  public _logout: string;

  public _password: string;

  public _id: number;

  public _role: string;

  public _errorMessage: string;

  constructor(private _userService: UserService, private _route: ActivatedRoute, private _router: Router) { }

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

  set logout(logout: string) {
    this._logout = logout;
  }

  get logout(): string {
    return this._logout;
  }

  set password(password: string) {
    this._password = password;
  }

  get password(): string {
    return this._password;
  }

  set role(role: string) {
    this._role = role;
  }

  get role(): string {
    return this._role;
  }

  set errorMessage(errorMessage: string) {
    this._errorMessage = errorMessage;
  }

  get errorMessage(): string {
    return this._errorMessage;
  }

  onLogin(): void {
    this.loginUser();
  }

  getUsers(): void {
    if (this._route.snapshot.paramMap) {
      this._userService.getUsers(this._route.snapshot.paramMap.get('users')).subscribe(value => {
        this._users = value;
      },
        error => {
          console.log(error);
          console.log(error.status);
          console.log(error.error);
        });
    }
  }

  logoutUser(): void {
    this._userService.logoutUser().subscribe(data => {

    },
      error => {
        console.log(error);
        console.log(error.status);
        console.log(error.error);
      });
    localStorage.removeItem('user');
    this._router.navigateByUrl('/');
  }

  onLogout(): void {
    this.logoutUser();
  }

  loginUser() {

    let payload = { username: this._login, password: this._password };

    this._userService.loginUser(payload).subscribe((res: any) => {
      let user = {
        login: this._login,
        id: res.id,
        role: res.role,
        token: res.accessToken,
      }
      localStorage.setItem('user', JSON.stringify(user));
      this._router.navigateByUrl('/buildings');
    },
      () => {
        this._errorMessage = "Password or username is wrong";
      })
  }
}