import { Observable } from "rxjs";
import { HttpClient } from "@angular/common/http";
import { fetchTrainer } from "../Models/fetchTrainer.model";



export  class TrainerFetchService{
    private baseUrl = 'http://13.48.82.196:8402/api/trainer'; 

  constructor(private http: HttpClient) { }
  getTrainerByName(name: string): Observable<fetchTrainer[]> {
    return this.http.get<fetchTrainer[]>(`${this.baseUrl}/name/${name}`);
}
}   