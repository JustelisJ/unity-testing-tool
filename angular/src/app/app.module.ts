import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { VgCoreModule } from '@videogular/ngx-videogular/core';
import { VgControlsModule } from '@videogular/ngx-videogular/controls';
import { VgOverlayPlayModule } from '@videogular/ngx-videogular/overlay-play';
import { VgBufferingModule } from '@videogular/ngx-videogular/buffering';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { VideoService } from './shared/services/video.service';
import { HomeComponent } from './views/home/home.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HeaderComponent } from './header/header/header.component';
import { GamesComponent } from './views/games/games.component';
import { PlayrunsComponent } from './views/playruns/playruns.component';

@NgModule({
  declarations: [AppComponent, HomeComponent, HeaderComponent, GamesComponent, PlayrunsComponent],
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
