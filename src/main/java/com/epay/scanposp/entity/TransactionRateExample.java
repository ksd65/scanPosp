package com.epay.scanposp.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionRateExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitSize;

    public TransactionRateExample() {
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