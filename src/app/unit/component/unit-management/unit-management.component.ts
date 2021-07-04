import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-unit-management',
  templateUrl: './unit-management.component.html',
  styleUrls: ['./unit-management.component.css']
})
export class UnitManagementComponent implements OnInit {

  constructor(private _router: Router) { }

  ngOnInit(): void {
    if (!localStorage.getItem('user')) {
      this._router.navigate(['/']);
    }
  }
}
