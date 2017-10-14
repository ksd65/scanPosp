package com.epay.scanposp.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SysOfficeConfigOemExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitSize;

    public SysOfficeConfigOemExample() {
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

    public void setLimitStart(Integer limitStart) {
        this.limitStart=limitStart;
    }

    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitSize(Integer limitSize) {
        this.limitSize=limitSize;
    }

    public Integer getLimitSize() {
        return limitSize;
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

        public Criteria andOfficeIdIsNull() {
            addCriterion("office_id is null");
            return (Criteria) this;
        }

        public Criteria andOfficeIdIsNotNull() {
            addCriterion("office_id is not null");
            return (Criteria) this;
        }

        public Criteria andOfficeIdEqualTo(String value) {
            addCriterion("office_id =", value, "officeId");
            return (Criteria) this;
        }

        public Criteria andOfficeIdNotEqualTo(String value) {
            addCriterion("office_id <>", value, "officeId");
            return (Criteria) this;
        }

        public Criteria andOfficeIdGreaterThan(String value) {
            addCriterion("office_id >", value, "officeId");
            return (Criteria) this;
        }

        public Criteria andOfficeIdGreaterThanOrEqualTo(String value) {
            addCriterion("office_id >=", value, "officeId");
            return (Criteria) this;
        }

        public Criteria andOfficeIdLessThan(String value) {
            addCriterion("office_id <", value, "officeId");
            return (Criteria) this;
        }

        public Criteria andOfficeIdLessThanOrEqualTo(String value) {
            addCriterion("office_id <=", value, "officeId");
            return (Criteria) this;
        }

        public Criteria andOfficeIdLike(String value) {
            addCriterion("office_id like", value, "officeId");
            return (Criteria) this;
        }

        public Criteria andOfficeIdNotLike(String value) {
            addCriterion("office_id not like", value, "officeId");
            return (Criteria) this;
        }

        public Criteria andOfficeIdIn(List<String> values) {
            addCriterion("office_id in", values, "officeId");
            return (Criteria) this;
        }

        public Criteria andOfficeIdNotIn(List<String> values) {
            addCriterion("office_id not in", values, "officeId");
            return (Criteria) this;
        }

        public Criteria andOfficeIdBetween(String value1, String value2) {
            addCriterion("office_id between", value1, value2, "officeId");
            return (Criteria) this;
        }

        public Criteria andOfficeIdNotBetween(String value1, String value2) {
            addCriterion("office_id not between", value1, value2, "officeId");
            return (Criteria) this;
        }

        public Criteria andWxAppidIsNull() {
            addCriterion("wx_appid is null");
            return (Criteria) this;
        }

        public Criteria andWxAppidIsNotNull() {
            addCriterion("wx_appid is not null");
            return (Criteria) this;
        }

        public Criteria andWxAppidEqualTo(String value) {
            addCriterion("wx_appid =", value, "wxAppid");
            return (Criteria) this;
        }

        public Criteria andWxAppidNotEqualTo(String value) {
            addCriterion("wx_appid <>", value, "wxAppid");
            return (Criteria) this;
        }

        public Criteria andWxAppidGreaterThan(String value) {
            addCriterion("wx_appid >", value, "wxAppid");
            return (Criteria) this;
        }

        public Criteria andWxAppidGreaterThanOrEqualTo(String value) {
            addCriterion("wx_appid >=", value, "wxAppid");
            return (Criteria) this;
        }

        public Criteria andWxAppidLessThan(String value) {
            addCriterion("wx_appid <", value, "wxAppid");
            return (Criteria) this;
        }

        public Criteria andWxAppidLessThanOrEqualTo(String value) {
            addCriterion("wx_appid <=", value, "wxAppid");
            return (Criteria) this;
        }

        public Criteria andWxAppidLike(String value) {
            addCriterion("wx_appid like", value, "wxAppid");
            return (Criteria) this;
        }

        public Criteria andWxAppidNotLike(String value) {
            addCriterion("wx_appid not like", value, "wxAppid");
            return (Criteria) this;
        }

        public Criteria andWxAppidIn(List<String> values) {
            addCriterion("wx_appid in", values, "wxAppid");
            return (Criteria) this;
        }

        public Criteria andWxAppidNotIn(List<String> values) {
            addCriterion("wx_appid not in", values, "wxAppid");
            return (Criteria) this;
        }

        public Criteria andWxAppidBetween(String value1, String value2) {
            addCriterion("wx_appid between", value1, value2, "wxAppid");
            return (Criteria) this;
        }

        public Criteria andWxAppidNotBetween(String value1, String value2) {
            addCriterion("wx_appid not between", value1, value2, "wxAppid");
            return (Criteria) this;
        }

        public Criteria andWxAppsecretIsNull() {
            addCriterion("wx_appsecret is null");
            return (Criteria) this;
        }

        public Criteria andWxAppsecretIsNotNull() {
            addCriterion("wx_appsecret is not null");
            return (Criteria) this;
        }

        public Criteria andWxAppsecretEqualTo(String value) {
            addCriterion("wx_appsecret =", value, "wxAppsecret");
            return (Criteria) this;
        }

        public Criteria andWxAppsecretNotEqualTo(String value) {
            addCriterion("wx_appsecret <>", value, "wxAppsecret");
            return (Criteria) this;
        }

        public Criteria andWxAppsecretGreaterThan(String value) {
            addCriterion("wx_appsecret >", value, "wxAppsecret");
            return (Criteria) this;
        }

        public Criteria andWxAppsecretGreaterThanOrEqualTo(String value) {
            addCriterion("wx_appsecret >=", value, "wxAppsecret");
            return (Criteria) this;
        }

        public Criteria andWxAppsecretLessThan(String value) {
            addCriterion("wx_appsecret <", value, "wxAppsecret");
            return (Criteria) this;
        }

        public Criteria andWxAppsecretLessThanOrEqualTo(String value) {
            addCriterion("wx_appsecret <=", value, "wxAppsecret");
            return (Criteria) this;
        }

        public Criteria andWxAppsecretLike(String value) {
            addCriterion("wx_appsecret like", value, "wxAppsecret");
            return (Criteria) this;
        }

        public Criteria andWxAppsecretNotLike(String value) {
            addCriterion("wx_appsecret not like", value, "wxAppsecret");
            return (Criteria) this;
        }

        public Criteria andWxAppsecretIn(List<String> values) {
            addCriterion("wx_appsecret in", values, "wxAppsecret");
            return (Criteria) this;
        }

        public Criteria andWxAppsecretNotIn(List<String> values) {
            addCriterion("wx_appsecret not in", values, "wxAppsecret");
            return (Criteria) this;
        }

        public Criteria andWxAppsecretBetween(String value1, String value2) {
            addCriterion("wx_appsecret between", value1, value2, "wxAppsecret");
            return (Criteria) this;
        }

        public Criteria andWxAppsecretNotBetween(String value1, String value2) {
            addCriterion("wx_appsecret not between", value1, value2, "wxAppsecret");
            return (Criteria) this;
        }

        public Criteria andWxTemplateidIsNull() {
            addCriterion("wx_templateid is null");
            return (Criteria) this;
        }

        public Criteria andWxTemplateidIsNotNull() {
            addCriterion("wx_templateid is not null");
            return (Criteria) this;
        }

        public Criteria andWxTemplateidEqualTo(String value) {
            addCriterion("wx_templateid =", value, "wxTemplateid");
            return (Criteria) this;
        }

        public Criteria andWxTemplateidNotEqualTo(String value) {
            addCriterion("wx_templateid <>", value, "wxTemplateid");
            return (Criteria) this;
        }

        public Criteria andWxTemplateidGreaterThan(String value) {
            addCriterion("wx_templateid >", value, "wxTemplateid");
            return (Criteria) this;
        }

        public Criteria andWxTemplateidGreaterThanOrEqualTo(String value) {
            addCriterion("wx_templateid >=", value, "wxTemplateid");
            return (Criteria) this;
        }

        public Criteria andWxTemplateidLessThan(String value) {
            addCriterion("wx_templateid <", value, "wxTemplateid");
            return (Criteria) this;
        }

        public Criteria andWxTemplateidLessThanOrEqualTo(String value) {
            addCriterion("wx_templateid <=", value, "wxTemplateid");
            return (Criteria) this;
        }

        public Criteria andWxTemplateidLike(String value) {
            addCriterion("wx_templateid like", value, "wxTemplateid");
            return (Criteria) this;
        }

        public Criteria andWxTemplateidNotLike(String value) {
            addCriterion("wx_templateid not like", value, "wxTemplateid");
            return (Criteria) this;
        }

        public Criteria andWxTemplateidIn(List<String> values) {
            addCriterion("wx_templateid in", values, "wxTemplateid");
            return (Criteria) this;
        }

        public Criteria andWxTemplateidNotIn(List<String> values) {
            addCriterion("wx_templateid not in", values, "wxTemplateid");
            return (Criteria) this;
        }

        public Criteria andWxTemplateidBetween(String value1, String value2) {
            addCriterion("wx_templateid between", value1, value2, "wxTemplateid");
            return (Criteria) this;
        }

        public Criteria andWxTemplateidNotBetween(String value1, String value2) {
            addCriterion("wx_templateid not between", value1, value2, "wxTemplateid");
            return (Criteria) this;
        }

        public Criteria andAccessTokenIsNull() {
            addCriterion("access_token is null");
            return (Criteria) this;
        }

        public Criteria andAccessTokenIsNotNull() {
            addCriterion("access_token is not null");
            return (Criteria) this;
        }

        public Criteria andAccessTokenEqualTo(String value) {
            addCriterion("access_token =", value, "accessToken");
            return (Criteria) this;
        }

        public Criteria andAccessTokenNotEqualTo(String value) {
            addCriterion("access_token <>", value, "accessToken");
            return (Criteria) this;
        }

        public Criteria andAccessTokenGreaterThan(String value) {
            addCriterion("access_token >", value, "accessToken");
            return (Criteria) this;
        }

        public Criteria andAccessTokenGreaterThanOrEqualTo(String value) {
            addCriterion("access_token >=", value, "accessToken");
            return (Criteria) this;
        }

        public Criteria andAccessTokenLessThan(String value) {
            addCriterion("access_token <", value, "accessToken");
            return (Criteria) this;
        }

        public Criteria andAccessTokenLessThanOrEqualTo(String value) {
            addCriterion("access_token <=", value, "accessToken");
            return (Criteria) this;
        }

        public Criteria andAccessTokenLike(String value) {
            addCriterion("access_token like", value, "accessToken");
            return (Criteria) this;
        }

        public Criteria andAccessTokenNotLike(String value) {
            addCriterion("access_token not like", value, "accessToken");
            return (Criteria) this;
        }

        public Criteria andAccessTokenIn(List<String> values) {
            addCriterion("access_token in", values, "accessToken");
            return (Criteria) this;
        }

        public Criteria andAccessTokenNotIn(List<String> values) {
            addCriterion("access_token not in", values, "accessToken");
            return (Criteria) this;
        }

        public Criteria andAccessTokenBetween(String value1, String value2) {
            addCriterion("access_token between", value1, value2, "accessToken");
            return (Criteria) this;
        }

        public Criteria andAccessTokenNotBetween(String value1, String value2) {
            addCriterion("access_token not between", value1, value2, "accessToken");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("create_date is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("create_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            addCriterion("create_date =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            addCriterion("create_date <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            addCriterion("create_date >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("create_date >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            addCriterion("create_date <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterion("create_date <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Date> values) {
            addCriterion("create_date in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {
            addCriterion("create_date not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            addCriterion("create_date between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterion("create_date not between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNull() {
            addCriterion("update_date is null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNotNull() {
            addCriterion("update_date is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateEqualTo(Date value) {
            addCriterion("update_date =", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotEqualTo(Date value) {
            addCriterion("update_date <>", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThan(Date value) {
            addCriterion("update_date >", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("update_date >=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThan(Date value) {
            addCriterion("update_date <", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThanOrEqualTo(Date value) {
            addCriterion("update_date <=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIn(List<Date> values) {
            addCriterion("update_date in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotIn(List<Date> values) {
            addCriterion("update_date not in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateBetween(Date value1, Date value2) {
            addCriterion("update_date between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotBetween(Date value1, Date value2) {
            addCriterion("update_date not between", value1, value2, "updateDate");
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