import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'; 


import { Observable } from 'rxjs';
import { Nutrition } from '../Models/nutrition.model';

@Injectable({ providedIn: 'root' })
export class NutritionRepo {
  public nutrition: Nutrition[] = [];
  private baseUrl = 'http://13.48.82.196:8401/nutrition';
  constructor(
    
    private http: HttpClient // Inject HttpClient
  ) {}

 

  saveNutrition(nutrition: Nutrition): Observable<Nutrition> {
  
   
    return this.http.post<Nutrition>(`${this.baseUrl}/save`, nutrition);
  }


  getAllUsers(): Observable<any[]> {
    return this.http.get<any[]>('http://13.48.82.196:8401/nutrition/getUsers');
  }


  getUserMedicalHistory(username: string): Observable<any> {
    const url = `http://13.48.82.196:8401/medicalHistory/fetchbyName/${username} `;
    return this.http.get<any>(url);
  }


  updateNutrition(nutrition: Nutrition): Observable<any> {
    const url = 'http://13.48.82.196:8401/nutrition/updateById';
    return this.http.put<any>(url, nutrition);
  }
  
}