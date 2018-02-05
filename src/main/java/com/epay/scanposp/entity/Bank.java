package com.epay.scanposp.entity;

public class Bank {
    private Integer id;

    private String name;

    private Integer sort;

    private Integer kbinId;
    
    private Integer ftBankId;
    
    private String code;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getKbinId() {
        return kbinId;
    }

    public void setKbinId(Integer kbinId) {
        this.kbinId = kbinId;
    }

	public Integer getFtBankId() {
		return ftBankId;
	}

	public void setFtBankId(Integer ftBankId) {
		this.ftBankId = ftBankId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
    
    
}