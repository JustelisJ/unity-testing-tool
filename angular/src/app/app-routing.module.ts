import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GamesComponent } from './views/games/games.component';
import { HomeComponent } from './views/home/home.component';
import { PlayrunsComponent } from './views/playruns/playruns.component';

const routes: Routes = [
  { path: '', redirectTo: '/app-games', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'app-playruns/:id', component: PlayrunsComponent },
  { path: 'app-games', component: GamesComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
