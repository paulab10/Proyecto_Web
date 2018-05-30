import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AdminComponent } from './admin/admin.component';
import { ProductDetailComponent } from './product-detail/product-detail.component';
import { AppRoutingModule } from './/app-routing.module';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { LoadFilesComponent } from './load-files/load-files.component';
import { InfoDetailsComponent } from './info-details/info-details.component';
import {UploadFileService} from "./upload-file.service";

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    AdminComponent,
    ProductDetailComponent,
    LoadFilesComponent,
    InfoDetailsComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    AppRoutingModule,
    ReactiveFormsModule
  ],
  providers: [
    UploadFileService
  ],
  bootstrap: [AppComponent,
  ]
})
export class AppModule { }
