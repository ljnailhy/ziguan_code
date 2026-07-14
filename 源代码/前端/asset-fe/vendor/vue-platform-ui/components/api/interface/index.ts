// 请求响应参数（不包含data）
export interface Result {
  code: string | number;
  msg: string;
  errorMessage?: string;
}

// 请求响应参数（包含data）
export interface ResultData<T = any> extends Result {
  data: T;
}

// 分页响应参数
export interface ResPage<T> {
  data: T[];
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
    captcha: string;
    captchaKey: string;
    terminal: "pc" | "app" | "mp-weixin";
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
    username: number;
    isMajor: boolean;
    customerSource: number;
    customerGrade: number;
    introduceUser: string;
  }
  export interface ResCustomerList {
    id: number;
    createStamp: string;
    creator: number;
    customerGrade: number;
    customerSource: number;
    enabled: boolean;
    followUserId: number;
    introduceUser: string;
    isMajor: boolean;
    nickname: string;
    orgId: number;
    phone: string;
    username: string;
    userId: number;
    lastEditStamp: number;
    lastEditor: number;
  }
}
