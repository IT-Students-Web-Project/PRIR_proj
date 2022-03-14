import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {faSignOutAlt} from '@fortawesome/free-solid-svg-icons';
import {AuthService} from "../auth/auth.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit, OnDestroy {

  faSignOut = faSignOutAlt;

  isAuthenticated = false;
  private userSub!: Subscription;

  constructor(private authService: AuthService) {
  }

  ngOnInit() {
    this.userSub = this.authService.user.subscribe(user => {
      this.isAuthenticated = !!user;
    });
  }

  onRegister() {
    this.authService.toggleLoginMode(false);
  }

  onLogin() {
    this.authService.toggleLoginMode(true);
  }

  onLogout() {
    this.authService.logout();
  }

  ngOnDestroy() {
    this.userSub.unsubscribe();
  }
}
