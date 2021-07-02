import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
//import { BuildingService } from '../../building.service';
//import { Building } from '../../model/building';

@Component({
  selector: 'app-building-management',
  templateUrl: './building-management.component.html',
  styleUrls: ['./building-management.component.css']
})
export class BuildingManagementComponent implements OnInit {

  ngOnInit(): void {
  }
  /*private _team1: Array<Building>;
  
 
  
  constructor (private _buildingService: BuildingService, private _route: ActivatedRoute) {}

  /**
   * Loads two teams on startup based on pathg params team1 and team2.
   */
  /*ngOnInit(): void {
    if (this._route.snapshot.paramMap) {
      this._buildingService.getTeam(this._route.snapshot.paramMap.get('team1')).subscribe(value => this._team1 = value);
      //this._teamService.getTeam(this._route.snapshot.paramMap.get('team2')).subscribe(value => this._team2 = value); 
    }
  }

  get team1(): Array<Building> {
    return this._team1;
  }

  @Input()
  set team1(team: Array<Building>) {
    this._team1 = team;
  }
*/


}
