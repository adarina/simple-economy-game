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

  public _errorMessage: string;

  ngOnInit(): void {
    this._errorMessage = "\n";
  }

  set username(username: string) {
    this._username = username;
  }

  get username(): string {
    return this._username;
  }

  set errorMessage(errorMessage: string) {
    this._errorMessage = errorMessage;
  }

  get errorMessage(): string {
    return this._errorMessage;
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
      this._errorMessage = "Confirm password is required";
    } else if (this._password === "") {
      this._errorMessage = "Password is required";
    } else if (this._password === this._confirmation) {
      this._userService.addUser(this._username, this._password).subscribe(data => {
        window.location.reload();
      },
        error => {
          console.log(error);
          console.log(error.status);
          console.log(error.error);
          this._errorMessage = "An error occurred while registering the user.";
        });
    } else {
      this._errorMessage = "Passwords must match";
    }
  }
}
