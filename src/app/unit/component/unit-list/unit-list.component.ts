import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Unit } from '../../model/unit';
import { UnitService } from '../../service/unit.service';

@Component({
  selector: 'app-unit-list',
  templateUrl: './unit-list.component.html',
  styleUrls: ['./unit-list.component.css']
})
export class UnitListComponent implements OnInit {

  private _units: Array<Unit>;

  constructor(private _unitService: UnitService, private _route: ActivatedRoute) { }

  ngOnInit(): void {
      this.getUnits();
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
}
