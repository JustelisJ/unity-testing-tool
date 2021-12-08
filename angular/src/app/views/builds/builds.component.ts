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
  builds: string[];
  buildReports: BuildReport[] = [];
  private sub: any;
  game: string;

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
      this.videoService.getBuilds(this.game);
    });
    this.videoService.builds$.subscribe((b) => (this.builds = b));

    // this.initDummyData();
  }

  initDummyData(): void {
    this.builds = ['build1', 'build2', 'build3', 'build4'];

    this.buildReports.push({
      game: 'test1',
      buildId: 'testBuild1',
      averagePlayTime: 1,
      totalNumberofBugs: 2,
      averageNumberOfBugsPerPlayrun: 3,
    });
    this.buildReports.push({
      game: 'test2',
      buildId: 'testBuild2',
      averagePlayTime: 1,
      totalNumberofBugs: 2,
      averageNumberOfBugsPerPlayrun: 3,
    });
    console.log(this.buildReports);
  }

  onItemClick(build: string): void {
    this.videoService.getPlayrunsNames(this.game, build);
    console.log(this.game, build);
    this.router.navigate(['/app-playruns', this.game, build]);
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }
}
