import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../service/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  constructor(private _userService: UserService, private _router: Router) { }

  public _username: string;

  public _password: string;

  public _confirmation: string;

  ngOnInit(): void {
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
    if (this._confirmation === "") {
      prompt("Confirm Password is required");
    } else if (this._password === "") {
      prompt("Password is required");
    } else if (this._password === this._confirmation) {
      this._userService.addUser(this._username, this._password).subscribe(data => {
        this._router.navigateByUrl('/');
      },
        error => {
          console.log(error);
          console.log(error.status);
          console.log(error.error);
        });
    } else {
      prompt("Passwords must match");
    }
  }
}
