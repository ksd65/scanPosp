package com.epay.scanposp.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegisterTmpExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitSize;

    public RegisterTmpExample() {
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

        public Criteria andBankIdIsNull() {
            addCriterion("bank_id is null");
            return (Criteria) this;
        }

        public Criteria andBankIdIsNotNull() {
            addCriterion("bank_id is not null");
            return (Criteria) this;
        }

        public Criteria andBankIdEqualTo(String value) {
            addCriterion("bank_id =", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdNotEqualTo(String value) {
            addCriterion("bank_id <>", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdGreaterThan(String value) {
            addCriterion("bank_id >", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdGreaterThanOrEqualTo(String value) {
            addCriterion("bank_id >=", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdLessThan(String value) {
            addCriterion("bank_id <", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdLessThanOrEqualTo(String value) {
            addCriterion("bank_id <=", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdLike(String value) {
            addCriterion("bank_id like", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdNotLike(String value) {
            addCriterion("bank_id not like", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdIn(List<String> values) {
            addCriterion("bank_id in", values, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdNotIn(List<String> values) {
            addCriterion("bank_id not in", values, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdBetween(String value1, String value2) {
            addCriterion("bank_id between", value1, value2, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdNotBetween(String value1, String value2) {
            addCriterion("bank_id not between", value1, value2, "bankId");
            return (Criteria) this;
        }

        public Criteria andSubIdIsNull() {
            addCriterion("sub_id is null");
            return (Criteria) this;
        }

        public Criteria andSubIdIsNotNull() {
            addCriterion("sub_id is not null");
            return (Criteria) this;
        }

        public Criteria andSubIdEqualTo(String value) {
            addCriterion("sub_id =", value, "subId");
            return (Criteria) this;
        }

        public Criteria andSubIdNotEqualTo(String value) {
            addCriterion("sub_id <>", value, "subId");
            return (Criteria) this;
        }

        public Criteria andSubIdGreaterThan(String value) {
            addCriterion("sub_id >", value, "subId");
            return (Criteria) this;
        }

        public Criteria andSubIdGreaterThanOrEqualTo(String value) {
            addCriterion("sub_id >=", value, "subId");
            return (Criteria) this;
        }

        public Criteria andSubIdLessThan(String value) {
            addCriterion("sub_id <", value, "subId");
            return (Criteria) this;
        }

        public Criteria andSubIdLessThanOrEqualTo(String value) {
            addCriterion("sub_id <=", value, "subId");
            return (Criteria) this;
        }

        public Criteria andSubIdLike(String value) {
            addCriterion("sub_id like", value, "subId");
            return (Criteria) this;
        }

        public Criteria andSubIdNotLike(String value) {
            addCriterion("sub_id not like", value, "subId");
            return (Criteria) this;
        }

        public Criteria andSubIdIn(List<String> values) {
            addCriterion("sub_id in", values, "subId");
            return (Criteria) this;
        }

        public Criteria andSubIdNotIn(List<String> values) {
            addCriterion("sub_id not in", values, "subId");
            return (Criteria) this;
        }

        public Criteria andSubIdBetween(String value1, String value2) {
            addCriterion("sub_id between", value1, value2, "subId");
            return (Criteria) this;
        }

        public Criteria andSubIdNotBetween(String value1, String value2) {
            addCriterion("sub_id not between", value1, value2, "subId");
            return (Criteria) this;
        }

        public Criteria andBankOpenIsNull() {
            addCriterion("bank_open is null");
            return (Criteria) this;
        }

        public Criteria andBankOpenIsNotNull() {
            addCriterion("bank_open is not null");
            return (Criteria) this;
        }

        public Criteria andBankOpenEqualTo(String value) {
            addCriterion("bank_open =", value, "bankOpen");
            return (Criteria) this;
        }

        public Criteria andBankOpenNotEqualTo(String value) {
            addCriterion("bank_open <>", value, "bankOpen");
            return (Criteria) this;
        }

        public Criteria andBankOpenGreaterThan(String value) {
            addCriterion("bank_open >", value, "bankOpen");
            return (Criteria) this;
        }

        public Criteria andBankOpenGreaterThanOrEqualTo(String value) {
            addCriterion("bank_open >=", value, "bankOpen");
            return (Criteria) this;
        }

        public Criteria andBankOpenLessThan(String value) {
            addCriterion("bank_open <", value, "bankOpen");
            return (Criteria) this;
        }

        public Criteria andBankOpenLessThanOrEqualTo(String value) {
            addCriterion("bank_open <=", value, "bankOpen");
            return (Criteria) this;
        }

        public Criteria andBankOpenLike(String value) {
            addCriterion("bank_open like", value, "bankOpen");
            return (Criteria) this;
        }

        public Criteria andBankOpenNotLike(String value) {
            addCriterion("bank_open not like", value, "bankOpen");
            return (Criteria) this;
        }

        public Criteria andBankOpenIn(List<String> values) {
            addCriterion("bank_open in", values, "bankOpen");
            return (Criteria) this;
        }

        public Criteria andBankOpenNotIn(List<String> values) {
            addCriterion("bank_open not in", values, "bankOpen");
            return (Criteria) this;
        }

        public Criteria andBankOpenBetween(String value1, String value2) {
            addCriterion("bank_open between", value1, value2, "bankOpen");
            return (Criteria) this;
        }

        public Criteria andBankOpenNotBetween(String value1, String value2) {
            addCriterion("bank_open not between", value1, value2, "bankOpen");
            return (Criteria) this;
        }

        public Criteria andAccountNameIsNull() {
            addCriterion("account_name is null");
            return (Criteria) this;
        }

        public Criteria andAccountNameIsNotNull() {
            addCriterion("account_name is not null");
            return (Criteria) this;
        }

        public Criteria andAccountNameEqualTo(String value) {
            addCriterion("account_name =", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameNotEqualTo(String value) {
            addCriterion("account_name <>", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameGreaterThan(String value) {
            addCriterion("account_name >", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameGreaterThanOrEqualTo(String value) {
            addCriterion("account_name >=", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameLessThan(String value) {
            addCriterion("account_name <", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameLessThanOrEqualTo(String value) {
            addCriterion("account_name <=", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameLike(String value) {
            addCriterion("account_name like", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameNotLike(String value) {
            addCriterion("account_name not like", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameIn(List<String> values) {
            addCriterion("account_name in", values, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameNotIn(List<String> values) {
            addCriterion("account_name not in", values, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameBetween(String value1, String value2) {
            addCriterion("account_name between", value1, value2, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameNotBetween(String value1, String value2) {
            addCriterion("account_name not between", value1, value2, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNumberIsNull() {
            addCriterion("account_number is null");
            return (Criteria) this;
        }

        public Criteria andAccountNumberIsNotNull() {
            addCriterion("account_number is not null");
            return (Criteria) this;
        }

        public Criteria andAccountNumberEqualTo(String value) {
            addCriterion("account_number =", value, "accountNumber");
            return (Criteria) this;
        }

        public Criteria andAccountNumberNotEqualTo(String value) {
            addCriterion("account_number <>", value, "accountNumber");
            return (Criteria) this;
        }

        public Criteria andAccountNumberGreaterThan(String value) {
            addCriterion("account_number >", value, "accountNumber");
            return (Criteria) this;
        }

        public Criteria andAccountNumberGreaterThanOrEqualTo(String value) {
            addCriterion("account_number >=", value, "accountNumber");
            return (Criteria) this;
        }

        public Criteria andAccountNumberLessThan(String value) {
            addCriterion("account_number <", value, "accountNumber");
            return (Criteria) this;
        }

        public Criteria andAccountNumberLessThanOrEqualTo(String value) {
            addCriterion("account_number <=", value, "accountNumber");
            return (Criteria) this;
        }

        public Criteria andAccountNumberLike(String value) {
            addCriterion("account_number like", value, "accountNumber");
            return (Criteria) this;
        }

        public Criteria andAccountNumberNotLike(String value) {
            addCriterion("account_number not like", value, "accountNumber");
            return (Criteria) this;
        }

        public Criteria andAccountNumberIn(List<String> values) {
            addCriterion("account_number in", values, "accountNumber");
            return (Criteria) this;
        }

        public Criteria andAccountNumberNotIn(List<String> values) {
            addCriterion("account_number not in", values, "accountNumber");
            return (Criteria) this;
        }

        public Criteria andAccountNumberBetween(String value1, String value2) {
            addCriterion("account_number between", value1, value2, "accountNumber");
            return (Criteria) this;
        }

        public Criteria andAccountNumberNotBetween(String value1, String value2) {
            addCriterion("account_number not between", value1, value2, "accountNumber");
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