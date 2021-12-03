import { AfterViewInit, Component, ElementRef, OnInit } from '@angular/core';
import { VideoService } from '../../shared/services/video.service';
import { Bug } from '../../shared/models/bug.model';
import { VideoObject } from 'src/app/shared/models/video.model';
import { Subscription } from 'rxjs';
import { ActivatedRoute } from '@angular/router';

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
  bugs: Bug[] = [];
  colors: string[] = [];
  private sub: any;
  game = '';
  videoName = '';
  build = '';

  videoItems = [
    {
      name: 'Company Presentation',
      src: 'assets/videos/testGame/1.0/CompanyPres.mp4',
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
      this.videoItems = [
        {
          name: this.videoName,
          src:
            'assets/videos/' +
            this.game +
            '/' +
            this.build +
            '/' +
            this.videoName,
          type: 'video/mp4',
        },
      ];
      this.currentVideo = this.videoItems[this.activeIndex];
      console.log(this.videoItems);
    });
    this.activeBug = false;
    // this.videoService.getVideoName();
    // this.videoService.videoName$.subscribe((v) => {
    //   this.videoObject = v;
    //   console.log(this.videoObject);
      // this.videoItems = [
      //   {
      //     name: this.videoObject?.name,
      //     src: 'assets/' + this.videoObject?.src,
      //     type: 'video/mp4',
      //   },
      // ];
      // this.currentVideo = this.videoItems[this.activeIndex];
      // console.log(this.videoItems);
    // });
    this.randColorPick();
    this.videoService.bugs$.subscribe((b) => {
      this.bugs = b;
      console.log(this.bugs);
    });
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
    // this.data
    //   .getDefaultMedia()
    //   .subscriptions.ended.subscribe(this.nextVideo.bind(this));
  }

  bugsInit(): void {
    const dummyBugs: Bug[] = [
      {
        bugName: 'test bug 1',
        bugDescription: 'test description 1',
        timeVideoReference: {
          start: 40,
          end: 90,
        },
      },
      {
        bugName: 'test bug 2',
        bugDescription: 'test description 2',
        timeVideoReference: {
          start: 75,
          end: 100,
        },
      },
      {
        bugName: 'test bug 3',
        bugDescription: 'test description 3',
        timeVideoReference: {
          start: 140,
          end: 150,
        },
      },
      {
        bugName: 'test bug 4',
        bugDescription: 'test description 4',
        timeVideoReference: {
          start: 160,
          end: 190,
        },
      },
    ];
    this.bugs = dummyBugs;
  }

  refreshLatestVideo(): void {
    this.activeBug = false;
    this.videoService.getVideoName();
    this.currentVideo = this.videoItems[this.activeIndex];
  }

  // nextVideo(): void {
  //   this.activeIndex++;

  //   if (this.activeIndex === this.videoItems.length) {
  //     this.activeIndex = 0;
  //   }

  //   this.currentVideo = this.videoItems[this.activeIndex];
  // }

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
    console.log('duration: ', this.duration / 60);
  }

  randColorPick(): void {
    this.bugs.forEach((bug) => {
      this.colors.push('#' + Math.floor(Math.random() * 16777215).toString(16));
    });
  }

  showBugInfoOnClick(bug: Bug): void {
    this.activeBug = true;
    this.bugName = bug.bugName;
    this.bugDescription = bug.bugDescription;
    this.data.getDefaultMedia().currentTime = bug.timeVideoReference.start;
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
