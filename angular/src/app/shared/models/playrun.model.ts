import { Bug, TimeInterval } from './bug.model';

export interface PlayRunReport {
  gameRef: string;
  buildRef: string;
  videoRef: string;
  bugReport: Bug[];
  // levelData: LevelData[];
}

export interface LevelData {
  levelName: string;
  playTimeInterval: TimeInterval;
  checkPoints: CheckPoint[];
  puzzles: string; // not final
}

export interface CheckPoint {
  checkPointName: string;
  checkPointDefinition: CheckPointDefinition;
  result: GameState;
  playInterval: TimeInterval;
}

export interface CheckPointDefinition {
  type: string; // not final
}

export enum GameState {
  COMPLETED,
  SKIPPED,
  PLAYER_SKIPPED
}
