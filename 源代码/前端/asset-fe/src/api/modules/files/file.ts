import http from "@/api";
import { VITE_API_INFRASTRUCTURE_URL, VITE_API_ASSET_URL } from "@/api/config/servicePort";

export function useFileApi() {
  return {
    findAll: (params: any) => {
      return http.post<any>(`${VITE_API_INFRASTRUCTURE_URL}/sys/files?current=${params.current}&size=${params.size}`, params, {
        cancel: false
      });
    },
    getProjectFile: (params: any) => {
      return http.get<any>(`${VITE_API_ASSET_URL}/project/info/files`, params);
    }

    // add: (data: object) => {
    // 	return request({
    // 		url: `${VITE_API_INFRASTRUCTURE_URL}/sys/file`,
    // 		method: 'post',
    // 		data,
    // 	});
    // },
    // update: (data: object) => {
    // 	return request({
    // 		url: `${VITE_API_INFRASTRUCTURE_URL}/sys/file`,
    // 		method: 'put',
    // 		data,
    // 	});
    // },
    // delete: (id: string | number) => {
    // 	return request({
    // 		url: `${VITE_API_INFRASTRUCTURE_URL}/sys/file/${id}`,
    // 		method: 'delete',
    // 	});
    // },
    // findById: (id: string | number) => {
    // 	return request({
    // 		url: `${VITE_API_INFRASTRUCTURE_URL}/sys/file/${id}`,
    // 		method: 'get',
    // 	});
    // },
    // upload: (data: object) => {
    // 	return request({
    // 		url: `${VITE_API_INFRASTRUCTURE_URL}/sys/file/upload/minio/auto-service/IMAGE`,
    // 		method: 'post',
    // 		data,
    // 		headers: {
    // 			'Content-Type': 'multipart/form-data',
    // 		},
    // 	});
    // },
  };
}
