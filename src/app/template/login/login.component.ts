import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
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

  constructor(private _userService: UserService, private _route: ActivatedRoute, private _router: Router) { }

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

  onLogin(): void {
    this.checkUser();
    this._router.navigateByUrl('/buildings');
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

  checkUser(): void {
    console.log(this._users)
    this._users.forEach(current => {
      if (current.username === this._login) {
        let user = {
          auth: btoa(current.id + ":" + this._password),
          id: current.id,
          login: current.username,
          role: current.role
          
        }
        localStorage.setItem('user', JSON.stringify(user));
      }
    })
  }

  onLogout(): void {
    this._router.navigateByUrl('/');
    localStorage.removeItem('user');
  }
}
