import { atom } from "recoil";

export const good = atom({
  key: "good",
  default: [],
});

export const notGood = atom({
  key: "notGood",
  default: [],
});

export const userInfo = atom({
  key: "userInfo",
  default: { preference: [], nickname: "", imagePath: "" },
});

export const survey = atom({
  key: "survey",
  default: {
    question: "",
    answerA: [],
    answerB: [],
    answerC: [],
    answerD: [],
    images: {},
  },
});

export const notes = atom({
  key: "notes",
  default: [],
});

export const surveyResult = atom({
  key: "surveyResult",
  default: {
    id: null,
    name: null,
    brand: null,
    imgURL: null,
  },
});
