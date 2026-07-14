<template>
	<div>
		<el-upload
			class="avatar-uploader"
			:class="{ disabled: uploadDisabled }"
                  :headers="{ Authorization: uploadToken, TenantId: tenantId }"
			action="/api/infrastructure/sys/file/upload"
			v-model:file-list="fileList"
			list-type="picture-card"
			:limit="props.limit"
			:on-preview="handlePictureCardPreview"
			:on-remove="handleRemove"
			:on-success="handlerSuccess"
		>
			<el-icon><Plus /></el-icon>
		</el-upload>
		<el-dialog v-model="dialogVisible">
			<img w-full :src="dialogImageUrl" alt="Preview Image" />
		</el-dialog>
	</div>
</template>
<script lang="ts" setup name="vzUpload">
import { ref, onMounted, computed, watch } from 'vue';
import { Plus } from '@element-plus/icons-vue';
import Cookies from 'js-cookie';
import type { UploadFile, UploadUserFile } from 'element-plus';

const emit = defineEmits(['onSuccess', 'update:fileList']);
const props = defineProps({
	limit: {
		type: Number,
		default: () => 1,
	},
	url: {
		type: String,
		default: () => '',
	},
});
const dialogImageUrl = ref('');
const dialogVisible = ref(false);
const token = Cookies.get('token');
const uploadToken = ref(token);
const tenantId = ref(JSON.parse(localStorage.getItem('tenant') || '{}')?.id);
const fileList = ref<UploadUserFile[]>([]);
const handleRemove = (file: UploadFile) => {
	if (props.limit == 1) {
		emit('update:fileList', '');
	}
};

const handlerSuccess = (file: EmptyObjectType) => {
	// let url = window.location.origin;
	if (props.limit == 1) {
		emit('update:fileList', '/files/' + file.data);
	}
	emit('onSuccess', file);
};
const handlePictureCardPreview = (file: UploadFile) => {
	dialogImageUrl.value = file.url!;
	dialogVisible.value = true;
};

// const handleDownload = (file: UploadFile) => {
// 	let url = window.location.origin;
// 	// window.open('http://' + url + '/files/' + row.fileUrl);
// 	const downloadUrl = url + '/files/' + file.response.data; // 替换为实际文件的 URL
// 	const downloadLink = document.createElement('a');
// 	downloadLink.href = downloadUrl;
// 	downloadLink.download = file.name; // 替换为要保存的文件名
// 	document.body.appendChild(downloadLink);
// 	downloadLink.click();
// 	document.body.removeChild(downloadLink);
// };
const uploadDisabled = computed(() => {
	return fileList.value.length >= props.limit;
});
watch(
	() => props.url,
	(val) => {
		fileList.value = val ? [{ url: val }] : [];
	}
);
onMounted(() => {
	uploadToken.value = token;
  tenantId.value = JSON.parse(localStorage.getItem("tenant") || "{}")?.id;
});
</script>
<style lang="scss">
.el-upload--picture-card {
	--el-upload-picture-card-size: 100px;
}
.el-upload-list--picture-card .el-upload-list__item {
	width: 100px;
	height: 100px;
}

.disabled .el-upload--picture-card {
	display: none;
}
</style>
