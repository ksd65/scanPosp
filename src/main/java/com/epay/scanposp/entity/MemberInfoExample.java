package com.epay.scanposp.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class MemberInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitSize;

    public MemberInfoExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
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

        public Criteria andCodeIsNull() {
            addCriterion("code is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(String value) {
            addCriterion("code =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(String value) {
            addCriterion("code <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(String value) {
            addCriterion("code >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(String value) {
            addCriterion("code >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(String value) {
            addCriterion("code <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(String value) {
            addCriterion("code <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLike(String value) {
            addCriterion("code like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(String value) {
            addCriterion("code not like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<String> values) {
            addCriterion("code in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<String> values) {
            addCriterion("code not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(String value1, String value2) {
            addCriterion("code between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(String value1, String value2) {
            addCriterion("code not between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andLoginCodeIsNull() {
            addCriterion("login_code is null");
            return (Criteria) this;
        }

        public Criteria andLoginCodeIsNotNull() {
            addCriterion("login_code is not null");
            return (Criteria) this;
        }

        public Criteria andLoginCodeEqualTo(String value) {
            addCriterion("login_code =", value, "loginCode");
            return (Criteria) this;
        }

        public Criteria andLoginCodeNotEqualTo(String value) {
            addCriterion("login_code <>", value, "loginCode");
            return (Criteria) this;
        }

        public Criteria andLoginCodeGreaterThan(String value) {
            addCriterion("login_code >", value, "loginCode");
            return (Criteria) this;
        }

        public Criteria andLoginCodeGreaterThanOrEqualTo(String value) {
            addCriterion("login_code >=", value, "loginCode");
            return (Criteria) this;
        }

        public Criteria andLoginCodeLessThan(String value) {
            addCriterion("login_code <", value, "loginCode");
            return (Criteria) this;
        }

        public Criteria andLoginCodeLessThanOrEqualTo(String value) {
            addCriterion("login_code <=", value, "loginCode");
            return (Criteria) this;
        }

        public Criteria andLoginCodeLike(String value) {
            addCriterion("login_code like", value, "loginCode");
            return (Criteria) this;
        }

        public Criteria andLoginCodeNotLike(String value) {
            addCriterion("login_code not like", value, "loginCode");
            return (Criteria) this;
        }

        public Criteria andLoginCodeIn(List<String> values) {
            addCriterion("login_code in", values, "loginCode");
            return (Criteria) this;
        }

        public Criteria andLoginCodeNotIn(List<String> values) {
            addCriterion("login_code not in", values, "loginCode");
            return (Criteria) this;
        }

        public Criteria andLoginCodeBetween(String value1, String value2) {
            addCriterion("login_code between", value1, value2, "loginCode");
            return (Criteria) this;
        }

        public Criteria andLoginCodeNotBetween(String value1, String value2) {
            addCriterion("login_code not between", value1, value2, "loginCode");
            return (Criteria) this;
        }

        public Criteria andLoginPassIsNull() {
            addCriterion("login_pass is null");
            return (Criteria) this;
        }

        public Criteria andLoginPassIsNotNull() {
            addCriterion("login_pass is not null");
            return (Criteria) this;
        }

        public Criteria andLoginPassEqualTo(String value) {
            addCriterion("login_pass =", value, "loginPass");
            return (Criteria) this;
        }

        public Criteria andLoginPassNotEqualTo(String value) {
            addCriterion("login_pass <>", value, "loginPass");
            return (Criteria) this;
        }

        public Criteria andLoginPassGreaterThan(String value) {
            addCriterion("login_pass >", value, "loginPass");
            return (Criteria) this;
        }

        public Criteria andLoginPassGreaterThanOrEqualTo(String value) {
            addCriterion("login_pass >=", value, "loginPass");
            return (Criteria) this;
        }

        public Criteria andLoginPassLessThan(String value) {
            addCriterion("login_pass <", value, "loginPass");
            return (Criteria) this;
        }

        public Criteria andLoginPassLessThanOrEqualTo(String value) {
            addCriterion("login_pass <=", value, "loginPass");
            return (Criteria) this;
        }

        public Criteria andLoginPassLike(String value) {
            addCriterion("login_pass like", value, "loginPass");
            return (Criteria) this;
        }

        public Criteria andLoginPassNotLike(String value) {
            addCriterion("login_pass not like", value, "loginPass");
            return (Criteria) this;
        }

        public Criteria andLoginPassIn(List<String> values) {
            addCriterion("login_pass in", values, "loginPass");
            return (Criteria) this;
        }

        public Criteria andLoginPassNotIn(List<String> values) {
            addCriterion("login_pass not in", values, "loginPass");
            return (Criteria) this;
        }

        public Criteria andLoginPassBetween(String value1, String value2) {
            addCriterion("login_pass between", value1, value2, "loginPass");
            return (Criteria) this;
        }

        public Criteria andLoginPassNotBetween(String value1, String value2) {
            addCriterion("login_pass not between", value1, value2, "loginPass");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNull() {
            addCriterion("parent_id is null");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNotNull() {
            addCriterion("parent_id is not null");
            return (Criteria) this;
        }

        public Criteria andParentIdEqualTo(Integer value) {
            addCriterion("parent_id =", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotEqualTo(Integer value) {
            addCriterion("parent_id <>", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThan(Integer value) {
            addCriterion("parent_id >", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("parent_id >=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThan(Integer value) {
            addCriterion("parent_id <", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThanOrEqualTo(Integer value) {
            addCriterion("parent_id <=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdIn(List<Integer> values) {
            addCriterion("parent_id in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotIn(List<Integer> values) {
            addCriterion("parent_id not in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdBetween(Integer value1, Integer value2) {
            addCriterion("parent_id between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotBetween(Integer value1, Integer value2) {
            addCriterion("parent_id not between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andMobilePhoneIsNull() {
            addCriterion("mobile_phone is null");
            return (Criteria) this;
        }

        public Criteria andMobilePhoneIsNotNull() {
            addCriterion("mobile_phone is not null");
            return (Criteria) this;
        }

        public Criteria andMobilePhoneEqualTo(String value) {
            addCriterion("mobile_phone =", value, "mobilePhone");
            return (Criteria) this;
        }

        public Criteria andMobilePhoneNotEqualTo(String value) {
            addCriterion("mobile_phone <>", value, "mobilePhone");
            return (Criteria) this;
        }

        public Criteria andMobilePhoneGreaterThan(String value) {
            addCriterion("mobile_phone >", value, "mobilePhone");
            return (Criteria) this;
        }

        public Criteria andMobilePhoneGreaterThanOrEqualTo(String value) {
            addCriterion("mobile_phone >=", value, "mobilePhone");
            return (Criteria) this;
        }

        public Criteria andMobilePhoneLessThan(String value) {
            addCriterion("mobile_phone <", value, "mobilePhone");
            return (Criteria) this;
        }

        public Criteria andMobilePhoneLessThanOrEqualTo(String value) {
            addCriterion("mobile_phone <=", value, "mobilePhone");
            return (Criteria) this;
        }

        public Criteria andMobilePhoneLike(String value) {
            addCriterion("mobile_phone like", value, "mobilePhone");
            return (Criteria) this;
        }

        public Criteria andMobilePhoneNotLike(String value) {
            addCriterion("mobile_phone not like", value, "mobilePhone");
            return (Criteria) this;
        }

        public Criteria andMobilePhoneIn(List<String> values) {
            addCriterion("mobile_phone in", values, "mobilePhone");
            return (Criteria) this;
        }

        public Criteria andMobilePhoneNotIn(List<String> values) {
            addCriterion("mobile_phone not in", values, "mobilePhone");
            return (Criteria) this;
        }

        public Criteria andMobilePhoneBetween(String value1, String value2) {
            addCriterion("mobile_phone between", value1, value2, "mobilePhone");
            return (Criteria) this;
        }

        public Criteria andMobilePhoneNotBetween(String value1, String value2) {
            addCriterion("mobile_phone not between", value1, value2, "mobilePhone");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andShortNameIsNull() {
            addCriterion("short_name is null");
            return (Criteria) this;
        }

        public Criteria andShortNameIsNotNull() {
            addCriterion("short_name is not null");
            return (Criteria) this;
        }

        public Criteria andShortNameEqualTo(String value) {
            addCriterion("short_name =", value, "shortName");
            return (Criteria) this;
        }

        public Criteria andShortNameNotEqualTo(String value) {
            addCriterion("short_name <>", value, "shortName");
            return (Criteria) this;
        }

        public Criteria andShortNameGreaterThan(String value) {
            addCriterion("short_name >", value, "shortName");
            return (Criteria) this;
        }

        public Criteria andShortNameGreaterThanOrEqualTo(String value) {
            addCriterion("short_name >=", value, "shortName");
            return (Criteria) this;
        }

        public Criteria andShortNameLessThan(String value) {
            addCriterion("short_name <", value, "shortName");
            return (Criteria) this;
        }

        public Criteria andShortNameLessThanOrEqualTo(String value) {
            addCriterion("short_name <=", value, "shortName");
            return (Criteria) this;
        }

        public Criteria andShortNameLike(String value) {
            addCriterion("short_name like", value, "shortName");
            return (Criteria) this;
        }

        public Criteria andShortNameNotLike(String value) {
            addCriterion("short_name not like", value, "shortName");
            return (Criteria) this;
        }

        public Criteria andShortNameIn(List<String> values) {
            addCriterion("short_name in", values, "shortName");
            return (Criteria) this;
        }

        public Criteria andShortNameNotIn(List<String> values) {
            addCriterion("short_name not in", values, "shortName");
            return (Criteria) this;
        }

        public Criteria andShortNameBetween(String value1, String value2) {
            addCriterion("short_name between", value1, value2, "shortName");
            return (Criteria) this;
        }

        public Criteria andShortNameNotBetween(String value1, String value2) {
            addCriterion("short_name not between", value1, value2, "shortName");
            return (Criteria) this;
        }

        public Criteria andContactIsNull() {
            addCriterion("contact is null");
            return (Criteria) this;
        }

        public Criteria andContactIsNotNull() {
            addCriterion("contact is not null");
            return (Criteria) this;
        }

        public Criteria andContactEqualTo(String value) {
            addCriterion("contact =", value, "contact");
            return (Criteria) this;
        }

        public Criteria andContactNotEqualTo(String value) {
            addCriterion("contact <>", value, "contact");
            return (Criteria) this;
        }

        public Criteria andContactGreaterThan(String value) {
            addCriterion("contact >", value, "contact");
            return (Criteria) this;
        }

        public Criteria andContactGreaterThanOrEqualTo(String value) {
            addCriterion("contact >=", value, "contact");
            return (Criteria) this;
        }

        public Criteria andContactLessThan(String value) {
            addCriterion("contact <", value, "contact");
            return (Criteria) this;
        }

        public Criteria andContactLessThanOrEqualTo(String value) {
            addCriterion("contact <=", value, "contact");
            return (Criteria) this;
        }

        public Criteria andContactLike(String value) {
            addCriterion("contact like", value, "contact");
            return (Criteria) this;
        }

        public Criteria andContactNotLike(String value) {
            addCriterion("contact not like", value, "contact");
            return (Criteria) this;
        }

        public Criteria andContactIn(List<String> values) {
            addCriterion("contact in", values, "contact");
            return (Criteria) this;
        }

        public Criteria andContactNotIn(List<String> values) {
            addCriterion("contact not in", values, "contact");
            return (Criteria) this;
        }

        public Criteria andContactBetween(String value1, String value2) {
            addCriterion("contact between", value1, value2, "contact");
            return (Criteria) this;
        }

        public Criteria andContactNotBetween(String value1, String value2) {
            addCriterion("contact not between", value1, value2, "contact");
            return (Criteria) this;
        }

        public Criteria andLevelIsNull() {
            addCriterion("level is null");
            return (Criteria) this;
        }

        public Criteria andLevelIsNotNull() {
            addCriterion("level is not null");
            return (Criteria) this;
        }

        public Criteria andLevelEqualTo(String value) {
            addCriterion("level =", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotEqualTo(String value) {
            addCriterion("level <>", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelGreaterThan(String value) {
            addCriterion("level >", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelGreaterThanOrEqualTo(String value) {
            addCriterion("level >=", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelLessThan(String value) {
            addCriterion("level <", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelLessThanOrEqualTo(String value) {
            addCriterion("level <=", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelLike(String value) {
            addCriterion("level like", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotLike(String value) {
            addCriterion("level not like", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelIn(List<String> values) {
            addCriterion("level in", values, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotIn(List<String> values) {
            addCriterion("level not in", values, "level");
            return (Criteria) this;
        }

        public Criteria andLevelBetween(String value1, String value2) {
            addCriterion("level between", value1, value2, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotBetween(String value1, String value2) {
            addCriterion("level not between", value1, value2, "level");
            return (Criteria) this;
        }

        public Criteria andDrawStatusIsNull() {
            addCriterion("draw_status is null");
            return (Criteria) this;
        }

        public Criteria andDrawStatusIsNotNull() {
            addCriterion("draw_status is not null");
            return (Criteria) this;
        }

        public Criteria andDrawStatusEqualTo(String value) {
            addCriterion("draw_status =", value, "drawStatus");
            return (Criteria) this;
        }

        public Criteria andDrawStatusNotEqualTo(String value) {
            addCriterion("draw_status <>", value, "drawStatus");
            return (Criteria) this;
        }

        public Criteria andDrawStatusGreaterThan(String value) {
            addCriterion("draw_status >", value, "drawStatus");
            return (Criteria) this;
        }

        public Criteria andDrawStatusGreaterThanOrEqualTo(String value) {
            addCriterion("draw_status >=", value, "drawStatus");
            return (Criteria) this;
        }

        public Criteria andDrawStatusLessThan(String value) {
            addCriterion("draw_status <", value, "drawStatus");
            return (Criteria) this;
        }

        public Criteria andDrawStatusLessThanOrEqualTo(String value) {
            addCriterion("draw_status <=", value, "drawStatus");
            return (Criteria) this;
        }

        public Criteria andDrawStatusLike(String value) {
            addCriterion("draw_status like", value, "drawStatus");
            return (Criteria) this;
        }

        public Criteria andDrawStatusNotLike(String value) {
            addCriterion("draw_status not like", value, "drawStatus");
            return (Criteria) this;
        }

        public Criteria andDrawStatusIn(List<String> values) {
            addCriterion("draw_status in", values, "drawStatus");
            return (Criteria) this;
        }

        public Criteria andDrawStatusNotIn(List<String> values) {
            addCriterion("draw_status not in", values, "drawStatus");
            return (Criteria) this;
        }

        public Criteria andDrawStatusBetween(String value1, String value2) {
            addCriterion("draw_status between", value1, value2, "drawStatus");
            return (Criteria) this;
        }

        public Criteria andDrawStatusNotBetween(String value1, String value2) {
            addCriterion("draw_status not between", value1, value2, "drawStatus");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andWxStatusIsNull() {
            addCriterion("wx_status is null");
            return (Criteria) this;
        }

        public Criteria andWxStatusIsNotNull() {
            addCriterion("wx_status is not null");
            return (Criteria) this;
        }

        public Criteria andWxStatusEqualTo(String value) {
            addCriterion("wx_status =", value, "wxStatus");
            return (Criteria) this;
        }

        public Criteria andWxStatusNotEqualTo(String value) {
            addCriterion("wx_status <>", value, "wxStatus");
            return (Criteria) this;
        }

        public Criteria andWxStatusGreaterThan(String value) {
            addCriterion("wx_status >", value, "wxStatus");
            return (Criteria) this;
        }

        public Criteria andWxStatusGreaterThanOrEqualTo(String value) {
            addCriterion("wx_status >=", value, "wxStatus");
            return (Criteria) this;
        }

        public Criteria andWxStatusLessThan(String value) {
            addCriterion("wx_status <", value, "wxStatus");
            return (Criteria) this;
        }

        public Criteria andWxStatusLessThanOrEqualTo(String value) {
            addCriterion("wx_status <=", value, "wxStatus");
            return (Criteria) this;
        }

        public Criteria andWxStatusLike(String value) {
            addCriterion("wx_status like", value, "wxStatus");
            return (Criteria) this;
        }

        public Criteria andWxStatusNotLike(String value) {
            addCriterion("wx_status not like", value, "wxStatus");
            return (Criteria) this;
        }

        public Criteria andWxStatusIn(List<String> values) {
            addCriterion("wx_status in", values, "wxStatus");
            return (Criteria) this;
        }

        public Criteria andWxStatusNotIn(List<String> values) {
            addCriterion("wx_status not in", values, "wxStatus");
            return (Criteria) this;
        }

        public Criteria andWxStatusBetween(String value1, String value2) {
            addCriterion("wx_status between", value1, value2, "wxStatus");
            return (Criteria) this;
        }

        public Criteria andWxStatusNotBetween(String value1, String value2) {
            addCriterion("wx_status not between", value1, value2, "wxStatus");
            return (Criteria) this;
        }

        public Criteria andZfbStatusIsNull() {
            addCriterion("zfb_status is null");
            return (Criteria) this;
        }

        public Criteria andZfbStatusIsNotNull() {
            addCriterion("zfb_status is not null");
            return (Criteria) this;
        }

        public Criteria andZfbStatusEqualTo(String value) {
            addCriterion("zfb_status =", value, "zfbStatus");
            return (Criteria) this;
        }

        public Criteria andZfbStatusNotEqualTo(String value) {
            addCriterion("zfb_status <>", value, "zfbStatus");
            return (Criteria) this;
        }

        public Criteria andZfbStatusGreaterThan(String value) {
            addCriterion("zfb_status >", value, "zfbStatus");
            return (Criteria) this;
        }

        public Criteria andZfbStatusGreaterThanOrEqualTo(String value) {
            addCriterion("zfb_status >=", value, "zfbStatus");
            return (Criteria) this;
        }

        public Criteria andZfbStatusLessThan(String value) {
            addCriterion("zfb_status <", value, "zfbStatus");
            return (Criteria) this;
        }

        public Criteria andZfbStatusLessThanOrEqualTo(String value) {
            addCriterion("zfb_status <=", value, "zfbStatus");
            return (Criteria) this;
        }

        public Criteria andZfbStatusLike(String value) {
            addCriterion("zfb_status like", value, "zfbStatus");
            return (Criteria) this;
        }

        public Criteria andZfbStatusNotLike(String value) {
            addCriterion("zfb_status not like", value, "zfbStatus");
            return (Criteria) this;
        }

        public Criteria andZfbStatusIn(List<String> values) {
            addCriterion("zfb_status in", values, "zfbStatus");
            return (Criteria) this;
        }

        public Criteria andZfbStatusNotIn(List<String> values) {
            addCriterion("zfb_status not in", values, "zfbStatus");
            return (Criteria) this;
        }

        public Criteria andZfbStatusBetween(String value1, String value2) {
            addCriterion("zfb_status between", value1, value2, "zfbStatus");
            return (Criteria) this;
        }

        public Criteria andZfbStatusNotBetween(String value1, String value2) {
            addCriterion("zfb_status not between", value1, value2, "zfbStatus");
            return (Criteria) this;
        }

        public Criteria andQqStatusIsNull() {
            addCriterion("qq_status is null");
            return (Criteria) this;
        }

        public Criteria andQqStatusIsNotNull() {
            addCriterion("qq_status is not null");
            return (Criteria) this;
        }

        public Criteria andQqStatusEqualTo(String value) {
            addCriterion("qq_status =", value, "qqStatus");
            return (Criteria) this;
        }

        public Criteria andQqStatusNotEqualTo(String value) {
            addCriterion("qq_status <>", value, "qqStatus");
            return (Criteria) this;
        }

        public Criteria andQqStatusGreaterThan(String value) {
            addCriterion("qq_status >", value, "qqStatus");
            return (Criteria) this;
        }

        public Criteria andQqStatusGreaterThanOrEqualTo(String value) {
            addCriterion("qq_status >=", value, "qqStatus");
            return (Criteria) this;
        }

        public Criteria andQqStatusLessThan(String value) {
            addCriterion("qq_status <", value, "qqStatus");
            return (Criteria) this;
        }

        public Criteria andQqStatusLessThanOrEqualTo(String value) {
            addCriterion("qq_status <=", value, "qqStatus");
            return (Criteria) this;
        }

        public Criteria andQqStatusLike(String value) {
            addCriterion("qq_status like", value, "qqStatus");
            return (Criteria) this;
        }

        public Criteria andQqStatusNotLike(String value) {
            addCriterion("qq_status not like", value, "qqStatus");
            return (Criteria) this;
        }

        public Criteria andQqStatusIn(List<String> values) {
            addCriterion("qq_status in", values, "qqStatus");
            return (Criteria) this;
        }

        public Criteria andQqStatusNotIn(List<String> values) {
            addCriterion("qq_status not in", values, "qqStatus");
            return (Criteria) this;
        }

        public Criteria andQqStatusBetween(String value1, String value2) {
            addCriterion("qq_status between", value1, value2, "qqStatus");
            return (Criteria) this;
        }

        public Criteria andQqStatusNotBetween(String value1, String value2) {
            addCriterion("qq_status not between", value1, value2, "qqStatus");
            return (Criteria) this;
        }

        public Criteria andBdStatusIsNull() {
            addCriterion("bd_status is null");
            return (Criteria) this;
        }

        public Criteria andBdStatusIsNotNull() {
            addCriterion("bd_status is not null");
            return (Criteria) this;
        }

        public Criteria andBdStatusEqualTo(String value) {
            addCriterion("bd_status =", value, "bdStatus");
            return (Criteria) this;
        }

        public Criteria andBdStatusNotEqualTo(String value) {
            addCriterion("bd_status <>", value, "bdStatus");
            return (Criteria) this;
        }

        public Criteria andBdStatusGreaterThan(String value) {
            addCriterion("bd_status >", value, "bdStatus");
            return (Criteria) this;
        }

        public Criteria andBdStatusGreaterThanOrEqualTo(String value) {
            addCriterion("bd_status >=", value, "bdStatus");
            return (Criteria) this;
        }

        public Criteria andBdStatusLessThan(String value) {
            addCriterion("bd_status <", value, "bdStatus");
            return (Criteria) this;
        }

        public Criteria andBdStatusLessThanOrEqualTo(String value) {
            addCriterion("bd_status <=", value, "bdStatus");
            return (Criteria) this;
        }

        public Criteria andBdStatusLike(String value) {
            addCriterion("bd_status like", value, "bdStatus");
            return (Criteria) this;
        }

        public Criteria andBdStatusNotLike(String value) {
            addCriterion("bd_status not like", value, "bdStatus");
            return (Criteria) this;
        }

        public Criteria andBdStatusIn(List<String> values) {
            addCriterion("bd_status in", values, "bdStatus");
            return (Criteria) this;
        }

        public Criteria andBdStatusNotIn(List<String> values) {
            addCriterion("bd_status not in", values, "bdStatus");
            return (Criteria) this;
        }

        public Criteria andBdStatusBetween(String value1, String value2) {
            addCriterion("bd_status between", value1, value2, "bdStatus");
            return (Criteria) this;
        }

        public Criteria andBdStatusNotBetween(String value1, String value2) {
            addCriterion("bd_status not between", value1, value2, "bdStatus");
            return (Criteria) this;
        }

        public Criteria andJdStatusIsNull() {
            addCriterion("jd_status is null");
            return (Criteria) this;
        }

        public Criteria andJdStatusIsNotNull() {
            addCriterion("jd_status is not null");
            return (Criteria) this;
        }

        public Criteria andJdStatusEqualTo(String value) {
            addCriterion("jd_status =", value, "jdStatus");
            return (Criteria) this;
        }

        public Criteria andJdStatusNotEqualTo(String value) {
            addCriterion("jd_status <>", value, "jdStatus");
            return (Criteria) this;
        }

        public Criteria andJdStatusGreaterThan(String value) {
            addCriterion("jd_status >", value, "jdStatus");
            return (Criteria) this;
        }

        public Criteria andJdStatusGreaterThanOrEqualTo(String value) {
            addCriterion("jd_status >=", value, "jdStatus");
            return (Criteria) this;
        }

        public Criteria andJdStatusLessThan(String value) {
            addCriterion("jd_status <", value, "jdStatus");
            return (Criteria) this;
        }

        public Criteria andJdStatusLessThanOrEqualTo(String value) {
            addCriterion("jd_status <=", value, "jdStatus");
            return (Criteria) this;
        }

        public Criteria andJdStatusLike(String value) {
            addCriterion("jd_status like", value, "jdStatus");
            return (Criteria) this;
        }

        public Criteria andJdStatusNotLike(String value) {
            addCriterion("jd_status not like", value, "jdStatus");
            return (Criteria) this;
        }

        public Criteria andJdStatusIn(List<String> values) {
            addCriterion("jd_status in", values, "jdStatus");
            return (Criteria) this;
        }

        public Criteria andJdStatusNotIn(List<String> values) {
            addCriterion("jd_status not in", values, "jdStatus");
            return (Criteria) this;
        }

        public Criteria andJdStatusBetween(String value1, String value2) {
            addCriterion("jd_status between", value1, value2, "jdStatus");
            return (Criteria) this;
        }

        public Criteria andJdStatusNotBetween(String value1, String value2) {
            addCriterion("jd_status not between", value1, value2, "jdStatus");
            return (Criteria) this;
        }

        public Criteria andMlJfStatusIsNull() {
            addCriterion("ml_jf_status is null");
            return (Criteria) this;
        }

        public Criteria andMlJfStatusIsNotNull() {
            addCriterion("ml_jf_status is not null");
            return (Criteria) this;
        }

        public Criteria andMlJfStatusEqualTo(String value) {
            addCriterion("ml_jf_status =", value, "mlJfStatus");
            return (Criteria) this;
        }

        public Criteria andMlJfStatusNotEqualTo(String value) {
            addCriterion("ml_jf_status <>", value, "mlJfStatus");
            return (Criteria) this;
        }

        public Criteria andMlJfStatusGreaterThan(String value) {
            addCriterion("ml_jf_status >", value, "mlJfStatus");
            return (Criteria) this;
        }

        public Criteria andMlJfStatusGreaterThanOrEqualTo(String value) {
            addCriterion("ml_jf_status >=", value, "mlJfStatus");
            return (Criteria) this;
        }

        public Criteria andMlJfStatusLessThan(String value) {
            addCriterion("ml_jf_status <", value, "mlJfStatus");
            return (Criteria) this;
        }

        public Criteria andMlJfStatusLessThanOrEqualTo(String value) {
            addCriterion("ml_jf_status <=", value, "mlJfStatus");
            return (Criteria) this;
        }

        public Criteria andMlJfStatusLike(String value) {
            addCriterion("ml_jf_status like", value, "mlJfStatus");
            return (Criteria) this;
        }

        public Criteria andMlJfStatusNotLike(String value) {
            addCriterion("ml_jf_status not like", value, "mlJfStatus");
            return (Criteria) this;
        }

        public Criteria andMlJfStatusIn(List<String> values) {
            addCriterion("ml_jf_status in", values, "mlJfStatus");
            return (Criteria) this;
        }

        public Criteria andMlJfStatusNotIn(List<String> values) {
            addCriterion("ml_jf_status not in", values, "mlJfStatus");
            return (Criteria) this;
        }

        public Criteria andMlJfStatusBetween(String value1, String value2) {
            addCriterion("ml_jf_status between", value1, value2, "mlJfStatus");
            return (Criteria) this;
        }

        public Criteria andMlJfStatusNotBetween(String value1, String value2) {
            addCriterion("ml_jf_status not between", value1, value2, "mlJfStatus");
            return (Criteria) this;
        }

        public Criteria andMlWjfStatusIsNull() {
            addCriterion("ml_wjf_status is null");
            return (Criteria) this;
        }

        public Criteria andMlWjfStatusIsNotNull() {
            addCriterion("ml_wjf_status is not null");
            return (Criteria) this;
        }

        public Criteria andMlWjfStatusEqualTo(String value) {
            addCriterion("ml_wjf_status =", value, "mlWjfStatus");
            return (Criteria) this;
        }

        public Criteria andMlWjfStatusNotEqualTo(String value) {
            addCriterion("ml_wjf_status <>", value, "mlWjfStatus");
            return (Criteria) this;
        }

        public Criteria andMlWjfStatusGreaterThan(String value) {
            addCriterion("ml_wjf_status >", value, "mlWjfStatus");
            return (Criteria) this;
        }

        public Criteria andMlWjfStatusGreaterThanOrEqualTo(String value) {
            addCriterion("ml_wjf_status >=", value, "mlWjfStatus");
            return (Criteria) this;
        }

        public Criteria andMlWjfStatusLessThan(String value) {
            addCriterion("ml_wjf_status <", value, "mlWjfStatus");
            return (Criteria) this;
        }

        public Criteria andMlWjfStatusLessThanOrEqualTo(String value) {
            addCriterion("ml_wjf_status <=", value, "mlWjfStatus");
            return (Criteria) this;
        }

        public Criteria andMlWjfStatusLike(String value) {
            addCriterion("ml_wjf_status like", value, "mlWjfStatus");
            return (Criteria) this;
        }

        public Criteria andMlWjfStatusNotLike(String value) {
            addCriterion("ml_wjf_status not like", value, "mlWjfStatus");
            return (Criteria) this;
        }

        public Criteria andMlWjfStatusIn(List<String> values) {
            addCriterion("ml_wjf_status in", values, "mlWjfStatus");
            return (Criteria) this;
        }

        public Criteria andMlWjfStatusNotIn(List<String> values) {
            addCriterion("ml_wjf_status not in", values, "mlWjfStatus");
            return (Criteria) this;
        }

        public Criteria andMlWjfStatusBetween(String value1, String value2) {
            addCriterion("ml_wjf_status between", value1, value2, "mlWjfStatus");
            return (Criteria) this;
        }

        public Criteria andMlWjfStatusNotBetween(String value1, String value2) {
            addCriterion("ml_wjf_status not between", value1, value2, "mlWjfStatus");
            return (Criteria) this;
        }

        public Criteria andSexTypeIsNull() {
            addCriterion("sex_type is null");
            return (Criteria) this;
        }

        public Criteria andSexTypeIsNotNull() {
            addCriterion("sex_type is not null");
            return (Criteria) this;
        }

        public Criteria andSexTypeEqualTo(String value) {
            addCriterion("sex_type =", value, "sexType");
            return (Criteria) this;
        }

        public Criteria andSexTypeNotEqualTo(String value) {
            addCriterion("sex_type <>", value, "sexType");
            return (Criteria) this;
        }

        public Criteria andSexTypeGreaterThan(String value) {
            addCriterion("sex_type >", value, "sexType");
            return (Criteria) this;
        }

        public Criteria andSexTypeGreaterThanOrEqualTo(String value) {
            addCriterion("sex_type >=", value, "sexType");
            return (Criteria) this;
        }

        public Criteria andSexTypeLessThan(String value) {
            addCriterion("sex_type <", value, "sexType");
            return (Criteria) this;
        }

        public Criteria andSexTypeLessThanOrEqualTo(String value) {
            addCriterion("sex_type <=", value, "sexType");
            return (Criteria) this;
        }

        public Criteria andSexTypeLike(String value) {
            addCriterion("sex_type like", value, "sexType");
            return (Criteria) this;
        }

        public Criteria andSexTypeNotLike(String value) {
            addCriterion("sex_type not like", value, "sexType");
            return (Criteria) this;
        }

        public Criteria andSexTypeIn(List<String> values) {
            addCriterion("sex_type in", values, "sexType");
            return (Criteria) this;
        }

        public Criteria andSexTypeNotIn(List<String> values) {
            addCriterion("sex_type not in", values, "sexType");
            return (Criteria) this;
        }

        public Criteria andSexTypeBetween(String value1, String value2) {
            addCriterion("sex_type between", value1, value2, "sexType");
            return (Criteria) this;
        }

        public Criteria andSexTypeNotBetween(String value1, String value2) {
            addCriterion("sex_type not between", value1, value2, "sexType");
            return (Criteria) this;
        }

        public Criteria andBirthdayIsNull() {
            addCriterion("birthday is null");
            return (Criteria) this;
        }

        public Criteria andBirthdayIsNotNull() {
            addCriterion("birthday is not null");
            return (Criteria) this;
        }

        public Criteria andBirthdayEqualTo(Date value) {
            addCriterionForJDBCDate("birthday =", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotEqualTo(Date value) {
            addCriterionForJDBCDate("birthday <>", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayGreaterThan(Date value) {
            addCriterionForJDBCDate("birthday >", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("birthday >=", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayLessThan(Date value) {
            addCriterionForJDBCDate("birthday <", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("birthday <=", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayIn(List<Date> values) {
            addCriterionForJDBCDate("birthday in", values, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotIn(List<Date> values) {
            addCriterionForJDBCDate("birthday not in", values, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("birthday between", value1, value2, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("birthday not between", value1, value2, "birthday");
            return (Criteria) this;
        }

        public Criteria andHomePhoneIsNull() {
            addCriterion("home_phone is null");
            return (Criteria) this;
        }

        public Criteria andHomePhoneIsNotNull() {
            addCriterion("home_phone is not null");
            return (Criteria) this;
        }

        public Criteria andHomePhoneEqualTo(String value) {
            addCriterion("home_phone =", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneNotEqualTo(String value) {
            addCriterion("home_phone <>", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneGreaterThan(String value) {
            addCriterion("home_phone >", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneGreaterThanOrEqualTo(String value) {
            addCriterion("home_phone >=", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneLessThan(String value) {
            addCriterion("home_phone <", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneLessThanOrEqualTo(String value) {
            addCriterion("home_phone <=", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneLike(String value) {
            addCriterion("home_phone like", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneNotLike(String value) {
            addCriterion("home_phone not like", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneIn(List<String> values) {
            addCriterion("home_phone in", values, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneNotIn(List<String> values) {
            addCriterion("home_phone not in", values, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneBetween(String value1, String value2) {
            addCriterion("home_phone between", value1, value2, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneNotBetween(String value1, String value2) {
            addCriterion("home_phone not between", value1, value2, "homePhone");
            return (Criteria) this;
        }

        public Criteria andCertNbrIsNull() {
            addCriterion("cert_nbr is null");
            return (Criteria) this;
        }

        public Criteria andCertNbrIsNotNull() {
            addCriterion("cert_nbr is not null");
            return (Criteria) this;
        }

        public Criteria andCertNbrEqualTo(String value) {
            addCriterion("cert_nbr =", value, "certNbr");
            return (Criteria) this;
        }

        public Criteria andCertNbrNotEqualTo(String value) {
            addCriterion("cert_nbr <>", value, "certNbr");
            return (Criteria) this;
        }

        public Criteria andCertNbrGreaterThan(String value) {
            addCriterion("cert_nbr >", value, "certNbr");
            return (Criteria) this;
        }

        public Criteria andCertNbrGreaterThanOrEqualTo(String value) {
            addCriterion("cert_nbr >=", value, "certNbr");
            return (Criteria) this;
        }

        public Criteria andCertNbrLessThan(String value) {
            addCriterion("cert_nbr <", value, "certNbr");
            return (Criteria) this;
        }

        public Criteria andCertNbrLessThanOrEqualTo(String value) {
            addCriterion("cert_nbr <=", value, "certNbr");
            return (Criteria) this;
        }

        public Criteria andCertNbrLike(String value) {
            addCriterion("cert_nbr like", value, "certNbr");
            return (Criteria) this;
        }

        public Criteria andCertNbrNotLike(String value) {
            addCriterion("cert_nbr not like", value, "certNbr");
            return (Criteria) this;
        }

        public Criteria andCertNbrIn(List<String> values) {
            addCriterion("cert_nbr in", values, "certNbr");
            return (Criteria) this;
        }

        public Criteria andCertNbrNotIn(List<String> values) {
            addCriterion("cert_nbr not in", values, "certNbr");
            return (Criteria) this;
        }

        public Criteria andCertNbrBetween(String value1, String value2) {
            addCriterion("cert_nbr between", value1, value2, "certNbr");
            return (Criteria) this;
        }

        public Criteria andCertNbrNotBetween(String value1, String value2) {
            addCriterion("cert_nbr not between", value1, value2, "certNbr");
            return (Criteria) this;
        }

        public Criteria andCertPic1IsNull() {
            addCriterion("cert_pic1 is null");
            return (Criteria) this;
        }

        public Criteria andCertPic1IsNotNull() {
            addCriterion("cert_pic1 is not null");
            return (Criteria) this;
        }

        public Criteria andCertPic1EqualTo(String value) {
            addCriterion("cert_pic1 =", value, "certPic1");
            return (Criteria) this;
        }

        public Criteria andCertPic1NotEqualTo(String value) {
            addCriterion("cert_pic1 <>", value, "certPic1");
            return (Criteria) this;
        }

        public Criteria andCertPic1GreaterThan(String value) {
            addCriterion("cert_pic1 >", value, "certPic1");
            return (Criteria) this;
        }

        public Criteria andCertPic1GreaterThanOrEqualTo(String value) {
            addCriterion("cert_pic1 >=", value, "certPic1");
            return (Criteria) this;
        }

        public Criteria andCertPic1LessThan(String value) {
            addCriterion("cert_pic1 <", value, "certPic1");
            return (Criteria) this;
        }

        public Criteria andCertPic1LessThanOrEqualTo(String value) {
            addCriterion("cert_pic1 <=", value, "certPic1");
            return (Criteria) this;
        }

        public Criteria andCertPic1Like(String value) {
            addCriterion("cert_pic1 like", value, "certPic1");
            return (Criteria) this;
        }

        public Criteria andCertPic1NotLike(String value) {
            addCriterion("cert_pic1 not like", value, "certPic1");
            return (Criteria) this;
        }

        public Criteria andCertPic1In(List<String> values) {
            addCriterion("cert_pic1 in", values, "certPic1");
            return (Criteria) this;
        }

        public Criteria andCertPic1NotIn(List<String> values) {
            addCriterion("cert_pic1 not in", values, "certPic1");
            return (Criteria) this;
        }

        public Criteria andCertPic1Between(String value1, String value2) {
            addCriterion("cert_pic1 between", value1, value2, "certPic1");
            return (Criteria) this;
        }

        public Criteria andCertPic1NotBetween(String value1, String value2) {
            addCriterion("cert_pic1 not between", value1, value2, "certPic1");
            return (Criteria) this;
        }

        public Criteria andCertPic2IsNull() {
            addCriterion("cert_pic2 is null");
            return (Criteria) this;
        }

        public Criteria andCertPic2IsNotNull() {
            addCriterion("cert_pic2 is not null");
            return (Criteria) this;
        }

        public Criteria andCertPic2EqualTo(String value) {
            addCriterion("cert_pic2 =", value, "certPic2");
            return (Criteria) this;
        }

        public Criteria andCertPic2NotEqualTo(String value) {
            addCriterion("cert_pic2 <>", value, "certPic2");
            return (Criteria) this;
        }

        public Criteria andCertPic2GreaterThan(String value) {
            addCriterion("cert_pic2 >", value, "certPic2");
            return (Criteria) this;
        }

        public Criteria andCertPic2GreaterThanOrEqualTo(String value) {
            addCriterion("cert_pic2 >=", value, "certPic2");
            return (Criteria) this;
        }

        public Criteria andCertPic2LessThan(String value) {
            addCriterion("cert_pic2 <", value, "certPic2");
            return (Criteria) this;
        }

        public Criteria andCertPic2LessThanOrEqualTo(String value) {
            addCriterion("cert_pic2 <=", value, "certPic2");
            return (Criteria) this;
        }

        public Criteria andCertPic2Like(String value) {
            addCriterion("cert_pic2 like", value, "certPic2");
            return (Criteria) this;
        }

        public Criteria andCertPic2NotLike(String value) {
            addCriterion("cert_pic2 not like", value, "certPic2");
            return (Criteria) this;
        }

        public Criteria andCertPic2In(List<String> values) {
            addCriterion("cert_pic2 in", values, "certPic2");
            return (Criteria) this;
        }

        public Criteria andCertPic2NotIn(List<String> values) {
            addCriterion("cert_pic2 not in", values, "certPic2");
            return (Criteria) this;
        }

        public Criteria andCertPic2Between(String value1, String value2) {
            addCriterion("cert_pic2 between", value1, value2, "certPic2");
            return (Criteria) this;
        }

        public Criteria andCertPic2NotBetween(String value1, String value2) {
            addCriterion("cert_pic2 not between", value1, value2, "certPic2");
            return (Criteria) this;
        }

        public Criteria andMemcertPicIsNull() {
            addCriterion("memCert_pic is null");
            return (Criteria) this;
        }

        public Criteria andMemcertPicIsNotNull() {
            addCriterion("memCert_pic is not null");
            return (Criteria) this;
        }

        public Criteria andMemcertPicEqualTo(String value) {
            addCriterion("memCert_pic =", value, "memcertPic");
            return (Criteria) this;
        }

        public Criteria andMemcertPicNotEqualTo(String value) {
            addCriterion("memCert_pic <>", value, "memcertPic");
            return (Criteria) this;
        }

        public Criteria andMemcertPicGreaterThan(String value) {
            addCriterion("memCert_pic >", value, "memcertPic");
            return (Criteria) this;
        }

        public Criteria andMemcertPicGreaterThanOrEqualTo(String value) {
            addCriterion("memCert_pic >=", value, "memcertPic");
            return (Criteria) this;
        }

        public Criteria andMemcertPicLessThan(String value) {
            addCriterion("memCert_pic <", value, "memcertPic");
            return (Criteria) this;
        }

        public Criteria andMemcertPicLessThanOrEqualTo(String value) {
            addCriterion("memCert_pic <=", value, "memcertPic");
            return (Criteria) this;
        }

        public Criteria andMemcertPicLike(String value) {
            addCriterion("memCert_pic like", value, "memcertPic");
            return (Criteria) this;
        }

        public Criteria andMemcertPicNotLike(String value) {
            addCriterion("memCert_pic not like", value, "memcertPic");
            return (Criteria) this;
        }

        public Criteria andMemcertPicIn(List<String> values) {
            addCriterion("memCert_pic in", values, "memcertPic");
            return (Criteria) this;
        }

        public Criteria andMemcertPicNotIn(List<String> values) {
            addCriterion("memCert_pic not in", values, "memcertPic");
            return (Criteria) this;
        }

        public Criteria andMemcertPicBetween(String value1, String value2) {
            addCriterion("memCert_pic between", value1, value2, "memcertPic");
            return (Criteria) this;
        }

        public Criteria andMemcertPicNotBetween(String value1, String value2) {
            addCriterion("memCert_pic not between", value1, value2, "memcertPic");
            return (Criteria) this;
        }

        public Criteria andCardNbrIsNull() {
            addCriterion("card_nbr is null");
            return (Criteria) this;
        }

        public Criteria andCardNbrIsNotNull() {
            addCriterion("card_nbr is not null");
            return (Criteria) this;
        }

        public Criteria andCardNbrEqualTo(String value) {
            addCriterion("card_nbr =", value, "cardNbr");
            return (Criteria) this;
        }

        public Criteria andCardNbrNotEqualTo(String value) {
            addCriterion("card_nbr <>", value, "cardNbr");
            return (Criteria) this;
        }

        public Criteria andCardNbrGreaterThan(String value) {
            addCriterion("card_nbr >", value, "cardNbr");
            return (Criteria) this;
        }

        public Criteria andCardNbrGreaterThanOrEqualTo(String value) {
            addCriterion("card_nbr >=", value, "cardNbr");
            return (Criteria) this;
        }

        public Criteria andCardNbrLessThan(String value) {
            addCriterion("card_nbr <", value, "cardNbr");
            return (Criteria) this;
        }

        public Criteria andCardNbrLessThanOrEqualTo(String value) {
            addCriterion("card_nbr <=", value, "cardNbr");
            return (Criteria) this;
        }

        public Criteria andCardNbrLike(String value) {
            addCriterion("card_nbr like", value, "cardNbr");
            return (Criteria) this;
        }

        public Criteria andCardNbrNotLike(String value) {
            addCriterion("card_nbr not like", value, "cardNbr");
            return (Criteria) this;
        }

        public Criteria andCardNbrIn(List<String> values) {
            addCriterion("card_nbr in", values, "cardNbr");
            return (Criteria) this;
        }

        public Criteria andCardNbrNotIn(List<String> values) {
            addCriterion("card_nbr not in", values, "cardNbr");
            return (Criteria) this;
        }

        public Criteria andCardNbrBetween(String value1, String value2) {
            addCriterion("card_nbr between", value1, value2, "cardNbr");
            return (Criteria) this;
        }

        public Criteria andCardNbrNotBetween(String value1, String value2) {
            addCriterion("card_nbr not between", value1, value2, "cardNbr");
            return (Criteria) this;
        }

        public Criteria andCardPic1IsNull() {
            addCriterion("card_pic1 is null");
            return (Criteria) this;
        }

        public Criteria andCardPic1IsNotNull() {
            addCriterion("card_pic1 is not null");
            return (Criteria) this;
        }

        public Criteria andCardPic1EqualTo(String value) {
            addCriterion("card_pic1 =", value, "cardPic1");
            return (Criteria) this;
        }

        public Criteria andCardPic1NotEqualTo(String value) {
            addCriterion("card_pic1 <>", value, "cardPic1");
            return (Criteria) this;
        }

        public Criteria andCardPic1GreaterThan(String value) {
            addCriterion("card_pic1 >", value, "cardPic1");
            return (Criteria) this;
        }

        public Criteria andCardPic1GreaterThanOrEqualTo(String value) {
            addCriterion("card_pic1 >=", value, "cardPic1");
            return (Criteria) this;
        }

        public Criteria andCardPic1LessThan(String value) {
            addCriterion("card_pic1 <", value, "cardPic1");
            return (Criteria) this;
        }

        public Criteria andCardPic1LessThanOrEqualTo(String value) {
            addCriterion("card_pic1 <=", value, "cardPic1");
            return (Criteria) this;
        }

        public Criteria andCardPic1Like(String value) {
            addCriterion("card_pic1 like", value, "cardPic1");
            return (Criteria) this;
        }

        public Criteria andCardPic1NotLike(String value) {
            addCriterion("card_pic1 not like", value, "cardPic1");
            return (Criteria) this;
        }

        public Criteria andCardPic1In(List<String> values) {
            addCriterion("card_pic1 in", values, "cardPic1");
            return (Criteria) this;
        }

        public Criteria andCardPic1NotIn(List<String> values) {
            addCriterion("card_pic1 not in", values, "cardPic1");
            return (Criteria) this;
        }

        public Criteria andCardPic1Between(String value1, String value2) {
            addCriterion("card_pic1 between", value1, value2, "cardPic1");
            return (Criteria) this;
        }

        public Criteria andCardPic1NotBetween(String value1, String value2) {
            addCriterion("card_pic1 not between", value1, value2, "cardPic1");
            return (Criteria) this;
        }

        public Criteria andCardPic2IsNull() {
            addCriterion("card_pic2 is null");
            return (Criteria) this;
        }

        public Criteria andCardPic2IsNotNull() {
            addCriterion("card_pic2 is not null");
            return (Criteria) this;
        }

        public Criteria andCardPic2EqualTo(String value) {
            addCriterion("card_pic2 =", value, "cardPic2");
            return (Criteria) this;
        }

        public Criteria andCardPic2NotEqualTo(String value) {
            addCriterion("card_pic2 <>", value, "cardPic2");
            return (Criteria) this;
        }

        public Criteria andCardPic2GreaterThan(String value) {
            addCriterion("card_pic2 >", value, "cardPic2");
            return (Criteria) this;
        }

        public Criteria andCardPic2GreaterThanOrEqualTo(String value) {
            addCriterion("card_pic2 >=", value, "cardPic2");
            return (Criteria) this;
        }

        public Criteria andCardPic2LessThan(String value) {
            addCriterion("card_pic2 <", value, "cardPic2");
            return (Criteria) this;
        }

        public Criteria andCardPic2LessThanOrEqualTo(String value) {
            addCriterion("card_pic2 <=", value, "cardPic2");
            return (Criteria) this;
        }

        public Criteria andCardPic2Like(String value) {
            addCriterion("card_pic2 like", value, "cardPic2");
            return (Criteria) this;
        }

        public Criteria andCardPic2NotLike(String value) {
            addCriterion("card_pic2 not like", value, "cardPic2");
            return (Criteria) this;
        }

        public Criteria andCardPic2In(List<String> values) {
            addCriterion("card_pic2 in", values, "cardPic2");
            return (Criteria) this;
        }

        public Criteria andCardPic2NotIn(List<String> values) {
            addCriterion("card_pic2 not in", values, "cardPic2");
            return (Criteria) this;
        }

        public Criteria andCardPic2Between(String value1, String value2) {
            addCriterion("card_pic2 between", value1, value2, "cardPic2");
            return (Criteria) this;
        }

        public Criteria andCardPic2NotBetween(String value1, String value2) {
            addCriterion("card_pic2 not between", value1, value2, "cardPic2");
            return (Criteria) this;
        }

        public Criteria andBusLicenceNbrIsNull() {
            addCriterion("bus_licence_nbr is null");
            return (Criteria) this;
        }

        public Criteria andBusLicenceNbrIsNotNull() {
            addCriterion("bus_licence_nbr is not null");
            return (Criteria) this;
        }

        public Criteria andBusLicenceNbrEqualTo(String value) {
            addCriterion("bus_licence_nbr =", value, "busLicenceNbr");
            return (Criteria) this;
        }

        public Criteria andBusLicenceNbrNotEqualTo(String value) {
            addCriterion("bus_licence_nbr <>", value, "busLicenceNbr");
            return (Criteria) this;
        }

        public Criteria andBusLicenceNbrGreaterThan(String value) {
            addCriterion("bus_licence_nbr >", value, "busLicenceNbr");
            return (Criteria) this;
        }

        public Criteria andBusLicenceNbrGreaterThanOrEqualTo(String value) {
            addCriterion("bus_licence_nbr >=", value, "busLicenceNbr");
            return (Criteria) this;
        }

        public Criteria andBusLicenceNbrLessThan(String value) {
            addCriterion("bus_licence_nbr <", value, "busLicenceNbr");
            return (Criteria) this;
        }

        public Criteria andBusLicenceNbrLessThanOrEqualTo(String value) {
            addCriterion("bus_licence_nbr <=", value, "busLicenceNbr");
            return (Criteria) this;
        }

        public Criteria andBusLicenceNbrLike(String value) {
            addCriterion("bus_licence_nbr like", value, "busLicenceNbr");
            return (Criteria) this;
        }

        public Criteria andBusLicenceNbrNotLike(String value) {
            addCriterion("bus_licence_nbr not like", value, "busLicenceNbr");
            return (Criteria) this;
        }

        public Criteria andBusLicenceNbrIn(List<String> values) {
            addCriterion("bus_licence_nbr in", values, "busLicenceNbr");
            return (Criteria) this;
        }

        public Criteria andBusLicenceNbrNotIn(List<String> values) {
            addCriterion("bus_licence_nbr not in", values, "busLicenceNbr");
            return (Criteria) this;
        }

        public Criteria andBusLicenceNbrBetween(String value1, String value2) {
            addCriterion("bus_licence_nbr between", value1, value2, "busLicenceNbr");
            return (Criteria) this;
        }

        public Criteria andBusLicenceNbrNotBetween(String value1, String value2) {
            addCriterion("bus_licence_nbr not between", value1, value2, "busLicenceNbr");
            return (Criteria) this;
        }

        public Criteria andAddrIsNull() {
            addCriterion("addr is null");
            return (Criteria) this;
        }

        public Criteria andAddrIsNotNull() {
            addCriterion("addr is not null");
            return (Criteria) this;
        }

        public Criteria andAddrEqualTo(String value) {
            addCriterion("addr =", value, "addr");
            return (Criteria) this;
        }

        public Criteria andAddrNotEqualTo(String value) {
            addCriterion("addr <>", value, "addr");
            return (Criteria) this;
        }

        public Criteria andAddrGreaterThan(String value) {
            addCriterion("addr >", value, "addr");
            return (Criteria) this;
        }

        public Criteria andAddrGreaterThanOrEqualTo(String value) {
            addCriterion("addr >=", value, "addr");
            return (Criteria) this;
        }

        public Criteria andAddrLessThan(String value) {
            addCriterion("addr <", value, "addr");
            return (Criteria) this;
        }

        public Criteria andAddrLessThanOrEqualTo(String value) {
            addCriterion("addr <=", value, "addr");
            return (Criteria) this;
        }

        public Criteria andAddrLike(String value) {
            addCriterion("addr like", value, "addr");
            return (Criteria) this;
        }

        public Criteria andAddrNotLike(String value) {
            addCriterion("addr not like", value, "addr");
            return (Criteria) this;
        }

        public Criteria andAddrIn(List<String> values) {
            addCriterion("addr in", values, "addr");
            return (Criteria) this;
        }

        public Criteria andAddrNotIn(List<String> values) {
            addCriterion("addr not in", values, "addr");
            return (Criteria) this;
        }

        public Criteria andAddrBetween(String value1, String value2) {
            addCriterion("addr between", value1, value2, "addr");
            return (Criteria) this;
        }

        public Criteria andAddrNotBetween(String value1, String value2) {
            addCriterion("addr not between", value1, value2, "addr");
            return (Criteria) this;
        }

        public Criteria andCategoryIsNull() {
            addCriterion("category is null");
            return (Criteria) this;
        }

        public Criteria andCategoryIsNotNull() {
            addCriterion("category is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryEqualTo(String value) {
            addCriterion("category =", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotEqualTo(String value) {
            addCriterion("category <>", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThan(String value) {
            addCriterion("category >", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThanOrEqualTo(String value) {
            addCriterion("category >=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThan(String value) {
            addCriterion("category <", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThanOrEqualTo(String value) {
            addCriterion("category <=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLike(String value) {
            addCriterion("category like", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotLike(String value) {
            addCriterion("category not like", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryIn(List<String> values) {
            addCriterion("category in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotIn(List<String> values) {
            addCriterion("category not in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryBetween(String value1, String value2) {
            addCriterion("category between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotBetween(String value1, String value2) {
            addCriterion("category not between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andProvinceIsNull() {
            addCriterion("province is null");
            return (Criteria) this;
        }

        public Criteria andProvinceIsNotNull() {
            addCriterion("province is not null");
            return (Criteria) this;
        }

        public Criteria andProvinceEqualTo(String value) {
            addCriterion("province =", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotEqualTo(String value) {
            addCriterion("province <>", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThan(String value) {
            addCriterion("province >", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThanOrEqualTo(String value) {
            addCriterion("province >=", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLessThan(String value) {
            addCriterion("province <", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLessThanOrEqualTo(String value) {
            addCriterion("province <=", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLike(String value) {
            addCriterion("province like", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotLike(String value) {
            addCriterion("province not like", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceIn(List<String> values) {
            addCriterion("province in", values, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotIn(List<String> values) {
            addCriterion("province not in", values, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceBetween(String value1, String value2) {
            addCriterion("province between", value1, value2, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotBetween(String value1, String value2) {
            addCriterion("province not between", value1, value2, "province");
            return (Criteria) this;
        }

        public Criteria andCityIsNull() {
            addCriterion("city is null");
            return (Criteria) this;
        }

        public Criteria andCityIsNotNull() {
            addCriterion("city is not null");
            return (Criteria) this;
        }

        public Criteria andCityEqualTo(String value) {
            addCriterion("city =", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotEqualTo(String value) {
            addCriterion("city <>", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThan(String value) {
            addCriterion("city >", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThanOrEqualTo(String value) {
            addCriterion("city >=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThan(String value) {
            addCriterion("city <", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThanOrEqualTo(String value) {
            addCriterion("city <=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLike(String value) {
            addCriterion("city like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotLike(String value) {
            addCriterion("city not like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityIn(List<String> values) {
            addCriterion("city in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotIn(List<String> values) {
            addCriterion("city not in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityBetween(String value1, String value2) {
            addCriterion("city between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotBetween(String value1, String value2) {
            addCriterion("city not between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andCountyIsNull() {
            addCriterion("county is null");
            return (Criteria) this;
        }

        public Criteria andCountyIsNotNull() {
            addCriterion("county is not null");
            return (Criteria) this;
        }

        public Criteria andCountyEqualTo(String value) {
            addCriterion("county =", value, "county");
            return (Criteria) this;
        }

        public Criteria andCountyNotEqualTo(String value) {
            addCriterion("county <>", value, "county");
            return (Criteria) this;
        }

        public Criteria andCountyGreaterThan(String value) {
            addCriterion("county >", value, "county");
            return (Criteria) this;
        }

        public Criteria andCountyGreaterThanOrEqualTo(String value) {
            addCriterion("county >=", value, "county");
            return (Criteria) this;
        }

        public Criteria andCountyLessThan(String value) {
            addCriterion("county <", value, "county");
            return (Criteria) this;
        }

        public Criteria andCountyLessThanOrEqualTo(String value) {
            addCriterion("county <=", value, "county");
            return (Criteria) this;
        }

        public Criteria andCountyLike(String value) {
            addCriterion("county like", value, "county");
            return (Criteria) this;
        }

        public Criteria andCountyNotLike(String value) {
            addCriterion("county not like", value, "county");
            return (Criteria) this;
        }

        public Criteria andCountyIn(List<String> values) {
            addCriterion("county in", values, "county");
            return (Criteria) this;
        }

        public Criteria andCountyNotIn(List<String> values) {
            addCriterion("county not in", values, "county");
            return (Criteria) this;
        }

        public Criteria andCountyBetween(String value1, String value2) {
            addCriterion("county between", value1, value2, "county");
            return (Criteria) this;
        }

        public Criteria andCountyNotBetween(String value1, String value2) {
            addCriterion("county not between", value1, value2, "county");
            return (Criteria) this;
        }

        public Criteria andContactTypeIsNull() {
            addCriterion("contact_type is null");
            return (Criteria) this;
        }

        public Criteria andContactTypeIsNotNull() {
            addCriterion("contact_type is not null");
            return (Criteria) this;
        }

        public Criteria andContactTypeEqualTo(String value) {
            addCriterion("contact_type =", value, "contactType");
            return (Criteria) this;
        }

        public Criteria andContactTypeNotEqualTo(String value) {
            addCriterion("contact_type <>", value, "contactType");
            return (Criteria) this;
        }

        public Criteria andContactTypeGreaterThan(String value) {
            addCriterion("contact_type >", value, "contactType");
            return (Criteria) this;
        }

        public Criteria andContactTypeGreaterThanOrEqualTo(String value) {
            addCriterion("contact_type >=", value, "contactType");
            return (Criteria) this;
        }

        public Criteria andContactTypeLessThan(String value) {
            addCriterion("contact_type <", value, "contactType");
            return (Criteria) this;
        }

        public Criteria andContactTypeLessThanOrEqualTo(String value) {
            addCriterion("contact_type <=", value, "contactType");
            return (Criteria) this;
        }

        public Criteria andContactTypeLike(String value) {
            addCriterion("contact_type like", value, "contactType");
            return (Criteria) this;
        }

        public Criteria andContactTypeNotLike(String value) {
            addCriterion("contact_type not like", value, "contactType");
            return (Criteria) this;
        }

        public Criteria andContactTypeIn(List<String> values) {
            addCriterion("contact_type in", values, "contactType");
            return (Criteria) this;
        }

        public Criteria andContactTypeNotIn(List<String> values) {
            addCriterion("contact_type not in", values, "contactType");
            return (Criteria) this;
        }

        public Criteria andContactTypeBetween(String value1, String value2) {
            addCriterion("contact_type between", value1, value2, "contactType");
            return (Criteria) this;
        }

        public Criteria andContactTypeNotBetween(String value1, String value2) {
            addCriterion("contact_type not between", value1, value2, "contactType");
            return (Criteria) this;
        }

        public Criteria andVerifyFlagIsNull() {
            addCriterion("verify_flag is null");
            return (Criteria) this;
        }

        public Criteria andVerifyFlagIsNotNull() {
            addCriterion("verify_flag is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyFlagEqualTo(String value) {
            addCriterion("verify_flag =", value, "verifyFlag");
            return (Criteria) this;
        }

        public Criteria andVerifyFlagNotEqualTo(String value) {
            addCriterion("verify_flag <>", value, "verifyFlag");
            return (Criteria) this;
        }

        public Criteria andVerifyFlagGreaterThan(String value) {
            addCriterion("verify_flag >", value, "verifyFlag");
            return (Criteria) this;
        }

        public Criteria andVerifyFlagGreaterThanOrEqualTo(String value) {
            addCriterion("verify_flag >=", value, "verifyFlag");
            return (Criteria) this;
        }

        public Criteria andVerifyFlagLessThan(String value) {
            addCriterion("verify_flag <", value, "verifyFlag");
            return (Criteria) this;
        }

        public Criteria andVerifyFlagLessThanOrEqualTo(String value) {
            addCriterion("verify_flag <=", value, "verifyFlag");
            return (Criteria) this;
        }

        public Criteria andVerifyFlagLike(String value) {
            addCriterion("verify_flag like", value, "verifyFlag");
            return (Criteria) this;
        }

        public Criteria andVerifyFlagNotLike(String value) {
            addCriterion("verify_flag not like", value, "verifyFlag");
            return (Criteria) this;
        }

        public Criteria andVerifyFlagIn(List<String> values) {
            addCriterion("verify_flag in", values, "verifyFlag");
            return (Criteria) this;
        }

        public Criteria andVerifyFlagNotIn(List<String> values) {
            addCriterion("verify_flag not in", values, "verifyFlag");
            return (Criteria) this;
        }

        public Criteria andVerifyFlagBetween(String value1, String value2) {
            addCriterion("verify_flag between", value1, value2, "verifyFlag");
            return (Criteria) this;
        }

        public Criteria andVerifyFlagNotBetween(String value1, String value2) {
            addCriterion("verify_flag not between", value1, value2, "verifyFlag");
            return (Criteria) this;
        }

        public Criteria andWxRouteIdIsNull() {
            addCriterion("wx_route_id is null");
            return (Criteria) this;
        }

        public Criteria andWxRouteIdIsNotNull() {
            addCriterion("wx_route_id is not null");
            return (Criteria) this;
        }

        public Criteria andWxRouteIdEqualTo(String value) {
            addCriterion("wx_route_id =", value, "wxRouteId");
            return (Criteria) this;
        }

        public Criteria andWxRouteIdNotEqualTo(String value) {
            addCriterion("wx_route_id <>", value, "wxRouteId");
            return (Criteria) this;
        }

        public Criteria andWxRouteIdGreaterThan(String value) {
            addCriterion("wx_route_id >", value, "wxRouteId");
            return (Criteria) this;
        }

        public Criteria andWxRouteIdGreaterThanOrEqualTo(String value) {
            addCriterion("wx_route_id >=", value, "wxRouteId");
            return (Criteria) this;
        }

        public Criteria andWxRouteIdLessThan(String value) {
            addCriterion("wx_route_id <", value, "wxRouteId");
            return (Criteria) this;
        }

        public Criteria andWxRouteIdLessThanOrEqualTo(String value) {
            addCriterion("wx_route_id <=", value, "wxRouteId");
            return (Criteria) this;
        }

        public Criteria andWxRouteIdLike(String value) {
            addCriterion("wx_route_id like", value, "wxRouteId");
            return (Criteria) this;
        }

        public Criteria andWxRouteIdNotLike(String value) {
            addCriterion("wx_route_id not like", value, "wxRouteId");
            return (Criteria) this;
        }

        public Criteria andWxRouteIdIn(List<String> values) {
            addCriterion("wx_route_id in", values, "wxRouteId");
            return (Criteria) this;
        }

        public Criteria andWxRouteIdNotIn(List<String> values) {
            addCriterion("wx_route_id not in", values, "wxRouteId");
            return (Criteria) this;
        }

        public Criteria andWxRouteIdBetween(String value1, String value2) {
            addCriterion("wx_route_id between", value1, value2, "wxRouteId");
            return (Criteria) this;
        }

        public Criteria andWxRouteIdNotBetween(String value1, String value2) {
            addCriterion("wx_route_id not between", value1, value2, "wxRouteId");
            return (Criteria) this;
        }

        public Criteria andWxMemberCodeIsNull() {
            addCriterion("wx_member_code is null");
            return (Criteria) this;
        }

        public Criteria andWxMemberCodeIsNotNull() {
            addCriterion("wx_member_code is not null");
            return (Criteria) this;
        }

        public Criteria andWxMemberCodeEqualTo(String value) {
            addCriterion("wx_member_code =", value, "wxMemberCode");
            return (Criteria) this;
        }

        public Criteria andWxMemberCodeNotEqualTo(String value) {
            addCriterion("wx_member_code <>", value, "wxMemberCode");
            return (Criteria) this;
        }

        public Criteria andWxMemberCodeGreaterThan(String value) {
            addCriterion("wx_member_code >", value, "wxMemberCode");
            return (Criteria) this;
        }

        public Criteria andWxMemberCodeGreaterThanOrEqualTo(String value) {
            addCriterion("wx_member_code >=", value, "wxMemberCode");
            return (Criteria) this;
        }

        public Criteria andWxMemberCodeLessThan(String value) {
            addCriterion("wx_member_code <", value, "wxMemberCode");
            return (Criteria) this;
        }

        public Criteria andWxMemberCodeLessThanOrEqualTo(String value) {
            addCriterion("wx_member_code <=", value, "wxMemberCode");
            return (Criteria) this;
        }

        public Criteria andWxMemberCodeLike(String value) {
            addCriterion("wx_member_code like", value, "wxMemberCode");
            return (Criteria) this;
        }

        public Criteria andWxMemberCodeNotLike(String value) {
            addCriterion("wx_member_code not like", value, "wxMemberCode");
            return (Criteria) this;
        }

        public Criteria andWxMemberCodeIn(List<String> values) {
            addCriterion("wx_member_code in", values, "wxMemberCode");
            return (Criteria) this;
        }

        public Criteria andWxMemberCodeNotIn(List<String> values) {
            addCriterion("wx_member_code not in", values, "wxMemberCode");
            return (Criteria) this;
        }

        public Criteria andWxMemberCodeBetween(String value1, String value2) {
            addCriterion("wx_member_code between", value1, value2, "wxMemberCode");
            return (Criteria) this;
        }

        public Criteria andWxMemberCodeNotBetween(String value1, String value2) {
            addCriterion("wx_member_code not between", value1, value2, "wxMemberCode");
            return (Criteria) this;
        }

        public Criteria andWxMerchantCodeIsNull() {
            addCriterion("wx_merchant_code is null");
            return (Criteria) this;
        }

        public Criteria andWxMerchantCodeIsNotNull() {
            addCriterion("wx_merchant_code is not null");
            return (Criteria) this;
        }

        public Criteria andWxMerchantCodeEqualTo(String value) {
            addCriterion("wx_merchant_code =", value, "wxMerchantCode");
            return (Criteria) this;
        }

        public Criteria andWxMerchantCodeNotEqualTo(String value) {
            addCriterion("wx_merchant_code <>", value, "wxMerchantCode");
            return (Criteria) this;
        }

        public Criteria andWxMerchantCodeGreaterThan(String value) {
            addCriterion("wx_merchant_code >", value, "wxMerchantCode");
            return (Criteria) this;
        }

        public Criteria andWxMerchantCodeGreaterThanOrEqualTo(String value) {
            addCriterion("wx_merchant_code >=", value, "wxMerchantCode");
            return (Criteria) this;
        }

        public Criteria andWxMerchantCodeLessThan(String value) {
            addCriterion("wx_merchant_code <", value, "wxMerchantCode");
            return (Criteria) this;
        }

        public Criteria andWxMerchantCodeLessThanOrEqualTo(String value) {
            addCriterion("wx_merchant_code <=", value, "wxMerchantCode");
            return (Criteria) this;
        }

        public Criteria andWxMerchantCodeLike(String value) {
            addCriterion("wx_merchant_code like", value, "wxMerchantCode");
            return (Criteria) this;
        }

        public Criteria andWxMerchantCodeNotLike(String value) {
            addCriterion("wx_merchant_code not like", value, "wxMerchantCode");
            return (Criteria) this;
        }

        public Criteria andWxMerchantCodeIn(List<String> values) {
            addCriterion("wx_merchant_code in", values, "wxMerchantCode");
            return (Criteria) this;
        }

        public Criteria andWxMerchantCodeNotIn(List<String> values) {
            addCriterion("wx_merchant_code not in", values, "wxMerchantCode");
            return (Criteria) this;
        }

        public Criteria andWxMerchantCodeBetween(String value1, String value2) {
            addCriterion("wx_merchant_code between", value1, value2, "wxMerchantCode");
            return (Criteria) this;
        }

        public Criteria andWxMerchantCodeNotBetween(String value1, String value2) {
            addCriterion("wx_merchant_code not between", value1, value2, "wxMerchantCode");
            return (Criteria) this;
        }

        public Criteria andSettleTypeIsNull() {
            addCriterion("settle_type is null");
            return (Criteria) this;
        }

        public Criteria andSettleTypeIsNotNull() {
            addCriterion("settle_type is not null");
            return (Criteria) this;
        }

        public Criteria andSettleTypeEqualTo(String value) {
            addCriterion("settle_type =", value, "settleType");
            return (Criteria) this;
        }

        public Criteria andSettleTypeNotEqualTo(String value) {
            addCriterion("settle_type <>", value, "settleType");
            return (Criteria) this;
        }

        public Criteria andSettleTypeGreaterThan(String value) {
            addCriterion("settle_type >", value, "settleType");
            return (Criteria) this;
        }

        public Criteria andSettleTypeGreaterThanOrEqualTo(String value) {
            addCriterion("settle_type >=", value, "settleType");
            return (Criteria) this;
        }

        public Criteria andSettleTypeLessThan(String value) {
            addCriterion("settle_type <", value, "settleType");
            return (Criteria) this;
        }

        public Criteria andSettleTypeLessThanOrEqualTo(String value) {
            addCriterion("settle_type <=", value, "settleType");
            return (Criteria) this;
        }

        public Criteria andSettleTypeLike(String value) {
            addCriterion("settle_type like", value, "settleType");
            return (Criteria) this;
        }

        public Criteria andSettleTypeNotLike(String value) {
            addCriterion("settle_type not like", value, "settleType");
            return (Criteria) this;
        }

        public Criteria andSettleTypeIn(List<String> values) {
            addCriterion("settle_type in", values, "settleType");
            return (Criteria) this;
        }

        public Criteria andSettleTypeNotIn(List<String> values) {
            addCriterion("settle_type not in", values, "settleType");
            return (Criteria) this;
        }

        public Criteria andSettleTypeBetween(String value1, String value2) {
            addCriterion("settle_type between", value1, value2, "settleType");
            return (Criteria) this;
        }

        public Criteria andSettleTypeNotBetween(String value1, String value2) {
            addCriterion("settle_type not between", value1, value2, "settleType");
            return (Criteria) this;
        }

        public Criteria andZfbRouteIdIsNull() {
            addCriterion("zfb_route_id is null");
            return (Criteria) this;
        }

        public Criteria andZfbRouteIdIsNotNull() {
            addCriterion("zfb_route_id is not null");
            return (Criteria) this;
        }

        public Criteria andZfbRouteIdEqualTo(String value) {
            addCriterion("zfb_route_id =", value, "zfbRouteId");
            return (Criteria) this;
        }

        public Criteria andZfbRouteIdNotEqualTo(String value) {
            addCriterion("zfb_route_id <>", value, "zfbRouteId");
            return (Criteria) this;
        }

        public Criteria andZfbRouteIdGreaterThan(String value) {
            addCriterion("zfb_route_id >", value, "zfbRouteId");
            return (Criteria) this;
        }

        public Criteria andZfbRouteIdGreaterThanOrEqualTo(String value) {
            addCriterion("zfb_route_id >=", value, "zfbRouteId");
            return (Criteria) this;
        }

        public Criteria andZfbRouteIdLessThan(String value) {
            addCriterion("zfb_route_id <", value, "zfbRouteId");
            return (Criteria) this;
        }

        public Criteria andZfbRouteIdLessThanOrEqualTo(String value) {
            addCriterion("zfb_route_id <=", value, "zfbRouteId");
            return (Criteria) this;
        }

        public Criteria andZfbRouteIdLike(String value) {
            addCriterion("zfb_route_id like", value, "zfbRouteId");
            return (Criteria) this;
        }

        public Criteria andZfbRouteIdNotLike(String value) {
            addCriterion("zfb_route_id not like", value, "zfbRouteId");
            return (Criteria) this;
        }

        public Criteria andZfbRouteIdIn(List<String> values) {
            addCriterion("zfb_route_id in", values, "zfbRouteId");
            return (Criteria) this;
        }

        public Criteria andZfbRouteIdNotIn(List<String> values) {
            addCriterion("zfb_route_id not in", values, "zfbRouteId");
            return (Criteria) this;
        }

        public Criteria andZfbRouteIdBetween(String value1, String value2) {
            addCriterion("zfb_route_id between", value1, value2, "zfbRouteId");
            return (Criteria) this;
        }

        public Criteria andZfbRouteIdNotBetween(String value1, String value2) {
            addCriterion("zfb_route_id not between", value1, value2, "zfbRouteId");
            return (Criteria) this;
        }

        public Criteria andZfbMemberCodeIsNull() {
            addCriterion("zfb_member_code is null");
            return (Criteria) this;
        }

        public Criteria andZfbMemberCodeIsNotNull() {
            addCriterion("zfb_member_code is not null");
            return (Criteria) this;
        }

        public Criteria andZfbMemberCodeEqualTo(String value) {
            addCriterion("zfb_member_code =", value, "zfbMemberCode");
            return (Criteria) this;
        }

        public Criteria andZfbMemberCodeNotEqualTo(String value) {
            addCriterion("zfb_member_code <>", value, "zfbMemberCode");
            return (Criteria) this;
        }

        public Criteria andZfbMemberCodeGreaterThan(String value) {
            addCriterion("zfb_member_code >", value, "zfbMemberCode");
            return (Criteria) this;
        }

        public Criteria andZfbMemberCodeGreaterThanOrEqualTo(String value) {
            addCriterion("zfb_member_code >=", value, "zfbMemberCode");
            return (Criteria) this;
        }

        public Criteria andZfbMemberCodeLessThan(String value) {
            addCriterion("zfb_member_code <", value, "zfbMemberCode");
            return (Criteria) this;
        }

        public Criteria andZfbMemberCodeLessThanOrEqualTo(String value) {
            addCriterion("zfb_member_code <=", value, "zfbMemberCode");
            return (Criteria) this;
        }

        public Criteria andZfbMemberCodeLike(String value) {
            addCriterion("zfb_member_code like", value, "zfbMemberCode");
            return (Criteria) this;
        }

        public Criteria andZfbMemberCodeNotLike(String value) {
            addCriterion("zfb_member_code not like", value, "zfbMemberCode");
            return (Criteria) this;
        }

        public Criteria andZfbMemberCodeIn(List<String> values) {
            addCriterion("zfb_member_code in", values, "zfbMemberCode");
            return (Criteria) this;
        }

        public Criteria andZfbMemberCodeNotIn(List<String> values) {
            addCriterion("zfb_member_code not in", values, "zfbMemberCode");
            return (Criteria) this;
        }

        public Criteria andZfbMemberCodeBetween(String value1, String value2) {
            addCriterion("zfb_member_code between", value1, value2, "zfbMemberCode");
            return (Criteria) this;
        }

        public Criteria andZfbMemberCodeNotBetween(String value1, String value2) {
            addCriterion("zfb_member_code not between", value1, value2, "zfbMemberCode");
            return (Criteria) this;
        }

        public Criteria andZfbMerchantCodeIsNull() {
            addCriterion("zfb_merchant_code is null");
            return (Criteria) this;
        }

        public Criteria andZfbMerchantCodeIsNotNull() {
            addCriterion("zfb_merchant_code is not null");
            return (Criteria) this;
        }

        public Criteria andZfbMerchantCodeEqualTo(String value) {
            addCriterion("zfb_merchant_code =", value, "zfbMerchantCode");
            return (Criteria) this;
        }

        public Criteria andZfbMerchantCodeNotEqualTo(String value) {
            addCriterion("zfb_merchant_code <>", value, "zfbMerchantCode");
            return (Criteria) this;
        }

        public Criteria andZfbMerchantCodeGreaterThan(String value) {
            addCriterion("zfb_merchant_code >", value, "zfbMerchantCode");
            return (Criteria) this;
        }

        public Criteria andZfbMerchantCodeGreaterThanOrEqualTo(String value) {
            addCriterion("zfb_merchant_code >=", value, "zfbMerchantCode");
            return (Criteria) this;
        }

        public Criteria andZfbMerchantCodeLessThan(String value) {
            addCriterion("zfb_merchant_code <", value, "zfbMerchantCode");
            return (Criteria) this;
        }

        public Criteria andZfbMerchantCodeLessThanOrEqualTo(String value) {
            addCriterion("zfb_merchant_code <=", value, "zfbMerchantCode");
            return (Criteria) this;
        }

        public Criteria andZfbMerchantCodeLike(String value) {
            addCriterion("zfb_merchant_code like", value, "zfbMerchantCode");
            return (Criteria) this;
        }

        public Criteria andZfbMerchantCodeNotLike(String value) {
            addCriterion("zfb_merchant_code not like", value, "zfbMerchantCode");
            return (Criteria) this;
        }

        public Criteria andZfbMerchantCodeIn(List<String> values) {
            addCriterion("zfb_merchant_code in", values, "zfbMerchantCode");
            return (Criteria) this;
        }

        public Criteria andZfbMerchantCodeNotIn(List<String> values) {
            addCriterion("zfb_merchant_code not in", values, "zfbMerchantCode");
            return (Criteria) this;
        }

        public Criteria andZfbMerchantCodeBetween(String value1, String value2) {
            addCriterion("zfb_merchant_code between", value1, value2, "zfbMerchantCode");
            return (Criteria) this;
        }

        public Criteria andZfbMerchantCodeNotBetween(String value1, String value2) {
            addCriterion("zfb_merchant_code not between", value1, value2, "zfbMerchantCode");
            return (Criteria) this;
        }

        public Criteria andQqRouteIdIsNull() {
            addCriterion("qq_route_id is null");
            return (Criteria) this;
        }

        public Criteria andQqRouteIdIsNotNull() {
            addCriterion("qq_route_id is not null");
            return (Criteria) this;
        }

        public Criteria andQqRouteIdEqualTo(String value) {
            addCriterion("qq_route_id =", value, "qqRouteId");
            return (Criteria) this;
        }

        public Criteria andQqRouteIdNotEqualTo(String value) {
            addCriterion("qq_route_id <>", value, "qqRouteId");
            return (Criteria) this;
        }

        public Criteria andQqRouteIdGreaterThan(String value) {
            addCriterion("qq_route_id >", value, "qqRouteId");
            return (Criteria) this;
        }

        public Criteria andQqRouteIdGreaterThanOrEqualTo(String value) {
            addCriterion("qq_route_id >=", value, "qqRouteId");
            return (Criteria) this;
        }

        public Criteria andQqRouteIdLessThan(String value) {
            addCriterion("qq_route_id <", value, "qqRouteId");
            return (Criteria) this;
        }

        public Criteria andQqRouteIdLessThanOrEqualTo(String value) {
            addCriterion("qq_route_id <=", value, "qqRouteId");
            return (Criteria) this;
        }

        public Criteria andQqRouteIdLike(String value) {
            addCriterion("qq_route_id like", value, "qqRouteId");
            return (Criteria) this;
        }

        public Criteria andQqRouteIdNotLike(String value) {
            addCriterion("qq_route_id not like", value, "qqRouteId");
            return (Criteria) this;
        }

        public Criteria andQqRouteIdIn(List<String> values) {
            addCriterion("qq_route_id in", values, "qqRouteId");
            return (Criteria) this;
        }

        public Criteria andQqRouteIdNotIn(List<String> values) {
            addCriterion("qq_route_id not in", values, "qqRouteId");
            return (Criteria) this;
        }

        public Criteria andQqRouteIdBetween(String value1, String value2) {
            addCriterion("qq_route_id between", value1, value2, "qqRouteId");
            return (Criteria) this;
        }

        public Criteria andQqRouteIdNotBetween(String value1, String value2) {
            addCriterion("qq_route_id not between", value1, value2, "qqRouteId");
            return (Criteria) this;
        }

        public Criteria andQqMemberCodeIsNull() {
            addCriterion("qq_member_code is null");
            return (Criteria) this;
        }

        public Criteria andQqMemberCodeIsNotNull() {
            addCriterion("qq_member_code is not null");
            return (Criteria) this;
        }

        public Criteria andQqMemberCodeEqualTo(String value) {
            addCriterion("qq_member_code =", value, "qqMemberCode");
            return (Criteria) this;
        }

        public Criteria andQqMemberCodeNotEqualTo(String value) {
            addCriterion("qq_member_code <>", value, "qqMemberCode");
            return (Criteria) this;
        }

        public Criteria andQqMemberCodeGreaterThan(String value) {
            addCriterion("qq_member_code >", value, "qqMemberCode");
            return (Criteria) this;
        }

        public Criteria andQqMemberCodeGreaterThanOrEqualTo(String value) {
            addCriterion("qq_member_code >=", value, "qqMemberCode");
            return (Criteria) this;
        }

        public Criteria andQqMemberCodeLessThan(String value) {
            addCriterion("qq_member_code <", value, "qqMemberCode");
            return (Criteria) this;
        }

        public Criteria andQqMemberCodeLessThanOrEqualTo(String value) {
            addCriterion("qq_member_code <=", value, "qqMemberCode");
            return (Criteria) this;
        }

        public Criteria andQqMemberCodeLike(String value) {
            addCriterion("qq_member_code like", value, "qqMemberCode");
            return (Criteria) this;
        }

        public Criteria andQqMemberCodeNotLike(String value) {
            addCriterion("qq_member_code not like", value, "qqMemberCode");
            return (Criteria) this;
        }

        public Criteria andQqMemberCodeIn(List<String> values) {
            addCriterion("qq_member_code in", values, "qqMemberCode");
            return (Criteria) this;
        }

        public Criteria andQqMemberCodeNotIn(List<String> values) {
            addCriterion("qq_member_code not in", values, "qqMemberCode");
            return (Criteria) this;
        }

        public Criteria andQqMemberCodeBetween(String value1, String value2) {
            addCriterion("qq_member_code between", value1, value2, "qqMemberCode");
            return (Criteria) this;
        }

        public Criteria andQqMemberCodeNotBetween(String value1, String value2) {
            addCriterion("qq_member_code not between", value1, value2, "qqMemberCode");
            return (Criteria) this;
        }

        public Criteria andQqMerchantCodeIsNull() {
            addCriterion("qq_merchant_code is null");
            return (Criteria) this;
        }

        public Criteria andQqMerchantCodeIsNotNull() {
            addCriterion("qq_merchant_code is not null");
            return (Criteria) this;
        }

        public Criteria andQqMerchantCodeEqualTo(String value) {
            addCriterion("qq_merchant_code =", value, "qqMerchantCode");
            return (Criteria) this;
        }

        public Criteria andQqMerchantCodeNotEqualTo(String value) {
            addCriterion("qq_merchant_code <>", value, "qqMerchantCode");
            return (Criteria) this;
        }

        public Criteria andQqMerchantCodeGreaterThan(String value) {
            addCriterion("qq_merchant_code >", value, "qqMerchantCode");
            return (Criteria) this;
        }

        public Criteria andQqMerchantCodeGreaterThanOrEqualTo(String value) {
            addCriterion("qq_merchant_code >=", value, "qqMerchantCode");
            return (Criteria) this;
        }

        public Criteria andQqMerchantCodeLessThan(String value) {
            addCriterion("qq_merchant_code <", value, "qqMerchantCode");
            return (Criteria) this;
        }

        public Criteria andQqMerchantCodeLessThanOrEqualTo(String value) {
            addCriterion("qq_merchant_code <=", value, "qqMerchantCode");
            return (Criteria) this;
        }

        public Criteria andQqMerchantCodeLike(String value) {
            addCriterion("qq_merchant_code like", value, "qqMerchantCode");
            return (Criteria) this;
        }

        public Criteria andQqMerchantCodeNotLike(String value) {
            addCriterion("qq_merchant_code not like", value, "qqMerchantCode");
            return (Criteria) this;
        }

        public Criteria andQqMerchantCodeIn(List<String> values) {
            addCriterion("qq_merchant_code in", values, "qqMerchantCode");
            return (Criteria) this;
        }

        public Criteria andQqMerchantCodeNotIn(List<String> values) {
            addCriterion("qq_merchant_code not in", values, "qqMerchantCode");
            return (Criteria) this;
        }

        public Criteria andQqMerchantCodeBetween(String value1, String value2) {
            addCriterion("qq_merchant_code between", value1, value2, "qqMerchantCode");
            return (Criteria) this;
        }

        public Criteria andQqMerchantCodeNotBetween(String value1, String value2) {
            addCriterion("qq_merchant_code not between", value1, value2, "qqMerchantCode");
            return (Criteria) this;
        }

        public Criteria andBdRouteIdIsNull() {
            addCriterion("bd_route_id is null");
            return (Criteria) this;
        }

        public Criteria andBdRouteIdIsNotNull() {
            addCriterion("bd_route_id is not null");
            return (Criteria) this;
        }

        public Criteria andBdRouteIdEqualTo(String value) {
            addCriterion("bd_route_id =", value, "bdRouteId");
            return (Criteria) this;
        }

        public Criteria andBdRouteIdNotEqualTo(String value) {
            addCriterion("bd_route_id <>", value, "bdRouteId");
            return (Criteria) this;
        }

        public Criteria andBdRouteIdGreaterThan(String value) {
            addCriterion("bd_route_id >", value, "bdRouteId");
            return (Criteria) this;
        }

        public Criteria andBdRouteIdGreaterThanOrEqualTo(String value) {
            addCriterion("bd_route_id >=", value, "bdRouteId");
            return (Criteria) this;
        }

        public Criteria andBdRouteIdLessThan(String value) {
            addCriterion("bd_route_id <", value, "bdRouteId");
            return (Criteria) this;
        }

        public Criteria andBdRouteIdLessThanOrEqualTo(String value) {
            addCriterion("bd_route_id <=", value, "bdRouteId");
            return (Criteria) this;
        }

        public Criteria andBdRouteIdLike(String value) {
            addCriterion("bd_route_id like", value, "bdRouteId");
            return (Criteria) this;
        }

        public Criteria andBdRouteIdNotLike(String value) {
            addCriterion("bd_route_id not like", value, "bdRouteId");
            return (Criteria) this;
        }

        public Criteria andBdRouteIdIn(List<String> values) {
            addCriterion("bd_route_id in", values, "bdRouteId");
            return (Criteria) this;
        }

        public Criteria andBdRouteIdNotIn(List<String> values) {
            addCriterion("bd_route_id not in", values, "bdRouteId");
            return (Criteria) this;
        }

        public Criteria andBdRouteIdBetween(String value1, String value2) {
            addCriterion("bd_route_id between", value1, value2, "bdRouteId");
            return (Criteria) this;
        }

        public Criteria andBdRouteIdNotBetween(String value1, String value2) {
            addCriterion("bd_route_id not between", value1, value2, "bdRouteId");
            return (Criteria) this;
        }

        public Criteria andBdMemberCodeIsNull() {
            addCriterion("bd_member_code is null");
            return (Criteria) this;
        }

        public Criteria andBdMemberCodeIsNotNull() {
            addCriterion("bd_member_code is not null");
            return (Criteria) this;
        }

        public Criteria andBdMemberCodeEqualTo(String value) {
            addCriterion("bd_member_code =", value, "bdMemberCode");
            return (Criteria) this;
        }

        public Criteria andBdMemberCodeNotEqualTo(String value) {
            addCriterion("bd_member_code <>", value, "bdMemberCode");
            return (Criteria) this;
        }

        public Criteria andBdMemberCodeGreaterThan(String value) {
            addCriterion("bd_member_code >", value, "bdMemberCode");
            return (Criteria) this;
        }

        public Criteria andBdMemberCodeGreaterThanOrEqualTo(String value) {
            addCriterion("bd_member_code >=", value, "bdMemberCode");
            return (Criteria) this;
        }

        public Criteria andBdMemberCodeLessThan(String value) {
            addCriterion("bd_member_code <", value, "bdMemberCode");
            return (Criteria) this;
        }

        public Criteria andBdMemberCodeLessThanOrEqualTo(String value) {
            addCriterion("bd_member_code <=", value, "bdMemberCode");
            return (Criteria) this;
        }

        public Criteria andBdMemberCodeLike(String value) {
            addCriterion("bd_member_code like", value, "bdMemberCode");
            return (Criteria) this;
        }

        public Criteria andBdMemberCodeNotLike(String value) {
            addCriterion("bd_member_code not like", value, "bdMemberCode");
            return (Criteria) this;
        }

        public Criteria andBdMemberCodeIn(List<String> values) {
            addCriterion("bd_member_code in", values, "bdMemberCode");
            return (Criteria) this;
        }

        public Criteria andBdMemberCodeNotIn(List<String> values) {
            addCriterion("bd_member_code not in", values, "bdMemberCode");
            return (Criteria) this;
        }

        public Criteria andBdMemberCodeBetween(String value1, String value2) {
            addCriterion("bd_member_code between", value1, value2, "bdMemberCode");
            return (Criteria) this;
        }

        public Criteria andBdMemberCodeNotBetween(String value1, String value2) {
            addCriterion("bd_member_code not between", value1, value2, "bdMemberCode");
            return (Criteria) this;
        }

        public Criteria andBdMerchantCodeIsNull() {
            addCriterion("bd_merchant_code is null");
            return (Criteria) this;
        }

        public Criteria andBdMerchantCodeIsNotNull() {
            addCriterion("bd_merchant_code is not null");
            return (Criteria) this;
        }

        public Criteria andBdMerchantCodeEqualTo(String value) {
            addCriterion("bd_merchant_code =", value, "bdMerchantCode");
            return (Criteria) this;
        }

        public Criteria andBdMerchantCodeNotEqualTo(String value) {
            addCriterion("bd_merchant_code <>", value, "bdMerchantCode");
            return (Criteria) this;
        }

        public Criteria andBdMerchantCodeGreaterThan(String value) {
            addCriterion("bd_merchant_code >", value, "bdMerchantCode");
            return (Criteria) this;
        }

        public Criteria andBdMerchantCodeGreaterThanOrEqualTo(String value) {
            addCriterion("bd_merchant_code >=", value, "bdMerchantCode");
            return (Criteria) this;
        }

        public Criteria andBdMerchantCodeLessThan(String value) {
            addCriterion("bd_merchant_code <", value, "bdMerchantCode");
            return (Criteria) this;
        }

        public Criteria andBdMerchantCodeLessThanOrEqualTo(String value) {
            addCriterion("bd_merchant_code <=", value, "bdMerchantCode");
            return (Criteria) this;
        }

        public Criteria andBdMerchantCodeLike(String value) {
            addCriterion("bd_merchant_code like", value, "bdMerchantCode");
            return (Criteria) this;
        }

        public Criteria andBdMerchantCodeNotLike(String value) {
            addCriterion("bd_merchant_code not like", value, "bdMerchantCode");
            return (Criteria) this;
        }

        public Criteria andBdMerchantCodeIn(List<String> values) {
            addCriterion("bd_merchant_code in", values, "bdMerchantCode");
            return (Criteria) this;
        }

        public Criteria andBdMerchantCodeNotIn(List<String> values) {
            addCriterion("bd_merchant_code not in", values, "bdMerchantCode");
            return (Criteria) this;
        }

        public Criteria andBdMerchantCodeBetween(String value1, String value2) {
            addCriterion("bd_merchant_code between", value1, value2, "bdMerchantCode");
            return (Criteria) this;
        }

        public Criteria andBdMerchantCodeNotBetween(String value1, String value2) {
            addCriterion("bd_merchant_code not between", value1, value2, "bdMerchantCode");
            return (Criteria) this;
        }

        public Criteria andJdRouteIdIsNull() {
            addCriterion("jd_route_id is null");
            return (Criteria) this;
        }

        public Criteria andJdRouteIdIsNotNull() {
            addCriterion("jd_route_id is not null");
            return (Criteria) this;
        }

        public Criteria andJdRouteIdEqualTo(String value) {
            addCriterion("jd_route_id =", value, "jdRouteId");
            return (Criteria) this;
        }

        public Criteria andJdRouteIdNotEqualTo(String value) {
            addCriterion("jd_route_id <>", value, "jdRouteId");
            return (Criteria) this;
        }

        public Criteria andJdRouteIdGreaterThan(String value) {
            addCriterion("jd_route_id >", value, "jdRouteId");
            return (Criteria) this;
        }

        public Criteria andJdRouteIdGreaterThanOrEqualTo(String value) {
            addCriterion("jd_route_id >=", value, "jdRouteId");
            return (Criteria) this;
        }

        public Criteria andJdRouteIdLessThan(String value) {
            addCriterion("jd_route_id <", value, "jdRouteId");
            return (Criteria) this;
        }

        public Criteria andJdRouteIdLessThanOrEqualTo(String value) {
            addCriterion("jd_route_id <=", value, "jdRouteId");
            return (Criteria) this;
        }

        public Criteria andJdRouteIdLike(String value) {
            addCriterion("jd_route_id like", value, "jdRouteId");
            return (Criteria) this;
        }

        public Criteria andJdRouteIdNotLike(String value) {
            addCriterion("jd_route_id not like", value, "jdRouteId");
            return (Criteria) this;
        }

        public Criteria andJdRouteIdIn(List<String> values) {
            addCriterion("jd_route_id in", values, "jdRouteId");
            return (Criteria) this;
        }

        public Criteria andJdRouteIdNotIn(List<String> values) {
            addCriterion("jd_route_id not in", values, "jdRouteId");
            return (Criteria) this;
        }

        public Criteria andJdRouteIdBetween(String value1, String value2) {
            addCriterion("jd_route_id between", value1, value2, "jdRouteId");
            return (Criteria) this;
        }

        public Criteria andJdRouteIdNotBetween(String value1, String value2) {
            addCriterion("jd_route_id not between", value1, value2, "jdRouteId");
            return (Criteria) this;
        }

        public Criteria andJdMemberCodeIsNull() {
            addCriterion("jd_member_code is null");
            return (Criteria) this;
        }

        public Criteria andJdMemberCodeIsNotNull() {
            addCriterion("jd_member_code is not null");
            return (Criteria) this;
        }

        public Criteria andJdMemberCodeEqualTo(String value) {
            addCriterion("jd_member_code =", value, "jdMemberCode");
            return (Criteria) this;
        }

        public Criteria andJdMemberCodeNotEqualTo(String value) {
            addCriterion("jd_member_code <>", value, "jdMemberCode");
            return (Criteria) this;
        }

        public Criteria andJdMemberCodeGreaterThan(String value) {
            addCriterion("jd_member_code >", value, "jdMemberCode");
            return (Criteria) this;
        }

        public Criteria andJdMemberCodeGreaterThanOrEqualTo(String value) {
            addCriterion("jd_member_code >=", value, "jdMemberCode");
            return (Criteria) this;
        }

        public Criteria andJdMemberCodeLessThan(String value) {
            addCriterion("jd_member_code <", value, "jdMemberCode");
            return (Criteria) this;
        }

        public Criteria andJdMemberCodeLessThanOrEqualTo(String value) {
            addCriterion("jd_member_code <=", value, "jdMemberCode");
            return (Criteria) this;
        }

        public Criteria andJdMemberCodeLike(String value) {
            addCriterion("jd_member_code like", value, "jdMemberCode");
            return (Criteria) this;
        }

        public Criteria andJdMemberCodeNotLike(String value) {
            addCriterion("jd_member_code not like", value, "jdMemberCode");
            return (Criteria) this;
        }

        public Criteria andJdMemberCodeIn(List<String> values) {
            addCriterion("jd_member_code in", values, "jdMemberCode");
            return (Criteria) this;
        }

        public Criteria andJdMemberCodeNotIn(List<String> values) {
            addCriterion("jd_member_code not in", values, "jdMemberCode");
            return (Criteria) this;
        }

        public Criteria andJdMemberCodeBetween(String value1, String value2) {
            addCriterion("jd_member_code between", value1, value2, "jdMemberCode");
            return (Criteria) this;
        }

        public Criteria andJdMemberCodeNotBetween(String value1, String value2) {
            addCriterion("jd_member_code not between", value1, value2, "jdMemberCode");
            return (Criteria) this;
        }

        public Criteria andJdMerchantCodeIsNull() {
            addCriterion("jd_merchant_code is null");
            return (Criteria) this;
        }

        public Criteria andJdMerchantCodeIsNotNull() {
            addCriterion("jd_merchant_code is not null");
            return (Criteria) this;
        }

        public Criteria andJdMerchantCodeEqualTo(String value) {
            addCriterion("jd_merchant_code =", value, "jdMerchantCode");
            return (Criteria) this;
        }

        public Criteria andJdMerchantCodeNotEqualTo(String value) {
            addCriterion("jd_merchant_code <>", value, "jdMerchantCode");
            return (Criteria) this;
        }

        public Criteria andJdMerchantCodeGreaterThan(String value) {
            addCriterion("jd_merchant_code >", value, "jdMerchantCode");
            return (Criteria) this;
        }

        public Criteria andJdMerchantCodeGreaterThanOrEqualTo(String value) {
            addCriterion("jd_merchant_code >=", value, "jdMerchantCode");
            return (Criteria) this;
        }

        public Criteria andJdMerchantCodeLessThan(String value) {
            addCriterion("jd_merchant_code <", value, "jdMerchantCode");
            return (Criteria) this;
        }

        public Criteria andJdMerchantCodeLessThanOrEqualTo(String value) {
            addCriterion("jd_merchant_code <=", value, "jdMerchantCode");
            return (Criteria) this;
        }

        public Criteria andJdMerchantCodeLike(String value) {
            addCriterion("jd_merchant_code like", value, "jdMerchantCode");
            return (Criteria) this;
        }

        public Criteria andJdMerchantCodeNotLike(String value) {
            addCriterion("jd_merchant_code not like", value, "jdMerchantCode");
            return (Criteria) this;
        }

        public Criteria andJdMerchantCodeIn(List<String> values) {
            addCriterion("jd_merchant_code in", values, "jdMerchantCode");
            return (Criteria) this;
        }

        public Criteria andJdMerchantCodeNotIn(List<String> values) {
            addCriterion("jd_merchant_code not in", values, "jdMerchantCode");
            return (Criteria) this;
        }

        public Criteria andJdMerchantCodeBetween(String value1, String value2) {
            addCriterion("jd_merchant_code between", value1, value2, "jdMerchantCode");
            return (Criteria) this;
        }

        public Criteria andJdMerchantCodeNotBetween(String value1, String value2) {
            addCriterion("jd_merchant_code not between", value1, value2, "jdMerchantCode");
            return (Criteria) this;
        }

        public Criteria andWxChannelMerchantCodeIsNull() {
            addCriterion("wx_channel_merchant_code is null");
            return (Criteria) this;
        }

        public Criteria andWxChannelMerchantCodeIsNotNull() {
            addCriterion("wx_channel_merchant_code is not null");
            return (Criteria) this;
        }

        public Criteria andWxChannelMerchantCodeEqualTo(String value) {
            addCriterion("wx_channel_merchant_code =", value, "wxChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andWxChannelMerchantCodeNotEqualTo(String value) {
            addCriterion("wx_channel_merchant_code <>", value, "wxChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andWxChannelMerchantCodeGreaterThan(String value) {
            addCriterion("wx_channel_merchant_code >", value, "wxChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andWxChannelMerchantCodeGreaterThanOrEqualTo(String value) {
            addCriterion("wx_channel_merchant_code >=", value, "wxChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andWxChannelMerchantCodeLessThan(String value) {
            addCriterion("wx_channel_merchant_code <", value, "wxChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andWxChannelMerchantCodeLessThanOrEqualTo(String value) {
            addCriterion("wx_channel_merchant_code <=", value, "wxChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andWxChannelMerchantCodeLike(String value) {
            addCriterion("wx_channel_merchant_code like", value, "wxChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andWxChannelMerchantCodeNotLike(String value) {
            addCriterion("wx_channel_merchant_code not like", value, "wxChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andWxChannelMerchantCodeIn(List<String> values) {
            addCriterion("wx_channel_merchant_code in", values, "wxChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andWxChannelMerchantCodeNotIn(List<String> values) {
            addCriterion("wx_channel_merchant_code not in", values, "wxChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andWxChannelMerchantCodeBetween(String value1, String value2) {
            addCriterion("wx_channel_merchant_code between", value1, value2, "wxChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andWxChannelMerchantCodeNotBetween(String value1, String value2) {
            addCriterion("wx_channel_merchant_code not between", value1, value2, "wxChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andQqChannelMerchantCodeIsNull() {
            addCriterion("qq_channel_merchant_code is null");
            return (Criteria) this;
        }

        public Criteria andQqChannelMerchantCodeIsNotNull() {
            addCriterion("qq_channel_merchant_code is not null");
            return (Criteria) this;
        }

        public Criteria andQqChannelMerchantCodeEqualTo(String value) {
            addCriterion("qq_channel_merchant_code =", value, "qqChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andQqChannelMerchantCodeNotEqualTo(String value) {
            addCriterion("qq_channel_merchant_code <>", value, "qqChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andQqChannelMerchantCodeGreaterThan(String value) {
            addCriterion("qq_channel_merchant_code >", value, "qqChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andQqChannelMerchantCodeGreaterThanOrEqualTo(String value) {
            addCriterion("qq_channel_merchant_code >=", value, "qqChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andQqChannelMerchantCodeLessThan(String value) {
            addCriterion("qq_channel_merchant_code <", value, "qqChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andQqChannelMerchantCodeLessThanOrEqualTo(String value) {
            addCriterion("qq_channel_merchant_code <=", value, "qqChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andQqChannelMerchantCodeLike(String value) {
            addCriterion("qq_channel_merchant_code like", value, "qqChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andQqChannelMerchantCodeNotLike(String value) {
            addCriterion("qq_channel_merchant_code not like", value, "qqChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andQqChannelMerchantCodeIn(List<String> values) {
            addCriterion("qq_channel_merchant_code in", values, "qqChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andQqChannelMerchantCodeNotIn(List<String> values) {
            addCriterion("qq_channel_merchant_code not in", values, "qqChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andQqChannelMerchantCodeBetween(String value1, String value2) {
            addCriterion("qq_channel_merchant_code between", value1, value2, "qqChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andQqChannelMerchantCodeNotBetween(String value1, String value2) {
            addCriterion("qq_channel_merchant_code not between", value1, value2, "qqChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andZfbChannelMerchantCodeIsNull() {
            addCriterion("zfb_channel_merchant_code is null");
            return (Criteria) this;
        }

        public Criteria andZfbChannelMerchantCodeIsNotNull() {
            addCriterion("zfb_channel_merchant_code is not null");
            return (Criteria) this;
        }

        public Criteria andZfbChannelMerchantCodeEqualTo(String value) {
            addCriterion("zfb_channel_merchant_code =", value, "zfbChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andZfbChannelMerchantCodeNotEqualTo(String value) {
            addCriterion("zfb_channel_merchant_code <>", value, "zfbChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andZfbChannelMerchantCodeGreaterThan(String value) {
            addCriterion("zfb_channel_merchant_code >", value, "zfbChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andZfbChannelMerchantCodeGreaterThanOrEqualTo(String value) {
            addCriterion("zfb_channel_merchant_code >=", value, "zfbChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andZfbChannelMerchantCodeLessThan(String value) {
            addCriterion("zfb_channel_merchant_code <", value, "zfbChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andZfbChannelMerchantCodeLessThanOrEqualTo(String value) {
            addCriterion("zfb_channel_merchant_code <=", value, "zfbChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andZfbChannelMerchantCodeLike(String value) {
            addCriterion("zfb_channel_merchant_code like", value, "zfbChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andZfbChannelMerchantCodeNotLike(String value) {
            addCriterion("zfb_channel_merchant_code not like", value, "zfbChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andZfbChannelMerchantCodeIn(List<String> values) {
            addCriterion("zfb_channel_merchant_code in", values, "zfbChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andZfbChannelMerchantCodeNotIn(List<String> values) {
            addCriterion("zfb_channel_merchant_code not in", values, "zfbChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andZfbChannelMerchantCodeBetween(String value1, String value2) {
            addCriterion("zfb_channel_merchant_code between", value1, value2, "zfbChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andZfbChannelMerchantCodeNotBetween(String value1, String value2) {
            addCriterion("zfb_channel_merchant_code not between", value1, value2, "zfbChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andBdChannelMerchantCodeIsNull() {
            addCriterion("bd_channel_merchant_code is null");
            return (Criteria) this;
        }

        public Criteria andBdChannelMerchantCodeIsNotNull() {
            addCriterion("bd_channel_merchant_code is not null");
            return (Criteria) this;
        }

        public Criteria andBdChannelMerchantCodeEqualTo(String value) {
            addCriterion("bd_channel_merchant_code =", value, "bdChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andBdChannelMerchantCodeNotEqualTo(String value) {
            addCriterion("bd_channel_merchant_code <>", value, "bdChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andBdChannelMerchantCodeGreaterThan(String value) {
            addCriterion("bd_channel_merchant_code >", value, "bdChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andBdChannelMerchantCodeGreaterThanOrEqualTo(String value) {
            addCriterion("bd_channel_merchant_code >=", value, "bdChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andBdChannelMerchantCodeLessThan(String value) {
            addCriterion("bd_channel_merchant_code <", value, "bdChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andBdChannelMerchantCodeLessThanOrEqualTo(String value) {
            addCriterion("bd_channel_merchant_code <=", value, "bdChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andBdChannelMerchantCodeLike(String value) {
            addCriterion("bd_channel_merchant_code like", value, "bdChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andBdChannelMerchantCodeNotLike(String value) {
            addCriterion("bd_channel_merchant_code not like", value, "bdChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andBdChannelMerchantCodeIn(List<String> values) {
            addCriterion("bd_channel_merchant_code in", values, "bdChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andBdChannelMerchantCodeNotIn(List<String> values) {
            addCriterion("bd_channel_merchant_code not in", values, "bdChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andBdChannelMerchantCodeBetween(String value1, String value2) {
            addCriterion("bd_channel_merchant_code between", value1, value2, "bdChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andBdChannelMerchantCodeNotBetween(String value1, String value2) {
            addCriterion("bd_channel_merchant_code not between", value1, value2, "bdChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andJdChannelMerchantCodeIsNull() {
            addCriterion("jd_channel_merchant_code is null");
            return (Criteria) this;
        }

        public Criteria andJdChannelMerchantCodeIsNotNull() {
            addCriterion("jd_channel_merchant_code is not null");
            return (Criteria) this;
        }

        public Criteria andJdChannelMerchantCodeEqualTo(String value) {
            addCriterion("jd_channel_merchant_code =", value, "jdChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andJdChannelMerchantCodeNotEqualTo(String value) {
            addCriterion("jd_channel_merchant_code <>", value, "jdChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andJdChannelMerchantCodeGreaterThan(String value) {
            addCriterion("jd_channel_merchant_code >", value, "jdChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andJdChannelMerchantCodeGreaterThanOrEqualTo(String value) {
            addCriterion("jd_channel_merchant_code >=", value, "jdChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andJdChannelMerchantCodeLessThan(String value) {
            addCriterion("jd_channel_merchant_code <", value, "jdChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andJdChannelMerchantCodeLessThanOrEqualTo(String value) {
            addCriterion("jd_channel_merchant_code <=", value, "jdChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andJdChannelMerchantCodeLike(String value) {
            addCriterion("jd_channel_merchant_code like", value, "jdChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andJdChannelMerchantCodeNotLike(String value) {
            addCriterion("jd_channel_merchant_code not like", value, "jdChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andJdChannelMerchantCodeIn(List<String> values) {
            addCriterion("jd_channel_merchant_code in", values, "jdChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andJdChannelMerchantCodeNotIn(List<String> values) {
            addCriterion("jd_channel_merchant_code not in", values, "jdChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andJdChannelMerchantCodeBetween(String value1, String value2) {
            addCriterion("jd_channel_merchant_code between", value1, value2, "jdChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andJdChannelMerchantCodeNotBetween(String value1, String value2) {
            addCriterion("jd_channel_merchant_code not between", value1, value2, "jdChannelMerchantCode");
            return (Criteria) this;
        }

        public Criteria andT0DrawFeeIsNull() {
            addCriterion("t0_draw_fee is null");
            return (Criteria) this;
        }

        public Criteria andT0DrawFeeIsNotNull() {
            addCriterion("t0_draw_fee is not null");
            return (Criteria) this;
        }

        public Criteria andT0DrawFeeEqualTo(BigDecimal value) {
            addCriterion("t0_draw_fee =", value, "t0DrawFee");
            return (Criteria) this;
        }

        public Criteria andT0DrawFeeNotEqualTo(BigDecimal value) {
            addCriterion("t0_draw_fee <>", value, "t0DrawFee");
            return (Criteria) this;
        }

        public Criteria andT0DrawFeeGreaterThan(BigDecimal value) {
            addCriterion("t0_draw_fee >", value, "t0DrawFee");
            return (Criteria) this;
        }

        public Criteria andT0DrawFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("t0_draw_fee >=", value, "t0DrawFee");
            return (Criteria) this;
        }

        public Criteria andT0DrawFeeLessThan(BigDecimal value) {
            addCriterion("t0_draw_fee <", value, "t0DrawFee");
            return (Criteria) this;
        }

        public Criteria andT0DrawFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("t0_draw_fee <=", value, "t0DrawFee");
            return (Criteria) this;
        }

        public Criteria andT0DrawFeeIn(List<BigDecimal> values) {
            addCriterion("t0_draw_fee in", values, "t0DrawFee");
            return (Criteria) this;
        }

        public Criteria andT0DrawFeeNotIn(List<BigDecimal> values) {
            addCriterion("t0_draw_fee not in", values, "t0DrawFee");
            return (Criteria) this;
        }

        public Criteria andT0DrawFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("t0_draw_fee between", value1, value2, "t0DrawFee");
            return (Criteria) this;
        }

        public Criteria andT0DrawFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("t0_draw_fee not between", value1, value2, "t0DrawFee");
            return (Criteria) this;
        }

        public Criteria andT0TradeRateIsNull() {
            addCriterion("t0_trade_rate is null");
            return (Criteria) this;
        }

        public Criteria andT0TradeRateIsNotNull() {
            addCriterion("t0_trade_rate is not null");
            return (Criteria) this;
        }

        public Criteria andT0TradeRateEqualTo(BigDecimal value) {
            addCriterion("t0_trade_rate =", value, "t0TradeRate");
            return (Criteria) this;
        }

        public Criteria andT0TradeRateNotEqualTo(BigDecimal value) {
            addCriterion("t0_trade_rate <>", value, "t0TradeRate");
            return (Criteria) this;
        }

        public Criteria andT0TradeRateGreaterThan(BigDecimal value) {
            addCriterion("t0_trade_rate >", value, "t0TradeRate");
            return (Criteria) this;
        }

        public Criteria andT0TradeRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("t0_trade_rate >=", value, "t0TradeRate");
            return (Criteria) this;
        }

        public Criteria andT0TradeRateLessThan(BigDecimal value) {
            addCriterion("t0_trade_rate <", value, "t0TradeRate");
            return (Criteria) this;
        }

        public Criteria andT0TradeRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("t0_trade_rate <=", value, "t0TradeRate");
            return (Criteria) this;
        }

        public Criteria andT0TradeRateIn(List<BigDecimal> values) {
            addCriterion("t0_trade_rate in", values, "t0TradeRate");
            return (Criteria) this;
        }

        public Criteria andT0TradeRateNotIn(List<BigDecimal> values) {
            addCriterion("t0_trade_rate not in", values, "t0TradeRate");
            return (Criteria) this;
        }

        public Criteria andT0TradeRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("t0_trade_rate between", value1, value2, "t0TradeRate");
            return (Criteria) this;
        }

        public Criteria andT0TradeRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("t0_trade_rate not between", value1, value2, "t0TradeRate");
            return (Criteria) this;
        }

        public Criteria andT1DrawFeeIsNull() {
            addCriterion("t1_draw_fee is null");
            return (Criteria) this;
        }

        public Criteria andT1DrawFeeIsNotNull() {
            addCriterion("t1_draw_fee is not null");
            return (Criteria) this;
        }

        public Criteria andT1DrawFeeEqualTo(BigDecimal value) {
            addCriterion("t1_draw_fee =", value, "t1DrawFee");
            return (Criteria) this;
        }

        public Criteria andT1DrawFeeNotEqualTo(BigDecimal value) {
            addCriterion("t1_draw_fee <>", value, "t1DrawFee");
            return (Criteria) this;
        }

        public Criteria andT1DrawFeeGreaterThan(BigDecimal value) {
            addCriterion("t1_draw_fee >", value, "t1DrawFee");
            return (Criteria) this;
        }

        public Criteria andT1DrawFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("t1_draw_fee >=", value, "t1DrawFee");
            return (Criteria) this;
        }

        public Criteria andT1DrawFeeLessThan(BigDecimal value) {
            addCriterion("t1_draw_fee <", value, "t1DrawFee");
            return (Criteria) this;
        }

        public Criteria andT1DrawFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("t1_draw_fee <=", value, "t1DrawFee");
            return (Criteria) this;
        }

        public Criteria andT1DrawFeeIn(List<BigDecimal> values) {
            addCriterion("t1_draw_fee in", values, "t1DrawFee");
            return (Criteria) this;
        }

        public Criteria andT1DrawFeeNotIn(List<BigDecimal> values) {
            addCriterion("t1_draw_fee not in", values, "t1DrawFee");
            return (Criteria) this;
        }

        public Criteria andT1DrawFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("t1_draw_fee between", value1, value2, "t1DrawFee");
            return (Criteria) this;
        }

        public Criteria andT1DrawFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("t1_draw_fee not between", value1, value2, "t1DrawFee");
            return (Criteria) this;
        }

        public Criteria andT1TradeRateIsNull() {
            addCriterion("t1_trade_rate is null");
            return (Criteria) this;
        }

        public Criteria andT1TradeRateIsNotNull() {
            addCriterion("t1_trade_rate is not null");
            return (Criteria) this;
        }

        public Criteria andT1TradeRateEqualTo(BigDecimal value) {
            addCriterion("t1_trade_rate =", value, "t1TradeRate");
            return (Criteria) this;
        }

        public Criteria andT1TradeRateNotEqualTo(BigDecimal value) {
            addCriterion("t1_trade_rate <>", value, "t1TradeRate");
            return (Criteria) this;
        }

        public Criteria andT1TradeRateGreaterThan(BigDecimal value) {
            addCriterion("t1_trade_rate >", value, "t1TradeRate");
            return (Criteria) this;
        }

        public Criteria andT1TradeRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("t1_trade_rate >=", value, "t1TradeRate");
            return (Criteria) this;
        }

        public Criteria andT1TradeRateLessThan(BigDecimal value) {
            addCriterion("t1_trade_rate <", value, "t1TradeRate");
            return (Criteria) this;
        }

        public Criteria andT1TradeRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("t1_trade_rate <=", value, "t1TradeRate");
            return (Criteria) this;
        }

        public Criteria andT1TradeRateIn(List<BigDecimal> values) {
            addCriterion("t1_trade_rate in", values, "t1TradeRate");
            return (Criteria) this;
        }

        public Criteria andT1TradeRateNotIn(List<BigDecimal> values) {
            addCriterion("t1_trade_rate not in", values, "t1TradeRate");
            return (Criteria) this;
        }

        public Criteria andT1TradeRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("t1_trade_rate between", value1, value2, "t1TradeRate");
            return (Criteria) this;
        }

        public Criteria andT1TradeRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("t1_trade_rate not between", value1, value2, "t1TradeRate");
            return (Criteria) this;
        }

        public Criteria andMlJfFeeIsNull() {
            addCriterion("ml_jf_fee is null");
            return (Criteria) this;
        }

        public Criteria andMlJfFeeIsNotNull() {
            addCriterion("ml_jf_fee is not null");
            return (Criteria) this;
        }

        public Criteria andMlJfFeeEqualTo(BigDecimal value) {
            addCriterion("ml_jf_fee =", value, "mlJfFee");
            return (Criteria) this;
        }

        public Criteria andMlJfFeeNotEqualTo(BigDecimal value) {
            addCriterion("ml_jf_fee <>", value, "mlJfFee");
            return (Criteria) this;
        }

        public Criteria andMlJfFeeGreaterThan(BigDecimal value) {
            addCriterion("ml_jf_fee >", value, "mlJfFee");
            return (Criteria) this;
        }

        public Criteria andMlJfFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ml_jf_fee >=", value, "mlJfFee");
            return (Criteria) this;
        }

        public Criteria andMlJfFeeLessThan(BigDecimal value) {
            addCriterion("ml_jf_fee <", value, "mlJfFee");
            return (Criteria) this;
        }

        public Criteria andMlJfFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ml_jf_fee <=", value, "mlJfFee");
            return (Criteria) this;
        }

        public Criteria andMlJfFeeIn(List<BigDecimal> values) {
            addCriterion("ml_jf_fee in", values, "mlJfFee");
            return (Criteria) this;
        }

        public Criteria andMlJfFeeNotIn(List<BigDecimal> values) {
            addCriterion("ml_jf_fee not in", values, "mlJfFee");
            return (Criteria) this;
        }

        public Criteria andMlJfFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ml_jf_fee between", value1, value2, "mlJfFee");
            return (Criteria) this;
        }

        public Criteria andMlJfFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ml_jf_fee not between", value1, value2, "mlJfFee");
            return (Criteria) this;
        }

        public Criteria andMlJfRateIsNull() {
            addCriterion("ml_jf_rate is null");
            return (Criteria) this;
        }

        public Criteria andMlJfRateIsNotNull() {
            addCriterion("ml_jf_rate is not null");
            return (Criteria) this;
        }

        public Criteria andMlJfRateEqualTo(BigDecimal value) {
            addCriterion("ml_jf_rate =", value, "mlJfRate");
            return (Criteria) this;
        }

        public Criteria andMlJfRateNotEqualTo(BigDecimal value) {
            addCriterion("ml_jf_rate <>", value, "mlJfRate");
            return (Criteria) this;
        }

        public Criteria andMlJfRateGreaterThan(BigDecimal value) {
            addCriterion("ml_jf_rate >", value, "mlJfRate");
            return (Criteria) this;
        }

        public Criteria andMlJfRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ml_jf_rate >=", value, "mlJfRate");
            return (Criteria) this;
        }

        public Criteria andMlJfRateLessThan(BigDecimal value) {
            addCriterion("ml_jf_rate <", value, "mlJfRate");
            return (Criteria) this;
        }

        public Criteria andMlJfRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ml_jf_rate <=", value, "mlJfRate");
            return (Criteria) this;
        }

        public Criteria andMlJfRateIn(List<BigDecimal> values) {
            addCriterion("ml_jf_rate in", values, "mlJfRate");
            return (Criteria) this;
        }

        public Criteria andMlJfRateNotIn(List<BigDecimal> values) {
            addCriterion("ml_jf_rate not in", values, "mlJfRate");
            return (Criteria) this;
        }

        public Criteria andMlJfRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ml_jf_rate between", value1, value2, "mlJfRate");
            return (Criteria) this;
        }

        public Criteria andMlJfRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ml_jf_rate not between", value1, value2, "mlJfRate");
            return (Criteria) this;
        }

        public Criteria andMlWjfFeeIsNull() {
            addCriterion("ml_wjf_fee is null");
            return (Criteria) this;
        }

        public Criteria andMlWjfFeeIsNotNull() {
            addCriterion("ml_wjf_fee is not null");
            return (Criteria) this;
        }

        public Criteria andMlWjfFeeEqualTo(BigDecimal value) {
            addCriterion("ml_wjf_fee =", value, "mlWjfFee");
            return (Criteria) this;
        }

        public Criteria andMlWjfFeeNotEqualTo(BigDecimal value) {
            addCriterion("ml_wjf_fee <>", value, "mlWjfFee");
            return (Criteria) this;
        }

        public Criteria andMlWjfFeeGreaterThan(BigDecimal value) {
            addCriterion("ml_wjf_fee >", value, "mlWjfFee");
            return (Criteria) this;
        }

        public Criteria andMlWjfFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ml_wjf_fee >=", value, "mlWjfFee");
            return (Criteria) this;
        }

        public Criteria andMlWjfFeeLessThan(BigDecimal value) {
            addCriterion("ml_wjf_fee <", value, "mlWjfFee");
            return (Criteria) this;
        }

        public Criteria andMlWjfFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ml_wjf_fee <=", value, "mlWjfFee");
            return (Criteria) this;
        }

        public Criteria andMlWjfFeeIn(List<BigDecimal> values) {
            addCriterion("ml_wjf_fee in", values, "mlWjfFee");
            return (Criteria) this;
        }

        public Criteria andMlWjfFeeNotIn(List<BigDecimal> values) {
            addCriterion("ml_wjf_fee not in", values, "mlWjfFee");
            return (Criteria) this;
        }

        public Criteria andMlWjfFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ml_wjf_fee between", value1, value2, "mlWjfFee");
            return (Criteria) this;
        }

        public Criteria andMlWjfFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ml_wjf_fee not between", value1, value2, "mlWjfFee");
            return (Criteria) this;
        }

        public Criteria andMlWjfRateIsNull() {
            addCriterion("ml_wjf_rate is null");
            return (Criteria) this;
        }

        public Criteria andMlWjfRateIsNotNull() {
            addCriterion("ml_wjf_rate is not null");
            return (Criteria) this;
        }

        public Criteria andMlWjfRateEqualTo(BigDecimal value) {
            addCriterion("ml_wjf_rate =", value, "mlWjfRate");
            return (Criteria) this;
        }

        public Criteria andMlWjfRateNotEqualTo(BigDecimal value) {
            addCriterion("ml_wjf_rate <>", value, "mlWjfRate");
            return (Criteria) this;
        }

        public Criteria andMlWjfRateGreaterThan(BigDecimal value) {
            addCriterion("ml_wjf_rate >", value, "mlWjfRate");
            return (Criteria) this;
        }

        public Criteria andMlWjfRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ml_wjf_rate >=", value, "mlWjfRate");
            return (Criteria) this;
        }

        public Criteria andMlWjfRateLessThan(BigDecimal value) {
            addCriterion("ml_wjf_rate <", value, "mlWjfRate");
            return (Criteria) this;
        }

        public Criteria andMlWjfRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ml_wjf_rate <=", value, "mlWjfRate");
            return (Criteria) this;
        }

        public Criteria andMlWjfRateIn(List<BigDecimal> values) {
            addCriterion("ml_wjf_rate in", values, "mlWjfRate");
            return (Criteria) this;
        }

        public Criteria andMlWjfRateNotIn(List<BigDecimal> values) {
            addCriterion("ml_wjf_rate not in", values, "mlWjfRate");
            return (Criteria) this;
        }

        public Criteria andMlWjfRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ml_wjf_rate between", value1, value2, "mlWjfRate");
            return (Criteria) this;
        }

        public Criteria andMlWjfRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ml_wjf_rate not between", value1, value2, "mlWjfRate");
            return (Criteria) this;
        }

        public Criteria andPayCodeIsNull() {
            addCriterion("pay_code is null");
            return (Criteria) this;
        }

        public Criteria andPayCodeIsNotNull() {
            addCriterion("pay_code is not null");
            return (Criteria) this;
        }

        public Criteria andPayCodeEqualTo(String value) {
            addCriterion("pay_code =", value, "payCode");
            return (Criteria) this;
        }

        public Criteria andPayCodeNotEqualTo(String value) {
            addCriterion("pay_code <>", value, "payCode");
            return (Criteria) this;
        }

        public Criteria andPayCodeGreaterThan(String value) {
            addCriterion("pay_code >", value, "payCode");
            return (Criteria) this;
        }

        public Criteria andPayCodeGreaterThanOrEqualTo(String value) {
            addCriterion("pay_code >=", value, "payCode");
            return (Criteria) this;
        }

        public Criteria andPayCodeLessThan(String value) {
            addCriterion("pay_code <", value, "payCode");
            return (Criteria) this;
        }

        public Criteria andPayCodeLessThanOrEqualTo(String value) {
            addCriterion("pay_code <=", value, "payCode");
            return (Criteria) this;
        }

        public Criteria andPayCodeLike(String value) {
            addCriterion("pay_code like", value, "payCode");
            return (Criteria) this;
        }

        public Criteria andPayCodeNotLike(String value) {
            addCriterion("pay_code not like", value, "payCode");
            return (Criteria) this;
        }

        public Criteria andPayCodeIn(List<String> values) {
            addCriterion("pay_code in", values, "payCode");
            return (Criteria) this;
        }

        public Criteria andPayCodeNotIn(List<String> values) {
            addCriterion("pay_code not in", values, "payCode");
            return (Criteria) this;
        }

        public Criteria andPayCodeBetween(String value1, String value2) {
            addCriterion("pay_code between", value1, value2, "payCode");
            return (Criteria) this;
        }

        public Criteria andPayCodeNotBetween(String value1, String value2) {
            addCriterion("pay_code not between", value1, value2, "payCode");
            return (Criteria) this;
        }

        public Criteria andSingleLimitIsNull() {
            addCriterion("single_limit is null");
            return (Criteria) this;
        }

        public Criteria andSingleLimitIsNotNull() {
            addCriterion("single_limit is not null");
            return (Criteria) this;
        }

        public Criteria andSingleLimitEqualTo(BigDecimal value) {
            addCriterion("single_limit =", value, "singleLimit");
            return (Criteria) this;
        }

        public Criteria andSingleLimitNotEqualTo(BigDecimal value) {
            addCriterion("single_limit <>", value, "singleLimit");
            return (Criteria) this;
        }

        public Criteria andSingleLimitGreaterThan(BigDecimal value) {
            addCriterion("single_limit >", value, "singleLimit");
            return (Criteria) this;
        }

        public Criteria andSingleLimitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("single_limit >=", value, "singleLimit");
            return (Criteria) this;
        }

        public Criteria andSingleLimitLessThan(BigDecimal value) {
            addCriterion("single_limit <", value, "singleLimit");
            return (Criteria) this;
        }

        public Criteria andSingleLimitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("single_limit <=", value, "singleLimit");
            return (Criteria) this;
        }

        public Criteria andSingleLimitIn(List<BigDecimal> values) {
            addCriterion("single_limit in", values, "singleLimit");
            return (Criteria) this;
        }

        public Criteria andSingleLimitNotIn(List<BigDecimal> values) {
            addCriterion("single_limit not in", values, "singleLimit");
            return (Criteria) this;
        }

        public Criteria andSingleLimitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("single_limit between", value1, value2, "singleLimit");
            return (Criteria) this;
        }

        public Criteria andSingleLimitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("single_limit not between", value1, value2, "singleLimit");
            return (Criteria) this;
        }

        public Criteria andDayLimitIsNull() {
            addCriterion("day_limit is null");
            return (Criteria) this;
        }

        public Criteria andDayLimitIsNotNull() {
            addCriterion("day_limit is not null");
            return (Criteria) this;
        }

        public Criteria andDayLimitEqualTo(BigDecimal value) {
            addCriterion("day_limit =", value, "dayLimit");
            return (Criteria) this;
        }

        public Criteria andDayLimitNotEqualTo(BigDecimal value) {
            addCriterion("day_limit <>", value, "dayLimit");
            return (Criteria) this;
        }

        public Criteria andDayLimitGreaterThan(BigDecimal value) {
            addCriterion("day_limit >", value, "dayLimit");
            return (Criteria) this;
        }

        public Criteria andDayLimitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("day_limit >=", value, "dayLimit");
            return (Criteria) this;
        }

        public Criteria andDayLimitLessThan(BigDecimal value) {
            addCriterion("day_limit <", value, "dayLimit");
            return (Criteria) this;
        }

        public Criteria andDayLimitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("day_limit <=", value, "dayLimit");
            return (Criteria) this;
        }

        public Criteria andDayLimitIn(List<BigDecimal> values) {
            addCriterion("day_limit in", values, "dayLimit");
            return (Criteria) this;
        }

        public Criteria andDayLimitNotIn(List<BigDecimal> values) {
            addCriterion("day_limit not in", values, "dayLimit");
            return (Criteria) this;
        }

        public Criteria andDayLimitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("day_limit between", value1, value2, "dayLimit");
            return (Criteria) this;
        }

        public Criteria andDayLimitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("day_limit not between", value1, value2, "dayLimit");
            return (Criteria) this;
        }

        public Criteria andBusPicIsNull() {
            addCriterion("bus_pic is null");
            return (Criteria) this;
        }

        public Criteria andBusPicIsNotNull() {
            addCriterion("bus_pic is not null");
            return (Criteria) this;
        }

        public Criteria andBusPicEqualTo(String value) {
            addCriterion("bus_pic =", value, "busPic");
            return (Criteria) this;
        }

        public Criteria andBusPicNotEqualTo(String value) {
            addCriterion("bus_pic <>", value, "busPic");
            return (Criteria) this;
        }

        public Criteria andBusPicGreaterThan(String value) {
            addCriterion("bus_pic >", value, "busPic");
            return (Criteria) this;
        }

        public Criteria andBusPicGreaterThanOrEqualTo(String value) {
            addCriterion("bus_pic >=", value, "busPic");
            return (Criteria) this;
        }

        public Criteria andBusPicLessThan(String value) {
            addCriterion("bus_pic <", value, "busPic");
            return (Criteria) this;
        }

        public Criteria andBusPicLessThanOrEqualTo(String value) {
            addCriterion("bus_pic <=", value, "busPic");
            return (Criteria) this;
        }

        public Criteria andBusPicLike(String value) {
            addCriterion("bus_pic like", value, "busPic");
            return (Criteria) this;
        }

        public Criteria andBusPicNotLike(String value) {
            addCriterion("bus_pic not like", value, "busPic");
            return (Criteria) this;
        }

        public Criteria andBusPicIn(List<String> values) {
            addCriterion("bus_pic in", values, "busPic");
            return (Criteria) this;
        }

        public Criteria andBusPicNotIn(List<String> values) {
            addCriterion("bus_pic not in", values, "busPic");
            return (Criteria) this;
        }

        public Criteria andBusPicBetween(String value1, String value2) {
            addCriterion("bus_pic between", value1, value2, "busPic");
            return (Criteria) this;
        }

        public Criteria andBusPicNotBetween(String value1, String value2) {
            addCriterion("bus_pic not between", value1, value2, "busPic");
            return (Criteria) this;
        }

        public Criteria andHeadPicIsNull() {
            addCriterion("head_pic is null");
            return (Criteria) this;
        }

        public Criteria andHeadPicIsNotNull() {
            addCriterion("head_pic is not null");
            return (Criteria) this;
        }

        public Criteria andHeadPicEqualTo(String value) {
            addCriterion("head_pic =", value, "headPic");
            return (Criteria) this;
        }

        public Criteria andHeadPicNotEqualTo(String value) {
            addCriterion("head_pic <>", value, "headPic");
            return (Criteria) this;
        }

        public Criteria andHeadPicGreaterThan(String value) {
            addCriterion("head_pic >", value, "headPic");
            return (Criteria) this;
        }

        public Criteria andHeadPicGreaterThanOrEqualTo(String value) {
            addCriterion("head_pic >=", value, "headPic");
            return (Criteria) this;
        }

        public Criteria andHeadPicLessThan(String value) {
            addCriterion("head_pic <", value, "headPic");
            return (Criteria) this;
        }

        public Criteria andHeadPicLessThanOrEqualTo(String value) {
            addCriterion("head_pic <=", value, "headPic");
            return (Criteria) this;
        }

        public Criteria andHeadPicLike(String value) {
            addCriterion("head_pic like", value, "headPic");
            return (Criteria) this;
        }

        public Criteria andHeadPicNotLike(String value) {
            addCriterion("head_pic not like", value, "headPic");
            return (Criteria) this;
        }

        public Criteria andHeadPicIn(List<String> values) {
            addCriterion("head_pic in", values, "headPic");
            return (Criteria) this;
        }

        public Criteria andHeadPicNotIn(List<String> values) {
            addCriterion("head_pic not in", values, "headPic");
            return (Criteria) this;
        }

        public Criteria andHeadPicBetween(String value1, String value2) {
            addCriterion("head_pic between", value1, value2, "headPic");
            return (Criteria) this;
        }

        public Criteria andHeadPicNotBetween(String value1, String value2) {
            addCriterion("head_pic not between", value1, value2, "headPic");
            return (Criteria) this;
        }

        public Criteria andDeskPicIsNull() {
            addCriterion("desk_pic is null");
            return (Criteria) this;
        }

        public Criteria andDeskPicIsNotNull() {
            addCriterion("desk_pic is not null");
            return (Criteria) this;
        }

        public Criteria andDeskPicEqualTo(String value) {
            addCriterion("desk_pic =", value, "deskPic");
            return (Criteria) this;
        }

        public Criteria andDeskPicNotEqualTo(String value) {
            addCriterion("desk_pic <>", value, "deskPic");
            return (Criteria) this;
        }

        public Criteria andDeskPicGreaterThan(String value) {
            addCriterion("desk_pic >", value, "deskPic");
            return (Criteria) this;
        }

        public Criteria andDeskPicGreaterThanOrEqualTo(String value) {
            addCriterion("desk_pic >=", value, "deskPic");
            return (Criteria) this;
        }

        public Criteria andDeskPicLessThan(String value) {
            addCriterion("desk_pic <", value, "deskPic");
            return (Criteria) this;
        }

        public Criteria andDeskPicLessThanOrEqualTo(String value) {
            addCriterion("desk_pic <=", value, "deskPic");
            return (Criteria) this;
        }

        public Criteria andDeskPicLike(String value) {
            addCriterion("desk_pic like", value, "deskPic");
            return (Criteria) this;
        }

        public Criteria andDeskPicNotLike(String value) {
            addCriterion("desk_pic not like", value, "deskPic");
            return (Criteria) this;
        }

        public Criteria andDeskPicIn(List<String> values) {
            addCriterion("desk_pic in", values, "deskPic");
            return (Criteria) this;
        }

        public Criteria andDeskPicNotIn(List<String> values) {
            addCriterion("desk_pic not in", values, "deskPic");
            return (Criteria) this;
        }

        public Criteria andDeskPicBetween(String value1, String value2) {
            addCriterion("desk_pic between", value1, value2, "deskPic");
            return (Criteria) this;
        }

        public Criteria andDeskPicNotBetween(String value1, String value2) {
            addCriterion("desk_pic not between", value1, value2, "deskPic");
            return (Criteria) this;
        }

        public Criteria andInsidePicIsNull() {
            addCriterion("inside_pic is null");
            return (Criteria) this;
        }

        public Criteria andInsidePicIsNotNull() {
            addCriterion("inside_pic is not null");
            return (Criteria) this;
        }

        public Criteria andInsidePicEqualTo(String value) {
            addCriterion("inside_pic =", value, "insidePic");
            return (Criteria) this;
        }

        public Criteria andInsidePicNotEqualTo(String value) {
            addCriterion("inside_pic <>", value, "insidePic");
            return (Criteria) this;
        }

        public Criteria andInsidePicGreaterThan(String value) {
            addCriterion("inside_pic >", value, "insidePic");
            return (Criteria) this;
        }

        public Criteria andInsidePicGreaterThanOrEqualTo(String value) {
            addCriterion("inside_pic >=", value, "insidePic");
            return (Criteria) this;
        }

        public Criteria andInsidePicLessThan(String value) {
            addCriterion("inside_pic <", value, "insidePic");
            return (Criteria) this;
        }

        public Criteria andInsidePicLessThanOrEqualTo(String value) {
            addCriterion("inside_pic <=", value, "insidePic");
            return (Criteria) this;
        }

        public Criteria andInsidePicLike(String value) {
            addCriterion("inside_pic like", value, "insidePic");
            return (Criteria) this;
        }

        public Criteria andInsidePicNotLike(String value) {
            addCriterion("inside_pic not like", value, "insidePic");
            return (Criteria) this;
        }

        public Criteria andInsidePicIn(List<String> values) {
            addCriterion("inside_pic in", values, "insidePic");
            return (Criteria) this;
        }

        public Criteria andInsidePicNotIn(List<String> values) {
            addCriterion("inside_pic not in", values, "insidePic");
            return (Criteria) this;
        }

        public Criteria andInsidePicBetween(String value1, String value2) {
            addCriterion("inside_pic between", value1, value2, "insidePic");
            return (Criteria) this;
        }

        public Criteria andInsidePicNotBetween(String value1, String value2) {
            addCriterion("inside_pic not between", value1, value2, "insidePic");
            return (Criteria) this;
        }

        public Criteria andStaffPicIsNull() {
            addCriterion("staff_pic is null");
            return (Criteria) this;
        }

        public Criteria andStaffPicIsNotNull() {
            addCriterion("staff_pic is not null");
            return (Criteria) this;
        }

        public Criteria andStaffPicEqualTo(String value) {
            addCriterion("staff_pic =", value, "staffPic");
            return (Criteria) this;
        }

        public Criteria andStaffPicNotEqualTo(String value) {
            addCriterion("staff_pic <>", value, "staffPic");
            return (Criteria) this;
        }

        public Criteria andStaffPicGreaterThan(String value) {
            addCriterion("staff_pic >", value, "staffPic");
            return (Criteria) this;
        }

        public Criteria andStaffPicGreaterThanOrEqualTo(String value) {
            addCriterion("staff_pic >=", value, "staffPic");
            return (Criteria) this;
        }

        public Criteria andStaffPicLessThan(String value) {
            addCriterion("staff_pic <", value, "staffPic");
            return (Criteria) this;
        }

        public Criteria andStaffPicLessThanOrEqualTo(String value) {
            addCriterion("staff_pic <=", value, "staffPic");
            return (Criteria) this;
        }

        public Criteria andStaffPicLike(String value) {
            addCriterion("staff_pic like", value, "staffPic");
            return (Criteria) this;
        }

        public Criteria andStaffPicNotLike(String value) {
            addCriterion("staff_pic not like", value, "staffPic");
            return (Criteria) this;
        }

        public Criteria andStaffPicIn(List<String> values) {
            addCriterion("staff_pic in", values, "staffPic");
            return (Criteria) this;
        }

        public Criteria andStaffPicNotIn(List<String> values) {
            addCriterion("staff_pic not in", values, "staffPic");
            return (Criteria) this;
        }

        public Criteria andStaffPicBetween(String value1, String value2) {
            addCriterion("staff_pic between", value1, value2, "staffPic");
            return (Criteria) this;
        }

        public Criteria andStaffPicNotBetween(String value1, String value2) {
            addCriterion("staff_pic not between", value1, value2, "staffPic");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNull() {
            addCriterion("create_by is null");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNotNull() {
            addCriterion("create_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreateByEqualTo(String value) {
            addCriterion("create_by =", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotEqualTo(String value) {
            addCriterion("create_by <>", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThan(String value) {
            addCriterion("create_by >", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThanOrEqualTo(String value) {
            addCriterion("create_by >=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThan(String value) {
            addCriterion("create_by <", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThanOrEqualTo(String value) {
            addCriterion("create_by <=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLike(String value) {
            addCriterion("create_by like", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotLike(String value) {
            addCriterion("create_by not like", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByIn(List<String> values) {
            addCriterion("create_by in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotIn(List<String> values) {
            addCriterion("create_by not in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByBetween(String value1, String value2) {
            addCriterion("create_by between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotBetween(String value1, String value2) {
            addCriterion("create_by not between", value1, value2, "createBy");
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

        public Criteria andUpdateByIsNull() {
            addCriterion("update_by is null");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNotNull() {
            addCriterion("update_by is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateByEqualTo(String value) {
            addCriterion("update_by =", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotEqualTo(String value) {
            addCriterion("update_by <>", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThan(String value) {
            addCriterion("update_by >", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThanOrEqualTo(String value) {
            addCriterion("update_by >=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThan(String value) {
            addCriterion("update_by <", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThanOrEqualTo(String value) {
            addCriterion("update_by <=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLike(String value) {
            addCriterion("update_by like", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotLike(String value) {
            addCriterion("update_by not like", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByIn(List<String> values) {
            addCriterion("update_by in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotIn(List<String> values) {
            addCriterion("update_by not in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByBetween(String value1, String value2) {
            addCriterion("update_by between", value1, value2, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotBetween(String value1, String value2) {
            addCriterion("update_by not between", value1, value2, "updateBy");
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

        public Criteria andRemarksIsNull() {
            addCriterion("remarks is null");
            return (Criteria) this;
        }

        public Criteria andRemarksIsNotNull() {
            addCriterion("remarks is not null");
            return (Criteria) this;
        }

        public Criteria andRemarksEqualTo(String value) {
            addCriterion("remarks =", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotEqualTo(String value) {
            addCriterion("remarks <>", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksGreaterThan(String value) {
            addCriterion("remarks >", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksGreaterThanOrEqualTo(String value) {
            addCriterion("remarks >=", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLessThan(String value) {
            addCriterion("remarks <", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLessThanOrEqualTo(String value) {
            addCriterion("remarks <=", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLike(String value) {
            addCriterion("remarks like", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotLike(String value) {
            addCriterion("remarks not like", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksIn(List<String> values) {
            addCriterion("remarks in", values, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotIn(List<String> values) {
            addCriterion("remarks not in", values, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksBetween(String value1, String value2) {
            addCriterion("remarks between", value1, value2, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotBetween(String value1, String value2) {
            addCriterion("remarks not between", value1, value2, "remarks");
            return (Criteria) this;
        }

        public Criteria andDelFlagIsNull() {
            addCriterion("del_flag is null");
            return (Criteria) this;
        }

        public Criteria andDelFlagIsNotNull() {
            addCriterion("del_flag is not null");
            return (Criteria) this;
        }

        public Criteria andDelFlagEqualTo(String value) {
            addCriterion("del_flag =", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotEqualTo(String value) {
            addCriterion("del_flag <>", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThan(String value) {
            addCriterion("del_flag >", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThanOrEqualTo(String value) {
            addCriterion("del_flag >=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThan(String value) {
            addCriterion("del_flag <", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThanOrEqualTo(String value) {
            addCriterion("del_flag <=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLike(String value) {
            addCriterion("del_flag like", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotLike(String value) {
            addCriterion("del_flag not like", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagIn(List<String> values) {
            addCriterion("del_flag in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotIn(List<String> values) {
            addCriterion("del_flag not in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagBetween(String value1, String value2) {
            addCriterion("del_flag between", value1, value2, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotBetween(String value1, String value2) {
            addCriterion("del_flag not between", value1, value2, "delFlag");
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