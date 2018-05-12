import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {UploadFileService} from "../upload-file.service";

@Component({
  selector: 'app-load-files',
  templateUrl: './load-files.component.html',
  styleUrls: ['./load-files.component.scss']
})
export class LoadFilesComponent implements OnInit {

  selectedFiles: FileList;
  currentFileUpload: File;
  progress: { percentage: number } = { percentage: 0 };

  constructor(private uploadService: UploadFileService,
              private router: Router) { }

  ngOnInit() {
  }

  selectFile(event) {
    this.selectedFiles = event.target.files;
  }


}
