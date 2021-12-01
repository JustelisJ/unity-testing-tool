import { Component, ElementRef, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { VideoService } from 'src/app/shared/services/video.service';

@Component({
  selector: 'app-games',
  templateUrl: './games.component.html',
  styleUrls: ['./games.component.scss'],
})
export class GamesComponent implements OnInit {
  games: string[] = [];

  constructor(
    private videoService: VideoService,
    private router: Router,
    private elementRef: ElementRef
  ) {}

  ngOnInit(): void {
    // this.videoService.getGames();
    // this.videoService.games$.subscribe((g) => (this.games = g));
    this.initDummyData();
    this.elementRef.nativeElement.ownerDocument.body.style.backgroundColor =
      '#212121';
  }

  initDummyData(): void {
    this.games = ['game1', 'game2', 'game3', 'game4', 'game5', 'game6'];
  }

  onItemClick(game: string): void {
    // this.videoService.getPlayruns(game);
    this.router.navigate(['/app-playruns', game]);
  }
}
