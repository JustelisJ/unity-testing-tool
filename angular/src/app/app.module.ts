import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { VgCoreModule } from '@videogular/ngx-videogular/core';
import { VgControlsModule } from '@videogular/ngx-videogular/controls';
import { VgOverlayPlayModule } from '@videogular/ngx-videogular/overlay-play';
import { VgBufferingModule } from '@videogular/ngx-videogular/buffering';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { VideoService } from './shared/services/video.service';
import { VideoComponent } from './views/video/video.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HeaderComponent } from './header/header/header.component';
import { GamesComponent } from './views/games/games.component';
import { PlayrunsComponent } from './views/playruns/playruns.component';
import { BuildsComponent } from './views/builds/builds.component';

@NgModule({
  declarations: [AppComponent, VideoComponent, HeaderComponent, GamesComponent, PlayrunsComponent, BuildsComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    VgCoreModule,
    VgControlsModule,
    VgOverlayPlayModule,
    VgBufferingModule,
  ],
  providers: [VideoService],
  bootstrap: [AppComponent],
})
export class AppModule {}
