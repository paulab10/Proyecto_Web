import { Component, OnInit } from '@angular/core';
import {Product} from '../product';
import {Router} from "@angular/router";

@Component({
  moduleId: module.id,
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {

  products: Product[] = [];

  constructor(private router: Router) { }

  goToLoadFiles() {
    this.router.navigate(['load-files']);
  }
}
