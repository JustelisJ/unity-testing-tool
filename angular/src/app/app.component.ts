import { Component, OnInit } from '@angular/core';
import { VideoService } from './shared/services/video.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  videoItems = [
    {
      name: 'Business Intelligence Presentation',
      src: 'assets/videos/BUI-Presentation.mp4',
      type: 'video/mp4',
    },
    {
      name: 'Big Buck Bunny',
      src: 'http://static.videogular.com/assets/videos/big_buck_bunny_720p_h264.mov',
      type: 'video/mp4',
    },
    {
      name: 'Elephants Dream',
      src: 'http://static.videogular.com/assets/videos/elephants-dream.mp4',
      type: 'video/mp4',
    },
  ];

  activeIndex = 0;
  currentVideo = this.videoItems[this.activeIndex];
  data: any;
  duration = 0;

  constructor(private videoService: VideoService) {}

  ngOnInit(): void {
    // this.videoService.getVideoName();
    // this.videoService.videoName$.subscribe((v) => {
    //   this.videoName = v;
    // });
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
    // this.videoService.getVideoName();
    this.currentVideo = this.videoItems[this.activeIndex];

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

    if (this.activeIndex === this.videoItems.length) {
      this.activeIndex = 0;
    }

    this.currentVideo = this.videoItems[this.activeIndex];
  }

  initVdo(): void {
    this.data.play();
  }

  startPlaylistVdo(item: any, index: number): void {
    this.activeIndex = index;
    this.currentVideo = item;
  }
}
