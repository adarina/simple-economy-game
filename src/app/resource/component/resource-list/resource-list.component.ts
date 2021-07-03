import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { timer } from 'rxjs';
import { Resource } from '../../model/resource';
import { ResourceService } from '../../service/resource.service';

@Component({
  selector: 'app-resource-list',
  templateUrl: './resource-list.component.html',
  styleUrls: ['./resource-list.component.css']
})

export class ResourceListComponent implements OnInit {

  private _resources: Array<Resource>;

  constructor(private _resourceService: ResourceService, private _route: ActivatedRoute) { }

  ngOnInit() {
    let sub = timer(0, 1000).subscribe(timer => {
      if(localStorage.getItem('user') === null) {
        sub.unsubscribe();
      } else {
      this.getResources();
      }
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
