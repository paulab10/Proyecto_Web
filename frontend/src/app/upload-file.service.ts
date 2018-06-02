import { Injectable } from '@angular/core';
import {HttpClient, HttpEvent, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {catchError} from "rxjs/operators";
import {ProductExcel} from "./productExcel";
import {of} from "rxjs/observable/of";

@Injectable()
export class UploadFileService {

  constructor(private http: HttpClient) { }

  pushFileToStorage(file: File, name: string): Observable<HttpEvent<{}>> {
    const formdata: FormData = new FormData();

    formdata.append('file', file);

    const req = new HttpRequest('POST', '/upload-file/'+ name, formdata, {
      reportProgress: true,
      responseType: 'text'
    });

    return this.http.request(req);
  }

  /** Get List with info marked as available */
  getAvailableList(supplier: string): Observable<ProductExcel[]> {
    return this.http.get<ProductExcel[]>(`/get/available/${supplier}`)
      .pipe(
        catchError(this.handleError<ProductExcel[]>('getAvailableList'))
      );
  }

  getToCreateList(supplier: string): Observable<ProductExcel[]> {
    return this.http.get<ProductExcel[]>(`/get/to-create/${supplier}`)
      .pipe(
        catchError(this.handleError<ProductExcel[]>('getToCreateList'))
      );
  }

  getToAdjustList(supplier: string): Observable<ProductExcel[]> {
    return this.http.get<ProductExcel[]>(`/get/to-adjust/${supplier}`)
      .pipe(
        catchError(this.handleError<ProductExcel[]>('getToAdjustList'))
      );
  }

  getToCheckList(supplier: string): Observable<ProductExcel[]> {
    return this.http.get<ProductExcel[]>(`/get/to-check/${supplier}`)
      .pipe(
        catchError(this.handleError<ProductExcel[]>('getToCheckList'))
      );
  }

  /** Update Detail View **/
  updateDetailView(): Observable<HttpEvent<{}>> {
    const req = new HttpRequest('PUT', '/update-dv/',"", {
      responseType: 'text'
    });

    return this.http.request(req);
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
