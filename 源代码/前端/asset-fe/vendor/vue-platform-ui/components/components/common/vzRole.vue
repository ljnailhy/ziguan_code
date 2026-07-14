<template>
	<div style="width: 100%">
		<el-select
			v-model="modelValue"
			:multiple="isMultiple"
			remote-show-suffix
			filterable
			remote
			reserve-keyword
			:remote-method="remoteMethod"
			:loading="loading"
			placeholder="搜索角色名称"
			@change="handleChange"
			style="width: 100%"
			value-key="id"
		>
			<template #footer>
				<el-pagination
					@current-change="handleCurrentChange"
					small
					background
					layout="prev, pager, next"
					:page-size="params.size"
					:total="total"
					class="mt-4"
				/>
			</template>
			<el-option v-for="item in options" :key="item.id" :label="item.roleName" :value="item">
				<span>{{ item.roleName }}</span>
			</el-option>
		</el-select>
	</div>
</template>
<script setup lang="ts" name="vzRole">
import { ref, Ref, onMounted, computed, PropType, watch } from 'vue';
import { useRoleApi } from '../../api/system/role';

interface Option {
	id: string | number;
	roleName: string;
}

const props = defineProps({
	dictType: {
		type: [Number, String],
		default: () => '',
	},
	dictValue: {
		type: [String, Array] as PropType<string | string[] | number | number[]>,
		default: () => '',
	},
	placeholder: {
		type: [String],
		default: () => '请选择角色',
	},
	disabled: {
		type: Boolean,
		default: () => false,
	},
	multiple: {
		type: Boolean,
		default: () => false,
	},
	role: {
		type: Array,
		default: () => [],
	},
});

const emit = defineEmits(['update:modelValue', 'change']);

let modelValue: Ref<string | number | (string[] | number[])>;

// 根据选择模式定义 selectedValues
if (props.multiple) {
	modelValue = ref<string[] | number[]>(Array.isArray(props.dictValue) ? props.dictValue : []);
} else {
	modelValue = ref<string | number>(Array.isArray(props.dictValue) ? props.dictValue[0] : props.dictValue || '');
}
const params = ref({
	current: 1,
	size: 50,
});
const options = ref<Array<Option>>([]);
const total = ref(0);
const keyword = ref('');
const loading = ref(false);
const isMultiple = computed(() => props.multiple === true); // 根据选择模式判断是否启用多选功能

const handleChange = (value: Array<any>) => {
	modelValue.value = value;
	emit('update:modelValue', value);
	emit('change', value);
};
const remoteMethod = (query: string) => {
	keyword.value = query;
	getFun();
};
const handleCurrentChange = (val: number) => {
	params.value.current = val;
	getFun();
};
const getFun = () => {
	loading.value = true;
	useRoleApi()
		.findAll(
			{
				...params.value,
				roleName: keyword.value,
			},
			params.value
		)
		.then((res) => {
			options.value = res.data;
			total.value = res.page.total;
		})
		.finally(() => {
			loading.value = false;
		});
};
watch(
	() => props.role,
	(val) => {
		modelValue.value = val ? val : [];
	}
);
onMounted(() => {
	getFun();
});
</script>
<style lang="scss"></style>
