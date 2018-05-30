import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {UploadFileService} from "../upload-file.service";
import {HttpEventType, HttpResponse} from "@angular/common/http";

@Component({
  selector: 'app-load-files',
  templateUrl: './load-files.component.html',
  styleUrls: ['./load-files.component.scss']
})
export class LoadFilesComponent implements OnInit {

  excelImg: string = "../../assets/excel_gray.png";
  selectedFiles: FileList;
  currentFileUpload: File;
  progress: { percentage: number } = { percentage: 0 };

  constructor(private uploadService: UploadFileService,
              private router: Router) { }

  ngOnInit() {
  }

  selectFile(event, fileName) {
    this.selectedFiles = event.target.files;
    this.excelImg = "../../assets/excel_color.png";
    this.uploadFile(fileName);
  }

  uploadFile(fileName) {
    this.progress.percentage = 0;

    this.currentFileUpload = this.selectedFiles.item(0);
    //this.currentFileUpload.
    this.uploadService.pushFileToStorage(this.currentFileUpload, fileName).subscribe(event => {
      if (event.type === HttpEventType.UploadProgress) {
        this.progress.percentage = Math.round(100 * event.loaded / event.total);
      } else if (event instanceof HttpResponse) {
        console.log('File is completely uploaded!');
      }
    });

    this.selectedFiles = undefined;
  }

  updateDetailView() {
    this.uploadService.updateDetailView().subscribe(event => {
      if (event instanceof HttpResponse) {
        console.log("DV Updated");
      }
    });
  }


}
