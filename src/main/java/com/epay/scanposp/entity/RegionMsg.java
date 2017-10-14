package com.epay.scanposp.entity;

public class RegionMsg {
    private Integer id;

    private String provCd;

    private String provNm;

    private String cityCd;

    private String cityNm;

    private String areaCd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProvCd() {
        return provCd;
    }

    public void setProvCd(String provCd) {
        this.provCd = provCd;
    }

    public String getProvNm() {
        return provNm;
    }

    public void setProvNm(String provNm) {
        this.provNm = provNm;
    }

    public String getCityCd() {
        return cityCd;
    }

    public void setCityCd(String cityCd) {
        this.cityCd = cityCd;
    }

    public String getCityNm() {
        return cityNm;
    }

    public void setCityNm(String cityNm) {
        this.cityNm = cityNm;
    }

    public String getAreaCd() {
        return areaCd;
    }

    public void setAreaCd(String areaCd) {
        this.areaCd = areaCd;
    }
}