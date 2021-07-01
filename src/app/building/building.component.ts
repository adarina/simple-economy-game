import { Component, OnInit, Input } from '@angular/core';
import { Building } from '../model/building';

@Component({
  selector: 'app-building',
  templateUrl: './building.component.html',
  styleUrls: ['./building.component.css']
})
export class BuildingComponent implements OnInit {

  private _building: Building;

  id: number;

  type: string;

  constructor() { }

  ngOnInit(): void {
  }

  @Input()
  set building(building:Building) {
    this._building = building;
  }

  get building(): Building {
    return this._building;
  }
}
