import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-building-management',
  templateUrl: './building-management.component.html',
  styleUrls: ['./building-management.component.css']
})
export class BuildingManagementComponent implements OnInit {

  constructor(private _router: Router) { }

  ngOnInit(): void {
    if (!localStorage.getItem('user')) {
      this._router.navigate(['/']);
    }
  }
}
