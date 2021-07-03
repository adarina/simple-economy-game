import { Component, OnInit } from '@angular/core';
import { UserService } from '../../service/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  constructor(private _userService: UserService) { }

  public _username: string;

  public _password: string;

  public _confirmation: string;

  ngOnInit(): void {
    console.log("lol")
  }

  set username(username: string) {
    this._username = username;
  }

  get username(): string {
    return this._username;
  }

  set password(password: string) {
    this._password = password;
  }

  get password(): string {
    return this._password;
  }

  set confirmation(confirmation: string) {
    this._confirmation = confirmation;
  }

  get confirmation(): string {
    return this._confirmation;
  }


  registerUser(): void {
    this._userService.addUser(this._username, this._password).subscribe(data => {
      this.ngOnInit();
    },
      error => {
        console.log(error);
        console.log(error.status);
        console.log(error.error);
      });
  }
}
