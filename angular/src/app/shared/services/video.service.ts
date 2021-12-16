import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Bug } from '../models/bug.model';
import { VideoObject } from '../models/video.model';
import { PlayRunReport } from '../models/playrun.model';
import { BuildReport } from 'src/app/shared/models/build.model';

@Injectable({
  providedIn: 'root',
})
export class VideoService {
  private gamesUrl = '/game';
  private buildsUrl = '/game/build';
  private buildreportsUrl = '/game/buildreport';
  private playrunUrl = '/playrun/';
  private playrunNamesUrl = '/video';

  public games$: BehaviorSubject<string[]> = new BehaviorSubject<string[]>(
    null
  );

  public builds$: BehaviorSubject<string[]> = new BehaviorSubject<string[]>(
    null
  );

  public buildReport$: BehaviorSubject<BuildReport[]> = new BehaviorSubject<
    BuildReport[]
  >(null);

  public playruns$: BehaviorSubject<string[]> = new BehaviorSubject<
    string[]
  >(null);

  public playrunReport$: BehaviorSubject<PlayRunReport> =
    new BehaviorSubject<PlayRunReport>(null);

  public videoName$: BehaviorSubject<VideoObject> =
    new BehaviorSubject<VideoObject>(null);

  public bugs$: BehaviorSubject<Bug[]> = new BehaviorSubject<Bug[]>(null);

  constructor(private http: HttpClient) {}

  public getGames(): any {
    try {
      const resp = this.http
        .get<string[]>(
          environment.apiConfig.api_local_url +
            encodeURI(this.gamesUrl)
        )
        .subscribe((data: string[]) => {
          this.games$.next(data);
          console.log(this.games$.value);
        });
      return resp;
    } catch (error) {
      console.error(error);
      throw error;
    }
  }

  public getBuilds(game: string): any {
    try {
      const resp = this.http
        .get<string[]>(
          environment.apiConfig.api_local_url +
            encodeURI(this.buildsUrl) +
            encodeURI('?game=') +
            game
        )
        .subscribe((data: string[]) => {
          this.builds$.next(data);
          console.log(this.builds$.value);
        });
      return resp;
    } catch (error) {
      console.error(error);
      throw error;
    }
  }

  public getBuildReports(game: string, build: string): any {
    try {
      const resp = this.http
        .get<BuildReport[]>(
          environment.apiConfig.api_local_url +
            encodeURI(this.buildreportsUrl) +
            encodeURI('?game=') +
            game +
            encodeURI('&build=') +
            build
        )
        .subscribe((data: BuildReport[]) => {
          this.buildReport$.next(data);
          console.log(this.buildReport$.value);
        });
      return resp;
    } catch (error) {
      console.error(error);
      throw error;
    }
  }

  public getPlayrunsNames(game: string, build: string): any {
    try {
      const resp = this.http
        .get<string[]>(
          environment.apiConfig.api_local_url +
            encodeURI(this.playrunNamesUrl) +
            encodeURI('?game=') +
            game +
            encodeURI('&build=') +
            build
        )
        .subscribe((data: string[]) => {
          this.playruns$.next(data);
          console.log(this.playruns$.value);
        });
      return resp;
    } catch (error) {
      console.error(error);
      throw error;
    }
  }

  public getPlayrunReport(game: string, build: string, playrun: string): any {
    try {
      const resp = this.http
        .get<PlayRunReport>(
          environment.apiConfig.api_local_url +
            encodeURI(this.playrunUrl) +
            encodeURI('?game=') +
            game +
            encodeURI('&build=') +
            build +
            encodeURI('&playrun=') +
            playrun
        )
        .subscribe((data: PlayRunReport) => {
          this.playrunReport$.next(data);
          console.log(this.playrunReport$.value);
        });
      return resp;
    } catch (error) {
      console.error(error);
      throw error;
    }
  }
}
