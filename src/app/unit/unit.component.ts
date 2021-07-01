import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Unit } from '../model/unit';
import { UnitService } from '../unit.service';
//import { UnitsComponent } from '../units/units.component';

@Component({
  selector: 'app-unit',
  templateUrl: './unit.component.html',
  styleUrls: ['./unit.component.css']
})


export class UnitComponent implements OnInit {

  private _unit: Unit;

  id: number;

  type: string;

  _amount: number;

  active: boolean;

  private _units: Array<Unit>;

  constructor(private _unitService: UnitService/*, private _unitsComponent: UnitsComponent*/,private _route: ActivatedRoute) { }

  set amount(amount: number) {
    this._amount = amount;
  }

  get amount(): number {
    return this._amount;
  }


  ngOnInit(): void {
  }

  getUnit(id: number) : void {
    this._unitService.getUnit(id).subscribe(data => { 
      this._unit.amount = data.amount;
    },
    error => {
      console.log(error); 
      console.log(error.status); 
      console.log(error.error);
    });
  }

  editUnit(id: number, type: string): void {
    console.log(this._amount);
    this._unitService.addUnit(type, this._amount).subscribe(data => {
      this.getUnit(id);
    },
      error => {
        console.log(error);
        console.log(error.status);
        console.log(error.error);
      });
  }

  @Input()
  set unit(unit: Unit) {
    this._unit = unit;
  }

  get unit(): Unit {
    return this._unit;
  }
}
