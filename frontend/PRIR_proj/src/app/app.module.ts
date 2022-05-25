import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {LoadingspinnerComponent} from './loadingspinner/loadingspinner.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {ToastrModule} from "ngx-toastr";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {NavbarComponent} from './navbar/navbar.component';
import {HomeComponent} from './pages/home/home.component';
import {MainComponent} from './pages/main/main.component';
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import { FileUploadComponent } from './pages/file-upload/file-upload.component';
import { FileVerificationComponent } from './pages/file-verification/file-verification.component';
import { FileDatabaseViewComponent } from './pages/file-database-view/file-database-view.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    LoadingspinnerComponent,
    NavbarComponent,
    HomeComponent,
    MainComponent,
    FileUploadComponent,
    FileVerificationComponent,
    FileDatabaseViewComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    ToastrModule.forRoot(),
    BrowserAnimationsModule,
    NgbModule,
    FontAwesomeModule,

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
