import {Injectable, Output} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {catchError, tap} from 'rxjs/operators';
import {BehaviorSubject, throwError} from 'rxjs';
import {Router} from "@angular/router";
import {environment} from "../../environments/environment";
import {User} from "../models/User";

export interface LoginCredentials {
  password: string;
  username: string;
  email: string;
}

@Injectable({
  providedIn: 'root'
})

export class AuthService {

  user = new BehaviorSubject<User>(null);
  isLoginMode: boolean = true;
  @Output() changeLoginMode: BehaviorSubject<boolean> = new BehaviorSubject(this.isLoginMode);
  private tokenExpirationTimer: any;

  constructor(private http: HttpClient, private router: Router) {
  }

  toggleLoginMode(trueOrFalse: boolean) {
    this.isLoginMode = trueOrFalse;
    this.changeLoginMode.next(this.isLoginMode);
  }

  login(loginCredentials: LoginCredentials) {
    return this.http.post(`${environment.APIEndpoint}/login`, loginCredentials, {observe: 'response'})
      .pipe(catchError(this.handleError), tap(resData => {
        this.handleAuthentication(
          loginCredentials.username,
          resData.headers.get('Authorization'),
          resData.headers.get('Expires'))
      }));
  }

  signUp(loginCredentials: LoginCredentials) {
    return this.http.post(`${environment.APIEndpoint}/register`, loginCredentials, {observe: 'response'})
      .pipe(catchError(this.handleError), tap(resData => {
        this.handleRegister(resData.status, JSON.stringify(resData.body));
      }));
  }

  handleRegister(status: number, body: string) {
    this.user.next(null);
    return "code: " + status + "body: " + body;
  }

  handleAuthentication(username: string, token: string, tokenExpirationTime: string) {
    const user = new User(username, token, new Date(new Date().getTime() + Number(tokenExpirationTime)));
    this.user.next(user);
    this.autoLogout(Number(tokenExpirationTime));
    localStorage.setItem('user', JSON.stringify(user));
  }

  getLoggedUser() {
    return localStorage.getItem('user');
  }

  autoLogin() {
    const userData: {
      username: string,
      _token: string,
      _tokenExpirationDate: string;
    } = JSON.parse(localStorage.getItem('user'));
    if (!userData) {
      return;
    }

    const loadedUser = new User(userData.username, userData._token, new Date(userData._tokenExpirationDate));

    if (loadedUser.token) {
      this.user.next(loadedUser);
      this.autoLogout(new Date(userData._tokenExpirationDate).getTime() - new Date().getTime());
    }
  }

  logout() {
    this.user.next(null);
    localStorage.removeItem('user');
    this.router.navigate(['/login']);
    if (this.tokenExpirationTimer) {
      clearTimeout(this.tokenExpirationTimer);
    }
    this.tokenExpirationTimer = null;
  }

  autoLogout(expirationDuration: number) {
    this.tokenExpirationTimer = setTimeout(() => {
      this.logout();
    }, expirationDuration)
  }

  private handleError(errorRes: HttpErrorResponse) {

    let errorMessage = 'An unknown error occurred!';

    if (errorRes.status == 401) {
      return throwError("Wrong username or password.");
    } else if (!errorRes.error || !errorRes.error.message) {
      return throwError(errorMessage);
    }

    switch (errorRes.error.message) {
      case 'EMAIL_EXISTS':
        errorMessage = 'There is an account with that email adress.';
        break;
    }

    switch (errorRes.error.message) {
      case 'USERNAME_EXISTS':
        errorMessage = 'There is an account with that username.';
        break;
    }

    switch (errorRes.error.message) {
      case 'UNKNOWN':
        errorMessage = 'UNKNOWN';
        break;
    }

    return throwError(errorMessage);
  }
}
