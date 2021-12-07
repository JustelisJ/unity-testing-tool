import { BuildReport } from './../../shared/models/build.model';
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
  buildReports: BuildReport[] = [];
  private sub: any;
  game: string;
  hovered = false;

  constructor(
    private videoService: VideoService,
    private router: Router,
    private route: ActivatedRoute,
    private elementRef: ElementRef
  ) {}

  ngOnInit(): void {
    this.elementRef.nativeElement.ownerDocument.body.style.backgroundColor =
      '#212121';
    this.sub = this.route.params.subscribe((params) => {
      this.game = params.game;
      // this.videoService.getBuilds(this.game);
    });
    this.videoService.builds$.subscribe((b) => (this.builds = b));

    this.initDummyData();
  }

  initDummyData(): void {
    this.builds = [
      'build1',
      'build2',
      'build3',
      'build4',
      'build5',
      'build6'
    ];

    this.buildReports.push({
      buildId:  'testBuild1',
      averagePlayTime: 1,
      totalNumberofBugs: 2,
      averageNumberOfBugsPerPlayrun: 3,
      checkpointsPassed: 4,
    });
    this.buildReports.push({
      buildId: 'testBuild2',
      averagePlayTime: 1,
      totalNumberofBugs: 2,
      averageNumberOfBugsPerPlayrun: 3,
      checkpointsPassed: 4,
    });
    console.log(this.buildReports);

  }

  onItemClick(build: string): void {
    this.videoService.getPlayruns(this.game, build);
    console.log(this.game, build);

    this.router.navigate(['/app-playruns', this.game, build]);
  }

  onHover(): void {
    this.hovered = true;
  }

  onLeave(): void {
    this.hovered = false;
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }
}
