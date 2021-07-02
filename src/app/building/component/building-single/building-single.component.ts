import { Component, OnInit, Input } from '@angular/core';
import { Building } from '../../model/building';
//import { Building } from '../model/building';

@Component({
  selector: 'app-building-single',
  templateUrl: './building-single.component.html',
  styleUrls: ['./building-single.component.css']
})
export class BuildingSingleComponent implements OnInit {

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
