package com.jfinal.api;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseVilla<M extends BaseVilla<M>> extends Model<M> implements IBean {

	public void setId(java.lang.String id) {
		set("id", id);
	}

	public java.lang.String getId() {
		return get("id");
	}

	public void setVillaTitle(java.lang.String villaTitle) {
		set("villa_title", villaTitle);
	}

	public java.lang.String getVillaTitle() {
		return get("villa_title");
	}

	public void setCoverImg(java.lang.String coverImg) {
		set("cover_img", coverImg);
	}

	public java.lang.String getCoverImg() {
		return get("cover_img");
	}

	public void setVillaPrice(java.math.BigDecimal villaPrice) {
		set("villa_price", villaPrice);
	}

	public java.math.BigDecimal getVillaPrice() {
		return get("villa_price");
	}

	public void setLandAreaMin(java.math.BigDecimal landAreaMin) {
		set("land_area_min", landAreaMin);
	}

	public java.math.BigDecimal getLandAreaMin() {
		return get("land_area_min");
	}

	public void setLandAreaMax(java.math.BigDecimal landAreaMax) {
		set("land_area_max", landAreaMax);
	}

	public java.math.BigDecimal getLandAreaMax() {
		return get("land_area_max");
	}

	public void setFloorArea(java.math.BigDecimal floorArea) {
		set("floor_area", floorArea);
	}

	public java.math.BigDecimal getFloorArea() {
		return get("floor_area");
	}

	public void setFaceHight(java.math.BigDecimal faceHight) {
		set("face_hight", faceHight);
	}

	public java.math.BigDecimal getFaceHight() {
		return get("face_hight");
	}

	public void setDeepth(java.math.BigDecimal deepth) {
		set("deepth", deepth);
	}

	public java.math.BigDecimal getDeepth() {
		return get("deepth");
	}

	public void setHouseType(java.lang.String houseType) {
		set("house_type", houseType);
	}

	public java.lang.String getHouseType() {
		return get("house_type");
	}

	public void setFloorNum(java.lang.Integer floorNum) {
		set("floor_num", floorNum);
	}

	public java.lang.Integer getFloorNum() {
		return get("floor_num");
	}

	public void setVillaStyle(java.lang.String villaStyle) {
		set("villa_style", villaStyle);
	}

	public java.lang.String getVillaStyle() {
		return get("villa_style");
	}

	public void setVillaDesc(java.lang.String villaDesc) {
		set("villa_desc", villaDesc);
	}

	public java.lang.String getVillaDesc() {
		return get("villa_desc");
	}

	public void setLayoutDiagram(java.lang.String layoutDiagram) {
		set("layout_diagram", layoutDiagram);
	}

	public java.lang.String getLayoutDiagram() {
		return get("layout_diagram");
	}

	public void setCustCase(java.lang.String custCase) {
		set("cust_case", custCase);
	}

	public java.lang.String getCustCase() {
		return get("cust_case");
	}

	public void setSelectedFlg(java.lang.String selectedFlg) {
		set("selected_flg", selectedFlg);
	}

	public java.lang.String getSelectedFlg() {
		return get("selected_flg");
	}

	public void setCustCaseRelativeFlg(java.lang.String custCaseRelativeFlg) {
		set("cust_case_relative_flg", custCaseRelativeFlg);
	}

	public java.lang.String getCustCaseRelativeFlg() {
		return get("cust_case_relative_flg");
	}

	public void setRelativeCaseId(java.lang.Integer relativeCaseId) {
		set("relative_case_id", relativeCaseId);
	}

	public java.lang.Integer getRelativeCaseId() {
		return get("relative_case_id");
	}

	public void setValidFlg(java.lang.String validFlg) {
		set("valid_flg", validFlg);
	}

	public java.lang.String getValidFlg() {
		return get("valid_flg");
	}

	public void setCreateDate(java.util.Date createDate) {
		set("create_date", createDate);
	}

	public java.util.Date getCreateDate() {
		return get("create_date");
	}

	public void setCreator(java.lang.String creator) {
		set("creator", creator);
	}

	public java.lang.String getCreator() {
		return get("creator");
	}

	public void setUpdateDate(java.util.Date updateDate) {
		set("update_date", updateDate);
	}

	public java.util.Date getUpdateDate() {
		return get("update_date");
	}

	public void setUpdator(java.lang.String updator) {
		set("updator", updator);
	}

	public java.lang.String getUpdator() {
		return get("updator");
	}

}
