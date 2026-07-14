<template>
	<div>
		{{ props.options.filter((res: EmptyObjectType) => res.value == props.value)[0]?.label }}
	</div>
</template>
<script setup lang="ts" name="dictEnum">
interface Option {
	value: string | number;
	label: string;
}
const props = defineProps({
	value: {
		type: [Number, String],
		default: () => '',
	},
	options: {
		type: Array as () => Option[],
		default: () => <EmptyArrayType>[],
	},
});
</script>
