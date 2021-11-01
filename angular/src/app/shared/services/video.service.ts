import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class VideoService {
  private videoUrl = '/video/recent'; // URL to video server

  public videoName$: BehaviorSubject<String> = new BehaviorSubject<String>(null);

  constructor(private http: HttpClient, private router: Router) {}

  public getVideoName() {
    try {
      const resp = this.http
        .get<String>(environment.apiConfig.api_local_url + this.videoUrl)
        .subscribe((data: String) => {
          this.videoName$.next(data);
          console.log(this.videoName$);
          // this.router.navigate(['/view-name']);
        });
      return resp;
    } catch (error) {
      console.error(error);
      throw error;
    }
  }
}
