<template>
	<div>
		{{ getTenant(props.tenantId) }}
	</div>
</template>
<script setup lang="ts" name="dictUserName">
import commonFunction from '../../utils/commonFunction';

const { getTenant } = commonFunction();

const props = defineProps({
	tenantId: {
		type: [Number, String],
		default: () => '',
	},
});
</script>
