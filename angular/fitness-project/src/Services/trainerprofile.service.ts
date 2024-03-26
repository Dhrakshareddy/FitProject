import { HttpClient } from "@angular/common/http";
import { Injectable, OnInit } from "@angular/core";
import { Observable } from "rxjs";
import { Trainer } from "../Models/trainer.model";


@Injectable({
    providedIn: 'root'
  })
  export class TrainerService {

    private apiUrl = 'http://13.48.82.196:8402/api/trainer/getById'; // Assuming your backend API URL

  constructor(private http: HttpClient) { }

  getTrainerById(id: number): Observable<Trainer> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.get<Trainer>(url);
  }


  }