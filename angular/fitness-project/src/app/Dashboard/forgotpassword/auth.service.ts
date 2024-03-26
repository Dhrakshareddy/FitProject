// auth.service.ts

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
 private apiUrl = 'http://13.48.82.196:8402/api/user';

  constructor(private http: HttpClient) {}

  requestPasswordReset(username: string): Observable<any> {
    const resetRequest = {
      username: username
    };

    return this.http.post(`${this.apiUrl}/password-reset-endpoint`, resetRequest);
  }

  // Add other authentication-related methods here
}
