import { Observable } from "rxjs";
import { HttpClient } from "@angular/common/http";
import { fetchUser } from "../Models/fetchUser.model";



export  class UserFetchService{
    private baseUrl = 'http://13.48.82.196:8402/api/user'; 

  constructor(private http: HttpClient) { }
  getUserByName(name: string): Observable<fetchUser[]> {
    return this.http.get<fetchUser[]>(`${this.baseUrl}/byName/${name}`);
}
}   