import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Unit } from '../../model/unit';
import { UnitService } from '../../service/unit.service';

@Component({
  selector: 'app-unit-single',
  templateUrl: './unit-single.component.html',
  styleUrls: ['./unit-single.component.css']
})


export class UnitSingleComponent implements OnInit {

  private _unit: Unit;

  id: number;

  type: string;

  _amount: number;

  constructor(private _unitService: UnitService, private _route: ActivatedRoute) { }

  set amount(amount: number) {
    this._amount = amount;
  }

  get amount(): number {
    return this._amount;
  }

  ngOnInit(): void {
  }

  @Input()
  set unit(unit: Unit) {
    this._unit = unit;
  }

  get unit(): Unit {
    return this._unit;
  }

  checkTroll(checkType: string): boolean {
    if (checkType === "TROLL") {
      return true;
    }
    return false;
  }

  checkGoblin(checkType: string): boolean {
    if (checkType === "GOBLIN") {
      return true;
    }
    return false;
  }

  checkOrc(checkType: string): boolean {
    if (checkType === "ORC") {
      return true;
    }
    return false;
  }
}
