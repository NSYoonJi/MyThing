import { API_END_POINT } from "../constants";
import axios from "axios";

axios.interceptors.request.use(
  (config) => {
    // 요청 보낼 때
    const token = sessionStorage.getItem("accessToken")

    if (token !== null) {
      config.headers["Authorization"] =
        "Bearer " + token
      // "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBY2Nlc3NUb2tlbiIsImV4cCI6MTcxNjY2OTIzNiwia2FrYW9JZCI6IjI3MzYwNzgxMzcifQ.I3DjZNN1BCf0gqfYs4LnG88vyLLQTughOP-R3mVkFYdauqUuSNlFaMAEGKAaaUvwGtrES5hkeQuos3aZ7sorcA";
    }

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

export const patchConfig = (url, data) => {
  return {
    method: "PATCH",
    url: API_END_POINT + url,
    headers: {
      "Content-Type": "application/json",
    },
    data: data,
  };
};

export const patchConfig_MULTI = (url, data) => {
  return {
    method: "PATCH",
    url: API_END_POINT + url,
    headers: {
      "Content-Type": "multipart/form-data",
    },
    data: data,
  };
};