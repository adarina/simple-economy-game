import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { timer } from 'rxjs';
import { Resource } from '../model/resource';
import { ResourceService } from '../resource.service';

@Component({
  selector: 'app-resources',
  templateUrl: './resources.component.html',
  styleUrls: ['./resources.component.css']
})

export class ResourcesComponent implements OnInit {

  private _resources: Array<Resource>;

  constructor(private _resourceService: ResourceService, private _route: ActivatedRoute) { }

  ngOnInit() {
    timer(100, 100).subscribe(timer => {
      this.getResources();
    });
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

  get resources(): Array<Resource> {
    return this._resources;
  }

  @Input()
  set resources(resources: Array<Resource>) {
    this._resources = resources;
  }
}
