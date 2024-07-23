import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { RegisterRequest } from '../../interfaces/registerRequest.interface';
import { createPasswordStrengthValidator } from 'src/app/util/password-validator';
import { retry, take } from 'rxjs';
import { SessionService } from 'src/app/services/session.service';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent {
  public hide = true;
  public onError = false;
  public mailAlreadyTaken = false;

  public form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    username: [
      '',
      [Validators.required, Validators.min(3), Validators.max(20)],
    ],
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
    const registerRequest = this.form.value as RegisterRequest;
    this.authService
      .register(registerRequest)
      .pipe(take(1))
      .subscribe({
        next: (response: SessionInformation) => {
          localStorage.setItem('token', response.token);
          this.sessionService.logIn(response);
          this.router.navigate(['/posts']);
        },
        error: (error) => {
          if (error.message === 'Error: Email is already taken!') {
            this.mailAlreadyTaken = true;
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

  get username() {
    return this.form.controls['username'];
  }
}
