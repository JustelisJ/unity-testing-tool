import { Component, ElementRef, OnDestroy, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { VideoService } from 'src/app/shared/services/video.service';

@Component({
  selector: 'app-builds',
  templateUrl: './builds.component.html',
  styleUrls: ['./builds.component.scss'],
})
export class BuildsComponent implements OnInit, OnDestroy {
  builds: string[]; // BuildReport[]
  private sub: any;
  game: string;

  constructor(
    private videoService: VideoService,
    private router: Router,
    private route: ActivatedRoute,
    private elementRef: ElementRef
  ) {}

  ngOnInit(): void {
    // this.videoService.getBuilds();
    // this.videoService.builds$.subscribe((b) => (this.builds = b));
    this.initDummyData();
    this.elementRef.nativeElement.ownerDocument.body.style.backgroundColor =
      '#212121';
    this.sub = this.route.params.subscribe((params) => {
      this.game = params.game;
    });
  }

  initDummyData(): void {
    this.builds = [
      'build1',
      'build2',
      'build3',
      'build4',
      'build5',
      'build6',
      'build7',
      'build8',
    ];
  }

  onItemClick(build: string): void {
    // this.videoService.getPlayruns(this.game, build);
    this.router.navigate(['/app-playruns', this.game, build]);
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }
}
