package com.zfgt.orm;

import java.util.Date;

import com.zfgt.common.util.QwyUtil;



/**
 * @author 曾礼强
 * 2015年8月8日下午2:19:36
 * 模块功能
 */
public class Modul implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String modulName;//模块名称
	private String modulPath;//模块路径
	private Long parentId;//父节点id
	private String note;//模块备注
	private String modulCode;//模块编码
	private String type;//类型1. 第一级2. 第二级 3. 第三级
	private Long sort;//排序
	private String modulType;//中文类型
	private Date insertTime;//插入时间;
	private Date updateTime;//更新时间;
	
	private Modul modul;

	// Constructors

	public String getModulType() {
		if(!QwyUtil.isNullAndEmpty(type)){
			if("1".equals(type)){
				return "第一级";
			}if("2".equals(type)){
				return "第二级";
			}if("3".equals(type)){
				return "第三级";
			}
		}
		return modulType;
	}
	
	

	public Modul getModul() {
		if(!QwyUtil.isNullAndEmpty(modul))
			return modul;
		return this;
	}



	public void setModul(Modul modul) {
		this.modul = modul;
	}



	/** default constructor */
	public Modul() {
	}

	/** full constructor */
	public Modul(String modulName, String modulPath, Long parentId,
			String note, String modulCode, String type, Long sort) {
		this.modulName = modulName;
		this.modulPath = modulPath;
		this.parentId = parentId;
		this.note = note;
		this.modulCode = modulCode;
		this.type = type;
		this.sort = sort;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModulName() {
		return this.modulName;
	}

	public void setModulName(String modulName) {
		this.modulName = modulName;
	}

	public String getModulPath() {
		return this.modulPath;
	}

	public void setModulPath(String modulPath) {
		this.modulPath = modulPath;
	}

	

	public String getNote() {
		return note;
	}



	public void setNote(String note) {
		this.note = note;
	}



	public Long getParentId() {
		return parentId;
	}



	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}



	

	public String getModulCode() {
		return this.modulCode;
	}

	public void setModulCode(String modulCode) {
		this.modulCode = modulCode;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}



	public Date getInsertTime() {
		return insertTime;
	}



	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}



	public Date getUpdateTime() {
		return updateTime;
	}



	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	

}