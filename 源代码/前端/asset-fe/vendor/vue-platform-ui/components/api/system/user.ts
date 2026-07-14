import request from '../../utils/request';
import { VITE_API_INFRASTRUCTURE_URL } from '../../config/global';
/**
 *
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 *
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 * @method getAll 获取部门(admin)
 * @method getTestMenu 获取后端动态路由菜单(test)
 */
export function useUsersApi() {
	return {
		findAll: (data: object, params: object) => {
			return request({
				url: `${VITE_API_INFRASTRUCTURE_URL}/sys/users`,
				method: 'post',
				data,
				params,
			});
		},
		add: (data: object) => {
			return request({
				url: `${VITE_API_INFRASTRUCTURE_URL}/sys/user`,
				method: 'post',
				data,
			});
		},
		update: (data: object) => {
			return request({
				url: `${VITE_API_INFRASTRUCTURE_URL}/sys/user`,
				method: 'put',
				data,
			});
		},
		delete: (id: string | number) => {
			return request({
				url: `${VITE_API_INFRASTRUCTURE_URL}/sys/user/${id}`,
				method: 'delete',
			});
		},
		findById: (id: string | number) => {
			return request({
				url: `${VITE_API_INFRASTRUCTURE_URL}/sys/user/${id}`,
				method: 'get',
			});
		},
		findByOrg: (data: object, params: object) => {
			return request({
				url: `${VITE_API_INFRASTRUCTURE_URL}/sys/users/org`,
				method: 'POST',
				data,
				params,
			});
		},
		findByIds: (data: Array<string | number>) => {
			return request({
				url: `${VITE_API_INFRASTRUCTURE_URL}/sys/users/ids?hadDetail=false`,
				method: 'post',
				data,
			});
		},
	};
}
