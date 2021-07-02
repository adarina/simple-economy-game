import { Component, Input, OnInit } from '@angular/core';
import { Resource } from '../../model/resource';

@Component({
  selector: 'app-resource-single',
  templateUrl: './resource-single.component.html',
  styleUrls: ['./resource-single.component.css']
})
export class ResourceSingleComponent implements OnInit {

  private _resource: Resource;

  id: number;

  type: string;

  amount: number;

  constructor() { }

  ngOnInit(): void {
  }

  @Input()
  set resource(resource:Resource) {
    this._resource = resource;
  }

  get resource(): Resource {
    return this._resource;
  }
}
