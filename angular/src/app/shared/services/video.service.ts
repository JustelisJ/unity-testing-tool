import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Bug } from '../models/bug.model';
import { VideoObject } from '../models/video.model';
import { PlayRunReport } from '../models/playrun.model';

@Injectable({
  providedIn: 'root',
})
export class VideoService {
  private gamesUrl = '/game';
  private buildsUrl = '/game/build';
  private playrunUrl = '/playrun/';
  private playrunNamesUrl = '/video';
  private videoUrl = '/video/recent';
  private bugsUrl = '/bug/';

  public games$: BehaviorSubject<string[]> = new BehaviorSubject<string[]>(
    null
  );
  // BuildReport[]
  public builds$: BehaviorSubject<string[]> = new BehaviorSubject<string[]>(
    null
  );
  // PlayRunReport[]
  public playruns$: BehaviorSubject<string[]> = new BehaviorSubject<string[]>(
    null
  );

  public videoName$: BehaviorSubject<VideoObject> =
    new BehaviorSubject<VideoObject>(null);

  public bugs$: BehaviorSubject<Bug[]> = new BehaviorSubject<Bug[]>(null);

  constructor(private http: HttpClient) {}

  public getGames(): any {
    try {
      const resp = this.http
        .get<string[]>(environment.apiConfig.api_local_url + this.gamesUrl)
        .subscribe((data: string[]) => {
          this.games$.next(data);
          console.log(this.games$);
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
          environment.apiConfig.api_local_url + this.buildsUrl + '?game=' + game
        )
        .subscribe((data: string[]) => {
          this.builds$.next(data);
          console.log(this.builds$);
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
            this.playrunNamesUrl +
            '?game=' +
            game +
            '&build=' +
            build
        )
        .subscribe((data: string[]) => {
          this.playruns$.next(data);
          console.log(this.playruns$);
        });
      return resp;
    } catch (error) {
      console.error(error);
      throw error;
    }
  }

  public getPlayruns(game: string, playrun: string): any {
    try {
      // PlayRunReport[]
      const resp = this.http
        .get<string[]>(
          environment.apiConfig.api_local_url +
            this.playrunUrl +
            '?game=' +
            game +
            '&playrun=' +
            playrun
        )
        .subscribe((data: string[]) => {
          this.playruns$.next(data);
          console.log(this.playruns$);
        });
      return resp;
    } catch (error) {
      console.error(error);
      throw error;
    }
  }

  public getVideoName(): any {
    try {
      const resp = this.http
        .get<VideoObject>(environment.apiConfig.api_local_url + this.videoUrl)
        .subscribe((data: VideoObject) => {
          this.videoName$.next(data);
          console.log(this.videoName$.value);
        });
      return resp;
    } catch (error) {
      console.error(error);
      throw error;
    }
  }

  public getBugs(): any {
    try {
      const resp = this.http
        .get<Bug[]>(environment.apiConfig.api_local_url + this.bugsUrl)
        .subscribe((data: Bug[]) => {
          this.bugs$.next(data);
          console.log(this.bugs$);
        });
      return resp;
    } catch (error) {
      console.error(error);
      throw error;
    }
  }
}
