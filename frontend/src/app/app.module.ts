import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AdminComponent } from './admin/admin.component';
import { ProductDetailComponent } from './product-detail/product-detail.component';
import { ProductsComponent } from './products/products.component';
import {StoreService} from './store.service';
import { AppRoutingModule } from './/app-routing.module';
import { AddComponent } from './add/add.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { LoadFilesComponent } from './load-files/load-files.component';
import { InfoDetailsComponent } from './info-details/info-details.component';

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    AdminComponent,
    ProductDetailComponent,
    ProductsComponent,
    AddComponent,
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
    StoreService
  ],
  bootstrap: [AppComponent,
  ]
})
export class AppModule { }
