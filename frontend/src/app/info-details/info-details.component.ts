import { Component, OnInit } from '@angular/core';
import {ProductExcel} from "../model/productExcel";
import {UploadFileService} from "../upload-file.service";
import {HttpResponse} from "@angular/common/http";

@Component({
  selector: 'app-info-details',
  templateUrl: './info-details.component.html',
  styleUrls: ['./info-details.component.scss']
})
export class InfoDetailsComponent implements OnInit {

  constructor(private uploadService: UploadFileService) { }

  products: ProductExcel[];

  supplierName: string;
  modalText: string;

  isProcessing = false;

  /* Active tabs*/
  tabActive = {
    sicte: false,
    dico: false,
    fscr: false,
    enecon: false,
    conectar: false,
    applus: false
  };

  /* Active table */
  tableActive = {
    available: false,
    create: false,
    adjust: false,
    check: false
  };


  ngOnInit() {
    this.tabActive.sicte = true;
    this.tableActive.available = true;
    this.supplierName = 'sicte';

    this.getAvailableList();
  }

  getAvailableList() {
    this.openModal("Retrieving available list...");
    this.uncheckAllTables();

    this.uploadService.getAvailableList(this.supplierName)
      .subscribe( products => {
        if (products != null) {
          this.products = products;
          console.log(products);
        }
      },
        error => console.log("error: " + error),
        () => {
          this.closeModal();
          this.tableActive.available = true;
        });
  }

  getPendingToCreateList() {
    this.openModal("Retrieving to create list...");
    this.uncheckAllTables();

    this.uploadService.getToCreateList(this.supplierName)
      .subscribe( products => {
        if (products != null) {
          this.products = products;
          console.log(products);
        }
      },
        error => console.log("error: " + error),
        () => {
          this.closeModal();
          this.tableActive.create = true;
        });
  }

  getPendingToAdjustList() {
    this.openModal("Retrieving to adjust list...");
    this.uncheckAllTables();

    this.uploadService.getToAdjustList(this.supplierName)
      .subscribe( products => {
        if (products != null) {
          this.products = products;
          console.log(products);
        }
      },
        error => console.log("error: " + error),
        () => {
          this.closeModal();
          this.tableActive.adjust = true;
        });
  }

  getPendingToCheckList() {
    this.openModal("Retrieving to check list...");
    this.uncheckAllTables();


    this.uploadService.getToCheckList(this.supplierName)
      .subscribe(
        products => {
          if (products != null) {
            this.products = products;
            console.log(products);
          }
        },
        error => console.log("error: " + error),
        () => {
          this.closeModal();
          this.tableActive.check = true;
        }
      );
  }

  uncheckAllTabs() {
    this.tabActive.sicte = false;
    this.tabActive.applus =  false;
    this.tabActive.conectar =  false;
    this.tabActive.dico =  false;
    this.tabActive.fscr =  false;
    this.tabActive.enecon =  false;
  }

  uncheckAllTables() {
    this.tableActive.available = false;
    this.tableActive.create = false;
    this.tableActive.adjust = false;
    this.tableActive.check = false;
  }

  onTabClicked(tabName) {
    this.uncheckAllTabs();

    this.tabActive[tabName] = true;
    this.supplierName = tabName;

    this.getAvailableList();
  }

  openModal(modalText: string) {
    this.isProcessing = true;
    this.modalText = modalText;
  }

  closeModal() {
    this.isProcessing = false;
  }

}
