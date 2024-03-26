// src/app/user/user.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from './user.model';




@Injectable({
  providedIn: 'root'
})
export class UserService {
  
  private apiUrl = 'http://13.48.82.196:8402/api/user';
  private doctorapiUrl = 'http://13.48.82.196:8402/api/doctor_profile';
  private trainerapiUrl = 'http://13.48.82.196:8402/api/trainer';
 
  getUserById: any;

  constructor(private http: HttpClient) {}

   getUsers(): Observable<User[]> {
     return this.http.get<any[]>(`${this.apiUrl}/getAll`);
   }

  //  getUserById(id: number): Observable<User> {
  //    return this.http.get<User>(`${this.apiUrl}/get/${id}`);
  // }

  saveUser(user: User): Observable<User> {
    (user);
    return this.http.post<User>(`${this.apiUrl}/save`, user);
  }

  deleteUser(id: number): Observable<string> {
    return this.http.delete(`${this.apiUrl}/deleteById/${id}`, { responseType: 'text' });
  }

  loginDetails(details: any): Observable<any> {
    // Assuming your API returns an observable
    return this.http.post<any>(`${this.apiUrl}/login`, details);
  }

  doctorLoginDetails(details: any): Observable<any> {
    // Assuming your API returns an observable
    return this.http.post<any>(`${this.doctorapiUrl}/login`, details);
  }

  trainerLoginDetails(details: any): Observable<any> {
    // Assuming your API returns an observable
    return this.http.post<any>(`${this.trainerapiUrl}/login`, details);
  }
 
  
  
}

