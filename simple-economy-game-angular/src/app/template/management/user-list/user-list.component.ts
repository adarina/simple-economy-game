import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/app/user/model/user';
import { UserService } from 'src/app/user/service/user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  private _users: Array<User>;

  private _role: any;

  constructor(private _userService: UserService, private _route: ActivatedRoute) { }

  ngOnInit(): void {
    let user = JSON.parse(localStorage.getItem('user'));
    this._role = user.role
    if (this._role === 'ROLE_ADMIN') {
      this.getUsers();
    }
  }

  getUsers() {
    if (this._route.snapshot.paramMap) {
      this._userService.getUsers(this._route.snapshot.paramMap.get('users')).subscribe(value => {
        this._users = value;
      },
        error => {
          console.log(error);
          console.log(error.status);
          console.log(error.error);
        })
    }
  }

  get users(): Array<User> {
    return this._users;
  }

  @Input()
  set users(users: Array<User>) {
    this._users = users;
  }

  get user(): boolean {
    if (this._role === 'ROLE_ADMIN') {
      return true;
    }
    return false;
  }
}
