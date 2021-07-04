import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register-management',
  templateUrl: './register-management.component.html',
  styleUrls: ['./register-management.component.css']
})
export class RegisterManagementComponent implements OnInit {

  constructor(private _router: Router) { }

  ngOnInit(): void {
    if (localStorage.getItem('user')) {
      this._router.navigate(['/buildings']);
    }
  }
}
