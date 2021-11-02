import { Component, OnInit } from '@angular/core';
import { VideoService } from '../../shared/services/video.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  private videoName = '';
  private videoItem;
  public currentVideo;
  private duration = 0;
  private data: any;
  private activeIndex = 0;

  constructor(private videoService: VideoService) {}

  ngOnInit(): void {
    this.videoService.getVideoName();
    this.videoService.videoName$.subscribe((v) => {
      this.videoName = v;
      this.videoItem = [
        {
          name: 'Business Intelligence Presentation',
          src: 'assets/videos/' + this.videoName,
          type: 'video/mp4',
        },
      ];
      this.currentVideo = this.videoItem[this.activeIndex];
      const video = document.createElement('video');
      video.preload = 'metadata';

      video.onloadedmetadata = () => {
        window.URL.revokeObjectURL(this.currentVideo.src);
        this.duration = video.duration;
      };
      console.log(video);
    });
  }

  videoPlayerInit(data: any): void {
    this.data = data;

    this.data
      .getDefaultMedia()
      .subscriptions.loadedMetadata.subscribe(this.initVdo.bind(this));
    this.data
      .getDefaultMedia()
      .subscriptions.ended.subscribe(this.nextVideo.bind(this));
  }

  refreshLatestVideo(): void {
    this.videoService.getVideoName();
    this.currentVideo = this.videoItem[this.activeIndex];

    const video = document.createElement('video');
    video.preload = 'metadata';

    video.onloadedmetadata = () => {
      window.URL.revokeObjectURL(this.currentVideo.src);
      this.duration = video.duration;
    };
    console.log(video);
  }

  nextVideo(): void {
    this.activeIndex++;

    if (this.activeIndex === this.videoItem.length) {
      this.activeIndex = 0;
    }

    this.currentVideo = this.videoItem[this.activeIndex];
  }

  initVdo(): void {
    this.data.play();
  }

  startPlaylistVdo(item: any, index: number): void {
    this.activeIndex = index;
    this.currentVideo = item;
  }
}
