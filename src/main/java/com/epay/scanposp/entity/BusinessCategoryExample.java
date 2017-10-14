package com.epay.scanposp.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BusinessCategoryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitSize;

    public BusinessCategoryExample() {
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

        public Criteria andWxCategoryNameIsNull() {
            addCriterion("wx_category_name is null");
            return (Criteria) this;
        }

        public Criteria andWxCategoryNameIsNotNull() {
            addCriterion("wx_category_name is not null");
            return (Criteria) this;
        }

        public Criteria andWxCategoryNameEqualTo(String value) {
            addCriterion("wx_category_name =", value, "wxCategoryName");
            return (Criteria) this;
        }

        public Criteria andWxCategoryNameNotEqualTo(String value) {
            addCriterion("wx_category_name <>", value, "wxCategoryName");
            return (Criteria) this;
        }

        public Criteria andWxCategoryNameGreaterThan(String value) {
            addCriterion("wx_category_name >", value, "wxCategoryName");
            return (Criteria) this;
        }

        public Criteria andWxCategoryNameGreaterThanOrEqualTo(String value) {
            addCriterion("wx_category_name >=", value, "wxCategoryName");
            return (Criteria) this;
        }

        public Criteria andWxCategoryNameLessThan(String value) {
            addCriterion("wx_category_name <", value, "wxCategoryName");
            return (Criteria) this;
        }

        public Criteria andWxCategoryNameLessThanOrEqualTo(String value) {
            addCriterion("wx_category_name <=", value, "wxCategoryName");
            return (Criteria) this;
        }

        public Criteria andWxCategoryNameLike(String value) {
            addCriterion("wx_category_name like", value, "wxCategoryName");
            return (Criteria) this;
        }

        public Criteria andWxCategoryNameNotLike(String value) {
            addCriterion("wx_category_name not like", value, "wxCategoryName");
            return (Criteria) this;
        }

        public Criteria andWxCategoryNameIn(List<String> values) {
            addCriterion("wx_category_name in", values, "wxCategoryName");
            return (Criteria) this;
        }

        public Criteria andWxCategoryNameNotIn(List<String> values) {
            addCriterion("wx_category_name not in", values, "wxCategoryName");
            return (Criteria) this;
        }

        public Criteria andWxCategoryNameBetween(String value1, String value2) {
            addCriterion("wx_category_name between", value1, value2, "wxCategoryName");
            return (Criteria) this;
        }

        public Criteria andWxCategoryNameNotBetween(String value1, String value2) {
            addCriterion("wx_category_name not between", value1, value2, "wxCategoryName");
            return (Criteria) this;
        }

        public Criteria andWxCategoryIdIsNull() {
            addCriterion("wx_category_id is null");
            return (Criteria) this;
        }

        public Criteria andWxCategoryIdIsNotNull() {
            addCriterion("wx_category_id is not null");
            return (Criteria) this;
        }

        public Criteria andWxCategoryIdEqualTo(String value) {
            addCriterion("wx_category_id =", value, "wxCategoryId");
            return (Criteria) this;
        }

        public Criteria andWxCategoryIdNotEqualTo(String value) {
            addCriterion("wx_category_id <>", value, "wxCategoryId");
            return (Criteria) this;
        }

        public Criteria andWxCategoryIdGreaterThan(String value) {
            addCriterion("wx_category_id >", value, "wxCategoryId");
            return (Criteria) this;
        }

        public Criteria andWxCategoryIdGreaterThanOrEqualTo(String value) {
            addCriterion("wx_category_id >=", value, "wxCategoryId");
            return (Criteria) this;
        }

        public Criteria andWxCategoryIdLessThan(String value) {
            addCriterion("wx_category_id <", value, "wxCategoryId");
            return (Criteria) this;
        }

        public Criteria andWxCategoryIdLessThanOrEqualTo(String value) {
            addCriterion("wx_category_id <=", value, "wxCategoryId");
            return (Criteria) this;
        }

        public Criteria andWxCategoryIdLike(String value) {
            addCriterion("wx_category_id like", value, "wxCategoryId");
            return (Criteria) this;
        }

        public Criteria andWxCategoryIdNotLike(String value) {
            addCriterion("wx_category_id not like", value, "wxCategoryId");
            return (Criteria) this;
        }

        public Criteria andWxCategoryIdIn(List<String> values) {
            addCriterion("wx_category_id in", values, "wxCategoryId");
            return (Criteria) this;
        }

        public Criteria andWxCategoryIdNotIn(List<String> values) {
            addCriterion("wx_category_id not in", values, "wxCategoryId");
            return (Criteria) this;
        }

        public Criteria andWxCategoryIdBetween(String value1, String value2) {
            addCriterion("wx_category_id between", value1, value2, "wxCategoryId");
            return (Criteria) this;
        }

        public Criteria andWxCategoryIdNotBetween(String value1, String value2) {
            addCriterion("wx_category_id not between", value1, value2, "wxCategoryId");
            return (Criteria) this;
        }

        public Criteria andZfbCategoryNameIsNull() {
            addCriterion("zfb_category_name is null");
            return (Criteria) this;
        }

        public Criteria andZfbCategoryNameIsNotNull() {
            addCriterion("zfb_category_name is not null");
            return (Criteria) this;
        }

        public Criteria andZfbCategoryNameEqualTo(String value) {
            addCriterion("zfb_category_name =", value, "zfbCategoryName");
            return (Criteria) this;
        }

        public Criteria andZfbCategoryNameNotEqualTo(String value) {
            addCriterion("zfb_category_name <>", value, "zfbCategoryName");
            return (Criteria) this;
        }

        public Criteria andZfbCategoryNameGreaterThan(String value) {
            addCriterion("zfb_category_name >", value, "zfbCategoryName");
            return (Criteria) this;
        }

        public Criteria andZfbCategoryNameGreaterThanOrEqualTo(String value) {
            addCriterion("zfb_category_name >=", value, "zfbCategoryName");
            return (Criteria) this;
        }

        public Criteria andZfbCategoryNameLessThan(String value) {
            addCriterion("zfb_category_name <", value, "zfbCategoryName");
            return (Criteria) this;
        }

        public Criteria andZfbCategoryNameLessThanOrEqualTo(String value) {
            addCriterion("zfb_category_name <=", value, "zfbCategoryName");
            return (Criteria) this;
        }

        public Criteria andZfbCategoryNameLike(String value) {
            addCriterion("zfb_category_name like", value, "zfbCategoryName");
            return (Criteria) this;
        }

        public Criteria andZfbCategoryNameNotLike(String value) {
            addCriterion("zfb_category_name not like", value, "zfbCategoryName");
            return (Criteria) this;
        }

        public Criteria andZfbCategoryNameIn(List<String> values) {
            addCriterion("zfb_category_name in", values, "zfbCategoryName");
            return (Criteria) this;
        }

        public Criteria andZfbCategoryNameNotIn(List<String> values) {
            addCriterion("zfb_category_name not in", values, "zfbCategoryName");
            return (Criteria) this;
        }

        public Criteria andZfbCategoryNameBetween(String value1, String value2) {
            addCriterion("zfb_category_name between", value1, value2, "zfbCategoryName");
            return (Criteria) this;
        }

        public Criteria andZfbCategoryNameNotBetween(String value1, String value2) {
            addCriterion("zfb_category_name not between", value1, value2, "zfbCategoryName");
            return (Criteria) this;
        }

        public Criteria andZfbCategoryIdIsNull() {
            addCriterion("zfb_category_id is null");
            return (Criteria) this;
        }

        public Criteria andZfbCategoryIdIsNotNull() {
            addCriterion("zfb_category_id is not null");
            return (Criteria) this;
        }

        public Criteria andZfbCategoryIdEqualTo(String value) {
            addCriterion("zfb_category_id =", value, "zfbCategoryId");
            return (Criteria) this;
        }

        public Criteria andZfbCategoryIdNotEqualTo(String value) {
            addCriterion("zfb_category_id <>", value, "zfbCategoryId");
            return (Criteria) this;
        }

        public Criteria andZfbCategoryIdGreaterThan(String value) {
            addCriterion("zfb_category_id >", value, "zfbCategoryId");
            return (Criteria) this;
        }

        public Criteria andZfbCategoryIdGreaterThanOrEqualTo(String value) {
            addCriterion("zfb_category_id >=", value, "zfbCategoryId");
            return (Criteria) this;
        }

        public Criteria andZfbCategoryIdLessThan(String value) {
            addCriterion("zfb_category_id <", value, "zfbCategoryId");
            return (Criteria) this;
        }

        public Criteria andZfbCategoryIdLessThanOrEqualTo(String value) {
            addCriterion("zfb_category_id <=", value, "zfbCategoryId");
            return (Criteria) this;
        }

        public Criteria andZfbCategoryIdLike(String value) {
            addCriterion("zfb_category_id like", value, "zfbCategoryId");
            return (Criteria) this;
        }

        public Criteria andZfbCategoryIdNotLike(String value) {
            addCriterion("zfb_category_id not like", value, "zfbCategoryId");
            return (Criteria) this;
        }

        public Criteria andZfbCategoryIdIn(List<String> values) {
            addCriterion("zfb_category_id in", values, "zfbCategoryId");
            return (Criteria) this;
        }

        public Criteria andZfbCategoryIdNotIn(List<String> values) {
            addCriterion("zfb_category_id not in", values, "zfbCategoryId");
            return (Criteria) this;
        }

        public Criteria andZfbCategoryIdBetween(String value1, String value2) {
            addCriterion("zfb_category_id between", value1, value2, "zfbCategoryId");
            return (Criteria) this;
        }

        public Criteria andZfbCategoryIdNotBetween(String value1, String value2) {
            addCriterion("zfb_category_id not between", value1, value2, "zfbCategoryId");
            return (Criteria) this;
        }

        public Criteria andQqCategoryNameIsNull() {
            addCriterion("qq_category_name is null");
            return (Criteria) this;
        }

        public Criteria andQqCategoryNameIsNotNull() {
            addCriterion("qq_category_name is not null");
            return (Criteria) this;
        }

        public Criteria andQqCategoryNameEqualTo(String value) {
            addCriterion("qq_category_name =", value, "qqCategoryName");
            return (Criteria) this;
        }

        public Criteria andQqCategoryNameNotEqualTo(String value) {
            addCriterion("qq_category_name <>", value, "qqCategoryName");
            return (Criteria) this;
        }

        public Criteria andQqCategoryNameGreaterThan(String value) {
            addCriterion("qq_category_name >", value, "qqCategoryName");
            return (Criteria) this;
        }

        public Criteria andQqCategoryNameGreaterThanOrEqualTo(String value) {
            addCriterion("qq_category_name >=", value, "qqCategoryName");
            return (Criteria) this;
        }

        public Criteria andQqCategoryNameLessThan(String value) {
            addCriterion("qq_category_name <", value, "qqCategoryName");
            return (Criteria) this;
        }

        public Criteria andQqCategoryNameLessThanOrEqualTo(String value) {
            addCriterion("qq_category_name <=", value, "qqCategoryName");
            return (Criteria) this;
        }

        public Criteria andQqCategoryNameLike(String value) {
            addCriterion("qq_category_name like", value, "qqCategoryName");
            return (Criteria) this;
        }

        public Criteria andQqCategoryNameNotLike(String value) {
            addCriterion("qq_category_name not like", value, "qqCategoryName");
            return (Criteria) this;
        }

        public Criteria andQqCategoryNameIn(List<String> values) {
            addCriterion("qq_category_name in", values, "qqCategoryName");
            return (Criteria) this;
        }

        public Criteria andQqCategoryNameNotIn(List<String> values) {
            addCriterion("qq_category_name not in", values, "qqCategoryName");
            return (Criteria) this;
        }

        public Criteria andQqCategoryNameBetween(String value1, String value2) {
            addCriterion("qq_category_name between", value1, value2, "qqCategoryName");
            return (Criteria) this;
        }

        public Criteria andQqCategoryNameNotBetween(String value1, String value2) {
            addCriterion("qq_category_name not between", value1, value2, "qqCategoryName");
            return (Criteria) this;
        }

        public Criteria andQqCategoryIdIsNull() {
            addCriterion("qq_category_id is null");
            return (Criteria) this;
        }

        public Criteria andQqCategoryIdIsNotNull() {
            addCriterion("qq_category_id is not null");
            return (Criteria) this;
        }

        public Criteria andQqCategoryIdEqualTo(String value) {
            addCriterion("qq_category_id =", value, "qqCategoryId");
            return (Criteria) this;
        }

        public Criteria andQqCategoryIdNotEqualTo(String value) {
            addCriterion("qq_category_id <>", value, "qqCategoryId");
            return (Criteria) this;
        }

        public Criteria andQqCategoryIdGreaterThan(String value) {
            addCriterion("qq_category_id >", value, "qqCategoryId");
            return (Criteria) this;
        }

        public Criteria andQqCategoryIdGreaterThanOrEqualTo(String value) {
            addCriterion("qq_category_id >=", value, "qqCategoryId");
            return (Criteria) this;
        }

        public Criteria andQqCategoryIdLessThan(String value) {
            addCriterion("qq_category_id <", value, "qqCategoryId");
            return (Criteria) this;
        }

        public Criteria andQqCategoryIdLessThanOrEqualTo(String value) {
            addCriterion("qq_category_id <=", value, "qqCategoryId");
            return (Criteria) this;
        }

        public Criteria andQqCategoryIdLike(String value) {
            addCriterion("qq_category_id like", value, "qqCategoryId");
            return (Criteria) this;
        }

        public Criteria andQqCategoryIdNotLike(String value) {
            addCriterion("qq_category_id not like", value, "qqCategoryId");
            return (Criteria) this;
        }

        public Criteria andQqCategoryIdIn(List<String> values) {
            addCriterion("qq_category_id in", values, "qqCategoryId");
            return (Criteria) this;
        }

        public Criteria andQqCategoryIdNotIn(List<String> values) {
            addCriterion("qq_category_id not in", values, "qqCategoryId");
            return (Criteria) this;
        }

        public Criteria andQqCategoryIdBetween(String value1, String value2) {
            addCriterion("qq_category_id between", value1, value2, "qqCategoryId");
            return (Criteria) this;
        }

        public Criteria andQqCategoryIdNotBetween(String value1, String value2) {
            addCriterion("qq_category_id not between", value1, value2, "qqCategoryId");
            return (Criteria) this;
        }

        public Criteria andBdCategoryNameIsNull() {
            addCriterion("bd_category_name is null");
            return (Criteria) this;
        }

        public Criteria andBdCategoryNameIsNotNull() {
            addCriterion("bd_category_name is not null");
            return (Criteria) this;
        }

        public Criteria andBdCategoryNameEqualTo(String value) {
            addCriterion("bd_category_name =", value, "bdCategoryName");
            return (Criteria) this;
        }

        public Criteria andBdCategoryNameNotEqualTo(String value) {
            addCriterion("bd_category_name <>", value, "bdCategoryName");
            return (Criteria) this;
        }

        public Criteria andBdCategoryNameGreaterThan(String value) {
            addCriterion("bd_category_name >", value, "bdCategoryName");
            return (Criteria) this;
        }

        public Criteria andBdCategoryNameGreaterThanOrEqualTo(String value) {
            addCriterion("bd_category_name >=", value, "bdCategoryName");
            return (Criteria) this;
        }

        public Criteria andBdCategoryNameLessThan(String value) {
            addCriterion("bd_category_name <", value, "bdCategoryName");
            return (Criteria) this;
        }

        public Criteria andBdCategoryNameLessThanOrEqualTo(String value) {
            addCriterion("bd_category_name <=", value, "bdCategoryName");
            return (Criteria) this;
        }

        public Criteria andBdCategoryNameLike(String value) {
            addCriterion("bd_category_name like", value, "bdCategoryName");
            return (Criteria) this;
        }

        public Criteria andBdCategoryNameNotLike(String value) {
            addCriterion("bd_category_name not like", value, "bdCategoryName");
            return (Criteria) this;
        }

        public Criteria andBdCategoryNameIn(List<String> values) {
            addCriterion("bd_category_name in", values, "bdCategoryName");
            return (Criteria) this;
        }

        public Criteria andBdCategoryNameNotIn(List<String> values) {
            addCriterion("bd_category_name not in", values, "bdCategoryName");
            return (Criteria) this;
        }

        public Criteria andBdCategoryNameBetween(String value1, String value2) {
            addCriterion("bd_category_name between", value1, value2, "bdCategoryName");
            return (Criteria) this;
        }

        public Criteria andBdCategoryNameNotBetween(String value1, String value2) {
            addCriterion("bd_category_name not between", value1, value2, "bdCategoryName");
            return (Criteria) this;
        }

        public Criteria andBdCategoryIdIsNull() {
            addCriterion("bd_category_id is null");
            return (Criteria) this;
        }

        public Criteria andBdCategoryIdIsNotNull() {
            addCriterion("bd_category_id is not null");
            return (Criteria) this;
        }

        public Criteria andBdCategoryIdEqualTo(String value) {
            addCriterion("bd_category_id =", value, "bdCategoryId");
            return (Criteria) this;
        }

        public Criteria andBdCategoryIdNotEqualTo(String value) {
            addCriterion("bd_category_id <>", value, "bdCategoryId");
            return (Criteria) this;
        }

        public Criteria andBdCategoryIdGreaterThan(String value) {
            addCriterion("bd_category_id >", value, "bdCategoryId");
            return (Criteria) this;
        }

        public Criteria andBdCategoryIdGreaterThanOrEqualTo(String value) {
            addCriterion("bd_category_id >=", value, "bdCategoryId");
            return (Criteria) this;
        }

        public Criteria andBdCategoryIdLessThan(String value) {
            addCriterion("bd_category_id <", value, "bdCategoryId");
            return (Criteria) this;
        }

        public Criteria andBdCategoryIdLessThanOrEqualTo(String value) {
            addCriterion("bd_category_id <=", value, "bdCategoryId");
            return (Criteria) this;
        }

        public Criteria andBdCategoryIdLike(String value) {
            addCriterion("bd_category_id like", value, "bdCategoryId");
            return (Criteria) this;
        }

        public Criteria andBdCategoryIdNotLike(String value) {
            addCriterion("bd_category_id not like", value, "bdCategoryId");
            return (Criteria) this;
        }

        public Criteria andBdCategoryIdIn(List<String> values) {
            addCriterion("bd_category_id in", values, "bdCategoryId");
            return (Criteria) this;
        }

        public Criteria andBdCategoryIdNotIn(List<String> values) {
            addCriterion("bd_category_id not in", values, "bdCategoryId");
            return (Criteria) this;
        }

        public Criteria andBdCategoryIdBetween(String value1, String value2) {
            addCriterion("bd_category_id between", value1, value2, "bdCategoryId");
            return (Criteria) this;
        }

        public Criteria andBdCategoryIdNotBetween(String value1, String value2) {
            addCriterion("bd_category_id not between", value1, value2, "bdCategoryId");
            return (Criteria) this;
        }

        public Criteria andJdCategoryNameIsNull() {
            addCriterion("jd_category_name is null");
            return (Criteria) this;
        }

        public Criteria andJdCategoryNameIsNotNull() {
            addCriterion("jd_category_name is not null");
            return (Criteria) this;
        }

        public Criteria andJdCategoryNameEqualTo(String value) {
            addCriterion("jd_category_name =", value, "jdCategoryName");
            return (Criteria) this;
        }

        public Criteria andJdCategoryNameNotEqualTo(String value) {
            addCriterion("jd_category_name <>", value, "jdCategoryName");
            return (Criteria) this;
        }

        public Criteria andJdCategoryNameGreaterThan(String value) {
            addCriterion("jd_category_name >", value, "jdCategoryName");
            return (Criteria) this;
        }

        public Criteria andJdCategoryNameGreaterThanOrEqualTo(String value) {
            addCriterion("jd_category_name >=", value, "jdCategoryName");
            return (Criteria) this;
        }

        public Criteria andJdCategoryNameLessThan(String value) {
            addCriterion("jd_category_name <", value, "jdCategoryName");
            return (Criteria) this;
        }

        public Criteria andJdCategoryNameLessThanOrEqualTo(String value) {
            addCriterion("jd_category_name <=", value, "jdCategoryName");
            return (Criteria) this;
        }

        public Criteria andJdCategoryNameLike(String value) {
            addCriterion("jd_category_name like", value, "jdCategoryName");
            return (Criteria) this;
        }

        public Criteria andJdCategoryNameNotLike(String value) {
            addCriterion("jd_category_name not like", value, "jdCategoryName");
            return (Criteria) this;
        }

        public Criteria andJdCategoryNameIn(List<String> values) {
            addCriterion("jd_category_name in", values, "jdCategoryName");
            return (Criteria) this;
        }

        public Criteria andJdCategoryNameNotIn(List<String> values) {
            addCriterion("jd_category_name not in", values, "jdCategoryName");
            return (Criteria) this;
        }

        public Criteria andJdCategoryNameBetween(String value1, String value2) {
            addCriterion("jd_category_name between", value1, value2, "jdCategoryName");
            return (Criteria) this;
        }

        public Criteria andJdCategoryNameNotBetween(String value1, String value2) {
            addCriterion("jd_category_name not between", value1, value2, "jdCategoryName");
            return (Criteria) this;
        }

        public Criteria andJdCategoryIdIsNull() {
            addCriterion("jd_category_id is null");
            return (Criteria) this;
        }

        public Criteria andJdCategoryIdIsNotNull() {
            addCriterion("jd_category_id is not null");
            return (Criteria) this;
        }

        public Criteria andJdCategoryIdEqualTo(String value) {
            addCriterion("jd_category_id =", value, "jdCategoryId");
            return (Criteria) this;
        }

        public Criteria andJdCategoryIdNotEqualTo(String value) {
            addCriterion("jd_category_id <>", value, "jdCategoryId");
            return (Criteria) this;
        }

        public Criteria andJdCategoryIdGreaterThan(String value) {
            addCriterion("jd_category_id >", value, "jdCategoryId");
            return (Criteria) this;
        }

        public Criteria andJdCategoryIdGreaterThanOrEqualTo(String value) {
            addCriterion("jd_category_id >=", value, "jdCategoryId");
            return (Criteria) this;
        }

        public Criteria andJdCategoryIdLessThan(String value) {
            addCriterion("jd_category_id <", value, "jdCategoryId");
            return (Criteria) this;
        }

        public Criteria andJdCategoryIdLessThanOrEqualTo(String value) {
            addCriterion("jd_category_id <=", value, "jdCategoryId");
            return (Criteria) this;
        }

        public Criteria andJdCategoryIdLike(String value) {
            addCriterion("jd_category_id like", value, "jdCategoryId");
            return (Criteria) this;
        }

        public Criteria andJdCategoryIdNotLike(String value) {
            addCriterion("jd_category_id not like", value, "jdCategoryId");
            return (Criteria) this;
        }

        public Criteria andJdCategoryIdIn(List<String> values) {
            addCriterion("jd_category_id in", values, "jdCategoryId");
            return (Criteria) this;
        }

        public Criteria andJdCategoryIdNotIn(List<String> values) {
            addCriterion("jd_category_id not in", values, "jdCategoryId");
            return (Criteria) this;
        }

        public Criteria andJdCategoryIdBetween(String value1, String value2) {
            addCriterion("jd_category_id between", value1, value2, "jdCategoryId");
            return (Criteria) this;
        }

        public Criteria andJdCategoryIdNotBetween(String value1, String value2) {
            addCriterion("jd_category_id not between", value1, value2, "jdCategoryId");
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