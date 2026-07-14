import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import { PaymentCollectionLedgerPageRequest, PaymentCollectionPageDTO } from "@/api/modules/dataStatistics/returnCount/interface";

/**
 *  分配/变更主表接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangshuai
 * @since 2024-06-28 10:13:53
 */
export function paymentCollectionLedger() {
  const CONTEXT = "/ledger";
  return {
    /**
     * 分页查找
     *
     * @param params 分配/变更主表分页入参(含页码)
     * @returns Result
     * @author wangshuai
     * @since 2024-06-28 10:13:53
     */
    findPaymentCollectionAll: (params: PaymentCollectionLedgerPageRequest) => {
      return http.post<Array<PaymentCollectionPageDTO>>(
        `${VITE_API_ASSET_URL}${CONTEXT}/payment/collection?current=${params.current}&size=${params.size}`,
        params
      );
    },
    /**
     * 导出
     * @author Yuqiang Wu
     * @since 2024-08-10 09:47:37
     */
    export: (params: any) => {
      return http.download(`${VITE_API_ASSET_URL}${CONTEXT}/payment/collection/export`, params);
    }
  };
}
