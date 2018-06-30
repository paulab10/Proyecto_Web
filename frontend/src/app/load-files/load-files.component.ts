import {Component, Input, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {UploadFileService} from "../upload-file.service";
import {HttpEventType, HttpResponse} from "@angular/common/http";
import {FilesStatus} from "../model/filesStatus";
import {forEach} from "@angular/router/src/utils/collection";
import {DataService} from "../data.service";

@Component({
  selector: 'app-load-files',
  templateUrl: './load-files.component.html',
  styleUrls: ['./load-files.component.scss']
})

export class LoadFilesComponent implements OnInit {

  excelImg = {
    detailview: "../../assets/excel_gray.png",
    sicte: "../../assets/excel_gray.png",
    fscr: "../../assets/excel_gray.png",
    enecon: "../../assets/excel_gray.png",
    applus: "../../assets/excel_gray.png",
    conectar: "../../assets/excel_gray.png",
    dico: "../../assets/excel_gray.png",
  };

  inputEnabled = {
    detailview: true,
    sicte: true,
    fscr: true,
    enecon: true,
    applus: true,
    conectar: true,
    dico: true,
  };

  selectedFiles: FileList;
  currentFileUpload: File;

  filesStatus: FilesStatus;

  progress: { percentage: number } = { percentage: 0 };
  isProcessing = false;
  modalText: string;

  constructor(private uploadService: UploadFileService,
              private router: Router,
              private data: DataService) { }

  ngOnInit() {
    this.data.changeMessage("suppliers");
    this.getFilesStatus();
  }

  selectFile(event, fileName) {
    this.selectedFiles = event.target.files;
    this.excelImg[fileName] = "../../assets/excel_color.png";
    if (fileName === "detailview") {
      this.uploadFile(fileName);
    } else {
      this.uploadFile("suppliers/" + fileName);
    }

  }

  uploadFile(fileName) {
    this.openModal("Uploading file...");
    this.progress.percentage = 0;

    this.currentFileUpload = this.selectedFiles.item(0);
    //this.currentFileUpload.
    this.uploadService.pushFileToStorage(this.currentFileUpload, fileName).subscribe(event => {
      if (event.type === HttpEventType.UploadProgress) {
        this.progress.percentage = Math.round(100 * event.loaded / event.total);
        console.log(this.progress.percentage);
      } else if (event instanceof HttpResponse) {
        this.closeModal();
        console.log('File is completely uploaded!');
      }
    });

    this.selectedFiles = undefined;
  }

  updateDetailView() {
    this.openModal("Detail View is being processed");

    this.uploadService.updateDetailView().subscribe(event => {
      if (event.type === HttpEventType.UploadProgress) {
        console.log("Uploading..");
      }
      else if (event instanceof HttpResponse) {
        console.log("DV Updated");
        this.isProcessing = false;
        this.router.navigateByUrl(`/view-details`);
      }
    });

  }

  openModal(modalText: string) {
    this.isProcessing = true;
    this.modalText = modalText;
  }

  closeModal() {
    this.isProcessing = false;
  }

  getFilesStatus() {
    this.uploadService.getFilesStatus("suppliers")
      .subscribe(status => {
        if (status != null) {
          this.filesStatus = status;
          this.updateCards();
          console.log(status);
        }
      });
  }

  updateCards() {
    this.inputEnabled.sicte = this.filesStatus.sicte;
    this.inputEnabled.enecon = this.filesStatus.enecon;
    this.inputEnabled.conectar = this.filesStatus.conectar;
    this.inputEnabled.fscr = this.filesStatus.fscr;
    this.inputEnabled.dico = this.filesStatus.dico;
    this.inputEnabled.applus = this.filesStatus.applus;

    this.inputEnabled.detailview = this.filesStatus.detailview;

    for(let key in this.inputEnabled) {
      if (this.inputEnabled[key]) {
        this.excelImg[key] = "../../assets/excel_color.png";
      }
    }
  }

}
