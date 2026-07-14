<template>
	<div>
		{{ getVersion(props.versionId) }}
	</div>
</template>
<script setup lang="ts" name="dictUserName">
import commonFunction from '../../utils/commonFunction';

const { getVersion } = commonFunction();

const props = defineProps({
	versionId: {
		type: [Number, String],
		default: () => '',
	},
});
</script>
