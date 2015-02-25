
<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/money/buglist!query.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection"
		value="${param.orderDirection}" />
</form>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/money/buglist!query.do" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td> 
						问题描述</td><td>
							<input name="bugDesc" size="20"  class="textInput " type="text"  value="<s:property value="vo.bugDesc"/>" />
					</td> 
					<td> 
						备注</td><td>
							<input name="remark" size="20"  class="textInput " type="text"  value="<s:property value="vo.remark"/>" />
					</td> 
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									检索
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li>
				<a class="add" href="/money/buglist!beforeAdd.do" target="dialog" mask="true"
					title="添加"><span>添加</span> </a>
			</li>
			<li>
				<a class="delete" href="/money/buglist!doDelete.do" postType="string"
					target="selectedTodo" rel="ids" title="确定要删除吗?"><span>删除</span>
				</a>
			</li>
			<li>
				<a class="edit" href="/money/buglist!beforeUpdate.do?sno={sno}" mask="true"
					target="dialog" title="修改"><span>修改</span> </a>
			</li>
			<li>
				<a class="icon" href="/money/buglist!export.do" target="dwzExport"
					targetType="navTab" title="确实要导出这些记录吗?"><span>导出</span> </a>
			</li>
		</ul>
	</div>
	<table class="table" layoutH="-138">
		<thead>
			<tr>
				<th width="30">
					<input type="checkbox" group="ids" class="checkboxCtrl">
				</th>
				<th width="100"    orderField="BUGTYPE" >
						问题类型 
				</th> 
				<th width="150"    orderField="BUGDESC" >
						问题描述 
				</th> 
				<th width="100"    orderField="CREATEUSER" >
						问题发现人 
				</th> 
				<th width="100"    orderField="CREATETIME" >
						发现时间 
				</th> 
				<th width="150"    orderField="NEEDTIME" >
						待解决时间 
				</th> 
				<th width="100"    orderField="CONSOLEPEOPLE" >
						解决人 
				</th> 
				<th width="150"    orderField="CONSOLETILE" >
						解决时间 
				</th> 
				<th width="150"    orderField="REMARK" >
						备注 
				</th> 
			</tr>
		</thead>
		<tbody>
			<s:iterator value="list" status="stu">
				<tr target="sno" rel="<s:property value="sno" />">
					<td style="text-align:center;">
						<input name="ids" value="<s:property value="sno" />"
							type="checkbox">
					</td>
					<td style="text-align:center;">
						<div style='width:100px'><s:property value="bugType" /></div>
					</td> 
					<td style="text-align:center;">
						<div style='width:150px'><s:property value="bugDesc" /></div>
					</td> 
					<td style="text-align:center;">
						<div style='width:100px'><s:property value="createUser" /></div>
					</td> 
					<td style="text-align:center;">
						<div style='width:100px'><s:property value="createTime" /></div>
					</td> 
					<td style="text-align:center;">
						<div style='width:150px'><s:property value="needTime" /></div>
					</td> 
					<td style="text-align:center;">
						<div style='width:100px'><s:property value="consolePeople" /></div>
					</td> 
					<td style="text-align:center;">
						<div style='width:150px'><s:property value="consoleTile" /></div>
					</td> 
					<td style="text-align:center;">
						<div style='width:150px'><s:property value="remark" /></div>
					</td> 
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage"
				onchange="navTabPageBreak({numPerPage:this.value,pageNum:1})">
				<option value="20"
					<%if((request.getAttribute("numPerPage")+"").equals("20")){%>
					selected <%} %>>
					20
				</option>
				<option value="50"
					<%if((request.getAttribute("numPerPage")+"").equals("50")){%>
					selected <%} %>>
					50
				</option>
				<option value="100"
					<%if((request.getAttribute("numPerPage")+"").equals("100")){%>
					selected <%} %>>
					100
				</option>
				<option value="200"
					<%if((request.getAttribute("numPerPage")+"").equals("200")){%>
					selected <%} %>>
					200
				</option>
			</select>
			<span>条，总共${totalCount}条记录</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${totalCount}"
			numPerPage="${numPerPage}" pageNumShown="20" currentPage="${pageNum}"></div>
	</div>
</div>

