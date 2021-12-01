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
  private videoUrl = '/video/recent'; // URL to video endpoint
  private bugsUrl = '/bug/'; // URL to bug endpoint
  private playrunUrl = '/playrun/'; // URL to playrun endpoint
  private gamesUrl = '/game'; // URL to games endpoint

  public videoName$: BehaviorSubject<VideoObject> = new BehaviorSubject<VideoObject>(null);
  public bugs$: BehaviorSubject<Bug[]> = new BehaviorSubject<Bug[]>(null);
  public playruns$: BehaviorSubject<PlayRunReport[]> = new BehaviorSubject<PlayRunReport[]>(null);
  public games$: BehaviorSubject<string[]> = new BehaviorSubject<string[]>(null);

  constructor(private http: HttpClient) {}

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

  public getPlayruns(): any {
    try {
      const resp = this.http
        .get<PlayRunReport[]>(
          environment.apiConfig.api_local_url + this.playrunUrl
        )
        .subscribe((data: PlayRunReport[]) => {
          this.playruns$.next(data);
          console.log(this.playruns$);
        });
      return resp;
    } catch (error) {
      console.error(error);
      throw error;
    }
  }

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
}
