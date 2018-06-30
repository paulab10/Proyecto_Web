import { Component, OnInit } from '@angular/core';
import {ProductExcel} from '../model/productExcel';
import {Router} from "@angular/router";
import {HttpEventType, HttpResponse} from "@angular/common/http";
import {UploadFileService} from "../upload-file.service";
import {DataService} from "../data.service";

@Component({
  moduleId: module.id,
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {

  products: ProductExcel[] = [];

  isProcessing = false;

  constructor(private router: Router,
              private uploadService: UploadFileService,
              private data: DataService) { }

  goToLoadFiles() {
    this.router.navigate(['load-files']);
  }

  goToLoadFilesCities() {
    this.router.navigate(['load-files-cities']);
  }

  goToViewDetails() {
    this.data.changeMessage("suppliers");
    this.updateDetailView();
  }

  updateDetailView() {
    this.openModal();

    this.uploadService.updateDetailView().subscribe(event => {
      if (event.type === HttpEventType.UploadProgress) {
        console.log("Uploading..");
      }
      else if (event instanceof HttpResponse) {
        console.log("DV Updated");
        this.isProcessing = false;
        this.router.navigateByUrl(`/info-details`);
      }
    });

  }

  openModal() {
    this.isProcessing = true;
  }
}
