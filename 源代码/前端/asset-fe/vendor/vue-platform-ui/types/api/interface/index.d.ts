export interface Result {
    code: string | number;
    msg: string;
    errorMessage?: string;
}
export interface ResultData<T = any> extends Result {
    data: T;
}
export interface ResPage<T> {
    data: T[];
    current: number;
    size: number;
    total: number;
}
export interface ReqPage {
    current: number;
    size: number;
}
export declare namespace Upload {
    interface ResFileUrl {
        fileUrl: string;
    }
}
export declare namespace Login {
    interface ReqLoginForm {
        account: string;
        password: string;
        captcha: string;
        captchaKey: string;
        terminal: "pc" | "app" | "mp-weixin";
        type: string;
    }
    interface ResLogin {
        access_token: string;
        loginUser: string;
    }
    interface ResAuthButtons {
        [key: string]: string[];
    }
}
export declare namespace Customer {
    interface ReqCustomerParams extends ReqPage {
        phone: string;
        username: number;
        isMajor: boolean;
        customerSource: number;
        customerGrade: number;
        introduceUser: string;
    }
    interface ResCustomerList {
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
