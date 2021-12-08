import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BuildsComponent } from './views/builds/builds.component';
import { GamesComponent } from './views/games/games.component';
import { VideoComponent } from './views/video/video.component';
import { PlayrunsComponent } from './views/playruns/playruns.component';

const routes: Routes = [
  { path: '', redirectTo: '/app-games', pathMatch: 'full' },
  { path: 'app-video/:playrun', component: VideoComponent },
  { path: 'app-playruns/:game/:build', component: PlayrunsComponent },
  { path: 'app-games', component: GamesComponent },
  { path: 'app-builds/:game', component: BuildsComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
