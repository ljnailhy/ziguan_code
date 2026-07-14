/*
	需求：实现一个拖拽指令，可在父元素区域任意拖拽元素。

	思路：
		1、设置需要拖拽的元素为absolute，其父元素为relative。
		2、鼠标按下(onmousedown)时记录目标元素当前的 left 和 top 值。
		3、鼠标移动(onmousemove)时计算每次移动的横向距离和纵向距离的变化值，并改变元素的 left 和 top 值
		4、鼠标松开(onmouseup)时完成一次拖拽

	使用：在 Dom 上加上 v-draggable 即可
	<div class="dialog-model" v-draggable></div>
*/
import type { Directive } from "vue";
// interface ElType extends HTMLElement {
//   parentNode: any;
// }
const thousands: Directive = {
  mounted: function (el) {
    // 获取数字输入框
    const numberInput = el.getElementsByTagName("input")[0];
    // 创建一个新的 el-input 元素用来展示格式化后的值
    const textInput = document.createElement("input");
    textInput.type = "text";
    textInput.autocomplete = "off";
    textInput.classList.add("el-input__inner");
    // 把创建的元素插入到数字框后
    numberInput.insertAdjacentElement("afterend", textInput);
    // 默认效果
    textInput.style.display = "block";
    numberInput.style.display = "none";
    // 文本框聚焦时显示原来数字类型框
    textInput.onfocus = () => {
      textInput.style.display = "none";
      numberInput.style.display = "block";
      // 等待数字框加载成功时聚焦数字框
      // nextTick(() => {
      numberInput.focus();
      // });
    };
    // 数字框失焦时显示格式化后的值
    numberInput.onblur = () => {
      numberInput.style.display = "none";
      textInput.style.display = "block";
    };
  },
  updated: function (el) {
    // 数据改变时格式化赋值到隐藏的文本框
    if (el.tagName.toLocaleUpperCase() !== "INPUT") {
      const numberInput = el.getElementsByTagName("input")[0];
      const textInput = el.getElementsByTagName("input")[1];
      // 同步禁用状态
      const parentClass = el.classList.value;
      if (parentClass.includes("is-disabled")) {
        textInput.setAttribute("disabled", true);
      } else {
        textInput.removeAttribute("disabled");
      }
      // 千分位格式化
      const valueWithoutComma = numberInput.value.replace(/,/g, ""); // 去除千分号的','
      if (valueWithoutComma) {
        // 转换为浮点数
        const floatValue = parseFloat(valueWithoutComma);
        // 格式化为千分位
        textInput.value = floatValue.toLocaleString("zh", {
          minimumFractionDigits: 2,
          maximumFractionDigits: 2
        });
      } else {
        textInput.value = "";
      }
    }
  }
};
export default thousands;
