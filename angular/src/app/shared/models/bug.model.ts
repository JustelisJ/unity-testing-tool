export interface BugReport {
  buildID: string;
  playRunReportsIds: string[];
  surveys: PlayRunSurvey[];
  bugReports: Map<string, Bug>;
  averagePlayTime: number;
  CheckPointsPassed: number;
}

export interface Bug {
  bugName: string;
  bugDescription: string;
  timeVideoReference: TimeInterval;
}

export interface TimeInterval {
  from: number;
  to: number;
}

export interface PlayRunSurvey {
  playerDetails: PlayerDetails;
  questionnaire: SurveyQuestion[];
}

export interface PlayerDetails {
  age: number;
  gender: string;
}

export interface SurveyQuestion {
  answerValue: string;
}

export interface SurveyQuestionTemplate {
  questionWording: string;
  answerType: AnswerType;
}

export enum AnswerType {
  TRUE_FALSE,
  NUMERIC,
  TEXT
}
