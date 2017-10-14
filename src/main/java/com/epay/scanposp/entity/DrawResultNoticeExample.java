package com.epay.scanposp.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DrawResultNoticeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitSize;

    public DrawResultNoticeExample() {
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

        public Criteria andMemberIdIsNull() {
            addCriterion("member_id is null");
            return (Criteria) this;
        }

        public Criteria andMemberIdIsNotNull() {
            addCriterion("member_id is not null");
            return (Criteria) this;
        }

        public Criteria andMemberIdEqualTo(Integer value) {
            addCriterion("member_id =", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotEqualTo(Integer value) {
            addCriterion("member_id <>", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdGreaterThan(Integer value) {
            addCriterion("member_id >", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("member_id >=", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdLessThan(Integer value) {
            addCriterion("member_id <", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdLessThanOrEqualTo(Integer value) {
            addCriterion("member_id <=", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdIn(List<Integer> values) {
            addCriterion("member_id in", values, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotIn(List<Integer> values) {
            addCriterion("member_id not in", values, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdBetween(Integer value1, Integer value2) {
            addCriterion("member_id between", value1, value2, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotBetween(Integer value1, Integer value2) {
            addCriterion("member_id not between", value1, value2, "memberId");
            return (Criteria) this;
        }

        public Criteria andOrderCodeIsNull() {
            addCriterion("order_code is null");
            return (Criteria) this;
        }

        public Criteria andOrderCodeIsNotNull() {
            addCriterion("order_code is not null");
            return (Criteria) this;
        }

        public Criteria andOrderCodeEqualTo(String value) {
            addCriterion("order_code =", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeNotEqualTo(String value) {
            addCriterion("order_code <>", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeGreaterThan(String value) {
            addCriterion("order_code >", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeGreaterThanOrEqualTo(String value) {
            addCriterion("order_code >=", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeLessThan(String value) {
            addCriterion("order_code <", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeLessThanOrEqualTo(String value) {
            addCriterion("order_code <=", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeLike(String value) {
            addCriterion("order_code like", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeNotLike(String value) {
            addCriterion("order_code not like", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeIn(List<String> values) {
            addCriterion("order_code in", values, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeNotIn(List<String> values) {
            addCriterion("order_code not in", values, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeBetween(String value1, String value2) {
            addCriterion("order_code between", value1, value2, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeNotBetween(String value1, String value2) {
            addCriterion("order_code not between", value1, value2, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderNumOuterIsNull() {
            addCriterion("order_num_outer is null");
            return (Criteria) this;
        }

        public Criteria andOrderNumOuterIsNotNull() {
            addCriterion("order_num_outer is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNumOuterEqualTo(String value) {
            addCriterion("order_num_outer =", value, "orderNumOuter");
            return (Criteria) this;
        }

        public Criteria andOrderNumOuterNotEqualTo(String value) {
            addCriterion("order_num_outer <>", value, "orderNumOuter");
            return (Criteria) this;
        }

        public Criteria andOrderNumOuterGreaterThan(String value) {
            addCriterion("order_num_outer >", value, "orderNumOuter");
            return (Criteria) this;
        }

        public Criteria andOrderNumOuterGreaterThanOrEqualTo(String value) {
            addCriterion("order_num_outer >=", value, "orderNumOuter");
            return (Criteria) this;
        }

        public Criteria andOrderNumOuterLessThan(String value) {
            addCriterion("order_num_outer <", value, "orderNumOuter");
            return (Criteria) this;
        }

        public Criteria andOrderNumOuterLessThanOrEqualTo(String value) {
            addCriterion("order_num_outer <=", value, "orderNumOuter");
            return (Criteria) this;
        }

        public Criteria andOrderNumOuterLike(String value) {
            addCriterion("order_num_outer like", value, "orderNumOuter");
            return (Criteria) this;
        }

        public Criteria andOrderNumOuterNotLike(String value) {
            addCriterion("order_num_outer not like", value, "orderNumOuter");
            return (Criteria) this;
        }

        public Criteria andOrderNumOuterIn(List<String> values) {
            addCriterion("order_num_outer in", values, "orderNumOuter");
            return (Criteria) this;
        }

        public Criteria andOrderNumOuterNotIn(List<String> values) {
            addCriterion("order_num_outer not in", values, "orderNumOuter");
            return (Criteria) this;
        }

        public Criteria andOrderNumOuterBetween(String value1, String value2) {
            addCriterion("order_num_outer between", value1, value2, "orderNumOuter");
            return (Criteria) this;
        }

        public Criteria andOrderNumOuterNotBetween(String value1, String value2) {
            addCriterion("order_num_outer not between", value1, value2, "orderNumOuter");
            return (Criteria) this;
        }

        public Criteria andReturnUrlIsNull() {
            addCriterion("return_url is null");
            return (Criteria) this;
        }

        public Criteria andReturnUrlIsNotNull() {
            addCriterion("return_url is not null");
            return (Criteria) this;
        }

        public Criteria andReturnUrlEqualTo(String value) {
            addCriterion("return_url =", value, "returnUrl");
            return (Criteria) this;
        }

        public Criteria andReturnUrlNotEqualTo(String value) {
            addCriterion("return_url <>", value, "returnUrl");
            return (Criteria) this;
        }

        public Criteria andReturnUrlGreaterThan(String value) {
            addCriterion("return_url >", value, "returnUrl");
            return (Criteria) this;
        }

        public Criteria andReturnUrlGreaterThanOrEqualTo(String value) {
            addCriterion("return_url >=", value, "returnUrl");
            return (Criteria) this;
        }

        public Criteria andReturnUrlLessThan(String value) {
            addCriterion("return_url <", value, "returnUrl");
            return (Criteria) this;
        }

        public Criteria andReturnUrlLessThanOrEqualTo(String value) {
            addCriterion("return_url <=", value, "returnUrl");
            return (Criteria) this;
        }

        public Criteria andReturnUrlLike(String value) {
            addCriterion("return_url like", value, "returnUrl");
            return (Criteria) this;
        }

        public Criteria andReturnUrlNotLike(String value) {
            addCriterion("return_url not like", value, "returnUrl");
            return (Criteria) this;
        }

        public Criteria andReturnUrlIn(List<String> values) {
            addCriterion("return_url in", values, "returnUrl");
            return (Criteria) this;
        }

        public Criteria andReturnUrlNotIn(List<String> values) {
            addCriterion("return_url not in", values, "returnUrl");
            return (Criteria) this;
        }

        public Criteria andReturnUrlBetween(String value1, String value2) {
            addCriterion("return_url between", value1, value2, "returnUrl");
            return (Criteria) this;
        }

        public Criteria andReturnUrlNotBetween(String value1, String value2) {
            addCriterion("return_url not between", value1, value2, "returnUrl");
            return (Criteria) this;
        }

        public Criteria andCountsIsNull() {
            addCriterion("counts is null");
            return (Criteria) this;
        }

        public Criteria andCountsIsNotNull() {
            addCriterion("counts is not null");
            return (Criteria) this;
        }

        public Criteria andCountsEqualTo(Integer value) {
            addCriterion("counts =", value, "counts");
            return (Criteria) this;
        }

        public Criteria andCountsNotEqualTo(Integer value) {
            addCriterion("counts <>", value, "counts");
            return (Criteria) this;
        }

        public Criteria andCountsGreaterThan(Integer value) {
            addCriterion("counts >", value, "counts");
            return (Criteria) this;
        }

        public Criteria andCountsGreaterThanOrEqualTo(Integer value) {
            addCriterion("counts >=", value, "counts");
            return (Criteria) this;
        }

        public Criteria andCountsLessThan(Integer value) {
            addCriterion("counts <", value, "counts");
            return (Criteria) this;
        }

        public Criteria andCountsLessThanOrEqualTo(Integer value) {
            addCriterion("counts <=", value, "counts");
            return (Criteria) this;
        }

        public Criteria andCountsIn(List<Integer> values) {
            addCriterion("counts in", values, "counts");
            return (Criteria) this;
        }

        public Criteria andCountsNotIn(List<Integer> values) {
            addCriterion("counts not in", values, "counts");
            return (Criteria) this;
        }

        public Criteria andCountsBetween(Integer value1, Integer value2) {
            addCriterion("counts between", value1, value2, "counts");
            return (Criteria) this;
        }

        public Criteria andCountsNotBetween(Integer value1, Integer value2) {
            addCriterion("counts not between", value1, value2, "counts");
            return (Criteria) this;
        }

        public Criteria andTxnTypeIsNull() {
            addCriterion("txn_type is null");
            return (Criteria) this;
        }

        public Criteria andTxnTypeIsNotNull() {
            addCriterion("txn_type is not null");
            return (Criteria) this;
        }

        public Criteria andTxnTypeEqualTo(String value) {
            addCriterion("txn_type =", value, "txnType");
            return (Criteria) this;
        }

        public Criteria andTxnTypeNotEqualTo(String value) {
            addCriterion("txn_type <>", value, "txnType");
            return (Criteria) this;
        }

        public Criteria andTxnTypeGreaterThan(String value) {
            addCriterion("txn_type >", value, "txnType");
            return (Criteria) this;
        }

        public Criteria andTxnTypeGreaterThanOrEqualTo(String value) {
            addCriterion("txn_type >=", value, "txnType");
            return (Criteria) this;
        }

        public Criteria andTxnTypeLessThan(String value) {
            addCriterion("txn_type <", value, "txnType");
            return (Criteria) this;
        }

        public Criteria andTxnTypeLessThanOrEqualTo(String value) {
            addCriterion("txn_type <=", value, "txnType");
            return (Criteria) this;
        }

        public Criteria andTxnTypeLike(String value) {
            addCriterion("txn_type like", value, "txnType");
            return (Criteria) this;
        }

        public Criteria andTxnTypeNotLike(String value) {
            addCriterion("txn_type not like", value, "txnType");
            return (Criteria) this;
        }

        public Criteria andTxnTypeIn(List<String> values) {
            addCriterion("txn_type in", values, "txnType");
            return (Criteria) this;
        }

        public Criteria andTxnTypeNotIn(List<String> values) {
            addCriterion("txn_type not in", values, "txnType");
            return (Criteria) this;
        }

        public Criteria andTxnTypeBetween(String value1, String value2) {
            addCriterion("txn_type between", value1, value2, "txnType");
            return (Criteria) this;
        }

        public Criteria andTxnTypeNotBetween(String value1, String value2) {
            addCriterion("txn_type not between", value1, value2, "txnType");
            return (Criteria) this;
        }

        public Criteria andDrawamountIsNull() {
            addCriterion("drawAmount is null");
            return (Criteria) this;
        }

        public Criteria andDrawamountIsNotNull() {
            addCriterion("drawAmount is not null");
            return (Criteria) this;
        }

        public Criteria andDrawamountEqualTo(BigDecimal value) {
            addCriterion("drawAmount =", value, "drawamount");
            return (Criteria) this;
        }

        public Criteria andDrawamountNotEqualTo(BigDecimal value) {
            addCriterion("drawAmount <>", value, "drawamount");
            return (Criteria) this;
        }

        public Criteria andDrawamountGreaterThan(BigDecimal value) {
            addCriterion("drawAmount >", value, "drawamount");
            return (Criteria) this;
        }

        public Criteria andDrawamountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("drawAmount >=", value, "drawamount");
            return (Criteria) this;
        }

        public Criteria andDrawamountLessThan(BigDecimal value) {
            addCriterion("drawAmount <", value, "drawamount");
            return (Criteria) this;
        }

        public Criteria andDrawamountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("drawAmount <=", value, "drawamount");
            return (Criteria) this;
        }

        public Criteria andDrawamountIn(List<BigDecimal> values) {
            addCriterion("drawAmount in", values, "drawamount");
            return (Criteria) this;
        }

        public Criteria andDrawamountNotIn(List<BigDecimal> values) {
            addCriterion("drawAmount not in", values, "drawamount");
            return (Criteria) this;
        }

        public Criteria andDrawamountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("drawAmount between", value1, value2, "drawamount");
            return (Criteria) this;
        }

        public Criteria andDrawamountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("drawAmount not between", value1, value2, "drawamount");
            return (Criteria) this;
        }

        public Criteria andDrawfeeIsNull() {
            addCriterion("drawFee is null");
            return (Criteria) this;
        }

        public Criteria andDrawfeeIsNotNull() {
            addCriterion("drawFee is not null");
            return (Criteria) this;
        }

        public Criteria andDrawfeeEqualTo(BigDecimal value) {
            addCriterion("drawFee =", value, "drawfee");
            return (Criteria) this;
        }

        public Criteria andDrawfeeNotEqualTo(BigDecimal value) {
            addCriterion("drawFee <>", value, "drawfee");
            return (Criteria) this;
        }

        public Criteria andDrawfeeGreaterThan(BigDecimal value) {
            addCriterion("drawFee >", value, "drawfee");
            return (Criteria) this;
        }

        public Criteria andDrawfeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("drawFee >=", value, "drawfee");
            return (Criteria) this;
        }

        public Criteria andDrawfeeLessThan(BigDecimal value) {
            addCriterion("drawFee <", value, "drawfee");
            return (Criteria) this;
        }

        public Criteria andDrawfeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("drawFee <=", value, "drawfee");
            return (Criteria) this;
        }

        public Criteria andDrawfeeIn(List<BigDecimal> values) {
            addCriterion("drawFee in", values, "drawfee");
            return (Criteria) this;
        }

        public Criteria andDrawfeeNotIn(List<BigDecimal> values) {
            addCriterion("drawFee not in", values, "drawfee");
            return (Criteria) this;
        }

        public Criteria andDrawfeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("drawFee between", value1, value2, "drawfee");
            return (Criteria) this;
        }

        public Criteria andDrawfeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("drawFee not between", value1, value2, "drawfee");
            return (Criteria) this;
        }

        public Criteria andTradefeeIsNull() {
            addCriterion("tradeFee is null");
            return (Criteria) this;
        }

        public Criteria andTradefeeIsNotNull() {
            addCriterion("tradeFee is not null");
            return (Criteria) this;
        }

        public Criteria andTradefeeEqualTo(BigDecimal value) {
            addCriterion("tradeFee =", value, "tradefee");
            return (Criteria) this;
        }

        public Criteria andTradefeeNotEqualTo(BigDecimal value) {
            addCriterion("tradeFee <>", value, "tradefee");
            return (Criteria) this;
        }

        public Criteria andTradefeeGreaterThan(BigDecimal value) {
            addCriterion("tradeFee >", value, "tradefee");
            return (Criteria) this;
        }

        public Criteria andTradefeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("tradeFee >=", value, "tradefee");
            return (Criteria) this;
        }

        public Criteria andTradefeeLessThan(BigDecimal value) {
            addCriterion("tradeFee <", value, "tradefee");
            return (Criteria) this;
        }

        public Criteria andTradefeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("tradeFee <=", value, "tradefee");
            return (Criteria) this;
        }

        public Criteria andTradefeeIn(List<BigDecimal> values) {
            addCriterion("tradeFee in", values, "tradefee");
            return (Criteria) this;
        }

        public Criteria andTradefeeNotIn(List<BigDecimal> values) {
            addCriterion("tradeFee not in", values, "tradefee");
            return (Criteria) this;
        }

        public Criteria andTradefeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tradeFee between", value1, value2, "tradefee");
            return (Criteria) this;
        }

        public Criteria andTradefeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tradeFee not between", value1, value2, "tradefee");
            return (Criteria) this;
        }

        public Criteria andDrawTimeIsNull() {
            addCriterion("draw_time is null");
            return (Criteria) this;
        }

        public Criteria andDrawTimeIsNotNull() {
            addCriterion("draw_time is not null");
            return (Criteria) this;
        }

        public Criteria andDrawTimeEqualTo(String value) {
            addCriterion("draw_time =", value, "drawTime");
            return (Criteria) this;
        }

        public Criteria andDrawTimeNotEqualTo(String value) {
            addCriterion("draw_time <>", value, "drawTime");
            return (Criteria) this;
        }

        public Criteria andDrawTimeGreaterThan(String value) {
            addCriterion("draw_time >", value, "drawTime");
            return (Criteria) this;
        }

        public Criteria andDrawTimeGreaterThanOrEqualTo(String value) {
            addCriterion("draw_time >=", value, "drawTime");
            return (Criteria) this;
        }

        public Criteria andDrawTimeLessThan(String value) {
            addCriterion("draw_time <", value, "drawTime");
            return (Criteria) this;
        }

        public Criteria andDrawTimeLessThanOrEqualTo(String value) {
            addCriterion("draw_time <=", value, "drawTime");
            return (Criteria) this;
        }

        public Criteria andDrawTimeLike(String value) {
            addCriterion("draw_time like", value, "drawTime");
            return (Criteria) this;
        }

        public Criteria andDrawTimeNotLike(String value) {
            addCriterion("draw_time not like", value, "drawTime");
            return (Criteria) this;
        }

        public Criteria andDrawTimeIn(List<String> values) {
            addCriterion("draw_time in", values, "drawTime");
            return (Criteria) this;
        }

        public Criteria andDrawTimeNotIn(List<String> values) {
            addCriterion("draw_time not in", values, "drawTime");
            return (Criteria) this;
        }

        public Criteria andDrawTimeBetween(String value1, String value2) {
            addCriterion("draw_time between", value1, value2, "drawTime");
            return (Criteria) this;
        }

        public Criteria andDrawTimeNotBetween(String value1, String value2) {
            addCriterion("draw_time not between", value1, value2, "drawTime");
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

        public Criteria andRespTypeIsNull() {
            addCriterion("resp_type is null");
            return (Criteria) this;
        }

        public Criteria andRespTypeIsNotNull() {
            addCriterion("resp_type is not null");
            return (Criteria) this;
        }

        public Criteria andRespTypeEqualTo(String value) {
            addCriterion("resp_type =", value, "respType");
            return (Criteria) this;
        }

        public Criteria andRespTypeNotEqualTo(String value) {
            addCriterion("resp_type <>", value, "respType");
            return (Criteria) this;
        }

        public Criteria andRespTypeGreaterThan(String value) {
            addCriterion("resp_type >", value, "respType");
            return (Criteria) this;
        }

        public Criteria andRespTypeGreaterThanOrEqualTo(String value) {
            addCriterion("resp_type >=", value, "respType");
            return (Criteria) this;
        }

        public Criteria andRespTypeLessThan(String value) {
            addCriterion("resp_type <", value, "respType");
            return (Criteria) this;
        }

        public Criteria andRespTypeLessThanOrEqualTo(String value) {
            addCriterion("resp_type <=", value, "respType");
            return (Criteria) this;
        }

        public Criteria andRespTypeLike(String value) {
            addCriterion("resp_type like", value, "respType");
            return (Criteria) this;
        }

        public Criteria andRespTypeNotLike(String value) {
            addCriterion("resp_type not like", value, "respType");
            return (Criteria) this;
        }

        public Criteria andRespTypeIn(List<String> values) {
            addCriterion("resp_type in", values, "respType");
            return (Criteria) this;
        }

        public Criteria andRespTypeNotIn(List<String> values) {
            addCriterion("resp_type not in", values, "respType");
            return (Criteria) this;
        }

        public Criteria andRespTypeBetween(String value1, String value2) {
            addCriterion("resp_type between", value1, value2, "respType");
            return (Criteria) this;
        }

        public Criteria andRespTypeNotBetween(String value1, String value2) {
            addCriterion("resp_type not between", value1, value2, "respType");
            return (Criteria) this;
        }

        public Criteria andResultCodeIsNull() {
            addCriterion("result_code is null");
            return (Criteria) this;
        }

        public Criteria andResultCodeIsNotNull() {
            addCriterion("result_code is not null");
            return (Criteria) this;
        }

        public Criteria andResultCodeEqualTo(String value) {
            addCriterion("result_code =", value, "resultCode");
            return (Criteria) this;
        }

        public Criteria andResultCodeNotEqualTo(String value) {
            addCriterion("result_code <>", value, "resultCode");
            return (Criteria) this;
        }

        public Criteria andResultCodeGreaterThan(String value) {
            addCriterion("result_code >", value, "resultCode");
            return (Criteria) this;
        }

        public Criteria andResultCodeGreaterThanOrEqualTo(String value) {
            addCriterion("result_code >=", value, "resultCode");
            return (Criteria) this;
        }

        public Criteria andResultCodeLessThan(String value) {
            addCriterion("result_code <", value, "resultCode");
            return (Criteria) this;
        }

        public Criteria andResultCodeLessThanOrEqualTo(String value) {
            addCriterion("result_code <=", value, "resultCode");
            return (Criteria) this;
        }

        public Criteria andResultCodeLike(String value) {
            addCriterion("result_code like", value, "resultCode");
            return (Criteria) this;
        }

        public Criteria andResultCodeNotLike(String value) {
            addCriterion("result_code not like", value, "resultCode");
            return (Criteria) this;
        }

        public Criteria andResultCodeIn(List<String> values) {
            addCriterion("result_code in", values, "resultCode");
            return (Criteria) this;
        }

        public Criteria andResultCodeNotIn(List<String> values) {
            addCriterion("result_code not in", values, "resultCode");
            return (Criteria) this;
        }

        public Criteria andResultCodeBetween(String value1, String value2) {
            addCriterion("result_code between", value1, value2, "resultCode");
            return (Criteria) this;
        }

        public Criteria andResultCodeNotBetween(String value1, String value2) {
            addCriterion("result_code not between", value1, value2, "resultCode");
            return (Criteria) this;
        }

        public Criteria andResultMessageIsNull() {
            addCriterion("result_message is null");
            return (Criteria) this;
        }

        public Criteria andResultMessageIsNotNull() {
            addCriterion("result_message is not null");
            return (Criteria) this;
        }

        public Criteria andResultMessageEqualTo(String value) {
            addCriterion("result_message =", value, "resultMessage");
            return (Criteria) this;
        }

        public Criteria andResultMessageNotEqualTo(String value) {
            addCriterion("result_message <>", value, "resultMessage");
            return (Criteria) this;
        }

        public Criteria andResultMessageGreaterThan(String value) {
            addCriterion("result_message >", value, "resultMessage");
            return (Criteria) this;
        }

        public Criteria andResultMessageGreaterThanOrEqualTo(String value) {
            addCriterion("result_message >=", value, "resultMessage");
            return (Criteria) this;
        }

        public Criteria andResultMessageLessThan(String value) {
            addCriterion("result_message <", value, "resultMessage");
            return (Criteria) this;
        }

        public Criteria andResultMessageLessThanOrEqualTo(String value) {
            addCriterion("result_message <=", value, "resultMessage");
            return (Criteria) this;
        }

        public Criteria andResultMessageLike(String value) {
            addCriterion("result_message like", value, "resultMessage");
            return (Criteria) this;
        }

        public Criteria andResultMessageNotLike(String value) {
            addCriterion("result_message not like", value, "resultMessage");
            return (Criteria) this;
        }

        public Criteria andResultMessageIn(List<String> values) {
            addCriterion("result_message in", values, "resultMessage");
            return (Criteria) this;
        }

        public Criteria andResultMessageNotIn(List<String> values) {
            addCriterion("result_message not in", values, "resultMessage");
            return (Criteria) this;
        }

        public Criteria andResultMessageBetween(String value1, String value2) {
            addCriterion("result_message between", value1, value2, "resultMessage");
            return (Criteria) this;
        }

        public Criteria andResultMessageNotBetween(String value1, String value2) {
            addCriterion("result_message not between", value1, value2, "resultMessage");
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