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
const currency: Directive = {
  beforeMount(el, binding) {
    const formatCurrency = value => {
      if (!value) return "--";
      const numValue = Number(value);
      if (isNaN(numValue)) {
        return value;
      }
      return numValue.toLocaleString("zh-CN", { style: "currency", currency: "CNY" });
    };

    el.innerHTML = formatCurrency(binding.value);
  },
  updated(el, binding) {
    const formatCurrency = value => {
      if (!value) return "--";
      const numValue = Number(value);
      if (isNaN(numValue)) {
        return value;
      }
      return numValue.toLocaleString("zh-CN", { style: "currency", currency: "CNY" });
    };

    el.innerHTML = formatCurrency(binding.value);
  }
};
export default currency;
