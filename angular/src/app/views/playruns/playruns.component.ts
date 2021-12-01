import { Component, ElementRef, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PlayRunReport } from 'src/app/shared/models/playrun.model';
import { VideoService } from 'src/app/shared/services/video.service';

@Component({
  selector: 'app-playruns',
  templateUrl: './playruns.component.html',
  styleUrls: ['./playruns.component.scss'],
})
export class PlayrunsComponent implements OnInit, OnDestroy {
  playruns: string[]; // PlayRunReport[]
  private sub: any;
  id: string;

  constructor(
    private videoService: VideoService,
    private router: Router,
    private route: ActivatedRoute,
    private elementRef: ElementRef
  ) {}

  ngOnInit(): void {
    // this.videoService.getPlayruns();
    // this.videoService.playruns$.subscribe((p) => (this.playruns = p));
    this.initDummyData();
    this.elementRef.nativeElement.ownerDocument.body.style.backgroundColor =
      '#212121';
    this.sub = this.route.params.subscribe((params) => {
      this.id = params.id;
    });
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
    // this.videoService.getVideo(playrun);
    this.router.navigate(['/home']);
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }
}
