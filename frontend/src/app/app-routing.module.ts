import { NgModule } from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ProductsComponent} from './products/products.component';
import {AdminComponent} from './admin/admin.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {AddComponent} from './add/add.component';
import {LoadFilesComponent} from "./load-files/load-files.component";
import {InfoDetailsComponent} from "./info-details/info-details.component";

const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'products', component: ProductsComponent },
  { path: 'admin', component: AdminComponent },
  { path: 'add', component: AddComponent },
  { path: 'load-files', component: LoadFilesComponent},
  { path: 'info-details', component: InfoDetailsComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [
    RouterModule
  ]

})
export class AppRoutingModule { }
