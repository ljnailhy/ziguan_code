export type LayoutType = "vertical" | "classic" | "transverse" | "columns";

export type AssemblySizeType = "large" | "default" | "small";

export type LanguageType = "zh" | "en" | null;

/* GlobalState */
export interface GlobalState {
  layout: LayoutType;
  assemblySize: AssemblySizeType;
  language: LanguageType;
  maximize: boolean;
  primary: string;
  isDark: boolean;
  isGrey: boolean;
  isWeak: boolean;
  asideInverted: boolean;
  headerInverted: boolean;
  isCollapse: boolean;
  accordion: boolean;
  breadcrumb: boolean;
  breadcrumbIcon: boolean;
  tabs: boolean;
  tabsIcon: boolean;
  footer: boolean;
}

/* UserState */
export interface UserState {
  token: string;
  userInfo: any;
  tenantInfo: any;
  menuList: any[];
}

/* tabsMenuProps */
export interface TabsMenuProps {
  icon: string;
  title: string;
  path: string;
  name: string;
  close: boolean;
  isKeepAlive: boolean;
}

/* TabsState */
export interface TabsState {
  tabsMenuList: TabsMenuProps[];
}

/* AuthState */
export interface AuthState {
  routeName: string;
  authButtonList: {
    [key: string]: string[];
  };
  authMenuList: any[];
}

/* KeepAliveState */
export interface KeepAliveState {
  keepAliveName: string[];
}

export interface BaseState {
  enums: {
    [key: string]: string[];
  };
  projects: {
    [key: string]: {};
  };
  assets: {
    [key: string]: {};
  };
  agencies: {
    [key: string]: {};
  };
  contracts: {
    [key: string]: {};
  };
  customers: {
    [key: string]: {};
  };
  tenants: any[];
  product: any[];
  roles: any[];
  posts: any[];
  vertions: any[];

  // users: {
  //   [key: string]: string[];
  // };
}

export interface DialogState {
  drawerProps: {
    [key: string]: {};
  };
  projectIds: any[];
  validationErrorMessage: string;
  deleteFiles: {
    [key: string]: any[];
  };
}
