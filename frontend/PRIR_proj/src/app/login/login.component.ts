import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {NgForm} from "@angular/forms";
import {Observable} from "rxjs";
import {HttpResponse} from "@angular/common/http";
import {ToastrService} from "ngx-toastr";
import {AuthService, LoginCredentials} from "../auth/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginCredentials: LoginCredentials = {
    password: null,
    username: null,
    email: null
  };

  isLoginMode: boolean = true;
  isLoading = false;

  constructor(private authService: AuthService, private router: Router, private toastr: ToastrService) {
  }

  onSwitchMode() {
    this.authService.isLoginMode ? this.authService.toggleLoginMode(false)
      : this.authService.toggleLoginMode(true);
  }

  ngOnInit(): void {
    this.authService.changeLoginMode.subscribe(isLoginMode => {
      this.isLoginMode = isLoginMode;
    });
  }

  onSubmit(form: NgForm) {
    if (!form.valid) {
      this.showError('Try again', 'Form invalid')
      return;
    }

    this.loginCredentials.password = form.value.password;
    this.loginCredentials.username = form.value.username;
    this.loginCredentials.email = form.value.email;

    let authObs: Observable<HttpResponse<Object>>;

    this.isLoading = true;

    this.isLoginMode ? authObs = this.authService.login(this.loginCredentials)
      : authObs = this.authService.signUp(this.loginCredentials);

    authObs.subscribe(
      resData => {
        if (this.isLoginMode) {
          this.isLoading = false;
          this.showSuccess('Enjoy progress tracking', 'Logged in');
          this.router.navigate(['/home']);
        } else {
          this.isLoading = false;
          this.authService.toggleLoginMode(true);
          this.router.navigate(['/login']);
          this.showSuccess('Proceed to login', 'Register success');
        }
      },
      errorMessage => {
        this.showError('Please check your email', errorMessage);
        this.isLoading = false;
      }
    );
    form.reset();
  }

  showSuccess(message: string, title: string) {
    this.toastr.success(message, title);
  }

  showError(message: string, title: string) {
    this.toastr.error(message, title);
  }
}
