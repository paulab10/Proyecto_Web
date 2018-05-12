import { Component, OnInit } from '@angular/core';
import { Product } from '../product';
import {StoreService} from '../store.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

  product = new Product;
  products: Product[];

  // baseUrl = '/edit'

  constructor(private storeService: StoreService,
              private router: Router) { }

  ngOnInit() {
    this.getAllProducts();
    // this.getAllAlticles();
  }

  getAllProducts(): void {
    this.storeService.getAllProducts()
      .subscribe(products => {
        this.products = products;
        console.log(this.products[0]);
      });
  }

  edit(product: Product): void {
    this.router.navigateByUrl(`/edit/${product.idProduct}`);
  }

  delete(product: Product): void {
    this.products = this.products.filter(p => p !== product);
    this.storeService.deleteProduct(product).subscribe();
  }

}
