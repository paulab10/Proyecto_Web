import { Injectable } from '@angular/core';
import {Product} from './product';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {catchError, tap} from 'rxjs/operators';
import {of} from 'rxjs/observable/of';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class StoreService {

  private baseUrl = '/store';    // URL to web API (Java Backend)
  constructor(private http: HttpClient) {
  }

  /** Get all products */
  getAllProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.baseUrl + '/products')
      .pipe(
        catchError(this.handleError('getProducts', []))
      );
  }

  /** Get product by Id */
  getProduct(id: number): Observable<Product> {
    return this.http.get<Product>(this.baseUrl + `/products/${id}`)
      .pipe(
        catchError(this.handleError<Product>('getProductById'))
      );
  }

  addProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(this.baseUrl + '/add', product, httpOptions).pipe(
      tap((_product: Product) => {
        if (_product) {
          console.log(`Added product with barcode: ${_product.idProduct}`);
        } else {
          console.log(`Product already exists`);
        }
      }),
      catchError(this.handleError<Product>('addProduct'))
    );
  }

  editProduct(product: Product): Observable<Product> {
    return this.http.put<Product>(this.baseUrl + '/edit', product, httpOptions).pipe(
      catchError(this.handleError<Product>())
    );
  }

  deleteProduct(product: Product): Observable<Product> {
    const id = product.idProduct;
    const url = `${this.baseUrl}/products/${id}`;

    return this.http.delete<Product>(url, httpOptions).pipe(
      tap(_ => console.log(`product deleted with barcode: ${id}`)),
      catchError(this.handleError<Product>('Deleting Product'))
    );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead
      // TODO: better job of transforming error for user consumption
      // this.log(`${operation} failed: ${error.message}`);
      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
