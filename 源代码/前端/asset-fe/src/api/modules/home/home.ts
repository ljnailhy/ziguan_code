import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import http from "@/api";

/**
 * @name 统计项目
 */

// 统计
export const getHomeStatistics = () => {
  return http.get<any>(`${VITE_API_ASSET_URL}/home/page/statistics`);
};

// 概况
export const getPastProjects = () => {
  return http.get<any>(`${VITE_API_ASSET_URL}/home/page/overview/past/projects`);
};

// 概况
export const getAnalyse = () => {
  return http.get<any>(`${VITE_API_ASSET_URL}/home/page/asset/analyse`);
};

// 核销项目分类
export const getClassification = () => {
  return http.get<any>(`${VITE_API_ASSET_URL}/home/page/write/off/classification`);
};

// 临期浴巾
export const getWarning = () => {
  return http.get<any>(`${VITE_API_ASSET_URL}/home/page/deadline/warning`);
};

// 临期浴巾
export const proceedStatistics = () => {
  return http.get<any>(`${VITE_API_ASSET_URL}/home/page/proceed/statistics`);
};
