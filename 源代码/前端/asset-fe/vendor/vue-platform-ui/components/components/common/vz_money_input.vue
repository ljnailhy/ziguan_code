<template>
  <div class="moneyInput">
    <el-input-number
      v-model="modelValue"
      :step="props.step"
      :min="props.min"
      :max="props.max"
      :disabled="props.disabled"
      :precision="props.precision"
      :controls="false"
      :placeholder="props.placeholder"
      @input="handleInput"
      @change="change"
      @focus="focus"
      @blur="blur"
      @keydown="channelInputLimit"
      v-thousands
    >
    </el-input-number>
    <span class="money">{{ text }}</span>
    <div class="chineseMoney" v-if="showWord">
      {{ toUpperCaseAmount(modelValue) }}
    </div>
  </div>
</template>

<script setup lang="ts" name="vzMoneyInput">
import { ref, watch, nextTick } from "vue";

interface MoneyInputProps {
  step?: number;
  max?: number;
  min?: number;
  text?: string;
  disabled?: boolean;
  precision?: number;
  value?: number;
  showWord?: boolean;
  placeholder?: string;
}

const props = withDefaults(defineProps<MoneyInputProps>(), {
  step: 1,
  text: "",
  max: 999999999999,
  disabled: false,
  precision: 2,
  showWord: true,
});

const emit = defineEmits(["update:modelValue", "change", "blur", "focus"]);

const modelValue = ref(); // 使用number类型的ref

const channelInputLimit = (e) => {
  const key = e.key;
  const notAllowList = ["e", "+", "-"];
  if (notAllowList.includes(key)) {
    e.returnValue = false;
    return false;
  }
  return true;
};

// 监听输入框的输入事件，更新input的值
const handleInput = (value: any) => {
  modelValue.value = value;
  if (value > props.max) {
    emit("update:modelValue", props.max);
  } else {
    emit("update:modelValue", value);
  }
};

//值改变时候
const change = (value: any) => {
  emit("change", value);
};

// 在组件 Input 失去焦点时触发
const blur = (value: any) => {
  emit("blur", value);
};

// 在组件 Input 获得焦点时触发
const focus = (value: any) => {
  emit("focus", value);
};

watch(
  () => props.value,
  (value) => {
    modelValue.value = value;
  },
  { deep: true, immediate: true }
);
const toUpperCaseAmount = (currencyDigits: number | string | undefined) => {
  var MAXIMUM_NUMBER: any = props.max;

  if (!currencyDigits) return "";
  if (currencyDigits > MAXIMUM_NUMBER) {
    return "";
  }

  var CN_ZERO = "零";
  var CN_ONE = "壹";
  var CN_TWO = "贰";
  var CN_THREE = "叁";
  var CN_FOUR = "肆";
  var CN_FIVE = "伍";
  var CN_SIX = "陆";
  var CN_SEVEN = "柒";
  var CN_EIGHT = "捌";
  var CN_NINE = "玖";
  var CN_TEN = "拾";
  var CN_HUNDRED = "佰";
  var CN_THOUSAND = "仟";
  var CN_TEN_THOUSAND = "万";
  var CN_HUNDRED_MILLION = "亿";
  var CN_SYMBOL = "人民币";
  var CN_DOLLAR = "元";
  var CN_TEN_CENT = "角";
  var CN_CENT = "分";
  var CN_INTEGER = "整";
  // Variables:
  var integral; // Represent integral part of digit number.
  var decimal; // Represent decimal part of digit number.
  var outputCharacters; // The output result.
  var parts;
  var digits, radices, bigRadices, decimals;
  var zeroCount;
  var i, p, d;
  var quotient, modulus;
  // Validate input string:
  currencyDigits = String(currencyDigits);

  // Process the coversion from currency digits to characters:
  // Separate integral and decimal parts before processing coversion:
  parts = String(currencyDigits).split(".");
  if (parts.length > 1) {
    integral = parts[0];
    decimal = parts[1];
    // Cut down redundant decimal digits that are after the second.
    decimal = decimal.substr(0, 2);
  } else {
    integral = parts[0];
    decimal = "";
  }
  // Prepare the characters corresponding to the digits:
  digits = [
    CN_ZERO,
    CN_ONE,
    CN_TWO,
    CN_THREE,
    CN_FOUR,
    CN_FIVE,
    CN_SIX,
    CN_SEVEN,
    CN_EIGHT,
    CN_NINE,
  ];
  radices = ["", CN_TEN, CN_HUNDRED, CN_THOUSAND];
  bigRadices = ["", CN_TEN_THOUSAND, CN_HUNDRED_MILLION];
  decimals = [CN_TEN_CENT, CN_CENT];
  // Start processing:
  outputCharacters = "";
  // Process integral part if it is larger than 0:
  if (Number(integral) > 0) {
    zeroCount = 0;
    for (i = 0; i < integral.length; i++) {
      p = integral.length - i - 1;
      d = integral.substr(i, 1);
      quotient = p / 4;
      modulus = p % 4;
      if (d == "0") {
        zeroCount++;
      } else {
        if (zeroCount > 0) {
          outputCharacters += digits[0];
        }
        zeroCount = 0;
        outputCharacters += digits[Number(d)] + radices[modulus];
      }
      if (modulus == 0 && zeroCount < 4) {
        outputCharacters += bigRadices[quotient];
        zeroCount = 0;
      }
    }
    outputCharacters += CN_DOLLAR;
  }
  // Process decimal part if there is:
  if (decimal != "") {
    for (i = 0; i < decimal.length; i++) {
      d = decimal.substr(i, 1);
      if (d != "0") {
        outputCharacters += digits[Number(d)] + decimals[i];
      }
    }
  }
  // Confirm and return the final output string:
  if (outputCharacters == "") {
    outputCharacters = CN_ZERO + CN_DOLLAR;
  }
  if (decimal == "") {
    outputCharacters += CN_INTEGER;
  }
  return outputCharacters;
};
//自定义指令
const vThousands = {
  mounted: function (el) {
    const numberInput = el.getElementsByTagName("input")[0];
    const textInput = document.createElement("input");
    textInput.type = "text";
    textInput.autocomplete = "off";
    textInput.classList.add("el-input__inner");

    numberInput.insertAdjacentElement("afterend", textInput);

    // 处理默认值
    const defaultValue = numberInput.value;
    if (defaultValue) {
      const floatValue = parseFloat(defaultValue.replace(/,/g, ""));
      textInput.value = floatValue.toLocaleString("zh", {
        minimumFractionDigits: 2,
        maximumFractionDigits: 2,
      });
    }

    textInput.style.display = "block";
    numberInput.style.display = "none";

    textInput.onfocus = () => {
      textInput.style.display = "none";
      numberInput.style.display = "block";
      numberInput.focus();
    };

    numberInput.onblur = () => {
      numberInput.style.display = "none";
      textInput.style.display = "block";
    };

    numberInput.addEventListener("input", () => {
      const valueWithoutComma = numberInput.value.replace(/,/g, "");
      if (valueWithoutComma) {
        const floatValue =
          parseFloat(valueWithoutComma) > props.max
            ? props.max
            : parseFloat(valueWithoutComma);

        textInput.value = floatValue.toLocaleString("zh", {
          minimumFractionDigits: 2,
          maximumFractionDigits: 2,
        });
      } else {
        textInput.value = "";
      }
    });
  },
  updated: function (el) {
    if (el.tagName.toLocaleUpperCase() !== "INPUT") {
      const numberInput = el.getElementsByTagName("input")[0];
      const textInput = el.getElementsByTagName("input")[1];

      const parentClass = el.classList.value;
      if (parentClass.includes("is-disabled")) {
        textInput.setAttribute("disabled", true);
      } else {
        textInput.removeAttribute("disabled");
      }

      const valueWithoutComma = numberInput.value.replace(/,/g, "");
      if (valueWithoutComma) {
        const floatValue =
          parseFloat(valueWithoutComma) > props.max
            ? props.max
            : parseFloat(valueWithoutComma);

        textInput.value = floatValue.toLocaleString("zh", {
          minimumFractionDigits: 2,
          maximumFractionDigits: 2,
        });
      } else {
        textInput.value = "";
      }
    }
  },
};
</script>

<style scoped lang="scss">
.moneyInput {
  position: relative;
  display: flex;
  align-items: center;
  flex: 1;
  .chineseMoney {
    position: absolute;
    bottom: -15px;
    font-size: 10px;
    color: var(--el-color-primary);
    padding: 0 5px;
  }
  .money {
    position: absolute;
    right: 10px;
    font-size: 13px;
  }
  .el-input-number {
    width: 100%;
    color: var(--el-text-color-regular);
  }
  .el-input-number.is-without-controls .el-input__wrapper {
    padding-left: 0px !important;
  }
  .el-input-number .el-input__inner {
    text-align: left !important;
    padding-left: 11px !important;
  }
}
</style>
