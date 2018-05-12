import { Component, OnInit } from '@angular/core';
import {StoreService} from '../store.service';
import {Product} from '../product';
import {FormControl, FormGroup} from '@angular/forms';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.scss']
})
export class AddComponent implements OnInit {

  myForm: FormGroup;
  pBarcode: FormControl;
  pName: FormControl;
  pPrice: FormControl;
  pCategory: FormControl;
  product: Product;

  message: String;
  messageColor = '#155a70';

  constructor(private storeService: StoreService) { }

  ngOnInit() {
    this.createFormControls();
    this.createForm();
    this.product = new Product();
  }

  createForm() {
    this.myForm = new FormGroup({
      id: this.pBarcode,
      name: this.pName,
      price: this.pPrice,
      category: this.pCategory
    });
  }

  createFormControls() {
    this.pBarcode = new FormControl('');
    this.pName = new FormControl('');
    /*this.email = new FormControl('', [
      Validators.required,
      Validators.pattern("[^ @]*@[^ @]*")
    ]);*/
    /*this.password = new FormControl('', [
      Validators.required,
      Validators.minLength(8)
    ]);*/
    this.pPrice = new FormControl('');
    this.pCategory = new FormControl('');
  }

  addProduct(): void {
    this.storeService.addProduct(this.product)
      .subscribe(product => {
        if (product != null) {
          this.messageColor = '#155a70';
          this.message = 'Product added succesfully';
        } else {
          this.messageColor = 'red';
          this.message = 'Product already exists';
        }
      });
  }

  onSubmit(): void {
    this.addProduct();
    this.myForm.reset();
  }

}
