export declare namespace Table {
    interface Pageable {
        current: number;
        size: number;
        total: number;
    }
    interface StateProps {
        tableData: any[];
        pageable: Pageable;
        searchParam: {
            [key: string]: any;
        };
        searchInitParam: {
            [key: string]: any;
        };
        totalParam: {
            [key: string]: any;
        };
        icon?: {
            [key: string]: any;
        };
    }
}
export declare namespace HandleData {
    type MessageType = "" | "success" | "warning" | "info" | "error";
}
export declare namespace Theme {
    type ThemeType = "light" | "inverted" | "dark";
    type GreyOrWeakType = "grey" | "weak";
}
