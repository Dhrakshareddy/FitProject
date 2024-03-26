
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../Models/user.classmodel';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }
 
  getUserById(userId: number): Observable<User> {
    return this.http.get<User>(`http://13.48.82.196:8402/api/user/getById/${userId}`);
  }

 

  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>('http://13.48.82.196:8402/api/user/getAll');
  }

 
}