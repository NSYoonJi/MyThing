import { API_END_POINT } from "../constants";
import axios from "axios";

axios.interceptors.request.use(
  (config) => {
    // 요청 보낼 때
    config.headers["Authorization"] =
      "Bearer " +
      "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBY2Nlc3NUb2tlbiIsImV4cCI6MTY4MDY1Njg5MCwia2FrYW9JZCI6IjI3MzMwNzE0ODQifQ.OLb7__aiNReZ-hxe8G0ZmscQk9JVThp9bIFnLKWl5HwNLHUOEnLSKyTO_w18a31pfF3Oa5iY1PSBwxMMMqn5GA";
    return config;
  },
  function (error) {
    // 요청했는데 에러나면
    return Promise.reject(error);
  }
);

export const postConfig_JSON = (url, data) => {
  return {
    method: "POST",
    url: API_END_POINT + url,
    headers: {
      "Content-Type": "application/json",
    },
    data: data,
  };
};

export const postConfig_MULTI = (url, data) => {
  return {
    method: "POST",
    url: API_END_POINT + url,
    headers: {
      "Content-Type": "multipart/form-data",
    },
    data: data,
  };
};

export const getConfig = (url, params) => {
  return {
    method: "GET",
    url: API_END_POINT + url,
    params: params,
  };
};
