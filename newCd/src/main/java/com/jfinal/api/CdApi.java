package com.jfinal.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.kit.Ret;
import com.jfinal.kit.RetKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.mj.constant.CoreConstant;


public class CdApi {
	public static final CdApi api = new CdApi();

	public Villa findById(String id) {
		return Villa.dao.findById(id);
	}

	/*---------------------------------------排序----------------------------------------------------------*/
	@SuppressWarnings("serial")
	private static Map<String, String> sortConfigMap = new HashMap<String, String>() {
		{
			put("villa_title", "t.villa_title");
			put("villa_price_str", "t.villa_price");
			put("floor_area_str", "t.floor_area");
			put("face_hight_str", "t.face_hight");
			put("deepth_str", "t.deepth");
			put("house_type", "t.house_type");
			put("floor_num_str", "t.floor_num");
			put("selected_flg_disp", "t.selected_flg");
		}
	};

	/**
	 * @Description: 别墅类型
	 * @author H
	 * @date 2020-12-08 08:59:58
	 * @return
	 */
	public Page<Record> page(Integer pageNo, Integer pageSize, Map<String, Object> cond) {

		List<Object> queryArgs = new ArrayList<Object>();
		String select = "select t.id,t.villa_title,t.villa_price,t.land_area_min,t.floor_area,t.face_hight,t.deepth,t.house_type,t.floor_num,"
				+ "t.villa_style,t.villa_desc,t.layout_diagram,t.cust_case,t.valid_flg,t.land_area_max,t.selected_flg";
		StringBuilder sql = new StringBuilder();

		sql.append("  from base_villa t ");
		sql.append("    where 1=1");


		// 查询
		String keyword = StringUtils.trim((String) cond.get(CoreConstant.SEARCH_KEYWORD));
		if (StringUtils.isNotEmpty(keyword)) {
			sql.append(" and (INSTR(t.villa_title,?) > 0 OR INSTR(t.villa_price,?) > 0 )");
			queryArgs.add(keyword);
			queryArgs.add(keyword);
		}

		String orderField = StringUtils.trim((String) cond.get(CoreConstant.ORDER_FIELD));
		// 默认排序
		if (StringUtils.isNotEmpty(orderField) && sortConfigMap.containsKey(orderField)) {
			sql.append(" order by ").append(sortConfigMap.get(orderField)).append(" ")
					.append(StringUtils.trim((String) cond.get(CoreConstant.ORDER_DIR)));
		} else {
			sql.append(" order by t.valid_flg desc,t.id desc");
		}

		return Db.paginate(pageNo, pageSize, select, sql.toString(), queryArgs.toArray());
	}


	/**
	 * @Description: 保存或更新墅型
	 * @author H
	 * @date 2020-12-08 10:07:26
	 * @return
	 */
	public Ret saveOrUpdate(Villa entity, String loginUserName) {
		boolean isNew = entity.getId() == null;
		if (isNew) {
			entity.setValidFlg("Y");// 有效性
			entity.setCreator(loginUserName);
			entity.setCreateDate(new Date());
			entity.save();
		} else {
			Villa uEntity = findById(entity.getId());
			uEntity.setCoverImg(entity.getCoverImg());
			uEntity.setVillaTitle(entity.getVillaTitle());
			uEntity.setVillaPrice(entity.getVillaPrice());
			uEntity.setLandAreaMin(entity.getLandAreaMin());
			uEntity.setLandAreaMax(entity.getLandAreaMax());
			uEntity.setFloorArea(entity.getFloorArea());
			uEntity.setFaceHight(entity.getFaceHight());
			uEntity.setDeepth(entity.getDeepth());
			uEntity.setHouseType(entity.getHouseType());
			uEntity.setFloorNum(entity.getFloorNum());
			uEntity.setVillaStyle(entity.getVillaStyle());
			uEntity.setSelectedFlg(entity.getSelectedFlg());
			uEntity.setVillaDesc(entity.getVillaDesc());
			uEntity.setLayoutDiagram(entity.getLayoutDiagram());
			uEntity.setCustCaseRelativeFlg(entity.getCustCaseRelativeFlg());
			uEntity.setRelativeCaseId(entity.getRelativeCaseId());
			uEntity.setCustCase(entity.getCustCase());
			uEntity.setUpdator(loginUserName);
			uEntity.setUpdateDate(new Date());
			uEntity.update();
		}
		
		return RetKit.ok(entity.getId());
	}

}
