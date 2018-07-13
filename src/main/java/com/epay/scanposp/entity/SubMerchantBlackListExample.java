package com.epay.scanposp.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SubMerchantBlackListExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitSize;

    public SubMerchantBlackListExample() {
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

                
        public Criteria andBlackTypeIsNull() {
            addCriterion("black_type is null");
            return (Criteria) this;
        }

        public Criteria andBlackTypeIsNotNull() {
            addCriterion("black_type is not null");
            return (Criteria) this;
        }

        public Criteria andBlackTypeEqualTo(String value) {
            addCriterion("black_type =", value, "blackType");
            return (Criteria) this;
        }

        public Criteria andBlackTypeNotEqualTo(String value) {
            addCriterion("black_type <>", value, "blackType");
            return (Criteria) this;
        }

        public Criteria andBlackTypeGreaterThan(String value) {
            addCriterion("black_type >", value, "blackType");
            return (Criteria) this;
        }

        public Criteria andBlackTypeGreaterThanOrEqualTo(String value) {
            addCriterion("black_type >=", value, "blackType");
            return (Criteria) this;
        }

        public Criteria andBlackTypeLessThan(String value) {
            addCriterion("black_type <", value, "blackType");
            return (Criteria) this;
        }

        public Criteria andBlackTypeLessThanOrEqualTo(String value) {
            addCriterion("black_type <=", value, "blackType");
            return (Criteria) this;
        }

        public Criteria andBlackTypeLike(String value) {
            addCriterion("black_type like", value, "blackType");
            return (Criteria) this;
        }

        public Criteria andBlackTypeNotLike(String value) {
            addCriterion("black_type not like", value, "blackType");
            return (Criteria) this;
        }

        public Criteria andBlackTypeIn(List<String> values) {
            addCriterion("black_type in", values, "blackType");
            return (Criteria) this;
        }

        public Criteria andBlackTypeNotIn(List<String> values) {
            addCriterion("black_type not in", values, "blackType");
            return (Criteria) this;
        }

        public Criteria andBlackTypeBetween(String value1, String value2) {
            addCriterion("black_type between", value1, value2, "blackType");
            return (Criteria) this;
        }
        
        public Criteria andBlackTypeNotBetween(String value1, String value2) {
            addCriterion("black_type not between", value1, value2, "blackType");
            return (Criteria) this;
        }
        
        public Criteria andRouteCodeIsNull() {
            addCriterion("route_code is null");
            return (Criteria) this;
        }

        public Criteria andRouteCodeIsNotNull() {
            addCriterion("route_code is not null");
            return (Criteria) this;
        }

        public Criteria andRouteCodeEqualTo(String value) {
            addCriterion("route_code =", value, "routeCode");
            return (Criteria) this;
        }

        public Criteria andRouteCodeNotEqualTo(String value) {
            addCriterion("route_code <>", value, "routeCode");
            return (Criteria) this;
        }

        public Criteria andRouteCodeGreaterThan(String value) {
            addCriterion("route_code >", value, "routeCode");
            return (Criteria) this;
        }

        public Criteria andRouteCodeGreaterThanOrEqualTo(String value) {
            addCriterion("route_code >=", value, "routeCode");
            return (Criteria) this;
        }

        public Criteria andRouteCodeLessThan(String value) {
            addCriterion("route_code <", value, "routeCode");
            return (Criteria) this;
        }

        public Criteria andRouteCodeLessThanOrEqualTo(String value) {
            addCriterion("route_code <=", value, "routeCode");
            return (Criteria) this;
        }

        public Criteria andRouteCodeLike(String value) {
            addCriterion("route_code like", value, "routeCode");
            return (Criteria) this;
        }

        public Criteria andRouteCodeNotLike(String value) {
            addCriterion("route_code not like", value, "routeCode");
            return (Criteria) this;
        }

        public Criteria andRouteCodeIn(List<String> values) {
            addCriterion("route_code in", values, "routeCode");
            return (Criteria) this;
        }

        public Criteria andRouteCodeNotIn(List<String> values) {
            addCriterion("route_code not in", values, "routeCode");
            return (Criteria) this;
        }

        public Criteria andRouteCodeBetween(String value1, String value2) {
            addCriterion("route_code between", value1, value2, "routeCode");
            return (Criteria) this;
        }

        public Criteria andRouteCodeNotBetween(String value1, String value2) {
            addCriterion("route_code not between", value1, value2, "routeCode");
            return (Criteria) this;
        }
        
        public Criteria andSubMerchantCodeIsNull() {
            addCriterion("sub_merchant_code is null");
            return (Criteria) this;
        }

        public Criteria andSubMerchantCodeIsNotNull() {
            addCriterion("sub_merchant_code is not null");
            return (Criteria) this;
        }

        public Criteria andSubMerchantCodeEqualTo(String value) {
            addCriterion("sub_merchant_code =", value, "subMerchantCode");
            return (Criteria) this;
        }

        public Criteria andSubMerchantCodeNotEqualTo(String value) {
            addCriterion("sub_merchant_code <>", value, "subMerchantCode");
            return (Criteria) this;
        }

        public Criteria andSubMerchantCodeGreaterThan(String value) {
            addCriterion("sub_merchant_code >", value, "subMerchantCode");
            return (Criteria) this;
        }

        public Criteria andSubMerchantCodeGreaterThanOrEqualTo(String value) {
            addCriterion("sub_merchant_code >=", value, "subMerchantCode");
            return (Criteria) this;
        }

        public Criteria andSubMerchantCodeLessThan(String value) {
            addCriterion("sub_merchant_code <", value, "subMerchantCode");
            return (Criteria) this;
        }

        public Criteria andSubMerchantCodeLessThanOrEqualTo(String value) {
            addCriterion("sub_merchant_code <=", value, "subMerchantCode");
            return (Criteria) this;
        }

        public Criteria andSubMerchantCodeLike(String value) {
            addCriterion("sub_merchant_code like", value, "subMerchantCode");
            return (Criteria) this;
        }

        public Criteria andSubMerchantCodeNotLike(String value) {
            addCriterion("sub_merchant_code not like", value, "subMerchantCode");
            return (Criteria) this;
        }

        public Criteria andSubMerchantCodeIn(List<String> values) {
            addCriterion("sub_merchant_code in", values, "subMerchantCode");
            return (Criteria) this;
        }

        public Criteria andSubMerchantCodeNotIn(List<String> values) {
            addCriterion("sub_merchant_code not in", values, "subMerchantCode");
            return (Criteria) this;
        }

        public Criteria andSubMerchantCodeBetween(String value1, String value2) {
            addCriterion("sub_merchant_code between", value1, value2, "subMerchantCode");
            return (Criteria) this;
        }

        public Criteria andSubMerchantCodeNotBetween(String value1, String value2) {
            addCriterion("sub_merchant_code not between", value1, value2, "subMerchantCode");
            return (Criteria) this;
        }

        
        public Criteria andTradeDateIsNull() {
            addCriterion("trade_date is null");
            return (Criteria) this;
        }

        public Criteria andTradeDateIsNotNull() {
            addCriterion("trade_date is not null");
            return (Criteria) this;
        }

        public Criteria andTradeDateEqualTo(String value) {
            addCriterion("trade_date =", value, "tradeDate");
            return (Criteria) this;
        }

        public Criteria andTradeDateNotEqualTo(String value) {
            addCriterion("trade_date <>", value, "tradeDate");
            return (Criteria) this;
        }

        public Criteria andTradeDateGreaterThan(String value) {
            addCriterion("trade_date >", value, "tradeDate");
            return (Criteria) this;
        }

        public Criteria andTradeDateGreaterThanOrEqualTo(String value) {
            addCriterion("trade_date >=", value, "tradeDate");
            return (Criteria) this;
        }

        public Criteria andTradeDateLessThan(String value) {
            addCriterion("trade_date <", value, "tradeDate");
            return (Criteria) this;
        }

        public Criteria andTradeDateLessThanOrEqualTo(String value) {
            addCriterion("trade_date <=", value, "tradeDate");
            return (Criteria) this;
        }

        public Criteria andTradeDateLike(String value) {
            addCriterion("trade_date like", value, "tradeDate");
            return (Criteria) this;
        }

        public Criteria andTradeDateNotLike(String value) {
            addCriterion("trade_date not like", value, "tradeDate");
            return (Criteria) this;
        }

        public Criteria andTradeDateIn(List<String> values) {
            addCriterion("trade_date in", values, "tradeDate");
            return (Criteria) this;
        }

        public Criteria andTradeDateNotIn(List<String> values) {
            addCriterion("trade_date not in", values, "tradeDate");
            return (Criteria) this;
        }

        public Criteria andTradeDateBetween(String value1, String value2) {
            addCriterion("trade_date between", value1, value2, "tradeDate");
            return (Criteria) this;
        }

        public Criteria andTradeDateNotBetween(String value1, String value2) {
            addCriterion("trade_date not between", value1, value2, "tradeDate");
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