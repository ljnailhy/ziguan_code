<template>
	<div>
		{{ props.value == true ? '是' : '否' }}
	</div>
</template>
<script setup lang="ts" name="dictSwitch">
const props = defineProps({
	value: {
		type: [Number, String, Boolean],
		default: () => '',
	},
});
</script>
