import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Building } from '../../model/building';
import { BuildingService } from '../../service/building.service';

@Component({
  selector: 'app-building-list',
  templateUrl: './building-list.component.html',
  styleUrls: ['./building-list.component.css']
})
export class BuildingListComponent implements OnInit {


  private _buildings: Array<Building>;

  constructor(private _buildingService: BuildingService, private _route: ActivatedRoute) { }

  ngOnInit(): void {
    if (this._route.snapshot.paramMap) {
      this._buildingService.getBuildings(this._route.snapshot.paramMap.get('buildings')).subscribe(value => this._buildings = value);
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
  /*private _team1: Array<Building>;
  
 
  
  constructor (private _buildingService: BuildingService, private _route: ActivatedRoute) {}

  /**
   * Loads two teams on startup based on pathg params team1 and team2.
   */
  

 

  
  //buildings: BuildingDTO;

  //resources: ResourceDTO;

  //buildingsArray: Building[] = [];

  /*private _type: string;
    
  private _stoneQuarryQuantity: number;

  private _huntersHutQuantity: number;

  private _goblinsCavernOwnership: boolean;
    
  private _orcsPitOwnership: boolean;

  private _trollsCaveOwnership: boolean;
  
  constructor(private _buildingService: BuildingService, private _resourceService: ResourceService) { }

  set type(type: string) {
    this._type = type;
  }

  get type(): string {
    return this._type;
  }
  ngOnInit(): void {
    this.
    
    //this.buildingsArray = this._buildingService.buildingsArray;
    //console.log(this.buildingsArray)
  }*/

  /*fetchBuildings() {
    this._buildingService.fetchBuildings().subscribe(data => { 
      this.buildings = data;
      //this.buildings = this.buildings.json().buildings;
      //this.buildings = data.json()['buildings'];
      //Array.of(this.buildings);
      //console.log(Array.of(this.buildings[0]))
      //onsole.log(this.buildings.)
      /*for (var val in this.buildings) {
        console.log(this.buildings[0].type); // prints values: 10, 20, 30, 40
      }*/
      //for (var i = 0; i < this.buildings.id; i++) {
        //console.log( results.genres[i].name );
    //}//console.log(JSON.stringify(this.buildings))
      //this.ngOnInit();
      //this.addBuildings();
      //let json = this.buildings;
      //const obj = JSON.parse(json);

//console.log(obj.count);
// expected output: 42

////console.log(obj.result);
      //let obj: { string: BuildingDTO[] } = JSON.parse(this.buildings.toString());
      //console.log(obj.string[0].id, obj.string[0].type);
      //let json = JSON.stringify(this.buildings);  

      //console.log(json);  
    /*},
    error => {
      console.log(error); 
      console.log(error.status); 
      console.log(error.error);
    });
  }*/

  /*updateBuildings(buildingName: string): void {
    this._buildingService.updateBuildings(this.buildings, buildingName)
      .subscribe(data => {
        this.fetchBuildings();
        //this.addBuildings(buildingName);
    },
      error => {
        console.log(error);
        console.log(error.status);
        console.log(error.error);
      }); 
  }*/

  checkMudGatherersCottage(): boolean {
    let lol = true;
    /*this._resourceService.fetchResources().subscribe(data => { 
      this.resources = data;
    },
    error => {
      console.log(error); 
      console.log(error.status); 
      console.log(error.error);
    });
    if (this.resources.mudQuantity >= 100) {
      lol = true;
    }*/
    return lol;
  }

  checkStoneQuarry(): boolean {
    return true;
  }

  /*addBuildings(buildingName: string) {
    
    if (buildingName == 'mudGatherersCottage') {
      this.buildingsArray.push(new Building(1,'Mud Gatherer\'s Cottage'))    
    } else if (buildingName == 'stoneQuarry') {
      this.buildingsArray.push(new Building(2,'Stone Quarry'))
    } else if (buildingName == 'huntersHut') {
      this.buildingsArray.push(new Building(3,'Hunter\'s Hut'))
    } else if (buildingName == 'goblinsCavern') {
      this.buildingsArray.push(new Building(4,'Goblin\'s Cavern'))
    } else if (buildingName == 'orcsPit') {
      this.buildingsArray.push(new Building(5,'Orc\'s Pit'))
    } else if (buildingName == 'trollsCave') {
      this.buildingsArray.push(new Building(6,'Troll\'s Cave'))
    }
  }*/

  /*addBuildings(buildings: BuildingDTO) {
    
    for (let i = 0; i < buildings.mudGatherersCottageQuantity; i++) {
      this.buildingsArray.push(new Building(1,'Mud Gatherer\'s Cottage'))   
    }
    for (let i = 0; i < buildings.stoneQuarryQuantity; i++) {
      this.buildingsArray.push(new Building(2,'Stone Quarry'))   
    }
    for (let i = 0; i < buildings.huntersHutQuantity; i++) {
      this.buildingsArray.push(new Building(3,'Hunter\'s Hut'))   
    }
  }*/
}
