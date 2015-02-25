package common.codegenerate;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import common.util.DomUtil;
import common.util.IDatamodelXmlReader;

/**
 * 解析数据说明文件.
 * 
 * @author lisq
 * 
 */
public class ModelParse implements IXmlParse{
	protected static Log log = LogFactory.getLog(ModelParse.class);
	private String fileName;
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean parseValidate() {
	 
		return true;
	}

	/**
	 * 解析class节点里面的内容返回一个classModel对象准备进行文件解析.
	 * @param list
	 * @return
	 */
	public ClassModel parseClass(Node list){
		List<ColumnModel> attributes = new ArrayList<ColumnModel>(); 
		String cacheIdColumn, cacheNameColumn, cacheName,table,className,arg1,classDesc,packageName;
		table = DomUtil.getAttribute(list, "table");
		ClassModel model = new ClassModel();
		
		// 下面设置缓存相关配置信息.
		cacheNameColumn = DomUtil.getAttribute(list, "cacheNameColumn");
		cacheIdColumn = DomUtil.getAttribute(list, "cacheIdColumn");
		cacheName = DomUtil.getAttribute(list, "cacheName");
		System.out.println("cacheName==="+cacheName);
		if (isEmpty(cacheName)) {
			model.setAddToCache("false");
		} else
			model.setAddToCache("true");
		model.setCacheIdColumn(cacheIdColumn);
		model.setCacheName(cacheName);
		model.setCacheNameColumn(cacheNameColumn);

		packageName = DomUtil.getAttribute(list, "package");
		className = DomUtil.getAttribute(list, "name");
		arg1 = DomUtil.getAttribute(list, "arg1");
		classDesc = DomUtil.getAttribute(list, "desc");
		NodeList children = list.getChildNodes();
		for (int j = 0; j < children.getLength(); j++) {
			Node child = children.item(j);
			if ("id".equals(child.getNodeName())
					|| "property".equals(child.getNodeName())) {
				ColumnModel config = new ColumnModel();
				if ("id".equals(child.getNodeName())) {
					config.setIskey("true");
					model.setKeyDesc(DomUtil.getAttribute(child, "desc"));
					model.setKeyName(DomUtil.getAttribute(child, "name"));
					model.setKeyColumn(DomUtil.getAttribute(child, "column"));
					model.setKeyType(DomUtil.getAttribute(child, "type"));
					model.setKeyColumnType(DomUtil.getAttribute(child,
							"columnType"));
				} else
					config.setIskey("false");
				// 节点类型
				config.setNodeType(child.getNodeName());
				// 节点汉字注释
				config.setDesc(DomUtil.getAttribute(child, "desc"));
				// 最大长度
				config.setMaxLength(DomUtil.getAttribute(child, "maxLength"));
				// 来自业务字典表的表名
				config.setFromTable(DomUtil.getAttribute(child, "fromTable"));
				// 来自业务字典表的id列
				config.setIdCoulmn(DomUtil.getAttribute(child, "idCoulmn"));
				/**
				 * 使用缓存id的键.
				 */
				config.setUseCacheId(DomUtil.getAttribute(child, "useCacheId"));
				// 来自业务字典表的name列
				config.setNameColumn(DomUtil.getAttribute(child, "nameColumn"));
				// 设置复杂搜索类型
				config.setComplexQueryType(DomUtil.getAttribute(child,
						"complexQueryType"));
				// 下拉菜单是否含有全选按钮
				config.setAllSelect(DomUtil.getAttribute(child, "allSelect"));
				// 最短长度
				config.setMinLength(DomUtil.getAttribute(child, "minLength"));
				// 是否可以导入该字段
				config.setCanImport(DomUtil.getAttribute(child, "canImport"));
				// 数据库中非空字段
				config.setNotNullInDb(DomUtil
						.getAttribute(child, "notNullInDb"));
				// 是否可以导出
				config.setCanExport(DomUtil.getAttribute(child, "canExport"));
				// 列属性名
				config.setCols(DomUtil.getAttribute(child, "cols"));
				// 数据的最大长度
				String size = DomUtil.getAttribute(child, "size");
				if (isEmpty(size))
					size = "30";
				config.setSize(size);
				// 列属性名
				config.setRows(DomUtil.getAttribute(child, "rows"));
				// 下拉菜单对应的文本的值
				config.setValues(DomUtil.getAttribute(child, "values"));
				// 下拉菜单的对应的文本的显示值
				config.setNames(DomUtil.getAttribute(child, "names"));
				// 浏览框设置的对应的url
				config.setBrowerUrl(DomUtil.getAttribute(child, "browerUrl"));
				// 设置为选择下拉框
				config.setBrower(DomUtil.getAttribute(child, "brower"));
				// 列属性名
				config.setName(DomUtil.getAttribute(child, "name"));
				// 是否要检索
				config.setQuery(DomUtil.getAttribute(child, "query"));
				// 列类型--对应数据库
				config.setColumnType(DomUtil.getAttribute(child, "columnType"));
				// 列属性名
				config.setName(DomUtil.getAttribute(child, "name"));
				// 是否模糊匹配
				config.setQuerylike(DomUtil.getAttribute(child, "querylike"));
				// 显示在list.jsp里面的列宽度
				config.setWidth(DomUtil.getAttribute(child, "width"));
				// 是否是文本域
				// config.setTextarea(DomUtil.getAttribute(child, "textarea"));
				// 是否在list.jsp里面列表中显示出来
				config.setVisible(DomUtil.getAttribute(child, "visible"));
				// 对应数据库中的列名
				config.setColumn(DomUtil.getAttribute(child, "column"));
				// 类型
				String type = DomUtil.getAttribute(child, "type");
				config.setType(type);
				// 显示的类型
				String showType = DomUtil.getAttribute(child, "showType");
				if (showType == null)
					showType = type;
				config.setShowType(showType);
				// 对应的系统业务字段的id
				config.setSelectCode(DomUtil.getAttribute(child, "selectCode"));
				// 是否必填字段
				config.setNotnull(DomUtil.getAttribute(child, "notnull"));
				// 是否在edit.jsp中可以编辑
				config.setNoedit(DomUtil.getAttribute(child, "noedit"));
				// 是否在添加页面中可以添加
				config.setNoadd(DomUtil.getAttribute(child, "noadd"));
				// 使用当前时间
				config.setCurrentTime(DomUtil
						.getAttribute(child, "currentTime"));
				// 使用当前用户
				config.setCurrentUser(DomUtil
						.getAttribute(child, "currentUser"));
				// 对应的样式
				config.setClas(DomUtil.getAttribute(child, "class"));
				// 在数据库里面的字段长度.
				config.setLength(DomUtil.getAttribute(child, "length"));
				attributes.add(config);
			}
		}
		model.setTable(table);
		model.setArg1(arg1);
		model.setClassName(className);
		model.setPackageName(packageName);
		model.setClassDesc(classDesc);
		model.setAttributes(attributes);

		String importFile = DomUtil.getAttribute(list, "importFile");
		String exportFile = DomUtil.getAttribute(list, "exportFile");
		String complexQuery = DomUtil.getAttribute(list, "complexQuery");
		String add = DomUtil.getAttribute(list, "add");
		String update = DomUtil.getAttribute(list, "update");
		String delete = DomUtil.getAttribute(list, "delete");
		// 设置是否有导入按钮，以及导入的权限id.
		model.setCanImport(importFile);
		if (parseString(importFile) > 0)
			model.setImportRole(importFile);

		model.setCanComplexQuery(complexQuery);

		model.setCanExport(exportFile);
		if (parseString(exportFile) > 0)
			model.setExportRole(exportFile);

		model.setCanAdd(add);
		if (parseString(add) > 0)
			model.setAddRole(add);

		model.setCanUpdate(update);
		if (parseString(update) > 0)
			model.setUpdateRole(update);

		model.setCanDelete(delete);
		if (parseString(delete) > 0)
			model.setDeleteRole(delete);

		return model;
	}
	
	/**
	 * 解析第一个class节点.
	 * @return
	 */
	public ClassModel parse() {
		Document doc = DomUtil.getXmlDocument(fileName);
		System.out.println( doc.getElementsByTagName("class").getLength());
		Node list = doc.getElementsByTagName("class").item(1);
		return parseClass(list);
	}
	
	/**
	 * 解析一个文件里面的多个class节点.
	 * @return
	 */
	public List<IDatamodelXmlReader> parseClasses() {
		Document doc = DomUtil.getXmlDocument(fileName);
		List<IDatamodelXmlReader> ans= new ArrayList<IDatamodelXmlReader>();
		int size = doc.getElementsByTagName("class").getLength();
		for(int i=0,j=size;i<j;i++){
			Node list = doc.getElementsByTagName("class").item(i);
			ans.add(parseClass(list));
		}
		return ans;
	}

	private int parseString(String str) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return -1;
		}
	}
	
	private static boolean isEmpty(String str) { 
			return str==null||"".equals(str.trim()); 
	}
}
