package ido.InsuredUser;

import ido.Dict_InsuredCompany.InsuredCompanySelectVO;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import common.base.AllSelect;
import common.base.AllSelectContants;
import common.base.ParamSelect;
import common.base.SpringContextUtil;
import common.cache.Cache;
import common.cache.CacheManager;
import common.cache.CacheUtil;
import common.util.NPOIReader;

import dwz.constants.BeanManagerKey;
import dwz.framework.constants.Constants;
import dwz.framework.core.business.AbstractBusinessObjectManager;
import dwz.framework.core.exception.ValidateFieldsException;

/**
 * 关于投保用户的业务操作实现类.
 * 
 * @author www(水清) 任何人和公司可以传播并且修改本程序，但是不得去掉本段声明以及作者署名. http://www.iteye.com
 */
public class InsuredUserManagerImpl extends AbstractBusinessObjectManager
		implements InsuredUserManager {

	private InsuredUserDao insureduserdao = null;
	private JdbcTemplate jdbcTemplate = null;

	/**
	 * 构造函数.
	 */
	public InsuredUserManagerImpl(InsuredUserDao insureduserdao,
			JdbcTemplate jdbcTemplate) {
		this.insureduserdao = insureduserdao;
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 查询总数.
	 * 
	 * @param criterias
	 *            查询条件
	 * @return
	 */
	public Integer searchInsuredUserNum(
			Map<InsuredUserSearchFields, Object> criterias) {
		if (criterias == null) {
			return 0;
		}
		Object[] quertParas = createQuery(true, criterias, null);
		String hql = quertParas[0].toString();
		Number totalCount = this.insureduserdao.countByQuery(hql,
				(Object[]) quertParas[1]);

		return totalCount.intValue();
	}

	public void importFromExcel(File file) {
		NPOIReader excel = null;
		try {
			excel = new NPOIReader(file);
			int index = excel.getSheetNames().indexOf("Sheet0");
			String[][] contents = excel.read(index, true, true);
			for (int i = 1; i < contents.length; i++) {
				InsuredUserVO vo = new InsuredUserVO();
				// 导入投保用户编号
				String iuserNoStr = contents[i][0];
				vo.setIuserNo(iuserNoStr);

				// 导入员工号
				String iuserNumberStr = contents[i][1];
				vo.setIuserNumber(iuserNumberStr);

				this.insureduserdao.insert(vo);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据条件查询分页信息.
	 * 
	 * @param criterias
	 *            条件
	 * @param orderField
	 *            排序列
	 * @param startIndex
	 *            开始索引
	 * @param count
	 *            总数
	 * @return
	 */
	public Collection<InsuredUser> searchInsuredUser(
			Map<InsuredUserSearchFields, Object> criterias, String orderField,
			int startIndex, int count) {
		ArrayList<InsuredUser> eaList = new ArrayList<InsuredUser>();
		if (criterias == null)
			return eaList;

		Object[] quertParas = createQuery(false, criterias, orderField);
		String hql = quertParas[0].toString();
		Collection<InsuredUserVO> voList = this.insureduserdao.findByQuery(hql,
				(Object[]) quertParas[1], startIndex, count);

		if (voList == null || voList.size() == 0)
			return eaList;

		Cache cache_comId = CacheManager
				.getCacheInfoNotNull(AllSelectContants.INSURED_COM_DICT
						.getName());
		ParamSelect select_comId = (ParamSelect) cache_comId.getValue();
		AllSelect allSelect = (AllSelect) SpringContextUtil
				.getBean(BeanManagerKey.allSelectManager.toString());
		ParamSelect select_toubaouser_status = allSelect
				.getParamsByType(AllSelectContants.TOUBAOUSER_STATUS.getName());
		ParamSelect select_sex = allSelect
				.getParamsByType(AllSelectContants.SEX.getName());
		Cache cache_insured = CacheManager
				.getCacheInfoNotNull(AllSelectContants.INSUREDUNIT_DICT
						.getName());
		ParamSelect select_ownerCompany = (ParamSelect) cache_insured
				.getValue();

		for (InsuredUserVO po : voList) {
			po.setComId(select_comId.getName("" + po.getComId()));
			po.setUnitId(select_ownerCompany.getName(po.getUnitId()));
			po.setIuserStatus(select_toubaouser_status.getName(""
					+ po.getIuserStatus()));
			po.setIuserIsman(select_sex.getName("" + po.getIuserIsman()));
			po.setCreateUserName(CacheUtil.getSystemUserName(""+po.getCreateUser()));
			po.setUpdateUserName(CacheUtil.getSystemUserName(""+po.getUpdateUser()));
			eaList.add(new InsuredUserImpl(po));
		}

		return eaList;
	}

	private Object[] createQuery(boolean useCount,
			Map<InsuredUserSearchFields, Object> criterias, String orderField) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				useCount ? "select count( insureduser) "
						: "select  insureduser ").append(
				"from InsuredUserVO as insureduser ");

		int count = 0;
		List argList = new ArrayList();
		if (criterias.size() > 0)
			for (Map.Entry<InsuredUserSearchFields, Object> entry : criterias
					.entrySet()) {
				InsuredUserSearchFields fd = entry.getKey();
				switch (fd) {
				case SNO:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.sno = ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case IUSERNO:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.iuserNo = ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case COMID:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.comId = ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case UNITID:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.unitId = ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case IUSERSTATUS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.iuserStatus = ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case IUSERNUMBER:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.iuserNumber = ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case LEFTMONEY:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.leftMoney = ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case EMERGENCYMONEY:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.emergencyMoney = ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case FROZENMONEY:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.frozenMoney = ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case HOSPITALMONEY:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.hospitalMoney = ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case TESMONEY:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.tesMoney = ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case IUSERNAME:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.iuserName = ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case IUSERISMAN:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.iuserIsman = ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case IUSERCARDNO:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.iuserCardno = ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case IUSERPHONE:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.iuserPhone = ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case IUSEREMAIL:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.iuserEmail = ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case IUSERBIRTHDAY:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.iuserBirthday = ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case IUSERREMARK:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.iuserRemark = ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case CREATEUSER:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.createUser = ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case CREATETIME:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.createTime = ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case UPDATEUSER:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.updateUser = ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case UPDATETIME:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.updateTime = ? ");
					argList.add(entry.getValue());
					count++;
					break;
				// 下面拼接高级查询条件
				case IUSERNO_COM_NOT_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.iuserNo  !=  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case IUSERNO_COM_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.iuserNo =  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case COMID_COM_NOT_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.comId  !=  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case COMID_COM_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.comId =  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case UNITID_COM_NOT_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.unitId  !=  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case UNITID_COM_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.unitId =  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case IUSERSTATUS_COM_NOT_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.iuserStatus  !=  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case IUSERSTATUS_COM_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.iuserStatus =  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case IUSERNUMBER_COM_NOT_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.iuserNumber  !=  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case IUSERNUMBER_COM_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.iuserNumber =  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case LEFTMONEY_COM_NOT_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.leftMoney  !=  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case LEFTMONEY_COM_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.leftMoney =  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case EMERGENCYMONEY_COM_NOT_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.emergencyMoney  !=  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case EMERGENCYMONEY_COM_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.emergencyMoney =  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case FROZENMONEY_COM_NOT_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.frozenMoney  !=  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case FROZENMONEY_COM_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.frozenMoney =  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case HOSPITALMONEY_COM_NOT_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.hospitalMoney  !=  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case HOSPITALMONEY_COM_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.hospitalMoney =  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case TESMONEY_COM_NOT_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.tesMoney  !=  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case TESMONEY_COM_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.tesMoney =  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case IUSERNAME_COM_NOT_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.iuserName  !=  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case IUSERNAME_COM_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.iuserName =  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case IUSERISMAN_COM_NOT_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.iuserIsman  !=  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case IUSERISMAN_COM_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.iuserIsman =  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case IUSERCARDNO_COM_NOT_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.iuserCardno  !=  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case IUSERCARDNO_COM_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.iuserCardno =  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case IUSERPHONE_COM_NOT_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.iuserPhone  !=  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case IUSERPHONE_COM_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.iuserPhone =  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case IUSEREMAIL_COM_NOT_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.iuserEmail  !=  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case IUSEREMAIL_COM_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.iuserEmail =  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case IUSERBIRTHDAY_COM_NOT_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.iuserBirthday  !=  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case IUSERBIRTHDAY_COM_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.iuserBirthday =  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case IUSERREMARK_COM_NOT_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.iuserRemark  !=  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case IUSERREMARK_COM_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.iuserRemark =  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case CREATEUSER_COM_NOT_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.createUser  !=  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case CREATEUSER_COM_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.createUser =  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case CREATETIME_COM_NOT_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.createTime  !=  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case CREATETIME_COM_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.createTime =  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case UPDATEUSER_COM_NOT_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.updateUser  !=  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case UPDATEUSER_COM_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.updateUser =  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case UPDATETIME_COM_NOT_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.updateTime  !=  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				case UPDATETIME_COM_EQUALS:
					sb.append(count == 0 ? " where" : " and").append(
							"  insureduser.updateTime =  ? ");
					argList.add(entry.getValue());
					count++;
					break;
				default:
					break;
				}
			}

		if (useCount) {
			return new Object[] { sb.toString(), argList.toArray() };
		}

		InsuredUserOrderByFields orderBy = InsuredUserOrderByFields.SNO_DESC;
		if (orderField != null && orderField.length() > 0) {
			orderBy = InsuredUserOrderByFields.valueOf(orderField);
		}

		switch (orderBy) {
		case SNO:
			 sb.append(" order by insureduser.sno");
		break;
		case SNO_DESC:
			 sb.append(" order by insureduser.sno desc");
		break;
		case IUSERNO:
			 sb.append(" order by insureduser.iuserNo");
		break;
		case IUSERNO_DESC:
			 sb.append(" order by insureduser.iuserNo desc");
		break;
		case IUSERNAME:
			 sb.append(" order by insureduser.iuserName");
		break;
		case IUSERNAME_DESC:
			 sb.append(" order by insureduser.iuserName desc");
		break;
		case IUSERISMAN:
			 sb.append(" order by insureduser.iuserIsman");
		break;
		case IUSERISMAN_DESC:
			 sb.append(" order by insureduser.iuserIsman desc");
		break;
		case IUSERCARDNO:
			 sb.append(" order by insureduser.iuserCardno");
		break;
		case IUSERCARDNO_DESC:
			 sb.append(" order by insureduser.iuserCardno desc");
		break;
		case IUSERPHONE:
			 sb.append(" order by insureduser.iuserPhone");
		break;
		case IUSERPHONE_DESC:
			 sb.append(" order by insureduser.iuserPhone desc");
		break;
		case UNITID:
			 sb.append(" order by insureduser.unitId");
		break;
		case UNITID_DESC:
			 sb.append(" order by insureduser.unitId desc");
		break;
		case COMID:
			 sb.append(" order by insureduser.comId");
		break;
		case COMID_DESC:
			 sb.append(" order by insureduser.comId desc");
		break;
		case IUSERSTATUS:
			 sb.append(" order by insureduser.iuserStatus");
		break;
		case IUSERSTATUS_DESC:
			 sb.append(" order by insureduser.iuserStatus desc");
		break;
		case IUSERNUMBER:
			 sb.append(" order by insureduser.iuserNumber");
		break;
		case IUSERNUMBER_DESC:
			 sb.append(" order by insureduser.iuserNumber desc");
		break;
		case LEFTMONEY:
			 sb.append(" order by insureduser.leftMoney");
		break;
		case LEFTMONEY_DESC:
			 sb.append(" order by insureduser.leftMoney desc");
		break;
		case EMERGENCYMONEY:
			 sb.append(" order by insureduser.emergencyMoney");
		break;
		case EMERGENCYMONEY_DESC:
			 sb.append(" order by insureduser.emergencyMoney desc");
		break;
		case FROZENMONEY:
			 sb.append(" order by insureduser.frozenMoney");
		break;
		case FROZENMONEY_DESC:
			 sb.append(" order by insureduser.frozenMoney desc");
		break;
		case HOSPITALMONEY:
			 sb.append(" order by insureduser.hospitalMoney");
		break;
		case HOSPITALMONEY_DESC:
			 sb.append(" order by insureduser.hospitalMoney desc");
		break;
		case TESMONEY:
			 sb.append(" order by insureduser.tesMoney");
		break;
		case TESMONEY_DESC:
			 sb.append(" order by insureduser.tesMoney desc");
		break;
		case IUSEREMAIL:
			 sb.append(" order by insureduser.iuserEmail");
		break;
		case IUSEREMAIL_DESC:
			 sb.append(" order by insureduser.iuserEmail desc");
		break;
		case IUSERBIRTHDAY:
			 sb.append(" order by insureduser.iuserBirthday");
		break;
		case IUSERBIRTHDAY_DESC:
			 sb.append(" order by insureduser.iuserBirthday desc");
		break;
		case IUSERREMARK:
			 sb.append(" order by insureduser.iuserRemark");
		break;
		case IUSERREMARK_DESC:
			 sb.append(" order by insureduser.iuserRemark desc");
		break;
		case CREATEUSER:
			 sb.append(" order by insureduser.createUser");
		break;
		case CREATEUSER_DESC:
			 sb.append(" order by insureduser.createUser desc");
		break;
		case CREATETIME:
			 sb.append(" order by insureduser.createTime");
		break;
		case CREATETIME_DESC:
			 sb.append(" order by insureduser.createTime desc");
		break;
		case UPDATEUSER:
			 sb.append(" order by insureduser.updateUser");
		break;
		case UPDATEUSER_DESC:
			 sb.append(" order by insureduser.updateUser desc");
		break;
		case UPDATETIME:
			 sb.append(" order by insureduser.updateTime");
		break;
		case UPDATETIME_DESC:
			 sb.append(" order by insureduser.updateTime desc");
		break;
		default:
			break;
	}
		return new Object[] { sb.toString(), argList.toArray() };
	}

	public void createInsuredUser(InsuredUser insureduser)
			throws ValidateFieldsException {
		InsuredUserImpl insureduserImpl = (InsuredUserImpl) insureduser;
		this.insureduserdao.insert(insureduserImpl.getInsuredUserVO());
	}

	public void removeInsuredUsers(String ids) {
		String[] idArr = ids.split(",");
		for (String s : idArr) {
			InsuredUserVO vo = this.insureduserdao.findByPrimaryKey(Integer
					.parseInt(s));
			this.insureduserdao.delete(vo);
		}
	}

	public void updateInsuredUser(InsuredUser insureduser)
			throws ValidateFieldsException {
		InsuredUserImpl insureduserImpl = (InsuredUserImpl) insureduser;

		InsuredUserVO po = insureduserImpl.getInsuredUserVO();
		this.insureduserdao.update(po);
	}

	public InsuredUser getInsuredUser(int id) {
		Collection<InsuredUserVO> insuredusers = this.insureduserdao
				.findRecordById(id);

		if (insuredusers == null || insuredusers.size() < 1)
			return null;

		InsuredUserVO insureduser = insuredusers
				.toArray(new InsuredUserVO[insuredusers.size()])[0];
		insureduser.setCreateUserName(CacheUtil.getSystemUserName(""+insureduser.getCreateUser()));
		insureduser.setUpdateUserName(CacheUtil.getSystemUserName(""+insureduser.getUpdateUser()));
		return new InsuredUserImpl(insureduser);
	}

	@Override
	public void updateStatus(final int userSno, final int status) {
		jdbcTemplate.update("update insured_user set iuser_status = ? where id= ?",
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, status);
						ps.setInt(2, userSno); 
					}
				});
	}

	@Override
	public void addMoney(final String userCode, String moneyType, final double money)
			throws ValidateFieldsException {
		//添加余额
		if(Constants.ADD_MONEY_YUE.equals(moneyType)){
			jdbcTemplate.update("update insured_user set left_money =left_money+ ? where iuser_no= ?",
					new PreparedStatementSetter() {
						public void setValues(PreparedStatement ps)
								throws SQLException {
							ps.setDouble(1, money);
							ps.setString(2, userCode); 
						}
					});
		} 
		else if(Constants.ADD_MONEY_TIJIAN.equals(moneyType)){
			jdbcTemplate.update("update insured_user set test_money =test_money+ ? where iuser_no= ?",
					new PreparedStatementSetter() {
						public void setValues(PreparedStatement ps)
								throws SQLException {
							ps.setDouble(1, money);
							ps.setString(2, userCode); 
						}
					});
		} 
		else if(Constants.ADD_MONEY_JIZHEN.equals(moneyType)){
			jdbcTemplate.update("update insured_user set Emergency_money =Emergency_money+ ? where iuser_no= ?",
					new PreparedStatementSetter() {
						public void setValues(PreparedStatement ps)
								throws SQLException {
							ps.setDouble(1, money);
							ps.setString(2, userCode); 
						}
					});
		}  
		else if(Constants.ADD_MONEY_ZHUYUAN.equals(moneyType)){
			jdbcTemplate.update("update insured_user set Hospital_money =Hospital_money+ ? where iuser_no= ?",
					new PreparedStatementSetter() {
						public void setValues(PreparedStatement ps)
								throws SQLException {
							ps.setDouble(1, money);
							ps.setString(2, userCode); 
						}
					});
		} 
		else if(Constants.ADD_MONEY_YUE.equals(moneyType)){
			jdbcTemplate.update("update insured_user set Frozen_money =Frozen_money+ ? where iuser_no= ?",
					new PreparedStatementSetter() {
						public void setValues(PreparedStatement ps)
								throws SQLException {
							ps.setDouble(1, money);
							ps.setString(2, userCode); 
						}
					});
		} 
	}

	@Override
	public void addCache() {
		ParamSelect ans = null;
		Collection<InsuredUserVO> all = this.insureduserdao.findAll();
		ans = new ParamSelect(all);

		CacheManager.clearOnly(AllSelectContants.INSURED_USER.getName());
		Cache c = new Cache(); 
		c.setKey(AllSelectContants.INSURED_USER.getName());
		c.setValue(ans);
		c.setName("投保用户字典"); 
		CacheManager.putCache(AllSelectContants.INSURED_USER.getName(), c);
	}

}