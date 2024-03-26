import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class DataService {

  private apiUrl = 'http://13.48.82.196:8402/api/user'; 

  constructor(private http: HttpClient) { }

  sendData(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/save`, data);
  }
}