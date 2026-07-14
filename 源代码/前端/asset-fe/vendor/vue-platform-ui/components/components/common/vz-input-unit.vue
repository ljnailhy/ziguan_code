<template>
	<div>
		<el-input
			v-model="svalue"
			:placeholder="placeholder"
			:readonly="readonly"
			:disabled="disabled"
			:clearable="clearable"
			:maxlength="maxlength"
			@input="updateValue"
			@change="inputunitChange"
			@clear="inputunitClear"
			@blur="inputunitBulr($event.target.value)"
		>
			<template #suffix>
				<span class="el-input_ut">{{ text }}</span>
			</template>
		</el-input>
	</div>
</template>
<script setup lang="ts" name="jkInputUnit">
import { ref } from 'vue';

const props = defineProps({
	value: {
		type: [String,Number],
		default:()=>''
	},
	text: {
		type: String,
		defalut: () => '',
	},
	maxlength: {
		type: [Number, String],
	},
	placeholder: {
		type: String,
	},
	readonly: {
		type: Boolean,
		default: () => false,
	},
	disabled: {
		type: Boolean,
		default: () => false,
	},
	clearable: {
		type: Boolean,
		default: () => false,
	},
});

const emit = defineEmits(['update:value', 'input', 'change', 'blur', 'clear']);

//定义变量
const svalue = ref(props.value);

//input事件
const updateValue = (val: string) => {
	emit('update:value', val);
	emit('input', val);
};
//change事件
const inputunitChange = (val: string) => {
	emit('change', val);
};
//blur事件
const inputunitBulr = (val: string) => {
	emit('blur', val);
};
//clear事件
const inputunitClear = () => {
	emit('clear');
};
</script>