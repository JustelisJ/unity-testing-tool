import { AfterViewInit, Component, ElementRef, OnInit } from '@angular/core';
import { VideoService } from '../../shared/services/video.service';
import { Bug } from '../../shared/models/bug.model';
import { VideoObject } from 'src/app/shared/models/video.model';
import { Subscription } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { PlayRunReport } from '../../shared/models/playrun.model';

@Component({
  selector: 'app-video',
  templateUrl: './video.component.html',
  styleUrls: ['./video.component.scss'],
})
export class VideoComponent implements OnInit, AfterViewInit {
  videoObject: VideoObject = {
    name: '',
    src: '',
  };
  playrunReport: PlayRunReport;
  colors: string[] = [];
  private sub: any;
  game = '';
  videoName = '';
  build = '';

  videoItems = [
    {
      name: 'BUI Presentation',
      src: 'assets/videos/BUI-Presentation.mp4',
      type: 'video/mp4',
    },
  ];

  activeIndex = 0;
  currentVideo = null;
  data: any;
  duration = 0;
  durationString = '00:00:00';
  hoverEvent: string;
  bugName = '';
  bugDescription = '';
  activeBug = false;
  bugs: Bug[] = [];

  constructor(
    private videoService: VideoService,
    private elementRef: ElementRef,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.sub = this.route.params.subscribe((params) => {
      this.game = params.game;
      this.build = params.build;
      this.videoName = params.playrun;
      this.videoService.playrunReport$.subscribe((p) => {
        this.playrunReport = p;
        this.videoItems = [
          {
            name: this.videoName,
            src:
              encodeURIComponent('assets/videos/') +
              this.game +
              encodeURIComponent('/') +
              this.build +
              encodeURIComponent('/') +
              this.videoName,
            type: 'video/mp4',
          },
        ];
        this.currentVideo = this.videoItems[this.activeIndex];
        this.randColorPick();
      });
    });
    this.activeBug = false;
  }

  ngAfterViewInit(): void {
    this.elementRef.nativeElement.ownerDocument.body.style.backgroundColor =
      '#212121';
  }

  videoPlayerInit(data: any): void {
    this.data = data;

    this.data
      .getDefaultMedia()
      .subscriptions.loadedMetadata.subscribe(this.initVdo.bind(this));
  }

  bugsInit(): void {
    const dummyBugs: Bug[] = [
      {
        bugName: 'test bug 1',
        bugDescription: 'test description 1',
        timeVideoReference: {
          from: 10,
          to: 30,
        },
      },
      {
        bugName: 'test bug 2',
        bugDescription: 'test description 2',
        timeVideoReference: {
          from: 45,
          to: 60,
        },
      },
    ];
    this.bugs = dummyBugs;
  }

  initVdo(): void {
    this.data.play();
  }

  startPlaylistVdo(item: any, index: number): void {
    this.activeIndex = index;
    this.currentVideo = item;
  }

  onMetadata(e, media): void {
    this.duration = media.duration;
    const minutes = Math.floor(this.duration / 60);
    const seconds = Math.floor(this.duration - minutes * 60);
    const hours = Math.floor(this.duration / 3600);
    this.durationString =
      (hours < 10 ? '0' + hours : hours) +
      ':' +
      (minutes < 10 ? '0' + minutes : minutes) +
      ':' +
      (seconds < 10 ? '0' + seconds : seconds);
  }

  secondsToTimeString(bug: Bug): string {
    let timeString = '';
    if (bug) {
      const minutes = Math.floor(bug.timeVideoReference.from / 60);
      const seconds = Math.floor(bug.timeVideoReference.from - minutes * 60);
      const hours = Math.floor(bug.timeVideoReference.from / 3600);
      timeString =
        (hours < 10 ? '0' + hours : hours) +
        ':' +
        (minutes < 10 ? '0' + minutes : minutes) +
        ':' +
        (seconds < 10 ? '0' + seconds : seconds);
    }
    if (bug) {
      const minutes = Math.floor(bug.timeVideoReference.to / 60);
      const seconds = Math.floor(bug.timeVideoReference.to - minutes * 60);
      const hours = Math.floor(bug.timeVideoReference.to / 3600);
      timeString +=
        ' - ' +
        (hours < 10 ? '0' + hours : hours) +
        ':' +
        (minutes < 10 ? '0' + minutes : minutes) +
        ':' +
        (seconds < 10 ? '0' + seconds : seconds);
    }
    return timeString;
  }

  randColorPick(): void {
    if (
      this.playrunReport &&
      this.playrunReport.bugReports &&
      this.playrunReport.bugReports.length > 0
    ) {
      this.playrunReport.bugReports.forEach(() => {
        this.colors.push(
          '#' + Math.floor(Math.random() * 16777215).toString(16)
        );
      });
    }
  }

  bugWidth(bug: Bug): number {
    return (
      ((bug.timeVideoReference.to - bug.timeVideoReference.from) /
        this.duration) * 100);
  }

  bugMargin(bug: Bug): number {
    return (bug.timeVideoReference.from / this.duration) * 100;
  }

  showBugInfoOnClick(bug: Bug): void {
    this.activeBug = true;
    this.bugName = bug.bugName;
    this.bugDescription = bug.bugDescription;
    this.data.getDefaultMedia().currentTime = bug.timeVideoReference.from;
  }

  showInfoOnHover(bug: Bug): void {
    if (!this.activeBug) {
      this.bugName = bug.bugName;
      this.bugDescription = bug.bugDescription;
    }
  }

  clearInfo(): void {
    if (!this.activeBug) {
      this.bugName = '';
      this.bugDescription = '';
    }
  }
}
