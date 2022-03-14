import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {AuthGuardService} from "./auth/auth-guard.service";
import {HomeComponent} from "./pages/home/home.component";
import {MainComponent} from "./pages/main/main.component";

const routes: Routes = [

  {path: '', component: MainComponent},
  {path: 'home', canActivate: [AuthGuardService], component: HomeComponent},
  {path: 'login', component: LoginComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
