import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { timer } from 'rxjs';
import { Unit } from '../../model/unit';
import { UnitService } from '../../service/unit.service';
//import { Unit } from '../../../model/unit';
//import { UnitService } from '../../../unit.service';

@Component({
  selector: 'app-units',
  templateUrl: './units.component.html',
  styleUrls: ['./units.component.css']
})
export class UnitsComponent implements OnInit {

  private _units: Array<Unit>;

  constructor(private _unitService: UnitService, private _route: ActivatedRoute) { }

  ngOnInit(): void {
    //timer(100, 100).subscribe(timer => {
      this.getUnits();
    //});
  }

  getUnits(): void {
    if (this._route.snapshot.paramMap) {
      this._unitService.getUnits(this._route.snapshot.paramMap.get('units')).subscribe(value => {
        this._units = value
      },
      error => {
        console.log(error);
        console.log(error.status);
        console.log(error.error);
      });
    }
  }

  get units(): Array<Unit> {
    return this._units;
  }

  @Input()
  set units(units: Array<Unit>) {
    this._units = units;
  }


  /*addBuilding(type: string): void {
    this._buildingService.addBuilding(type).subscribe(data => {
      this.ngOnInit();
    },
      error => {
        console.log(error);
        console.log(error.status);
        console.log(error.error);
      });
  }*/
}
