import request from '../../utils/request';
import { VITE_API_INFRASTRUCTURE_URL } from '../../config/global';
/**
 *
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 *
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 * @method findAll 获取字典
 * @method addSysDics 新增字典
 */
export function useDicsApi() {
	return {
		findAll: (data: object, params: object) => {
			return request({
				url: `${VITE_API_INFRASTRUCTURE_URL}/sys/dictionarys`,
				method: 'post',
				data,
				params,
			});
		},
		add: (data: object) => {
			return request({
				url: `${VITE_API_INFRASTRUCTURE_URL}/sys/dictionary`,
				method: 'post',
				data,
			});
		},
		update: (data: object) => {
			return request({
				url: `${VITE_API_INFRASTRUCTURE_URL}/sys/dictionary`,
				method: 'put',
				data,
			});
		},
		delete: (id: string | number) => {
			return request({
				url: `${VITE_API_INFRASTRUCTURE_URL}/sys/dictionary/${id}`,
				method: 'delete',
			});
		},
		findById: (id: string | number) => {
			return request({
				url: `${VITE_API_INFRASTRUCTURE_URL}/sys/dictionary/${id}`,
				method: 'get',
			});
		},
		findDicItemById(data: object) {
			return request({
				url: `${VITE_API_INFRASTRUCTURE_URL}/sys/dictionaryitems?current=1&size=10000`,
				method: 'post',
				data,
			});
		},
	};
}
