import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../interfaces/user.interface';
import { FormGroup } from '@angular/forms';
import { MessageResponse } from '../interfaces/messageResponse';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private pathService: string = '/api/user';

  constructor(private http: HttpClient) {}

  public getUser(): Observable<User> {
    return this.http.get<User>(`${this.pathService}`);
  }

  public update(id: number, form: FormGroup): Observable<MessageResponse> {
    return this.http.put<MessageResponse>(
      `${this.pathService}/update/${id}`,
      form.value
    );
  }

  public subscribe(id: number): Observable<User> {
    return this.http.post<User>(`${this.pathService}/subscribe/${id}`, id);
  }

  public unSubscribe(id: number): Observable<User> {
    return this.http.delete<User>(`${this.pathService}/unsubscribe/${id}`);
  }
}
