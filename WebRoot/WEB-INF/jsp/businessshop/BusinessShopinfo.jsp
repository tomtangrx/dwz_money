
<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/include.inc.jsp"%> 
<div class="pageContent">
	<form method="post" action="/money/businessshop!doAdd.do"
		class="pageForm required-validate"
		onsubmit="return myCallback(this, closeDialogWindow);">
		<div class="pageFormContent" layoutH="57">
					 <div class="unit">
						<label>
							商家编号:
						</label>
							<input name="shopmId" class="textInput required"  type="text"   />
					</div>
					 <div class="unit">
						<label>
							商铺名称 :
						</label>
							<input name="shopName" class="textInput required"  type="text"   />
					</div>
					 <div class="unit">
						<label>
							商铺编号 :
						</label>
							<input name="shopSno" class="textInput required"  type="text"   />
					</div>
					 <div class="unit">
						<label>
							商铺状态 :
						</label>
							<my:newselect tagName="shopStatus"  paraType="shopstatus" width="100"  />
					</div>
					 <div class="unit">
						<label>
							联系人名称:
						</label>
							<input name="shopContactName" class="textInput required"  type="text"   />
					</div>
					 <div class="unit">
						<label>
							联系人手机:
						</label>
							<input name="shopConPhone" class="textInput required"  type="text"   />
					</div>
					 <div class="unit">
						<label>
							邮箱:
						</label>
							<input name="shopEmail"  class="email required"  type="text"   />
					</div>
					 <div class="unit">
						<label>
							地址:
						</label>
							<textarea class="required" name="shopAddress"  rows="4" cols="40"></textarea>
					</div>
					 <div class="unit">
						<label>
							签约日期:
						</label>
							<input type="text" name="shopDate" class="date required" size="30" readOnly="true"   />
							<a class="inputDateButton" href="javascript:;">选择</a>
					</div>
					 <div class="unit">
						<label>
							经度:
						</label>
							<input name="shopJingdu" class="textInput "  type="text"   />
					</div>
					 <div class="unit">
						<label>
							纬度:
						</label>
							<input name="shopWeidu" class="textInput "  type="text"   />
					</div>
					 <div class="unit">
						<label>
							省份:
						</label>
							<my:newselect tagName="shopProvince"  tableName="dict_province" nameColumn="s_provname" idColumn ="n_provid" width="100"  />
					</div>
					 <div class="unit">
						<label>
							市:
						</label>
							<my:newselect tagName="shopCity"  tableName="dict_city" nameColumn="s_cityname" idColumn ="n_cityid" width="100"  />
					</div>
					 <div class="unit">
						<label>
							区县:
						</label>
							<my:newselect tagName="shopxian"  paraType="yesorno" width="100"  />
					</div>
					 <div class="unit">
						<label>
							备注:
						</label>
							<input name="shopRemark" class="textInput "  type="text"   />
					</div>
					 <div class="unit">
						<label>
							主营:
						</label>
							<input name="shopMain" class="textInput "  type="text"   />
					</div>
					 <div class="unit">
						<label>
							简介:
						</label>
							<input name="shopIntroduce" class="textInput "  type="text"   />
					</div>
					 <div class="unit">
						<label>
							特色:
						</label>
							<input name="shopSpecial" class="textInput "  type="text"   />
					</div>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								保存
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								取消
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>