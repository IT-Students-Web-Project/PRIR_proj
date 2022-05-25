import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {AuthGuardService} from "./auth/auth-guard.service";
import {HomeComponent} from "./pages/home/home.component";
import {MainComponent} from "./pages/main/main.component";
import { FileUploadComponent } from './pages/file-upload/file-upload.component';
import { FileVerificationComponent } from './pages/file-verification/file-verification.component';
import {FileDatabaseViewComponent} from "./pages/file-database-view/file-database-view.component";


const routes: Routes = [

  {path: '', component: MainComponent},
  {path: 'login', component: LoginComponent},
  {path: 'home', component: HomeComponent, canActivate: [AuthGuardService]},
  {path: 'file-upload', component: FileUploadComponent, canActivate: [AuthGuardService]},
  {path: 'file-verification/:fileId', component: FileVerificationComponent },
  {path: 'file-database-view', component: FileDatabaseViewComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
