package com.eenet.basequery.chart.company;

import java.util.Date;

import com.eenet.base.BaseEntity;

public class ThemeFunnelCompanyWillCP extends BaseEntity{

	
	private static final long serialVersionUID = 5866891569541028742L;
	private String DIM_CUSTOMER_PROP;//客户性质
	private Date DIM_CREATED_DT;//时间
	private String WILL_CUSTOMER_COUNT;//新增客户数
	private String AGREE_CUSTOMER_COUNT;//已分配客户数
	private String CREATE_DT_FROM;//统计开始时间
	private String CREATE_DT_TO;//统计结束时间
	

	public String getCREATE_DT_FROM() {
		return CREATE_DT_FROM;
	}
	public void setCREATE_DT_FROM(String cREATE_DT_FROM) {
		CREATE_DT_FROM = cREATE_DT_FROM;
	}
	public String getCREATE_DT_TO() {
		return CREATE_DT_TO;
	}
	public void setCREATE_DT_TO(String cREATE_DT_TO) {
		CREATE_DT_TO = cREATE_DT_TO;
	}
	private Integer currentCount;
	private Integer pageSize;

	
	public Integer getCurrentCount() {
		return currentCount;
	}
	public void setCurrentCount(Integer currentCount) {
		this.currentCount = currentCount;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}


	
	public Date getDIM_CREATED_DT() {
		return DIM_CREATED_DT;
	}
	public void setDIM_CREATED_DT(Date dIM_CREATED_DT) {
		DIM_CREATED_DT = dIM_CREATED_DT;
	}
	public String getDIM_CUSTOMER_PROP() {
		return DIM_CUSTOMER_PROP;
	}
	public void setDIM_CUSTOMER_PROP(String dIM_CUSTOMER_PROP) {
		DIM_CUSTOMER_PROP = dIM_CUSTOMER_PROP;
	}
	public String getWILL_CUSTOMER_COUNT() {
		return WILL_CUSTOMER_COUNT;
	}
	public void setWILL_CUSTOMER_COUNT(String wILL_CUSTOMER_COUNT) {
		WILL_CUSTOMER_COUNT = wILL_CUSTOMER_COUNT;
	}
	public String getAGREE_CUSTOMER_COUNT() {
		return AGREE_CUSTOMER_COUNT;
	}
	public void setAGREE_CUSTOMER_COUNT(String aGREE_CUSTOMER_COUNT) {
		AGREE_CUSTOMER_COUNT = aGREE_CUSTOMER_COUNT;
	}

	
	
	
	
}
