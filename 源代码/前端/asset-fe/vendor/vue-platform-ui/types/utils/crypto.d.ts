export declare function rsaEncryptToBase64(plain: string): string;
export declare const aesEncrypt: (data: string, key: string, iv: string) => string;
export declare const aesDecrypt: (encryptedData: string, key: string, iv: string) => string;
export declare const generateIV: () => string;
export declare const generateAesKey: () => string;
