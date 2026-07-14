import { OrderEnum } from "@/enums/commonEnums";

// 请求响应参数（包含data）
export interface Result<T = any> {
  code: string;
  errorMessage?: string;
  data: T;
  page: Partial<ResPage>;
}

// 分页响应参数
export interface ResPage {
  current: number;
  size: number;
  total: number;
}

// 分页请求参数
export interface ReqPage {
  current: number;
  size: number;
}

// 文件上传模块
export namespace Upload {
  export interface ResFileUrl {
    fileUrl: string;
  }
}

// 登录模块
export namespace Login {
  export interface ReqLoginForm {
    account: string;
    password: string;
    type: string;
  }
  export interface ResLogin {
    access_token: string;
    loginUser: string;
  }
  export interface ResAuthButtons {
    [key: string]: string[];
  }
}

// 用户管理模块
export namespace Customer {
  export interface ReqCustomerParams extends ReqPage {
    phone: string;
    username: string;
    isMajor: boolean;
    customerSource: number;
    customerGrade: number;
    introduceUser: string;
    nickname: string;
  }
  export interface CustomerPageRequest extends ReqPage {
    /** 创建时间范围 */
    createStampRange?: number[];
    /** 租户id */
    tenantId?: number;
    /** 创建人 */
    creator?: number;
    /** 创建时间 */
    createStamp?: number;
    /** 最后修改人 */
    lastEditor?: number;
    /** 最后修改时间 */
    lastEditStamp?: number;
    /** 排序字段名 */
    field?: string;
    /** 排序类型 */
    order?: OrderEnum;
  }
  export interface ResCustomerList extends ReqCustomerParams {
    id: number;
    createStamp: string;
    creator: number;
    enabled: boolean;
    followUserId: number;
    orgId: number;
    userId: number;
    lastEditStamp: number;
    lastEditor: number;
  }
}
