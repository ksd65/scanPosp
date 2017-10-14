package com.epay.scanposp.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AgentRateExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitSize;

    public AgentRateExample() {
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

        public Criteria andBonusQuickRateIsNull() {
            addCriterion("bonus_quick_rate is null");
            return (Criteria) this;
        }

        public Criteria andBonusQuickRateIsNotNull() {
            addCriterion("bonus_quick_rate is not null");
            return (Criteria) this;
        }

        public Criteria andBonusQuickRateEqualTo(BigDecimal value) {
            addCriterion("bonus_quick_rate =", value, "bonusQuickRate");
            return (Criteria) this;
        }

        public Criteria andBonusQuickRateNotEqualTo(BigDecimal value) {
            addCriterion("bonus_quick_rate <>", value, "bonusQuickRate");
            return (Criteria) this;
        }

        public Criteria andBonusQuickRateGreaterThan(BigDecimal value) {
            addCriterion("bonus_quick_rate >", value, "bonusQuickRate");
            return (Criteria) this;
        }

        public Criteria andBonusQuickRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("bonus_quick_rate >=", value, "bonusQuickRate");
            return (Criteria) this;
        }

        public Criteria andBonusQuickRateLessThan(BigDecimal value) {
            addCriterion("bonus_quick_rate <", value, "bonusQuickRate");
            return (Criteria) this;
        }

        public Criteria andBonusQuickRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("bonus_quick_rate <=", value, "bonusQuickRate");
            return (Criteria) this;
        }

        public Criteria andBonusQuickRateIn(List<BigDecimal> values) {
            addCriterion("bonus_quick_rate in", values, "bonusQuickRate");
            return (Criteria) this;
        }

        public Criteria andBonusQuickRateNotIn(List<BigDecimal> values) {
            addCriterion("bonus_quick_rate not in", values, "bonusQuickRate");
            return (Criteria) this;
        }

        public Criteria andBonusQuickRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bonus_quick_rate between", value1, value2, "bonusQuickRate");
            return (Criteria) this;
        }

        public Criteria andBonusQuickRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bonus_quick_rate not between", value1, value2, "bonusQuickRate");
            return (Criteria) this;
        }

        public Criteria andBonusQuickFeeIsNull() {
            addCriterion("bonus_quick_fee is null");
            return (Criteria) this;
        }

        public Criteria andBonusQuickFeeIsNotNull() {
            addCriterion("bonus_quick_fee is not null");
            return (Criteria) this;
        }

        public Criteria andBonusQuickFeeEqualTo(BigDecimal value) {
            addCriterion("bonus_quick_fee =", value, "bonusQuickFee");
            return (Criteria) this;
        }

        public Criteria andBonusQuickFeeNotEqualTo(BigDecimal value) {
            addCriterion("bonus_quick_fee <>", value, "bonusQuickFee");
            return (Criteria) this;
        }

        public Criteria andBonusQuickFeeGreaterThan(BigDecimal value) {
            addCriterion("bonus_quick_fee >", value, "bonusQuickFee");
            return (Criteria) this;
        }

        public Criteria andBonusQuickFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("bonus_quick_fee >=", value, "bonusQuickFee");
            return (Criteria) this;
        }

        public Criteria andBonusQuickFeeLessThan(BigDecimal value) {
            addCriterion("bonus_quick_fee <", value, "bonusQuickFee");
            return (Criteria) this;
        }

        public Criteria andBonusQuickFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("bonus_quick_fee <=", value, "bonusQuickFee");
            return (Criteria) this;
        }

        public Criteria andBonusQuickFeeIn(List<BigDecimal> values) {
            addCriterion("bonus_quick_fee in", values, "bonusQuickFee");
            return (Criteria) this;
        }

        public Criteria andBonusQuickFeeNotIn(List<BigDecimal> values) {
            addCriterion("bonus_quick_fee not in", values, "bonusQuickFee");
            return (Criteria) this;
        }

        public Criteria andBonusQuickFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bonus_quick_fee between", value1, value2, "bonusQuickFee");
            return (Criteria) this;
        }

        public Criteria andBonusQuickFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bonus_quick_fee not between", value1, value2, "bonusQuickFee");
            return (Criteria) this;
        }

        public Criteria andQuickRateIsNull() {
            addCriterion("quick_rate is null");
            return (Criteria) this;
        }

        public Criteria andQuickRateIsNotNull() {
            addCriterion("quick_rate is not null");
            return (Criteria) this;
        }

        public Criteria andQuickRateEqualTo(BigDecimal value) {
            addCriterion("quick_rate =", value, "quickRate");
            return (Criteria) this;
        }

        public Criteria andQuickRateNotEqualTo(BigDecimal value) {
            addCriterion("quick_rate <>", value, "quickRate");
            return (Criteria) this;
        }

        public Criteria andQuickRateGreaterThan(BigDecimal value) {
            addCriterion("quick_rate >", value, "quickRate");
            return (Criteria) this;
        }

        public Criteria andQuickRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("quick_rate >=", value, "quickRate");
            return (Criteria) this;
        }

        public Criteria andQuickRateLessThan(BigDecimal value) {
            addCriterion("quick_rate <", value, "quickRate");
            return (Criteria) this;
        }

        public Criteria andQuickRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("quick_rate <=", value, "quickRate");
            return (Criteria) this;
        }

        public Criteria andQuickRateIn(List<BigDecimal> values) {
            addCriterion("quick_rate in", values, "quickRate");
            return (Criteria) this;
        }

        public Criteria andQuickRateNotIn(List<BigDecimal> values) {
            addCriterion("quick_rate not in", values, "quickRate");
            return (Criteria) this;
        }

        public Criteria andQuickRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("quick_rate between", value1, value2, "quickRate");
            return (Criteria) this;
        }

        public Criteria andQuickRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("quick_rate not between", value1, value2, "quickRate");
            return (Criteria) this;
        }

        public Criteria andQuickFeeIsNull() {
            addCriterion("quick_fee is null");
            return (Criteria) this;
        }

        public Criteria andQuickFeeIsNotNull() {
            addCriterion("quick_fee is not null");
            return (Criteria) this;
        }

        public Criteria andQuickFeeEqualTo(BigDecimal value) {
            addCriterion("quick_fee =", value, "quickFee");
            return (Criteria) this;
        }

        public Criteria andQuickFeeNotEqualTo(BigDecimal value) {
            addCriterion("quick_fee <>", value, "quickFee");
            return (Criteria) this;
        }

        public Criteria andQuickFeeGreaterThan(BigDecimal value) {
            addCriterion("quick_fee >", value, "quickFee");
            return (Criteria) this;
        }

        public Criteria andQuickFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("quick_fee >=", value, "quickFee");
            return (Criteria) this;
        }

        public Criteria andQuickFeeLessThan(BigDecimal value) {
            addCriterion("quick_fee <", value, "quickFee");
            return (Criteria) this;
        }

        public Criteria andQuickFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("quick_fee <=", value, "quickFee");
            return (Criteria) this;
        }

        public Criteria andQuickFeeIn(List<BigDecimal> values) {
            addCriterion("quick_fee in", values, "quickFee");
            return (Criteria) this;
        }

        public Criteria andQuickFeeNotIn(List<BigDecimal> values) {
            addCriterion("quick_fee not in", values, "quickFee");
            return (Criteria) this;
        }

        public Criteria andQuickFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("quick_fee between", value1, value2, "quickFee");
            return (Criteria) this;
        }

        public Criteria andQuickFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("quick_fee not between", value1, value2, "quickFee");
            return (Criteria) this;
        }

        public Criteria andMT0DrawFeeIsNull() {
            addCriterion("m_t0_draw_fee is null");
            return (Criteria) this;
        }

        public Criteria andMT0DrawFeeIsNotNull() {
            addCriterion("m_t0_draw_fee is not null");
            return (Criteria) this;
        }

        public Criteria andMT0DrawFeeEqualTo(BigDecimal value) {
            addCriterion("m_t0_draw_fee =", value, "mT0DrawFee");
            return (Criteria) this;
        }

        public Criteria andMT0DrawFeeNotEqualTo(BigDecimal value) {
            addCriterion("m_t0_draw_fee <>", value, "mT0DrawFee");
            return (Criteria) this;
        }

        public Criteria andMT0DrawFeeGreaterThan(BigDecimal value) {
            addCriterion("m_t0_draw_fee >", value, "mT0DrawFee");
            return (Criteria) this;
        }

        public Criteria andMT0DrawFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("m_t0_draw_fee >=", value, "mT0DrawFee");
            return (Criteria) this;
        }

        public Criteria andMT0DrawFeeLessThan(BigDecimal value) {
            addCriterion("m_t0_draw_fee <", value, "mT0DrawFee");
            return (Criteria) this;
        }

        public Criteria andMT0DrawFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("m_t0_draw_fee <=", value, "mT0DrawFee");
            return (Criteria) this;
        }

        public Criteria andMT0DrawFeeIn(List<BigDecimal> values) {
            addCriterion("m_t0_draw_fee in", values, "mT0DrawFee");
            return (Criteria) this;
        }

        public Criteria andMT0DrawFeeNotIn(List<BigDecimal> values) {
            addCriterion("m_t0_draw_fee not in", values, "mT0DrawFee");
            return (Criteria) this;
        }

        public Criteria andMT0DrawFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("m_t0_draw_fee between", value1, value2, "mT0DrawFee");
            return (Criteria) this;
        }

        public Criteria andMT0DrawFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("m_t0_draw_fee not between", value1, value2, "mT0DrawFee");
            return (Criteria) this;
        }

        public Criteria andMT0TradeRateIsNull() {
            addCriterion("m_t0_trade_rate is null");
            return (Criteria) this;
        }

        public Criteria andMT0TradeRateIsNotNull() {
            addCriterion("m_t0_trade_rate is not null");
            return (Criteria) this;
        }

        public Criteria andMT0TradeRateEqualTo(BigDecimal value) {
            addCriterion("m_t0_trade_rate =", value, "mT0TradeRate");
            return (Criteria) this;
        }

        public Criteria andMT0TradeRateNotEqualTo(BigDecimal value) {
            addCriterion("m_t0_trade_rate <>", value, "mT0TradeRate");
            return (Criteria) this;
        }

        public Criteria andMT0TradeRateGreaterThan(BigDecimal value) {
            addCriterion("m_t0_trade_rate >", value, "mT0TradeRate");
            return (Criteria) this;
        }

        public Criteria andMT0TradeRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("m_t0_trade_rate >=", value, "mT0TradeRate");
            return (Criteria) this;
        }

        public Criteria andMT0TradeRateLessThan(BigDecimal value) {
            addCriterion("m_t0_trade_rate <", value, "mT0TradeRate");
            return (Criteria) this;
        }

        public Criteria andMT0TradeRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("m_t0_trade_rate <=", value, "mT0TradeRate");
            return (Criteria) this;
        }

        public Criteria andMT0TradeRateIn(List<BigDecimal> values) {
            addCriterion("m_t0_trade_rate in", values, "mT0TradeRate");
            return (Criteria) this;
        }

        public Criteria andMT0TradeRateNotIn(List<BigDecimal> values) {
            addCriterion("m_t0_trade_rate not in", values, "mT0TradeRate");
            return (Criteria) this;
        }

        public Criteria andMT0TradeRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("m_t0_trade_rate between", value1, value2, "mT0TradeRate");
            return (Criteria) this;
        }

        public Criteria andMT0TradeRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("m_t0_trade_rate not between", value1, value2, "mT0TradeRate");
            return (Criteria) this;
        }

        public Criteria andMT1DrawFeeIsNull() {
            addCriterion("m_t1_draw_fee is null");
            return (Criteria) this;
        }

        public Criteria andMT1DrawFeeIsNotNull() {
            addCriterion("m_t1_draw_fee is not null");
            return (Criteria) this;
        }

        public Criteria andMT1DrawFeeEqualTo(BigDecimal value) {
            addCriterion("m_t1_draw_fee =", value, "mT1DrawFee");
            return (Criteria) this;
        }

        public Criteria andMT1DrawFeeNotEqualTo(BigDecimal value) {
            addCriterion("m_t1_draw_fee <>", value, "mT1DrawFee");
            return (Criteria) this;
        }

        public Criteria andMT1DrawFeeGreaterThan(BigDecimal value) {
            addCriterion("m_t1_draw_fee >", value, "mT1DrawFee");
            return (Criteria) this;
        }

        public Criteria andMT1DrawFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("m_t1_draw_fee >=", value, "mT1DrawFee");
            return (Criteria) this;
        }

        public Criteria andMT1DrawFeeLessThan(BigDecimal value) {
            addCriterion("m_t1_draw_fee <", value, "mT1DrawFee");
            return (Criteria) this;
        }

        public Criteria andMT1DrawFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("m_t1_draw_fee <=", value, "mT1DrawFee");
            return (Criteria) this;
        }

        public Criteria andMT1DrawFeeIn(List<BigDecimal> values) {
            addCriterion("m_t1_draw_fee in", values, "mT1DrawFee");
            return (Criteria) this;
        }

        public Criteria andMT1DrawFeeNotIn(List<BigDecimal> values) {
            addCriterion("m_t1_draw_fee not in", values, "mT1DrawFee");
            return (Criteria) this;
        }

        public Criteria andMT1DrawFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("m_t1_draw_fee between", value1, value2, "mT1DrawFee");
            return (Criteria) this;
        }

        public Criteria andMT1DrawFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("m_t1_draw_fee not between", value1, value2, "mT1DrawFee");
            return (Criteria) this;
        }

        public Criteria andMT1TradeRateIsNull() {
            addCriterion("m_t1_trade_rate is null");
            return (Criteria) this;
        }

        public Criteria andMT1TradeRateIsNotNull() {
            addCriterion("m_t1_trade_rate is not null");
            return (Criteria) this;
        }

        public Criteria andMT1TradeRateEqualTo(BigDecimal value) {
            addCriterion("m_t1_trade_rate =", value, "mT1TradeRate");
            return (Criteria) this;
        }

        public Criteria andMT1TradeRateNotEqualTo(BigDecimal value) {
            addCriterion("m_t1_trade_rate <>", value, "mT1TradeRate");
            return (Criteria) this;
        }

        public Criteria andMT1TradeRateGreaterThan(BigDecimal value) {
            addCriterion("m_t1_trade_rate >", value, "mT1TradeRate");
            return (Criteria) this;
        }

        public Criteria andMT1TradeRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("m_t1_trade_rate >=", value, "mT1TradeRate");
            return (Criteria) this;
        }

        public Criteria andMT1TradeRateLessThan(BigDecimal value) {
            addCriterion("m_t1_trade_rate <", value, "mT1TradeRate");
            return (Criteria) this;
        }

        public Criteria andMT1TradeRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("m_t1_trade_rate <=", value, "mT1TradeRate");
            return (Criteria) this;
        }

        public Criteria andMT1TradeRateIn(List<BigDecimal> values) {
            addCriterion("m_t1_trade_rate in", values, "mT1TradeRate");
            return (Criteria) this;
        }

        public Criteria andMT1TradeRateNotIn(List<BigDecimal> values) {
            addCriterion("m_t1_trade_rate not in", values, "mT1TradeRate");
            return (Criteria) this;
        }

        public Criteria andMT1TradeRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("m_t1_trade_rate between", value1, value2, "mT1TradeRate");
            return (Criteria) this;
        }

        public Criteria andMT1TradeRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("m_t1_trade_rate not between", value1, value2, "mT1TradeRate");
            return (Criteria) this;
        }

        public Criteria andMBonusQuickRateIsNull() {
            addCriterion("m_bonus_quick_rate is null");
            return (Criteria) this;
        }

        public Criteria andMBonusQuickRateIsNotNull() {
            addCriterion("m_bonus_quick_rate is not null");
            return (Criteria) this;
        }

        public Criteria andMBonusQuickRateEqualTo(BigDecimal value) {
            addCriterion("m_bonus_quick_rate =", value, "mBonusQuickRate");
            return (Criteria) this;
        }

        public Criteria andMBonusQuickRateNotEqualTo(BigDecimal value) {
            addCriterion("m_bonus_quick_rate <>", value, "mBonusQuickRate");
            return (Criteria) this;
        }

        public Criteria andMBonusQuickRateGreaterThan(BigDecimal value) {
            addCriterion("m_bonus_quick_rate >", value, "mBonusQuickRate");
            return (Criteria) this;
        }

        public Criteria andMBonusQuickRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("m_bonus_quick_rate >=", value, "mBonusQuickRate");
            return (Criteria) this;
        }

        public Criteria andMBonusQuickRateLessThan(BigDecimal value) {
            addCriterion("m_bonus_quick_rate <", value, "mBonusQuickRate");
            return (Criteria) this;
        }

        public Criteria andMBonusQuickRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("m_bonus_quick_rate <=", value, "mBonusQuickRate");
            return (Criteria) this;
        }

        public Criteria andMBonusQuickRateIn(List<BigDecimal> values) {
            addCriterion("m_bonus_quick_rate in", values, "mBonusQuickRate");
            return (Criteria) this;
        }

        public Criteria andMBonusQuickRateNotIn(List<BigDecimal> values) {
            addCriterion("m_bonus_quick_rate not in", values, "mBonusQuickRate");
            return (Criteria) this;
        }

        public Criteria andMBonusQuickRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("m_bonus_quick_rate between", value1, value2, "mBonusQuickRate");
            return (Criteria) this;
        }

        public Criteria andMBonusQuickRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("m_bonus_quick_rate not between", value1, value2, "mBonusQuickRate");
            return (Criteria) this;
        }

        public Criteria andMBonusQuickFeeIsNull() {
            addCriterion("m_bonus_quick_fee is null");
            return (Criteria) this;
        }

        public Criteria andMBonusQuickFeeIsNotNull() {
            addCriterion("m_bonus_quick_fee is not null");
            return (Criteria) this;
        }

        public Criteria andMBonusQuickFeeEqualTo(BigDecimal value) {
            addCriterion("m_bonus_quick_fee =", value, "mBonusQuickFee");
            return (Criteria) this;
        }

        public Criteria andMBonusQuickFeeNotEqualTo(BigDecimal value) {
            addCriterion("m_bonus_quick_fee <>", value, "mBonusQuickFee");
            return (Criteria) this;
        }

        public Criteria andMBonusQuickFeeGreaterThan(BigDecimal value) {
            addCriterion("m_bonus_quick_fee >", value, "mBonusQuickFee");
            return (Criteria) this;
        }

        public Criteria andMBonusQuickFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("m_bonus_quick_fee >=", value, "mBonusQuickFee");
            return (Criteria) this;
        }

        public Criteria andMBonusQuickFeeLessThan(BigDecimal value) {
            addCriterion("m_bonus_quick_fee <", value, "mBonusQuickFee");
            return (Criteria) this;
        }

        public Criteria andMBonusQuickFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("m_bonus_quick_fee <=", value, "mBonusQuickFee");
            return (Criteria) this;
        }

        public Criteria andMBonusQuickFeeIn(List<BigDecimal> values) {
            addCriterion("m_bonus_quick_fee in", values, "mBonusQuickFee");
            return (Criteria) this;
        }

        public Criteria andMBonusQuickFeeNotIn(List<BigDecimal> values) {
            addCriterion("m_bonus_quick_fee not in", values, "mBonusQuickFee");
            return (Criteria) this;
        }

        public Criteria andMBonusQuickFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("m_bonus_quick_fee between", value1, value2, "mBonusQuickFee");
            return (Criteria) this;
        }

        public Criteria andMBonusQuickFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("m_bonus_quick_fee not between", value1, value2, "mBonusQuickFee");
            return (Criteria) this;
        }

        public Criteria andMQuickRateIsNull() {
            addCriterion("m_quick_rate is null");
            return (Criteria) this;
        }

        public Criteria andMQuickRateIsNotNull() {
            addCriterion("m_quick_rate is not null");
            return (Criteria) this;
        }

        public Criteria andMQuickRateEqualTo(BigDecimal value) {
            addCriterion("m_quick_rate =", value, "mQuickRate");
            return (Criteria) this;
        }

        public Criteria andMQuickRateNotEqualTo(BigDecimal value) {
            addCriterion("m_quick_rate <>", value, "mQuickRate");
            return (Criteria) this;
        }

        public Criteria andMQuickRateGreaterThan(BigDecimal value) {
            addCriterion("m_quick_rate >", value, "mQuickRate");
            return (Criteria) this;
        }

        public Criteria andMQuickRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("m_quick_rate >=", value, "mQuickRate");
            return (Criteria) this;
        }

        public Criteria andMQuickRateLessThan(BigDecimal value) {
            addCriterion("m_quick_rate <", value, "mQuickRate");
            return (Criteria) this;
        }

        public Criteria andMQuickRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("m_quick_rate <=", value, "mQuickRate");
            return (Criteria) this;
        }

        public Criteria andMQuickRateIn(List<BigDecimal> values) {
            addCriterion("m_quick_rate in", values, "mQuickRate");
            return (Criteria) this;
        }

        public Criteria andMQuickRateNotIn(List<BigDecimal> values) {
            addCriterion("m_quick_rate not in", values, "mQuickRate");
            return (Criteria) this;
        }

        public Criteria andMQuickRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("m_quick_rate between", value1, value2, "mQuickRate");
            return (Criteria) this;
        }

        public Criteria andMQuickRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("m_quick_rate not between", value1, value2, "mQuickRate");
            return (Criteria) this;
        }

        public Criteria andMQuickFeeIsNull() {
            addCriterion("m_quick_fee is null");
            return (Criteria) this;
        }

        public Criteria andMQuickFeeIsNotNull() {
            addCriterion("m_quick_fee is not null");
            return (Criteria) this;
        }

        public Criteria andMQuickFeeEqualTo(BigDecimal value) {
            addCriterion("m_quick_fee =", value, "mQuickFee");
            return (Criteria) this;
        }

        public Criteria andMQuickFeeNotEqualTo(BigDecimal value) {
            addCriterion("m_quick_fee <>", value, "mQuickFee");
            return (Criteria) this;
        }

        public Criteria andMQuickFeeGreaterThan(BigDecimal value) {
            addCriterion("m_quick_fee >", value, "mQuickFee");
            return (Criteria) this;
        }

        public Criteria andMQuickFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("m_quick_fee >=", value, "mQuickFee");
            return (Criteria) this;
        }

        public Criteria andMQuickFeeLessThan(BigDecimal value) {
            addCriterion("m_quick_fee <", value, "mQuickFee");
            return (Criteria) this;
        }

        public Criteria andMQuickFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("m_quick_fee <=", value, "mQuickFee");
            return (Criteria) this;
        }

        public Criteria andMQuickFeeIn(List<BigDecimal> values) {
            addCriterion("m_quick_fee in", values, "mQuickFee");
            return (Criteria) this;
        }

        public Criteria andMQuickFeeNotIn(List<BigDecimal> values) {
            addCriterion("m_quick_fee not in", values, "mQuickFee");
            return (Criteria) this;
        }

        public Criteria andMQuickFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("m_quick_fee between", value1, value2, "mQuickFee");
            return (Criteria) this;
        }

        public Criteria andMQuickFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("m_quick_fee not between", value1, value2, "mQuickFee");
            return (Criteria) this;
        }

        public Criteria andMUrlIsNull() {
            addCriterion("m_url is null");
            return (Criteria) this;
        }

        public Criteria andMUrlIsNotNull() {
            addCriterion("m_url is not null");
            return (Criteria) this;
        }

        public Criteria andMUrlEqualTo(String value) {
            addCriterion("m_url =", value, "mUrl");
            return (Criteria) this;
        }

        public Criteria andMUrlNotEqualTo(String value) {
            addCriterion("m_url <>", value, "mUrl");
            return (Criteria) this;
        }

        public Criteria andMUrlGreaterThan(String value) {
            addCriterion("m_url >", value, "mUrl");
            return (Criteria) this;
        }

        public Criteria andMUrlGreaterThanOrEqualTo(String value) {
            addCriterion("m_url >=", value, "mUrl");
            return (Criteria) this;
        }

        public Criteria andMUrlLessThan(String value) {
            addCriterion("m_url <", value, "mUrl");
            return (Criteria) this;
        }

        public Criteria andMUrlLessThanOrEqualTo(String value) {
            addCriterion("m_url <=", value, "mUrl");
            return (Criteria) this;
        }

        public Criteria andMUrlLike(String value) {
            addCriterion("m_url like", value, "mUrl");
            return (Criteria) this;
        }

        public Criteria andMUrlNotLike(String value) {
            addCriterion("m_url not like", value, "mUrl");
            return (Criteria) this;
        }

        public Criteria andMUrlIn(List<String> values) {
            addCriterion("m_url in", values, "mUrl");
            return (Criteria) this;
        }

        public Criteria andMUrlNotIn(List<String> values) {
            addCriterion("m_url not in", values, "mUrl");
            return (Criteria) this;
        }

        public Criteria andMUrlBetween(String value1, String value2) {
            addCriterion("m_url between", value1, value2, "mUrl");
            return (Criteria) this;
        }

        public Criteria andMUrlNotBetween(String value1, String value2) {
            addCriterion("m_url not between", value1, value2, "mUrl");
            return (Criteria) this;
        }

        public Criteria andMImgIsNull() {
            addCriterion("m_img is null");
            return (Criteria) this;
        }

        public Criteria andMImgIsNotNull() {
            addCriterion("m_img is not null");
            return (Criteria) this;
        }

        public Criteria andMImgEqualTo(String value) {
            addCriterion("m_img =", value, "mImg");
            return (Criteria) this;
        }

        public Criteria andMImgNotEqualTo(String value) {
            addCriterion("m_img <>", value, "mImg");
            return (Criteria) this;
        }

        public Criteria andMImgGreaterThan(String value) {
            addCriterion("m_img >", value, "mImg");
            return (Criteria) this;
        }

        public Criteria andMImgGreaterThanOrEqualTo(String value) {
            addCriterion("m_img >=", value, "mImg");
            return (Criteria) this;
        }

        public Criteria andMImgLessThan(String value) {
            addCriterion("m_img <", value, "mImg");
            return (Criteria) this;
        }

        public Criteria andMImgLessThanOrEqualTo(String value) {
            addCriterion("m_img <=", value, "mImg");
            return (Criteria) this;
        }

        public Criteria andMImgLike(String value) {
            addCriterion("m_img like", value, "mImg");
            return (Criteria) this;
        }

        public Criteria andMImgNotLike(String value) {
            addCriterion("m_img not like", value, "mImg");
            return (Criteria) this;
        }

        public Criteria andMImgIn(List<String> values) {
            addCriterion("m_img in", values, "mImg");
            return (Criteria) this;
        }

        public Criteria andMImgNotIn(List<String> values) {
            addCriterion("m_img not in", values, "mImg");
            return (Criteria) this;
        }

        public Criteria andMImgBetween(String value1, String value2) {
            addCriterion("m_img between", value1, value2, "mImg");
            return (Criteria) this;
        }

        public Criteria andMImgNotBetween(String value1, String value2) {
            addCriterion("m_img not between", value1, value2, "mImg");
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