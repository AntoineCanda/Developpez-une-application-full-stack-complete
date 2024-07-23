import { Component } from '@angular/core';
import {
  FormBuilder,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { SessionService } from 'src/app/services/session.service';
import { Router } from '@angular/router';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { retry, take } from 'rxjs';
import { AuthService } from '../../services/auth.service';
import { LoginRequest } from '../../interfaces/loginRequest.interface';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { createPasswordStrengthValidator } from 'src/app/util/password-validator';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  standalone: true,
  imports: [
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    FormsModule,
    ReactiveFormsModule,
  ],
})
export class LoginComponent {
  public hide = true;
  public onError = false;
  public badCredentials = false;

  public form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: [
      '',
      [
        Validators.required,
        Validators.min(8),
        createPasswordStrengthValidator(),
      ],
    ],
    updateOn: 'blur',
  });

  constructor(
    private authService: AuthService,
    private fb: FormBuilder,
    private router: Router,
    private sessionService: SessionService
  ) {}

  public submit(): void {
    const loginRequest = this.form.value as LoginRequest;
    this.authService
      .login(loginRequest)
      .pipe(take(1))
      .subscribe({
        next: (response: SessionInformation) => {
          localStorage.setItem('token', response.token);
          this.sessionService.logIn(response);
          this.router.navigate(['/posts']);
        },
        error: (error) => {
          if (error === 'Bad credentials') {
            this.badCredentials = true;
          } else {
            this.onError = true;
            localStorage.removeItem('token');
            retry(1);
          }
        },
      });
  }

  public back() {
    window.history.back();
  }

  get email() {
    return this.form.controls['email'];
  }

  get password() {
    return this.form.controls['password'];
  }
}
