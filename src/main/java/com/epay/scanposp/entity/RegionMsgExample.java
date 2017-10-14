package com.epay.scanposp.entity;

import java.util.ArrayList;
import java.util.List;

public class RegionMsgExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RegionMsgExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andProvCdIsNull() {
            addCriterion("prov_cd is null");
            return (Criteria) this;
        }

        public Criteria andProvCdIsNotNull() {
            addCriterion("prov_cd is not null");
            return (Criteria) this;
        }

        public Criteria andProvCdEqualTo(String value) {
            addCriterion("prov_cd =", value, "provCd");
            return (Criteria) this;
        }

        public Criteria andProvCdNotEqualTo(String value) {
            addCriterion("prov_cd <>", value, "provCd");
            return (Criteria) this;
        }

        public Criteria andProvCdGreaterThan(String value) {
            addCriterion("prov_cd >", value, "provCd");
            return (Criteria) this;
        }

        public Criteria andProvCdGreaterThanOrEqualTo(String value) {
            addCriterion("prov_cd >=", value, "provCd");
            return (Criteria) this;
        }

        public Criteria andProvCdLessThan(String value) {
            addCriterion("prov_cd <", value, "provCd");
            return (Criteria) this;
        }

        public Criteria andProvCdLessThanOrEqualTo(String value) {
            addCriterion("prov_cd <=", value, "provCd");
            return (Criteria) this;
        }

        public Criteria andProvCdLike(String value) {
            addCriterion("prov_cd like", value, "provCd");
            return (Criteria) this;
        }

        public Criteria andProvCdNotLike(String value) {
            addCriterion("prov_cd not like", value, "provCd");
            return (Criteria) this;
        }

        public Criteria andProvCdIn(List<String> values) {
            addCriterion("prov_cd in", values, "provCd");
            return (Criteria) this;
        }

        public Criteria andProvCdNotIn(List<String> values) {
            addCriterion("prov_cd not in", values, "provCd");
            return (Criteria) this;
        }

        public Criteria andProvCdBetween(String value1, String value2) {
            addCriterion("prov_cd between", value1, value2, "provCd");
            return (Criteria) this;
        }

        public Criteria andProvCdNotBetween(String value1, String value2) {
            addCriterion("prov_cd not between", value1, value2, "provCd");
            return (Criteria) this;
        }

        public Criteria andProvNmIsNull() {
            addCriterion("prov_nm is null");
            return (Criteria) this;
        }

        public Criteria andProvNmIsNotNull() {
            addCriterion("prov_nm is not null");
            return (Criteria) this;
        }

        public Criteria andProvNmEqualTo(String value) {
            addCriterion("prov_nm =", value, "provNm");
            return (Criteria) this;
        }

        public Criteria andProvNmNotEqualTo(String value) {
            addCriterion("prov_nm <>", value, "provNm");
            return (Criteria) this;
        }

        public Criteria andProvNmGreaterThan(String value) {
            addCriterion("prov_nm >", value, "provNm");
            return (Criteria) this;
        }

        public Criteria andProvNmGreaterThanOrEqualTo(String value) {
            addCriterion("prov_nm >=", value, "provNm");
            return (Criteria) this;
        }

        public Criteria andProvNmLessThan(String value) {
            addCriterion("prov_nm <", value, "provNm");
            return (Criteria) this;
        }

        public Criteria andProvNmLessThanOrEqualTo(String value) {
            addCriterion("prov_nm <=", value, "provNm");
            return (Criteria) this;
        }

        public Criteria andProvNmLike(String value) {
            addCriterion("prov_nm like", value, "provNm");
            return (Criteria) this;
        }

        public Criteria andProvNmNotLike(String value) {
            addCriterion("prov_nm not like", value, "provNm");
            return (Criteria) this;
        }

        public Criteria andProvNmIn(List<String> values) {
            addCriterion("prov_nm in", values, "provNm");
            return (Criteria) this;
        }

        public Criteria andProvNmNotIn(List<String> values) {
            addCriterion("prov_nm not in", values, "provNm");
            return (Criteria) this;
        }

        public Criteria andProvNmBetween(String value1, String value2) {
            addCriterion("prov_nm between", value1, value2, "provNm");
            return (Criteria) this;
        }

        public Criteria andProvNmNotBetween(String value1, String value2) {
            addCriterion("prov_nm not between", value1, value2, "provNm");
            return (Criteria) this;
        }

        public Criteria andCityCdIsNull() {
            addCriterion("city_cd is null");
            return (Criteria) this;
        }

        public Criteria andCityCdIsNotNull() {
            addCriterion("city_cd is not null");
            return (Criteria) this;
        }

        public Criteria andCityCdEqualTo(String value) {
            addCriterion("city_cd =", value, "cityCd");
            return (Criteria) this;
        }

        public Criteria andCityCdNotEqualTo(String value) {
            addCriterion("city_cd <>", value, "cityCd");
            return (Criteria) this;
        }

        public Criteria andCityCdGreaterThan(String value) {
            addCriterion("city_cd >", value, "cityCd");
            return (Criteria) this;
        }

        public Criteria andCityCdGreaterThanOrEqualTo(String value) {
            addCriterion("city_cd >=", value, "cityCd");
            return (Criteria) this;
        }

        public Criteria andCityCdLessThan(String value) {
            addCriterion("city_cd <", value, "cityCd");
            return (Criteria) this;
        }

        public Criteria andCityCdLessThanOrEqualTo(String value) {
            addCriterion("city_cd <=", value, "cityCd");
            return (Criteria) this;
        }

        public Criteria andCityCdLike(String value) {
            addCriterion("city_cd like", value, "cityCd");
            return (Criteria) this;
        }

        public Criteria andCityCdNotLike(String value) {
            addCriterion("city_cd not like", value, "cityCd");
            return (Criteria) this;
        }

        public Criteria andCityCdIn(List<String> values) {
            addCriterion("city_cd in", values, "cityCd");
            return (Criteria) this;
        }

        public Criteria andCityCdNotIn(List<String> values) {
            addCriterion("city_cd not in", values, "cityCd");
            return (Criteria) this;
        }

        public Criteria andCityCdBetween(String value1, String value2) {
            addCriterion("city_cd between", value1, value2, "cityCd");
            return (Criteria) this;
        }

        public Criteria andCityCdNotBetween(String value1, String value2) {
            addCriterion("city_cd not between", value1, value2, "cityCd");
            return (Criteria) this;
        }

        public Criteria andCityNmIsNull() {
            addCriterion("city_nm is null");
            return (Criteria) this;
        }

        public Criteria andCityNmIsNotNull() {
            addCriterion("city_nm is not null");
            return (Criteria) this;
        }

        public Criteria andCityNmEqualTo(String value) {
            addCriterion("city_nm =", value, "cityNm");
            return (Criteria) this;
        }

        public Criteria andCityNmNotEqualTo(String value) {
            addCriterion("city_nm <>", value, "cityNm");
            return (Criteria) this;
        }

        public Criteria andCityNmGreaterThan(String value) {
            addCriterion("city_nm >", value, "cityNm");
            return (Criteria) this;
        }

        public Criteria andCityNmGreaterThanOrEqualTo(String value) {
            addCriterion("city_nm >=", value, "cityNm");
            return (Criteria) this;
        }

        public Criteria andCityNmLessThan(String value) {
            addCriterion("city_nm <", value, "cityNm");
            return (Criteria) this;
        }

        public Criteria andCityNmLessThanOrEqualTo(String value) {
            addCriterion("city_nm <=", value, "cityNm");
            return (Criteria) this;
        }

        public Criteria andCityNmLike(String value) {
            addCriterion("city_nm like", value, "cityNm");
            return (Criteria) this;
        }

        public Criteria andCityNmNotLike(String value) {
            addCriterion("city_nm not like", value, "cityNm");
            return (Criteria) this;
        }

        public Criteria andCityNmIn(List<String> values) {
            addCriterion("city_nm in", values, "cityNm");
            return (Criteria) this;
        }

        public Criteria andCityNmNotIn(List<String> values) {
            addCriterion("city_nm not in", values, "cityNm");
            return (Criteria) this;
        }

        public Criteria andCityNmBetween(String value1, String value2) {
            addCriterion("city_nm between", value1, value2, "cityNm");
            return (Criteria) this;
        }

        public Criteria andCityNmNotBetween(String value1, String value2) {
            addCriterion("city_nm not between", value1, value2, "cityNm");
            return (Criteria) this;
        }

        public Criteria andAreaCdIsNull() {
            addCriterion("area_cd is null");
            return (Criteria) this;
        }

        public Criteria andAreaCdIsNotNull() {
            addCriterion("area_cd is not null");
            return (Criteria) this;
        }

        public Criteria andAreaCdEqualTo(String value) {
            addCriterion("area_cd =", value, "areaCd");
            return (Criteria) this;
        }

        public Criteria andAreaCdNotEqualTo(String value) {
            addCriterion("area_cd <>", value, "areaCd");
            return (Criteria) this;
        }

        public Criteria andAreaCdGreaterThan(String value) {
            addCriterion("area_cd >", value, "areaCd");
            return (Criteria) this;
        }

        public Criteria andAreaCdGreaterThanOrEqualTo(String value) {
            addCriterion("area_cd >=", value, "areaCd");
            return (Criteria) this;
        }

        public Criteria andAreaCdLessThan(String value) {
            addCriterion("area_cd <", value, "areaCd");
            return (Criteria) this;
        }

        public Criteria andAreaCdLessThanOrEqualTo(String value) {
            addCriterion("area_cd <=", value, "areaCd");
            return (Criteria) this;
        }

        public Criteria andAreaCdLike(String value) {
            addCriterion("area_cd like", value, "areaCd");
            return (Criteria) this;
        }

        public Criteria andAreaCdNotLike(String value) {
            addCriterion("area_cd not like", value, "areaCd");
            return (Criteria) this;
        }

        public Criteria andAreaCdIn(List<String> values) {
            addCriterion("area_cd in", values, "areaCd");
            return (Criteria) this;
        }

        public Criteria andAreaCdNotIn(List<String> values) {
            addCriterion("area_cd not in", values, "areaCd");
            return (Criteria) this;
        }

        public Criteria andAreaCdBetween(String value1, String value2) {
            addCriterion("area_cd between", value1, value2, "areaCd");
            return (Criteria) this;
        }

        public Criteria andAreaCdNotBetween(String value1, String value2) {
            addCriterion("area_cd not between", value1, value2, "areaCd");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}