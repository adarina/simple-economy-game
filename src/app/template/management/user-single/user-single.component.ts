import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/user/model/user';
import { UserService } from 'src/app/user/service/user.service';

@Component({
  selector: 'app-user-single',
  templateUrl: './user-single.component.html',
  styleUrls: ['./user-single.component.css']
})
export class UserSingleComponent implements OnInit {

  private _user: User;

  id: number;

  _value: string;

  constructor(private _userService: UserService, private _router: Router) { }

  ngOnInit(): void {
  }

  @Input()
  set user(user: User) {
    this._user = user;
  }

  get user(): User {
    return this._user;
  }

  deleteUser(id: number): void {
    let user = JSON.parse(localStorage.getItem('user'));
    this._userService.deleteUserById(id).subscribe(data => {
      if (id == user.id) {
        localStorage.removeItem('user');
        this._router.navigateByUrl('/');
      } else {
        let currentUrl = this._router.url;
        this._router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
          this._router.navigate([currentUrl]);
        });
      }
    },
      error => {
        console.log(error);
        console.log(error.status);
        console.log(error.error);
      });
  }

  onItemSelector(value: string) {
    this._value = value;
  }
}