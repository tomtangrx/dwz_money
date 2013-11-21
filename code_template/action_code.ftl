<#include "/com.renjie120.codegenerate.common.ftl">package ${model.packageName};

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;

import dwz.constants.BeanManagerKey;
import dwz.framework.core.exception.ValidateFieldsException;
import dwz.framework.utils.excel.XlsExport;
import dwz.present.BaseAction;

/**
 * 关于${model.classDesc}的Action操作类.
 * @author ${author}
 * ${auth}
 * ${website}
 */ 
public class ${nm}Action extends BaseAction {
	/**
	 *  序列化对象.
	 */
	private static final long serialVersionUID = 1L;
	//业务接口对象.
	${nm}Manager pMgr = bf.getManager(BeanManagerKey.${classarg}Manager);
	//业务实体对象
	private ${nm} ${classarg}Vo;
	//当前页数
	private int page = 1;
	//每页显示数量
	private int pageSize = 50;
	//总页数
	private long count;
	
	public String beforeAdd() {
		return "detail";
	}

	public String doAdd() {
		try {
			${nm}Impl ${classarg}Impl = new ${nm}Impl(<@allfield2 nm=model.attributes />);
			pMgr.create${nm}(${classarg}Impl);
		} catch (ValidateFieldsException e) {
			log.error(e);
			return ajaxForwardError(e.getLocalizedMessage());
		}
		writeToPage(response,getText("msg.operation.success"));
		return null;
	}

	public String doDelete() {
		String ids = request.getParameter("ids");
		pMgr.remove${nm}(ids);
		return ajaxForwardSuccess(getText("msg.operation.success"));
	}

	public String beforeUpdate() {
		${classarg}Vo = pMgr.get${nm}(orgId);
		return "editdetail";
	}

	public String doUpdate() {
		try {
			${nm}Impl ${classarg}Impl = new ${nm}Impl(<@allfield2 nm=model.attributes />);
			pMgr.update${nm}(${classarg}Impl);
		} catch (ValidateFieldsException e) {
			e.printStackTrace();
		}
		writeToPage(response,getText("msg.operation.success"));
		return null;
	} 
	
	public enum ExportFiled {
		<#assign index=0><#assign size=model.attributes?size>
		<#list model.attributes as attr>  ${attr.name?upper_case}("${attr.desc}")<#assign index=index+1><#if index<size>,</#if></#list>;
		private String str;

		ExportFiled(String str) {
			this.str = str;
		}

		public String getName() {
			return this.str;
		}
	}

	public String beforeQuery() {
		return "query";
	}

	public String export() {
		response.setContentType("Application/excel");
		response.addHeader("Content-Disposition","attachment;filename=${nm}List.xls");

		int pageNum = getPageNum();
		int numPerPage = getNumPerPage();
		int startIndex = (pageNum - 1) * numPerPage;
		Map<${nm}SearchFields, Object> criterias = getCriterias();

		Collection<${nm}> ${classarg}List = pMgr.search${nm}(criterias, realOrderField(),
				startIndex, numPerPage);

		XlsExport e = new XlsExport();
		int rowIndex = 0;

		e.createRow(rowIndex++);
		for (ExportFiled filed : ExportFiled.values()) {
			e.setCell(filed.ordinal(), filed.getName());
		}

		for (${nm} ${classarg} : ${classarg}List) {
			e.createRow(rowIndex++);

			for (ExportFiled filed : ExportFiled.values()) {
				switch (filed) {
				<#list model.attributes as attr>
					case ${attr.name?upper_case}:
						 e.setCell(filed.ordinal(), ${classarg}.get${attr.name?cap_first}()); 
					break;
				</#list>  
				default:
					break;
				}

			}
		}

		e.exportXls(response);
		return null;
	}

	public String query() {
		int pageNum = getPageNum();
		int numPerPage = getNumPerPage();
		int startIndex = (pageNum - 1) * numPerPage;
		Map<${nm}SearchFields, Object> criterias = getCriterias();

		Collection<${nm}> moneyList = pMgr.search${nm}(criterias, realOrderField(),
				startIndex, numPerPage);

		request.setAttribute("pageNum", pageNum);
		request.setAttribute("numPerPage", numPerPage);
		request.setAttribute("totalCount", pMgr.searchOrgNum(criterias));
		ActionContext.getContext().put("list", moneyList);
		ActionContext.getContext().put("pageNum", pageNum);
		ActionContext.getContext().put("numPerPage", numPerPage);
		ActionContext.getContext().put("totalCount",pMgr.searchOrgNum(criterias));
		return "list";
	}

	public String reQuery() {
		return "list";
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	private Map<${nm}SearchFields, Object> getCriterias() {
		Map<${nm}SearchFields, Object> criterias = new HashMap<${nm}SearchFields, Object>();
		return criterias;
	}

	public ${nm} get${nm}Vo() {
		return ${classarg}Vo;
	}

	public void set${nm}Vo(${nm} ${classarg}Vo) {
		this.${classarg}Vo = ${classarg}Vo;
	}
  
  	<@allGetAndSet nm=model.attributes />
}
