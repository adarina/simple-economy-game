import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { timer } from 'rxjs';
import { Resource } from 'src/app/resource/model/resource';
import { ResourceService } from 'src/app/resource/service/resource.service';
import { Building } from '../../model/building';
import { BuildingService } from '../../service/building.service';

@Component({
  selector: 'app-building-list',
  templateUrl: './building-list.component.html',
  styleUrls: ['./building-list.component.css']
})
export class BuildingListComponent implements OnInit {


  private _buildings: Array<Building>;

  private _resources: Array<Resource>;

  private _building: Building;

  constructor(private _buildingService: BuildingService, private _route: ActivatedRoute, private _resourceService: ResourceService) { }

  ngOnInit(): void {
    let sub = timer(0, 1000).subscribe(timer => {
      if (localStorage.getItem('user') === null) {
        sub.unsubscribe();
      } else {
        this.getBuildings();
        this.getResources();
      }
    });
  }

  getBuildings(): void {
    if (this._route.snapshot.paramMap) {
      this._buildingService.getBuildings(this._route.snapshot.paramMap.get('buildings')).subscribe(value => {
        this._buildings = value;
      },
        error => {
          console.log(error);
          console.log(error.status);
          console.log(error.error);
        })
    }
  }

  getResources(): void {
    if (this._route.snapshot.paramMap) {
      this._resourceService.getResources(this._route.snapshot.paramMap.get('resources')).subscribe(value => {
        this._resources = value;
      },
        error => {
          console.log(error);
          console.log(error.status);
          console.log(error.error);
        });
    }
  }

  get buildings(): Array<Building> {
    return this._buildings;
  }

  @Input()
  set buildings(buildings: Array<Building>) {
    this._buildings = buildings;
  }

  addBuilding(type: string): void {
    this._buildingService.addBuilding(type).subscribe(data => {
      this.ngOnInit();
    },
      error => {
        console.log(error);
        console.log(error.status);
        console.log(error.error);
      });
  }

  getBuilding(id: number): void {
    this._buildingService.getBuilding(id).subscribe(data => {
      this._building = data;
    },
      error => {
        console.log(error);
        console.log(error.status);
        console.log(error.error);
      });
  }

  checkMudGatherersCottage(): boolean {
    let check = true;
    if (this._buildings == undefined) {
      check = false;
    } else if (this._resources == undefined) {
      check = false;
    } else {
      this._resources.forEach(current => {
        if (current.type === "MUD" && current.amount <= 100) {
          check = false;
        }
      }
      )
    };
    return check;
  }

  checkStoneQuarry(): boolean {
    let check = true;
    if (this._buildings == undefined) {
      check = false;
    } else if (this._resources == undefined) {
      check = false;
    } else {
      this._resources.forEach(current => {
        if (current.type === "MUD" && current.amount <= 1000) {
          check = false;
        }
      }
      )
    };
    return check;
  }

  checkHuntersHut(): boolean {
    let check = true;
    let firstTmp = false
    let secondTmp = false
    if (this._buildings == undefined) {
      check = false;
    } else if (this._resources == undefined) {
      check = false;
    } else {
      this._resources.forEach(current => {
        if (current.type === "MUD" && current.amount >= 2000) {
          firstTmp = true;
        } else if (current.type === "STONE" && current.amount >= 1000) {
          secondTmp = true;
        }
      }
      )
    };
    if (!(firstTmp && secondTmp)) {
      check = false;
    }
    return check;
  }

  checkGoblinsCavern(): boolean {
    let firstTmp = false
    let secondTmp = false
    if (this._buildings == undefined) {
      return false;
    } else if (this._resources == undefined) {
      return false;
    } else {
      this._resources.forEach(current => {
        if (current.type === "MUD" && current.amount >= 4000) {
          firstTmp = true;
        } else if (current.type === "STONE" && current.amount >= 2000) {
          secondTmp = true;
        }
      });
      if (!(firstTmp && secondTmp)) {
        return false;
      } else {
        let check = true;
        this._buildings.forEach(current => {
          if (current.type === "CAVERN") {
            check = false;
            return;
          }
        });
        return check;
      }
    }
  }

  checkOrcsPit(): boolean {
    let firstTmp = false
    let secondTmp = false
    if (this._buildings == undefined) {
      return false;
    } else if (this._resources == undefined) {
      return false;
    } else {
      this._resources.forEach(current => {
        if (current.type === "MUD" && current.amount >= 8000) {
          firstTmp = true;
        } else if (current.type === "STONE" && current.amount >= 4000) {
          secondTmp = true;
        }
      });
      if (!(firstTmp && secondTmp)) {
        return false;
      } else {
        let check = false;
        this._buildings.forEach(current => {
          if (current.type === "PIT") {
            check = false;
            return;
          }
          if (current.type === "CAVERN") {
            check = true;
            return;
          }
        });
        return check;
      }
    }
  }

  checkTrollsCave(): boolean {
    let firstTmp = false
    let secondTmp = false
    if (this._buildings == undefined) {
      return false;
    } else if (this._resources == undefined) {
      return false;
    } else {
      this._resources.forEach(current => {
        if (current.type === "MUD" && current.amount >= 20000) {
          firstTmp = true;
        } else if (current.type === "STONE" && current.amount >= 10000) {
          secondTmp = true;
        }
      });
      if (!(firstTmp && secondTmp)) {
        return false;
      } else {
        let check = false;
        this._buildings.forEach(current => {
          if (current.type === "CAVE") {
            check = false;
            return;
          }
          if (current.type === "PIT") {
            check = true;
            return;
          }
        });
        return check;
      }
    }
  }
}
