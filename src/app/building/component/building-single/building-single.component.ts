import { Component, OnInit, Input } from '@angular/core';
import { Building } from '../../model/building';

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
  set building(building: Building) {
    this._building = building;
  }

  get building(): Building {
    return this._building;
  }

  checkCottage(checkType: string): boolean {
    if (checkType === "COTTAGE") {
      return true;
    }
    return false;
  }

  checkQuarry(checkType: string): boolean {
    if (checkType === "QUARRY") {
      return true;
    }
    return false;
  }

  checkHut(checkType: string): boolean {
    if (checkType === "HUT") {
      return true;
    }
    return false;
  }

  checkCavern(checkType: string): boolean {
    if (checkType === "CAVERN") {
      return true;
    }
    return false;
  }

  checkPit(checkType: string): boolean {
    if (checkType === "PIT") {
      return true;
    }
    return false;
  }

  checkCave(checkType: string): boolean {
    if (checkType === "CAVE") {
      return true;
    }
    return false;
  }
}
