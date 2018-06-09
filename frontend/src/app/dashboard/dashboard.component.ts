import { Component, OnInit } from '@angular/core';
import {ProductExcel} from '../model/productExcel';
import {Router} from "@angular/router";

@Component({
  moduleId: module.id,
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {

  products: ProductExcel[] = [];

  constructor(private router: Router) { }

  goToLoadFiles() {
    this.router.navigate(['load-files']);
  }

  goToLoadFilesCities() {
    this.router.navigate(['load-files-cities']);
  }
}
