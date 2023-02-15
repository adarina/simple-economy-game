import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/user/service/user.service';

@Component({
  selector: 'app-user-delete',
  templateUrl: './user-delete.component.html',
  styleUrls: ['./user-delete.component.css']
})
export class UserDeleteComponent implements OnInit {

  constructor(private _userService: UserService, private _router: Router) { }

  ngOnInit(): void {
  }

  deleteUser(): void {
    this._userService.deleteUser().subscribe(data => {
      localStorage.removeItem('user');
      this._router.navigateByUrl('/');
    },
      error => {
        console.log(error);
        console.log(error.status);
        console.log(error.error);
      });
  }
}
