// 省市区
import pinia from '../stores/index';
import { storeToRefs } from 'pinia';
import { regionsInfo } from '../stores/regions';

export default function () {
	const stores = regionsInfo();
	const { regionInfos } = storeToRefs(stores);
	
	//获取省市区
	const getArea = async (keyName: string, params: object, level: number) => {
		await regionsInfo(pinia).getAreaList(keyName, params, level)
		return regionInfos.value[keyName]
	}

	return {
		getArea
	};
}
