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

  selectedUnit: any;

  _amount: number;

  active: boolean;

  _value: string;

  public _errorMessage: string;

  constructor(private _unitService: UnitService, private _route: ActivatedRoute) { }

  ngOnInit(): void {
    this._errorMessage = "\n"
    this.getUnits();

  }

  set amount(amount: number) {
    this._amount = amount;
  }

  get amount(): number {
    return this._amount;
  }

  set errorMessage(errorMessage: string) {
    this._errorMessage = errorMessage;
  }

  get errorMessage(): string {
    return this._errorMessage;
  }

  getUnits(): void {
    if (this._route.snapshot.paramMap) {
      this._unitService.getUnits(this._route.snapshot.paramMap.get('units')).subscribe(value => {
        this._units = value
        this.selectedUnit = this._units[0];

      },
        error => {
          console.log(error);
          console.log(error.status);
          console.log(error.error);
        });
    }
  }

  editUnit(id: number, type: string): void {
    if (this._value == 'release') {
      this._amount = -this._amount;
    }
    this._unitService.updateUnit(id, type, this._amount).subscribe(data => {
      this.getUnit(id);
    },
      () => {
        this._errorMessage = "Insufficient resources or you can't recruit this unit or all units have been released";
      });
    this._amount = null;
  }

  onItemSelector(value: string) {
    this._value = value;
  }

  getUnit(id: number): void {
    this._unitService.getUnit(id).subscribe(data => {

      this.selectedUnit.amount = data.amount;
    },
      error => {
        console.log(error);
        console.log(error.status);
        console.log(error.error);
      });
  }

  get units(): Array<Unit> {
    return this._units;
  }

  @Input()
  set units(units: Array<Unit>) {
    this._units = units;
  }
}
