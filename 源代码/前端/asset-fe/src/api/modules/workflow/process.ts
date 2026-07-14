import { VITE_API_WORKFLOW_URL } from "@/api/config/servicePort";
import http from "@/api";

export function processApi() {
  return {
    /**
     * 分页查找
     *
     * @param params 代理机构分页入参(含页码)
     * @returns Result
     * @author wangtao
     * @since 2024-06-25 19:28:45
     */
    listTodoProcess: (params: any) => {
      return http.get(
        `${VITE_API_WORKFLOW_URL}/process/todoList?params%5BbeginTime%5D=${params.beginTime}&params%5BendTime%5D=${params.endtime}&params%5Btitle%5D=${params.title}`,
        params
      );
    },
    finishedList: (params: any) => {
      return http.get(
        `${VITE_API_WORKFLOW_URL}/process/finishedList?params%5BbeginTime%5D=${params.beginTime}&params%5Bendtime%5D=${params.endtime}&params%5Btitle%5D=${params.title}`,
        params
      );
    },
    ownList: (params: any) => {
      return http.get(
        `${VITE_API_WORKFLOW_URL}/process/ownList?params%5BbeginTime%5D=${params.beginTime}&params%5Bendtime%5D=${params.endtime}&params%5Btitle%5D=${params.title}`,
        params
      );
    },
    // 根据数据对象ID获取历史变量
    getHistoricVariableByDoId: (doId: number) => {
      return http.get(
        `${VITE_API_WORKFLOW_URL}/process/instance/id/${doId}`,
        {},
        {
          cancel: false,
          loading: false
        }
      );
    }
  };
}

// export function processApi() {
// 	return {
// 		// 我待办的流程
// 		listTodoProcess: (data: object, params: object) => {
// 			return request({
// 				url: `${VITE_API_WORKFLOW_URL}/process/todoList`,
// 				method: 'get',
// 				params: {
// 					pageNum: 1,
// 					pageSize: 10000,
// 				},
// 			});
// 		},
// // 根据数据对象ID获取流程实例ID
// getProcessInstanceIdByDoId: (doId: number) => {
//   return request({
//     url: `${VITE_API_WORKFLOW_URL}/process/instance/id/${doId}`,
//         method: 'get',
//       });
//     },
//   };
// }
