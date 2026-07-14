/**
 * 向父窗口发送页面加载完成消息
 * @param event
 */
const postComplete = (event: any) => {
  let { source, origin } = event;
  if (!source) {
    source = parent;
  }
  source.postMessage(
    {
      type: "complete"
    },
    origin || "*"
  );
};

/**
 * 获取event的数据
 * @param event
 * @param source 来源
 * @returns
 */
const getEventData = (event: any, source = parent) => {
  // 判断消息是否来自指定的窗口
  if (!event || event.source !== source) {
    console.warn("Received message without source: " + event.data);
    return;
  }

  console.log("Received workflow message: " + JSON.stringify(event.data));
  if (!event.data) {
    return;
  }

  try {
    // return JSON.parse(event.data);
    return event.data;
  } catch (e) {
    console.error("Data '" + event.data + "' isn't a json.");
  }
};

/**
 * 发送提示信息阻止提交
 * @param event
 * @param errorMessage 提示信息
 * @param type 提示类型
 */
const postToast = (event: any, errorMessage: string, type?: "warning" | "error" | "success") => {
  let { source, origin } = event;
  if (!source) {
    source = parent;
  }
  source.postMessage(
    {
      type: type || "error",
      errorMessage
    },
    origin || "*"
  );
};

// 导出方法
export { postComplete, getEventData, postToast };
