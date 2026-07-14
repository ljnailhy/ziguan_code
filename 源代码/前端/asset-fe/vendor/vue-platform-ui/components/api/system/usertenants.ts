import request from '../../utils/request';
import { VITE_API_INFRASTRUCTURE_URL } from '../../config/global';
/**
 *
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 *
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 */
export function useUsertenantsApi() {
	return {
		findAll: (data: object, params: object) => {
			return request({
				url: `${VITE_API_INFRASTRUCTURE_URL}/sys/usertenants`,
				method: 'post',
				data,
				params,
			});
		},
		add: (data: object) => {
			return request({
				url: `${VITE_API_INFRASTRUCTURE_URL}/sys/usertenant`,
				method: 'post',
				data,
			});
		},
		update: (data: object) => {
			return request({
				url: `${VITE_API_INFRASTRUCTURE_URL}/sys/usertenant`,
				method: 'put',
				data,
			});
		},
		delete: (id: string | number) => {
			return request({
				url: `${VITE_API_INFRASTRUCTURE_URL}/sys/usertenant/${id}`,
				method: 'delete',
			});
		},
		findById: (id: string | number) => {
			return request({
				url: `${VITE_API_INFRASTRUCTURE_URL}/sys/usertenant/${id}`,
				method: 'get',
			});
		},
	};
}
