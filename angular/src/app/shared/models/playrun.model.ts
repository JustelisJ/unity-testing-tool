import { Bug, TimeInterval } from './bug.model';

export interface PlayRunReport {
  gameRef: string;
  buildRef: string;
  videoRef: string;
  bugReport: Bug[];
}

export interface LevelData {
  levelName: string;
  playTimeInterval: TimeInterval;
  checkPoints?: CheckPoint[];
}

export interface CheckPoint {
  checkPointName: string;
  result: GameState;
  playInterval: TimeInterval;
}

export enum GameState {
  COMPLETED,
  SKIPPED,
  PLAYER_SKIPPED
}
