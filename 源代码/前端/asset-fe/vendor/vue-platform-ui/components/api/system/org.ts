import { VITE_API_INFRASTRUCTURE_URL } from '../../config/global';
import request from '../../utils/request';

/**
 *
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 *
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 * @method findAll 获取部门(admin)
 * @method getTestMenu 获取后端动态路由菜单(test)
 */
export function useOrgApi() {
	return {
		findAll: (data: object, params: object) => {
			return request({
				url: `${VITE_API_INFRASTRUCTURE_URL}/sys/orgs`,
				method: 'post',
				data,
				params,
			});
		},
		add: (data: object) => {
			return request({
				url: `${VITE_API_INFRASTRUCTURE_URL}/sys/org`,
				method: 'post',
				data,
			});
		},
		update: (data: object) => {
			return request({
				url: `${VITE_API_INFRASTRUCTURE_URL}/sys/org`,
				method: 'put',
				data,
			});
		},
		delete: (id: string | number) => {
			return request({
				url: `${VITE_API_INFRASTRUCTURE_URL}/sys/org/${id}`,
				method: 'delete',
			});
		},
		findById: (id: string | number) => {
			return request({
				url: `${VITE_API_INFRASTRUCTURE_URL}/sys/org/${id}`,
				method: 'get',
			});
		},
		findByIds: (data: Array<string | number>) => {
			return request({
				url: `${VITE_API_INFRASTRUCTURE_URL}/sys/orgs/ids`,
				method: 'post',
				data,
			});
		},
	};
}
