package com.epay.scanposp.entity;

public class BankRoute {
    private Integer id;

    private String code;

    private String routeCode;

    private String routeBankCode;

    private String delFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}

	public String getRouteBankCode() {
		return routeBankCode;
	}

	public void setRouteBankCode(String routeBankCode) {
		this.routeBankCode = routeBankCode;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

    
}