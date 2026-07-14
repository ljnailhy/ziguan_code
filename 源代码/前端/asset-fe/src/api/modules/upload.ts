import { Upload } from "@/api/interface/index";
import { VITE_API_INFRASTRUCTURE_URL } from "@/api/config/servicePort";
import http from "@/api";

/**
 * @name 文件上传模块
 * @param bucketName => asset-service
 * @param fileType =>AUDIO, FILE, IMAGE, VIDEO
 */

// 文件上传
export const uploadImg = (params: FormData, bucketName = "asset-service", fileType = "IMAGE") => {
  return http.post<Upload.ResFileUrl>(`${VITE_API_INFRASTRUCTURE_URL}/sys/file/upload/minio/${bucketName}/${fileType}`, params, {
    cancel: false,
    loading: false
  });
};
