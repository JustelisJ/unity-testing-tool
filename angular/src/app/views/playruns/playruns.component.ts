import { Component, ElementRef, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { VideoService } from 'src/app/shared/services/video.service';

@Component({
  selector: 'app-playruns',
  templateUrl: './playruns.component.html',
  styleUrls: ['./playruns.component.scss'],
})
export class PlayrunsComponent implements OnInit, OnDestroy {
  playruns: string[];
  private sub: any;
  game = '';
  build = '';

  constructor(
    private videoService: VideoService,
    private router: Router,
    private route: ActivatedRoute,
    private elementRef: ElementRef
  ) {}

  ngOnInit(): void {
    this.sub = this.route.params.subscribe((params) => {
      this.game = params.game;
      this.build = params.build;
      this.videoService.getPlayrunsNames(this.game, this.build);
    });
    this.videoService.playruns$.subscribe((p) => (this.playruns = p));

    this.elementRef.nativeElement.ownerDocument.body.style.backgroundColor =
      '#212121';
    // this.initDummyData();
  }

  initDummyData(): void {
    this.playruns = [
      'run1',
      'run2',
      'run3',
      'run4',
      'run5',
      'run6',
      'run7',
      'run8',
    ];
  }

  onItemClick(playrun: string): void {
    this.videoService.getPlayrunReport(this.game, this.build, playrun);
    this.router.navigate(['/app-video', this.game, this.build, playrun]);
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }
}
