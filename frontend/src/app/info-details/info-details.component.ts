import { Component, OnInit } from '@angular/core';
import {ProductExcel} from "../productExcel";
import {UploadFileService} from "../upload-file.service";

@Component({
  selector: 'app-info-details',
  templateUrl: './info-details.component.html',
  styleUrls: ['./info-details.component.scss']
})
export class InfoDetailsComponent implements OnInit {

  constructor(private uploadService: UploadFileService) { }

  products: ProductExcel[];

  supplierName: string = "sicte";

  ngOnInit() {
    //this.getAvailableList();
  }

  getAvailableList() {
    this.uploadService.getAvailableList(this.supplierName)
      .subscribe( products => {
        if (products != null) {
          this.products = products;
          console.log(products);
        }
      });
  }

  getPendingToCreateList() {
    this.uploadService.getToCreateList(this.supplierName)
      .subscribe( products => {
        if (products != null) {
          this.products = products;
          console.log(products);
        }
      });
  }

  getPendingToAdjustList() {
    this.uploadService.getToAdjustList(this.supplierName)
      .subscribe( products => {
        if (products != null) {
          this.products = products;
          console.log(products);
        }
      });
  }

  getPendingToCheckList() {
    this.uploadService.getToCheckList(this.supplierName)
      .subscribe( products => {
        if (products != null) {
          this.products = products;
          console.log(products);
        }
      });
  }

}
