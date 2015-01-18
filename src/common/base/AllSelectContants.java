﻿package common.base;

public enum AllSelectContants {
	QUESTION_SORT("questionSort"), MENULEVEL("menulevel"), ORDERSTATUS(
			"orderstatus"), PLAN_STATUS("planstatus"), YESORNO("yesorno"), PLAN_TYPE(
			"plantype"), USER_TYPE("usertype"), MENU_TARGET("menutarget"), QUESTION_STATUS(
			"questionStatus"), DIARY_TYPE("diarytype"), DEAL_TYPE("dealType"), INSURE_COMPANY(
			"insure_company"), YESORNO_STATUS("yesorno_status"), INSUREDCOMPANY_DICT(
			"insuredcompany_dict"), ALLPARAMTYPE("allparamtype"), MENUTREE(
			"menuTree"), ORGTREE("orgTree"), INSUREDTREE(
			"insuredTree"),INSUREDUNIT_DICT("insuredunit_dict");
	private String name;

	AllSelectContants(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
