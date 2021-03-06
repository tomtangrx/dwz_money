package common.codegenerate;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import common.util.DomUtil;

/**
 * 解析数据说明文件.
 * 
 * @author lisq
 * 
 */
public class ModelParse {
	private String fileName;
	private List<ColumnModel> attributes = new ArrayList<ColumnModel>();
	private String table;
	private String className;
	private String arg1;
	public String getArg1() {
		return arg1;
	}

	public void setArg1(String arg1) {
		this.arg1 = arg1;
	}

	private String classDesc;
	private String packageName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public ClassModel parse() {
		ClassModel model = new ClassModel();
		Document doc = DomUtil.getXmlDocument(fileName);
		Node list = doc.getElementsByTagName("class").item(0);
		table = DomUtil.getAttribute(list, "table");
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
				} else
					config.setIskey("false");
				//节点类型
				config.setNodeType(child.getNodeName());
				//节点汉字注释
				config.setDesc(DomUtil.getAttribute(child, "desc"));
				//最大长度
				config.setMaxLength(DomUtil.getAttribute(child, "maxLength"));
				//最短长度
				config.setMinLength(DomUtil.getAttribute(child, "minLength"));
				//列属性名
				config.setCols(DomUtil.getAttribute(child, "cols"));
				//列属性名
				config.setRows(DomUtil.getAttribute(child, "rows")); 
				//下拉菜单对应的文本的值
				config.setValues(DomUtil.getAttribute(child, "values"));
				//下拉菜单的对应的文本的显示值
				config.setNames(DomUtil.getAttribute(child, "names"));
				//浏览框设置的对应的url
				config.setBrowerUrl(DomUtil.getAttribute(child, "browerUrl"));
				//设置为选择下拉框
				config.setBrower(DomUtil.getAttribute(child, "brower"));
				//列属性名
				config.setName(DomUtil.getAttribute(child, "name"));
				//是否要检索
				config.setQuery(DomUtil.getAttribute(child, "query")); 
				//列类型
				config.setColumnType(DomUtil.getAttribute(child, "columnType"));
				//列属性名
				config.setName(DomUtil.getAttribute(child, "name"));
				//是否模糊匹配
				config.setQuerylike(DomUtil.getAttribute(child, "querylike"));
				//显示在list.jsp里面的列宽度
				config.setWidth(DomUtil.getAttribute(child, "width"));
				//是否是文本域
				config.setTextarea(DomUtil.getAttribute(child, "textarea"));
				//是否在list.jsp里面列表中显示出来
				config.setVisible(DomUtil.getAttribute(child, "visible"));
				//对应数据库中的列名
				config.setColumn(DomUtil.getAttribute(child, "column"));
				//类型
				config.setType(DomUtil.getAttribute(child, "type"));
				//是否是业务字典
				config.setSelectType(DomUtil.getAttribute(child, "selectType"));
				//是否必填字段
				config.setNotnull(DomUtil.getAttribute(child, "notnull"));
				//是否在edit.jsp中可以编辑
				config.setNoedit(DomUtil.getAttribute(child, "noedit"));
				//对应的样式
				config.setClas(DomUtil.getAttribute(child, "class"));
				//在数据库里面的字段长度.
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
		return model;
	}
}
