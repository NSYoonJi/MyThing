// import { getConfig, postConfig_MULTI, postConfig_JSON } from "./config";
import { postConfig_JSON } from "./config";
import { getConfig, postConfig_MULTI } from "./config";
import axios from "axios";

export const getUserInfo = async () => {
  const res = await axios(getConfig("/members"));
  return res;
};

export const getUserReviews = async () => {
  const res = await axios(getConfig("/profile/review"));
  return res;
};

export const postUserReivew = (data) => {
  axios(postConfig_MULTI("/profile/review", data));
};

export const getUserServey = async () => {
  const res = await axios(getConfig("/profile/survey"));
  return res;
};

// 인기 향수 리스트
export const getPopularPerfume = async () => {
  const res = await axios(getConfig("/recommend/popular"));
  return res;
};

export const getSurveyQuestion = async (num) => {
  const res = await axios(getConfig(`/survey/${num}`));
  return res;
};

export const getPerfumeInfo = async (no) => {
  const res = await axios(getConfig(`/perfume/${no}`));
  return res.data;
};

export const getNoteList = async (name) => {
  return await axios(getConfig(`/recommend/${name}`));
};

// 클릭 기반 향수 리스트(오늘의 향수)
export const getClickPerfume = async (perfumeId) => {
  const res = await axios(getConfig(`/recommend/survey/${perfumeId}`));
  return res;
};

// 연령 추천
export const getAgeGenderPerfume = async () => {
  const res = await axios(getConfig("/perfume/age"));
  return res;
};

export const postSurveyResult = async (data) => {
  const res = await axios(postConfig_JSON("/guest/guest-survey", data));
  return res;
};

export const postJoinMember = async (data) => {
  const res = await axios(postConfig_MULTI("/join", data));
  return res;
}