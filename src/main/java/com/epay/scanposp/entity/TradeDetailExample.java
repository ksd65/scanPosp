package com.epay.scanposp.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TradeDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitSize;

    public TradeDetailExample() {
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
        public Criteria andTxnDateIsNull() {
            addCriterion("txn_date is null");
            return (Criteria) this;
        }

        public Criteria andTxnDateIsNotNull() {
            addCriterion("txn_date is not null");
            return (Criteria) this;
        }

        public Criteria andTxnDateEqualTo(String value) {
            addCriterion("txn_date =", value, "txnDate");
            return (Criteria) this;
        }

        public Criteria andTxnDateNotEqualTo(String value) {
            addCriterion("txn_date <>", value, "txnDate");
            return (Criteria) this;
        }

        public Criteria andTxnDateGreaterThan(String value) {
            addCriterion("txn_date >", value, "txnDate");
            return (Criteria) this;
        }

        public Criteria andTxnDateGreaterThanOrEqualTo(String value) {
            addCriterion("txn_date >=", value, "txnDate");
            return (Criteria) this;
        }

        public Criteria andTxnDateLessThan(String value) {
            addCriterion("txn_date <", value, "txnDate");
            return (Criteria) this;
        }

        public Criteria andTxnDateLessThanOrEqualTo(String value) {
            addCriterion("txn_date <=", value, "txnDate");
            return (Criteria) this;
        }

        public Criteria andTxnDateLike(String value) {
            addCriterion("txn_date like", value, "txnDate");
            return (Criteria) this;
        }

        public Criteria andTxnDateNotLike(String value) {
            addCriterion("txn_date not like", value, "txnDate");
            return (Criteria) this;
        }

        public Criteria andTxnDateIn(List<String> values) {
            addCriterion("txn_date in", values, "txnDate");
            return (Criteria) this;
        }

        public Criteria andTxnDateNotIn(List<String> values) {
            addCriterion("txn_date not in", values, "txnDate");
            return (Criteria) this;
        }

        public Criteria andTxnDateBetween(String value1, String value2) {
            addCriterion("txn_date between", value1, value2, "txnDate");
            return (Criteria) this;
        }

        public Criteria andTxnDateNotBetween(String value1, String value2) {
            addCriterion("txn_date not between", value1, value2, "txnDate");
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

        public Criteria andMemberCodeIsNull() {
            addCriterion("member_code is null");
            return (Criteria) this;
        }

        public Criteria andMemberCodeIsNotNull() {
            addCriterion("member_code is not null");
            return (Criteria) this;
        }

        public Criteria andMemberCodeEqualTo(String value) {
            addCriterion("member_code =", value, "memberCode");
            return (Criteria) this;
        }

        public Criteria andMemberCodeNotEqualTo(String value) {
            addCriterion("member_code <>", value, "memberCode");
            return (Criteria) this;
        }

        public Criteria andMemberCodeGreaterThan(String value) {
            addCriterion("member_code >", value, "memberCode");
            return (Criteria) this;
        }

        public Criteria andMemberCodeGreaterThanOrEqualTo(String value) {
            addCriterion("member_code >=", value, "memberCode");
            return (Criteria) this;
        }

        public Criteria andMemberCodeLessThan(String value) {
            addCriterion("member_code <", value, "memberCode");
            return (Criteria) this;
        }

        public Criteria andMemberCodeLessThanOrEqualTo(String value) {
            addCriterion("member_code <=", value, "memberCode");
            return (Criteria) this;
        }

        public Criteria andMemberCodeLike(String value) {
            addCriterion("member_code like", value, "memberCode");
            return (Criteria) this;
        }

        public Criteria andMemberCodeNotLike(String value) {
            addCriterion("member_code not like", value, "memberCode");
            return (Criteria) this;
        }

        public Criteria andMemberCodeIn(List<String> values) {
            addCriterion("member_code in", values, "memberCode");
            return (Criteria) this;
        }

        public Criteria andMemberCodeNotIn(List<String> values) {
            addCriterion("member_code not in", values, "memberCode");
            return (Criteria) this;
        }

        public Criteria andMemberCodeBetween(String value1, String value2) {
            addCriterion("member_code between", value1, value2, "memberCode");
            return (Criteria) this;
        }

        public Criteria andMemberCodeNotBetween(String value1, String value2) {
            addCriterion("member_code not between", value1, value2, "memberCode");
            return (Criteria) this;
        }

        public Criteria andMerchantCodeIsNull() {
            addCriterion("merchant_code is null");
            return (Criteria) this;
        }

        public Criteria andMerchantCodeIsNotNull() {
            addCriterion("merchant_code is not null");
            return (Criteria) this;
        }

        public Criteria andMerchantCodeEqualTo(String value) {
            addCriterion("merchant_code =", value, "merchantCode");
            return (Criteria) this;
        }

        public Criteria andMerchantCodeNotEqualTo(String value) {
            addCriterion("merchant_code <>", value, "merchantCode");
            return (Criteria) this;
        }

        public Criteria andMerchantCodeGreaterThan(String value) {
            addCriterion("merchant_code >", value, "merchantCode");
            return (Criteria) this;
        }

        public Criteria andMerchantCodeGreaterThanOrEqualTo(String value) {
            addCriterion("merchant_code >=", value, "merchantCode");
            return (Criteria) this;
        }

        public Criteria andMerchantCodeLessThan(String value) {
            addCriterion("merchant_code <", value, "merchantCode");
            return (Criteria) this;
        }

        public Criteria andMerchantCodeLessThanOrEqualTo(String value) {
            addCriterion("merchant_code <=", value, "merchantCode");
            return (Criteria) this;
        }

        public Criteria andMerchantCodeLike(String value) {
            addCriterion("merchant_code like", value, "merchantCode");
            return (Criteria) this;
        }

        public Criteria andMerchantCodeNotLike(String value) {
            addCriterion("merchant_code not like", value, "merchantCode");
            return (Criteria) this;
        }

        public Criteria andMerchantCodeIn(List<String> values) {
            addCriterion("merchant_code in", values, "merchantCode");
            return (Criteria) this;
        }

        public Criteria andMerchantCodeNotIn(List<String> values) {
            addCriterion("merchant_code not in", values, "merchantCode");
            return (Criteria) this;
        }

        public Criteria andMerchantCodeBetween(String value1, String value2) {
            addCriterion("merchant_code between", value1, value2, "merchantCode");
            return (Criteria) this;
        }

        public Criteria andMerchantCodeNotBetween(String value1, String value2) {
            addCriterion("merchant_code not between", value1, value2, "merchantCode");
            return (Criteria) this;
        }

        public Criteria andMoneyIsNull() {
            addCriterion("money is null");
            return (Criteria) this;
        }

        public Criteria andMoneyIsNotNull() {
            addCriterion("money is not null");
            return (Criteria) this;
        }

        public Criteria andMoneyEqualTo(BigDecimal value) {
            addCriterion("money =", value, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyNotEqualTo(BigDecimal value) {
            addCriterion("money <>", value, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyGreaterThan(BigDecimal value) {
            addCriterion("money >", value, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("money >=", value, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyLessThan(BigDecimal value) {
            addCriterion("money <", value, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("money <=", value, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyIn(List<BigDecimal> values) {
            addCriterion("money in", values, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyNotIn(List<BigDecimal> values) {
            addCriterion("money not in", values, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("money between", value1, value2, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("money not between", value1, value2, "money");
            return (Criteria) this;
        }

        public Criteria andMemberTradeRateIsNull() {
            addCriterion("member_trade_rate is null");
            return (Criteria) this;
        }

        public Criteria andMemberTradeRateIsNotNull() {
            addCriterion("member_trade_rate is not null");
            return (Criteria) this;
        }

        public Criteria andMemberTradeRateEqualTo(BigDecimal value) {
            addCriterion("member_trade_rate =", value, "memberTradeRate");
            return (Criteria) this;
        }

        public Criteria andMemberTradeRateNotEqualTo(BigDecimal value) {
            addCriterion("member_trade_rate <>", value, "memberTradeRate");
            return (Criteria) this;
        }

        public Criteria andMemberTradeRateGreaterThan(BigDecimal value) {
            addCriterion("member_trade_rate >", value, "memberTradeRate");
            return (Criteria) this;
        }

        public Criteria andMemberTradeRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("member_trade_rate >=", value, "memberTradeRate");
            return (Criteria) this;
        }

        public Criteria andMemberTradeRateLessThan(BigDecimal value) {
            addCriterion("member_trade_rate <", value, "memberTradeRate");
            return (Criteria) this;
        }

        public Criteria andMemberTradeRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("member_trade_rate <=", value, "memberTradeRate");
            return (Criteria) this;
        }

        public Criteria andMemberTradeRateIn(List<BigDecimal> values) {
            addCriterion("member_trade_rate in", values, "memberTradeRate");
            return (Criteria) this;
        }

        public Criteria andMemberTradeRateNotIn(List<BigDecimal> values) {
            addCriterion("member_trade_rate not in", values, "memberTradeRate");
            return (Criteria) this;
        }

        public Criteria andMemberTradeRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("member_trade_rate between", value1, value2, "memberTradeRate");
            return (Criteria) this;
        }

        public Criteria andMemberTradeRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("member_trade_rate not between", value1, value2, "memberTradeRate");
            return (Criteria) this;
        }

        public Criteria andMemberDrawFeeIsNull() {
            addCriterion("member_draw_fee is null");
            return (Criteria) this;
        }

        public Criteria andMemberDrawFeeIsNotNull() {
            addCriterion("member_draw_fee is not null");
            return (Criteria) this;
        }

        public Criteria andMemberDrawFeeEqualTo(BigDecimal value) {
            addCriterion("member_draw_fee =", value, "memberDrawFee");
            return (Criteria) this;
        }

        public Criteria andMemberDrawFeeNotEqualTo(BigDecimal value) {
            addCriterion("member_draw_fee <>", value, "memberDrawFee");
            return (Criteria) this;
        }

        public Criteria andMemberDrawFeeGreaterThan(BigDecimal value) {
            addCriterion("member_draw_fee >", value, "memberDrawFee");
            return (Criteria) this;
        }

        public Criteria andMemberDrawFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("member_draw_fee >=", value, "memberDrawFee");
            return (Criteria) this;
        }

        public Criteria andMemberDrawFeeLessThan(BigDecimal value) {
            addCriterion("member_draw_fee <", value, "memberDrawFee");
            return (Criteria) this;
        }

        public Criteria andMemberDrawFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("member_draw_fee <=", value, "memberDrawFee");
            return (Criteria) this;
        }

        public Criteria andMemberDrawFeeIn(List<BigDecimal> values) {
            addCriterion("member_draw_fee in", values, "memberDrawFee");
            return (Criteria) this;
        }

        public Criteria andMemberDrawFeeNotIn(List<BigDecimal> values) {
            addCriterion("member_draw_fee not in", values, "memberDrawFee");
            return (Criteria) this;
        }

        public Criteria andMemberDrawFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("member_draw_fee between", value1, value2, "memberDrawFee");
            return (Criteria) this;
        }

        public Criteria andMemberDrawFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("member_draw_fee not between", value1, value2, "memberDrawFee");
            return (Criteria) this;
        }

        public Criteria andMemberSettleMoneyIsNull() {
            addCriterion("member_settle_money is null");
            return (Criteria) this;
        }

        public Criteria andMemberSettleMoneyIsNotNull() {
            addCriterion("member_settle_money is not null");
            return (Criteria) this;
        }

        public Criteria andMemberSettleMoneyEqualTo(BigDecimal value) {
            addCriterion("member_settle_money =", value, "memberSettleMoney");
            return (Criteria) this;
        }

        public Criteria andMemberSettleMoneyNotEqualTo(BigDecimal value) {
            addCriterion("member_settle_money <>", value, "memberSettleMoney");
            return (Criteria) this;
        }

        public Criteria andMemberSettleMoneyGreaterThan(BigDecimal value) {
            addCriterion("member_settle_money >", value, "memberSettleMoney");
            return (Criteria) this;
        }

        public Criteria andMemberSettleMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("member_settle_money >=", value, "memberSettleMoney");
            return (Criteria) this;
        }

        public Criteria andMemberSettleMoneyLessThan(BigDecimal value) {
            addCriterion("member_settle_money <", value, "memberSettleMoney");
            return (Criteria) this;
        }

        public Criteria andMemberSettleMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("member_settle_money <=", value, "memberSettleMoney");
            return (Criteria) this;
        }

        public Criteria andMemberSettleMoneyIn(List<BigDecimal> values) {
            addCriterion("member_settle_money in", values, "memberSettleMoney");
            return (Criteria) this;
        }

        public Criteria andMemberSettleMoneyNotIn(List<BigDecimal> values) {
            addCriterion("member_settle_money not in", values, "memberSettleMoney");
            return (Criteria) this;
        }

        public Criteria andMemberSettleMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("member_settle_money between", value1, value2, "memberSettleMoney");
            return (Criteria) this;
        }

        public Criteria andMemberSettleMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("member_settle_money not between", value1, value2, "memberSettleMoney");
            return (Criteria) this;
        }

        public Criteria andD0MoneyIsNull() {
            addCriterion("d0_money is null");
            return (Criteria) this;
        }

        public Criteria andD0MoneyIsNotNull() {
            addCriterion("d0_money is not null");
            return (Criteria) this;
        }

        public Criteria andD0MoneyEqualTo(BigDecimal value) {
            addCriterion("d0_money =", value, "d0Money");
            return (Criteria) this;
        }

        public Criteria andD0MoneyNotEqualTo(BigDecimal value) {
            addCriterion("d0_money <>", value, "d0Money");
            return (Criteria) this;
        }

        public Criteria andD0MoneyGreaterThan(BigDecimal value) {
            addCriterion("d0_money >", value, "d0Money");
            return (Criteria) this;
        }

        public Criteria andD0MoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("d0_money >=", value, "d0Money");
            return (Criteria) this;
        }

        public Criteria andD0MoneyLessThan(BigDecimal value) {
            addCriterion("d0_money <", value, "d0Money");
            return (Criteria) this;
        }

        public Criteria andD0MoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("d0_money <=", value, "d0Money");
            return (Criteria) this;
        }

        public Criteria andD0MoneyIn(List<BigDecimal> values) {
            addCriterion("d0_money in", values, "d0Money");
            return (Criteria) this;
        }

        public Criteria andD0MoneyNotIn(List<BigDecimal> values) {
            addCriterion("d0_money not in", values, "d0Money");
            return (Criteria) this;
        }

        public Criteria andD0MoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("d0_money between", value1, value2, "d0Money");
            return (Criteria) this;
        }

        public Criteria andD0MoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("d0_money not between", value1, value2, "d0Money");
            return (Criteria) this;
        }

        public Criteria andD0MemberFeeIsNull() {
            addCriterion("d0_member_fee is null");
            return (Criteria) this;
        }

        public Criteria andD0MemberFeeIsNotNull() {
            addCriterion("d0_member_fee is not null");
            return (Criteria) this;
        }

        public Criteria andD0MemberFeeEqualTo(BigDecimal value) {
            addCriterion("d0_member_fee =", value, "d0MemberFee");
            return (Criteria) this;
        }

        public Criteria andD0MemberFeeNotEqualTo(BigDecimal value) {
            addCriterion("d0_member_fee <>", value, "d0MemberFee");
            return (Criteria) this;
        }

        public Criteria andD0MemberFeeGreaterThan(BigDecimal value) {
            addCriterion("d0_member_fee >", value, "d0MemberFee");
            return (Criteria) this;
        }

        public Criteria andD0MemberFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("d0_member_fee >=", value, "d0MemberFee");
            return (Criteria) this;
        }

        public Criteria andD0MemberFeeLessThan(BigDecimal value) {
            addCriterion("d0_member_fee <", value, "d0MemberFee");
            return (Criteria) this;
        }

        public Criteria andD0MemberFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("d0_member_fee <=", value, "d0MemberFee");
            return (Criteria) this;
        }

        public Criteria andD0MemberFeeIn(List<BigDecimal> values) {
            addCriterion("d0_member_fee in", values, "d0MemberFee");
            return (Criteria) this;
        }

        public Criteria andD0MemberFeeNotIn(List<BigDecimal> values) {
            addCriterion("d0_member_fee not in", values, "d0MemberFee");
            return (Criteria) this;
        }

        public Criteria andD0MemberFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("d0_member_fee between", value1, value2, "d0MemberFee");
            return (Criteria) this;
        }

        public Criteria andD0MemberFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("d0_member_fee not between", value1, value2, "d0MemberFee");
            return (Criteria) this;
        }

        public Criteria andD0MemberDrawIsNull() {
            addCriterion("d0_member_draw is null");
            return (Criteria) this;
        }

        public Criteria andD0MemberDrawIsNotNull() {
            addCriterion("d0_member_draw is not null");
            return (Criteria) this;
        }

        public Criteria andD0MemberDrawEqualTo(BigDecimal value) {
            addCriterion("d0_member_draw =", value, "d0MemberDraw");
            return (Criteria) this;
        }

        public Criteria andD0MemberDrawNotEqualTo(BigDecimal value) {
            addCriterion("d0_member_draw <>", value, "d0MemberDraw");
            return (Criteria) this;
        }

        public Criteria andD0MemberDrawGreaterThan(BigDecimal value) {
            addCriterion("d0_member_draw >", value, "d0MemberDraw");
            return (Criteria) this;
        }

        public Criteria andD0MemberDrawGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("d0_member_draw >=", value, "d0MemberDraw");
            return (Criteria) this;
        }

        public Criteria andD0MemberDrawLessThan(BigDecimal value) {
            addCriterion("d0_member_draw <", value, "d0MemberDraw");
            return (Criteria) this;
        }

        public Criteria andD0MemberDrawLessThanOrEqualTo(BigDecimal value) {
            addCriterion("d0_member_draw <=", value, "d0MemberDraw");
            return (Criteria) this;
        }

        public Criteria andD0MemberDrawIn(List<BigDecimal> values) {
            addCriterion("d0_member_draw in", values, "d0MemberDraw");
            return (Criteria) this;
        }

        public Criteria andD0MemberDrawNotIn(List<BigDecimal> values) {
            addCriterion("d0_member_draw not in", values, "d0MemberDraw");
            return (Criteria) this;
        }

        public Criteria andD0MemberDrawBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("d0_member_draw between", value1, value2, "d0MemberDraw");
            return (Criteria) this;
        }

        public Criteria andD0MemberDrawNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("d0_member_draw not between", value1, value2, "d0MemberDraw");
            return (Criteria) this;
        }

        public Criteria andD0RoutewayFeeIsNull() {
            addCriterion("d0_routeway_fee is null");
            return (Criteria) this;
        }

        public Criteria andD0RoutewayFeeIsNotNull() {
            addCriterion("d0_routeway_fee is not null");
            return (Criteria) this;
        }

        public Criteria andD0RoutewayFeeEqualTo(BigDecimal value) {
            addCriterion("d0_routeway_fee =", value, "d0RoutewayFee");
            return (Criteria) this;
        }

        public Criteria andD0RoutewayFeeNotEqualTo(BigDecimal value) {
            addCriterion("d0_routeway_fee <>", value, "d0RoutewayFee");
            return (Criteria) this;
        }

        public Criteria andD0RoutewayFeeGreaterThan(BigDecimal value) {
            addCriterion("d0_routeway_fee >", value, "d0RoutewayFee");
            return (Criteria) this;
        }

        public Criteria andD0RoutewayFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("d0_routeway_fee >=", value, "d0RoutewayFee");
            return (Criteria) this;
        }

        public Criteria andD0RoutewayFeeLessThan(BigDecimal value) {
            addCriterion("d0_routeway_fee <", value, "d0RoutewayFee");
            return (Criteria) this;
        }

        public Criteria andD0RoutewayFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("d0_routeway_fee <=", value, "d0RoutewayFee");
            return (Criteria) this;
        }

        public Criteria andD0RoutewayFeeIn(List<BigDecimal> values) {
            addCriterion("d0_routeway_fee in", values, "d0RoutewayFee");
            return (Criteria) this;
        }

        public Criteria andD0RoutewayFeeNotIn(List<BigDecimal> values) {
            addCriterion("d0_routeway_fee not in", values, "d0RoutewayFee");
            return (Criteria) this;
        }

        public Criteria andD0RoutewayFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("d0_routeway_fee between", value1, value2, "d0RoutewayFee");
            return (Criteria) this;
        }

        public Criteria andD0RoutewayFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("d0_routeway_fee not between", value1, value2, "d0RoutewayFee");
            return (Criteria) this;
        }

        public Criteria andD0RoutewayDrawIsNull() {
            addCriterion("d0_routeway_draw is null");
            return (Criteria) this;
        }

        public Criteria andD0RoutewayDrawIsNotNull() {
            addCriterion("d0_routeway_draw is not null");
            return (Criteria) this;
        }

        public Criteria andD0RoutewayDrawEqualTo(BigDecimal value) {
            addCriterion("d0_routeway_draw =", value, "d0RoutewayDraw");
            return (Criteria) this;
        }

        public Criteria andD0RoutewayDrawNotEqualTo(BigDecimal value) {
            addCriterion("d0_routeway_draw <>", value, "d0RoutewayDraw");
            return (Criteria) this;
        }

        public Criteria andD0RoutewayDrawGreaterThan(BigDecimal value) {
            addCriterion("d0_routeway_draw >", value, "d0RoutewayDraw");
            return (Criteria) this;
        }

        public Criteria andD0RoutewayDrawGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("d0_routeway_draw >=", value, "d0RoutewayDraw");
            return (Criteria) this;
        }

        public Criteria andD0RoutewayDrawLessThan(BigDecimal value) {
            addCriterion("d0_routeway_draw <", value, "d0RoutewayDraw");
            return (Criteria) this;
        }

        public Criteria andD0RoutewayDrawLessThanOrEqualTo(BigDecimal value) {
            addCriterion("d0_routeway_draw <=", value, "d0RoutewayDraw");
            return (Criteria) this;
        }

        public Criteria andD0RoutewayDrawIn(List<BigDecimal> values) {
            addCriterion("d0_routeway_draw in", values, "d0RoutewayDraw");
            return (Criteria) this;
        }

        public Criteria andD0RoutewayDrawNotIn(List<BigDecimal> values) {
            addCriterion("d0_routeway_draw not in", values, "d0RoutewayDraw");
            return (Criteria) this;
        }

        public Criteria andD0RoutewayDrawBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("d0_routeway_draw between", value1, value2, "d0RoutewayDraw");
            return (Criteria) this;
        }

        public Criteria andD0RoutewayDrawNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("d0_routeway_draw not between", value1, value2, "d0RoutewayDraw");
            return (Criteria) this;
        }

        public Criteria andT1MoneyIsNull() {
            addCriterion("t1_money is null");
            return (Criteria) this;
        }

        public Criteria andT1MoneyIsNotNull() {
            addCriterion("t1_money is not null");
            return (Criteria) this;
        }

        public Criteria andT1MoneyEqualTo(BigDecimal value) {
            addCriterion("t1_money =", value, "t1Money");
            return (Criteria) this;
        }

        public Criteria andT1MoneyNotEqualTo(BigDecimal value) {
            addCriterion("t1_money <>", value, "t1Money");
            return (Criteria) this;
        }

        public Criteria andT1MoneyGreaterThan(BigDecimal value) {
            addCriterion("t1_money >", value, "t1Money");
            return (Criteria) this;
        }

        public Criteria andT1MoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("t1_money >=", value, "t1Money");
            return (Criteria) this;
        }

        public Criteria andT1MoneyLessThan(BigDecimal value) {
            addCriterion("t1_money <", value, "t1Money");
            return (Criteria) this;
        }

        public Criteria andT1MoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("t1_money <=", value, "t1Money");
            return (Criteria) this;
        }

        public Criteria andT1MoneyIn(List<BigDecimal> values) {
            addCriterion("t1_money in", values, "t1Money");
            return (Criteria) this;
        }

        public Criteria andT1MoneyNotIn(List<BigDecimal> values) {
            addCriterion("t1_money not in", values, "t1Money");
            return (Criteria) this;
        }

        public Criteria andT1MoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("t1_money between", value1, value2, "t1Money");
            return (Criteria) this;
        }

        public Criteria andT1MoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("t1_money not between", value1, value2, "t1Money");
            return (Criteria) this;
        }

        public Criteria andT1MemberFeeIsNull() {
            addCriterion("t1_member_fee is null");
            return (Criteria) this;
        }

        public Criteria andT1MemberFeeIsNotNull() {
            addCriterion("t1_member_fee is not null");
            return (Criteria) this;
        }

        public Criteria andT1MemberFeeEqualTo(BigDecimal value) {
            addCriterion("t1_member_fee =", value, "t1MemberFee");
            return (Criteria) this;
        }

        public Criteria andT1MemberFeeNotEqualTo(BigDecimal value) {
            addCriterion("t1_member_fee <>", value, "t1MemberFee");
            return (Criteria) this;
        }

        public Criteria andT1MemberFeeGreaterThan(BigDecimal value) {
            addCriterion("t1_member_fee >", value, "t1MemberFee");
            return (Criteria) this;
        }

        public Criteria andT1MemberFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("t1_member_fee >=", value, "t1MemberFee");
            return (Criteria) this;
        }

        public Criteria andT1MemberFeeLessThan(BigDecimal value) {
            addCriterion("t1_member_fee <", value, "t1MemberFee");
            return (Criteria) this;
        }

        public Criteria andT1MemberFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("t1_member_fee <=", value, "t1MemberFee");
            return (Criteria) this;
        }

        public Criteria andT1MemberFeeIn(List<BigDecimal> values) {
            addCriterion("t1_member_fee in", values, "t1MemberFee");
            return (Criteria) this;
        }

        public Criteria andT1MemberFeeNotIn(List<BigDecimal> values) {
            addCriterion("t1_member_fee not in", values, "t1MemberFee");
            return (Criteria) this;
        }

        public Criteria andT1MemberFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("t1_member_fee between", value1, value2, "t1MemberFee");
            return (Criteria) this;
        }

        public Criteria andT1MemberFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("t1_member_fee not between", value1, value2, "t1MemberFee");
            return (Criteria) this;
        }

        public Criteria andT1MemberDrawIsNull() {
            addCriterion("t1_member_draw is null");
            return (Criteria) this;
        }

        public Criteria andT1MemberDrawIsNotNull() {
            addCriterion("t1_member_draw is not null");
            return (Criteria) this;
        }

        public Criteria andT1MemberDrawEqualTo(BigDecimal value) {
            addCriterion("t1_member_draw =", value, "t1MemberDraw");
            return (Criteria) this;
        }

        public Criteria andT1MemberDrawNotEqualTo(BigDecimal value) {
            addCriterion("t1_member_draw <>", value, "t1MemberDraw");
            return (Criteria) this;
        }

        public Criteria andT1MemberDrawGreaterThan(BigDecimal value) {
            addCriterion("t1_member_draw >", value, "t1MemberDraw");
            return (Criteria) this;
        }

        public Criteria andT1MemberDrawGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("t1_member_draw >=", value, "t1MemberDraw");
            return (Criteria) this;
        }

        public Criteria andT1MemberDrawLessThan(BigDecimal value) {
            addCriterion("t1_member_draw <", value, "t1MemberDraw");
            return (Criteria) this;
        }

        public Criteria andT1MemberDrawLessThanOrEqualTo(BigDecimal value) {
            addCriterion("t1_member_draw <=", value, "t1MemberDraw");
            return (Criteria) this;
        }

        public Criteria andT1MemberDrawIn(List<BigDecimal> values) {
            addCriterion("t1_member_draw in", values, "t1MemberDraw");
            return (Criteria) this;
        }

        public Criteria andT1MemberDrawNotIn(List<BigDecimal> values) {
            addCriterion("t1_member_draw not in", values, "t1MemberDraw");
            return (Criteria) this;
        }

        public Criteria andT1MemberDrawBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("t1_member_draw between", value1, value2, "t1MemberDraw");
            return (Criteria) this;
        }

        public Criteria andT1MemberDrawNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("t1_member_draw not between", value1, value2, "t1MemberDraw");
            return (Criteria) this;
        }

        public Criteria andT1RoutewayFeeIsNull() {
            addCriterion("t1_routeway_fee is null");
            return (Criteria) this;
        }

        public Criteria andT1RoutewayFeeIsNotNull() {
            addCriterion("t1_routeway_fee is not null");
            return (Criteria) this;
        }

        public Criteria andT1RoutewayFeeEqualTo(BigDecimal value) {
            addCriterion("t1_routeway_fee =", value, "t1RoutewayFee");
            return (Criteria) this;
        }

        public Criteria andT1RoutewayFeeNotEqualTo(BigDecimal value) {
            addCriterion("t1_routeway_fee <>", value, "t1RoutewayFee");
            return (Criteria) this;
        }

        public Criteria andT1RoutewayFeeGreaterThan(BigDecimal value) {
            addCriterion("t1_routeway_fee >", value, "t1RoutewayFee");
            return (Criteria) this;
        }

        public Criteria andT1RoutewayFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("t1_routeway_fee >=", value, "t1RoutewayFee");
            return (Criteria) this;
        }

        public Criteria andT1RoutewayFeeLessThan(BigDecimal value) {
            addCriterion("t1_routeway_fee <", value, "t1RoutewayFee");
            return (Criteria) this;
        }

        public Criteria andT1RoutewayFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("t1_routeway_fee <=", value, "t1RoutewayFee");
            return (Criteria) this;
        }

        public Criteria andT1RoutewayFeeIn(List<BigDecimal> values) {
            addCriterion("t1_routeway_fee in", values, "t1RoutewayFee");
            return (Criteria) this;
        }

        public Criteria andT1RoutewayFeeNotIn(List<BigDecimal> values) {
            addCriterion("t1_routeway_fee not in", values, "t1RoutewayFee");
            return (Criteria) this;
        }

        public Criteria andT1RoutewayFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("t1_routeway_fee between", value1, value2, "t1RoutewayFee");
            return (Criteria) this;
        }

        public Criteria andT1RoutewayFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("t1_routeway_fee not between", value1, value2, "t1RoutewayFee");
            return (Criteria) this;
        }

        public Criteria andT1RoutewayDrawIsNull() {
            addCriterion("t1_routeway_draw is null");
            return (Criteria) this;
        }

        public Criteria andT1RoutewayDrawIsNotNull() {
            addCriterion("t1_routeway_draw is not null");
            return (Criteria) this;
        }

        public Criteria andT1RoutewayDrawEqualTo(BigDecimal value) {
            addCriterion("t1_routeway_draw =", value, "t1RoutewayDraw");
            return (Criteria) this;
        }

        public Criteria andT1RoutewayDrawNotEqualTo(BigDecimal value) {
            addCriterion("t1_routeway_draw <>", value, "t1RoutewayDraw");
            return (Criteria) this;
        }

        public Criteria andT1RoutewayDrawGreaterThan(BigDecimal value) {
            addCriterion("t1_routeway_draw >", value, "t1RoutewayDraw");
            return (Criteria) this;
        }

        public Criteria andT1RoutewayDrawGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("t1_routeway_draw >=", value, "t1RoutewayDraw");
            return (Criteria) this;
        }

        public Criteria andT1RoutewayDrawLessThan(BigDecimal value) {
            addCriterion("t1_routeway_draw <", value, "t1RoutewayDraw");
            return (Criteria) this;
        }

        public Criteria andT1RoutewayDrawLessThanOrEqualTo(BigDecimal value) {
            addCriterion("t1_routeway_draw <=", value, "t1RoutewayDraw");
            return (Criteria) this;
        }

        public Criteria andT1RoutewayDrawIn(List<BigDecimal> values) {
            addCriterion("t1_routeway_draw in", values, "t1RoutewayDraw");
            return (Criteria) this;
        }

        public Criteria andT1RoutewayDrawNotIn(List<BigDecimal> values) {
            addCriterion("t1_routeway_draw not in", values, "t1RoutewayDraw");
            return (Criteria) this;
        }

        public Criteria andT1RoutewayDrawBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("t1_routeway_draw between", value1, value2, "t1RoutewayDraw");
            return (Criteria) this;
        }

        public Criteria andT1RoutewayDrawNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("t1_routeway_draw not between", value1, value2, "t1RoutewayDraw");
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

        public Criteria andPtSerialNoIsNull() {
            addCriterion("pt_serial_no is null");
            return (Criteria) this;
        }

        public Criteria andPtSerialNoIsNotNull() {
            addCriterion("pt_serial_no is not null");
            return (Criteria) this;
        }

        public Criteria andPtSerialNoEqualTo(String value) {
            addCriterion("pt_serial_no =", value, "ptSerialNo");
            return (Criteria) this;
        }

        public Criteria andPtSerialNoNotEqualTo(String value) {
            addCriterion("pt_serial_no <>", value, "ptSerialNo");
            return (Criteria) this;
        }

        public Criteria andPtSerialNoGreaterThan(String value) {
            addCriterion("pt_serial_no >", value, "ptSerialNo");
            return (Criteria) this;
        }

        public Criteria andPtSerialNoGreaterThanOrEqualTo(String value) {
            addCriterion("pt_serial_no >=", value, "ptSerialNo");
            return (Criteria) this;
        }

        public Criteria andPtSerialNoLessThan(String value) {
            addCriterion("pt_serial_no <", value, "ptSerialNo");
            return (Criteria) this;
        }

        public Criteria andPtSerialNoLessThanOrEqualTo(String value) {
            addCriterion("pt_serial_no <=", value, "ptSerialNo");
            return (Criteria) this;
        }

        public Criteria andPtSerialNoLike(String value) {
            addCriterion("pt_serial_no like", value, "ptSerialNo");
            return (Criteria) this;
        }

        public Criteria andPtSerialNoNotLike(String value) {
            addCriterion("pt_serial_no not like", value, "ptSerialNo");
            return (Criteria) this;
        }

        public Criteria andPtSerialNoIn(List<String> values) {
            addCriterion("pt_serial_no in", values, "ptSerialNo");
            return (Criteria) this;
        }

        public Criteria andPtSerialNoNotIn(List<String> values) {
            addCriterion("pt_serial_no not in", values, "ptSerialNo");
            return (Criteria) this;
        }

        public Criteria andPtSerialNoBetween(String value1, String value2) {
            addCriterion("pt_serial_no between", value1, value2, "ptSerialNo");
            return (Criteria) this;
        }

        public Criteria andPtSerialNoNotBetween(String value1, String value2) {
            addCriterion("pt_serial_no not between", value1, value2, "ptSerialNo");
            return (Criteria) this;
        }

        public Criteria andReqDateIsNull() {
            addCriterion("req_date is null");
            return (Criteria) this;
        }

        public Criteria andReqDateIsNotNull() {
            addCriterion("req_date is not null");
            return (Criteria) this;
        }

        public Criteria andReqDateEqualTo(String value) {
            addCriterion("req_date =", value, "reqDate");
            return (Criteria) this;
        }

        public Criteria andReqDateNotEqualTo(String value) {
            addCriterion("req_date <>", value, "reqDate");
            return (Criteria) this;
        }

        public Criteria andReqDateGreaterThan(String value) {
            addCriterion("req_date >", value, "reqDate");
            return (Criteria) this;
        }

        public Criteria andReqDateGreaterThanOrEqualTo(String value) {
            addCriterion("req_date >=", value, "reqDate");
            return (Criteria) this;
        }

        public Criteria andReqDateLessThan(String value) {
            addCriterion("req_date <", value, "reqDate");
            return (Criteria) this;
        }

        public Criteria andReqDateLessThanOrEqualTo(String value) {
            addCriterion("req_date <=", value, "reqDate");
            return (Criteria) this;
        }

        public Criteria andReqDateLike(String value) {
            addCriterion("req_date like", value, "reqDate");
            return (Criteria) this;
        }

        public Criteria andReqDateNotLike(String value) {
            addCriterion("req_date not like", value, "reqDate");
            return (Criteria) this;
        }

        public Criteria andReqDateIn(List<String> values) {
            addCriterion("req_date in", values, "reqDate");
            return (Criteria) this;
        }

        public Criteria andReqDateNotIn(List<String> values) {
            addCriterion("req_date not in", values, "reqDate");
            return (Criteria) this;
        }

        public Criteria andReqDateBetween(String value1, String value2) {
            addCriterion("req_date between", value1, value2, "reqDate");
            return (Criteria) this;
        }

        public Criteria andReqDateNotBetween(String value1, String value2) {
            addCriterion("req_date not between", value1, value2, "reqDate");
            return (Criteria) this;
        }

        public Criteria andRespDateIsNull() {
            addCriterion("resp_date is null");
            return (Criteria) this;
        }

        public Criteria andRespDateIsNotNull() {
            addCriterion("resp_date is not null");
            return (Criteria) this;
        }

        public Criteria andRespDateEqualTo(String value) {
            addCriterion("resp_date =", value, "respDate");
            return (Criteria) this;
        }

        public Criteria andRespDateNotEqualTo(String value) {
            addCriterion("resp_date <>", value, "respDate");
            return (Criteria) this;
        }

        public Criteria andRespDateGreaterThan(String value) {
            addCriterion("resp_date >", value, "respDate");
            return (Criteria) this;
        }

        public Criteria andRespDateGreaterThanOrEqualTo(String value) {
            addCriterion("resp_date >=", value, "respDate");
            return (Criteria) this;
        }

        public Criteria andRespDateLessThan(String value) {
            addCriterion("resp_date <", value, "respDate");
            return (Criteria) this;
        }

        public Criteria andRespDateLessThanOrEqualTo(String value) {
            addCriterion("resp_date <=", value, "respDate");
            return (Criteria) this;
        }

        public Criteria andRespDateLike(String value) {
            addCriterion("resp_date like", value, "respDate");
            return (Criteria) this;
        }

        public Criteria andRespDateNotLike(String value) {
            addCriterion("resp_date not like", value, "respDate");
            return (Criteria) this;
        }

        public Criteria andRespDateIn(List<String> values) {
            addCriterion("resp_date in", values, "respDate");
            return (Criteria) this;
        }

        public Criteria andRespDateNotIn(List<String> values) {
            addCriterion("resp_date not in", values, "respDate");
            return (Criteria) this;
        }

        public Criteria andRespDateBetween(String value1, String value2) {
            addCriterion("resp_date between", value1, value2, "respDate");
            return (Criteria) this;
        }

        public Criteria andRespDateNotBetween(String value1, String value2) {
            addCriterion("resp_date not between", value1, value2, "respDate");
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

        public Criteria andRespCodeIsNull() {
            addCriterion("resp_code is null");
            return (Criteria) this;
        }

        public Criteria andRespCodeIsNotNull() {
            addCriterion("resp_code is not null");
            return (Criteria) this;
        }

        public Criteria andRespCodeEqualTo(String value) {
            addCriterion("resp_code =", value, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeNotEqualTo(String value) {
            addCriterion("resp_code <>", value, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeGreaterThan(String value) {
            addCriterion("resp_code >", value, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeGreaterThanOrEqualTo(String value) {
            addCriterion("resp_code >=", value, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeLessThan(String value) {
            addCriterion("resp_code <", value, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeLessThanOrEqualTo(String value) {
            addCriterion("resp_code <=", value, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeLike(String value) {
            addCriterion("resp_code like", value, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeNotLike(String value) {
            addCriterion("resp_code not like", value, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeIn(List<String> values) {
            addCriterion("resp_code in", values, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeNotIn(List<String> values) {
            addCriterion("resp_code not in", values, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeBetween(String value1, String value2) {
            addCriterion("resp_code between", value1, value2, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeNotBetween(String value1, String value2) {
            addCriterion("resp_code not between", value1, value2, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespMsgIsNull() {
            addCriterion("resp_msg is null");
            return (Criteria) this;
        }

        public Criteria andRespMsgIsNotNull() {
            addCriterion("resp_msg is not null");
            return (Criteria) this;
        }

        public Criteria andRespMsgEqualTo(String value) {
            addCriterion("resp_msg =", value, "respMsg");
            return (Criteria) this;
        }

        public Criteria andRespMsgNotEqualTo(String value) {
            addCriterion("resp_msg <>", value, "respMsg");
            return (Criteria) this;
        }

        public Criteria andRespMsgGreaterThan(String value) {
            addCriterion("resp_msg >", value, "respMsg");
            return (Criteria) this;
        }

        public Criteria andRespMsgGreaterThanOrEqualTo(String value) {
            addCriterion("resp_msg >=", value, "respMsg");
            return (Criteria) this;
        }

        public Criteria andRespMsgLessThan(String value) {
            addCriterion("resp_msg <", value, "respMsg");
            return (Criteria) this;
        }

        public Criteria andRespMsgLessThanOrEqualTo(String value) {
            addCriterion("resp_msg <=", value, "respMsg");
            return (Criteria) this;
        }

        public Criteria andRespMsgLike(String value) {
            addCriterion("resp_msg like", value, "respMsg");
            return (Criteria) this;
        }

        public Criteria andRespMsgNotLike(String value) {
            addCriterion("resp_msg not like", value, "respMsg");
            return (Criteria) this;
        }

        public Criteria andRespMsgIn(List<String> values) {
            addCriterion("resp_msg in", values, "respMsg");
            return (Criteria) this;
        }

        public Criteria andRespMsgNotIn(List<String> values) {
            addCriterion("resp_msg not in", values, "respMsg");
            return (Criteria) this;
        }

        public Criteria andRespMsgBetween(String value1, String value2) {
            addCriterion("resp_msg between", value1, value2, "respMsg");
            return (Criteria) this;
        }

        public Criteria andRespMsgNotBetween(String value1, String value2) {
            addCriterion("resp_msg not between", value1, value2, "respMsg");
            return (Criteria) this;
        }

        public Criteria andCardTypeIsNull() {
            addCriterion("card_type is null");
            return (Criteria) this;
        }

        public Criteria andCardTypeIsNotNull() {
            addCriterion("card_type is not null");
            return (Criteria) this;
        }

        public Criteria andCardTypeEqualTo(String value) {
            addCriterion("card_type =", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotEqualTo(String value) {
            addCriterion("card_type <>", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeGreaterThan(String value) {
            addCriterion("card_type >", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeGreaterThanOrEqualTo(String value) {
            addCriterion("card_type >=", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeLessThan(String value) {
            addCriterion("card_type <", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeLessThanOrEqualTo(String value) {
            addCriterion("card_type <=", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeLike(String value) {
            addCriterion("card_type like", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotLike(String value) {
            addCriterion("card_type not like", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeIn(List<String> values) {
            addCriterion("card_type in", values, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotIn(List<String> values) {
            addCriterion("card_type not in", values, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeBetween(String value1, String value2) {
            addCriterion("card_type between", value1, value2, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotBetween(String value1, String value2) {
            addCriterion("card_type not between", value1, value2, "cardType");
            return (Criteria) this;
        }

        public Criteria andRouteIdIsNull() {
            addCriterion("route_id is null");
            return (Criteria) this;
        }

        public Criteria andRouteIdIsNotNull() {
            addCriterion("route_id is not null");
            return (Criteria) this;
        }

        public Criteria andRouteIdEqualTo(String value) {
            addCriterion("route_id =", value, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdNotEqualTo(String value) {
            addCriterion("route_id <>", value, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdGreaterThan(String value) {
            addCriterion("route_id >", value, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdGreaterThanOrEqualTo(String value) {
            addCriterion("route_id >=", value, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdLessThan(String value) {
            addCriterion("route_id <", value, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdLessThanOrEqualTo(String value) {
            addCriterion("route_id <=", value, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdLike(String value) {
            addCriterion("route_id like", value, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdNotLike(String value) {
            addCriterion("route_id not like", value, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdIn(List<String> values) {
            addCriterion("route_id in", values, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdNotIn(List<String> values) {
            addCriterion("route_id not in", values, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdBetween(String value1, String value2) {
            addCriterion("route_id between", value1, value2, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdNotBetween(String value1, String value2) {
            addCriterion("route_id not between", value1, value2, "routeId");
            return (Criteria) this;
        }

        public Criteria andPayTimeIsNull() {
            addCriterion("pay_time is null");
            return (Criteria) this;
        }

        public Criteria andPayTimeIsNotNull() {
            addCriterion("pay_time is not null");
            return (Criteria) this;
        }

        public Criteria andPayTimeEqualTo(String value) {
            addCriterion("pay_time =", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotEqualTo(String value) {
            addCriterion("pay_time <>", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeGreaterThan(String value) {
            addCriterion("pay_time >", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeGreaterThanOrEqualTo(String value) {
            addCriterion("pay_time >=", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLessThan(String value) {
            addCriterion("pay_time <", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLessThanOrEqualTo(String value) {
            addCriterion("pay_time <=", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLike(String value) {
            addCriterion("pay_time like", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotLike(String value) {
            addCriterion("pay_time not like", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeIn(List<String> values) {
            addCriterion("pay_time in", values, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotIn(List<String> values) {
            addCriterion("pay_time not in", values, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeBetween(String value1, String value2) {
            addCriterion("pay_time between", value1, value2, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotBetween(String value1, String value2) {
            addCriterion("pay_time not between", value1, value2, "payTime");
            return (Criteria) this;
        }

        public Criteria andBalanceDateIsNull() {
            addCriterion("balance_date is null");
            return (Criteria) this;
        }

        public Criteria andBalanceDateIsNotNull() {
            addCriterion("balance_date is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceDateEqualTo(String value) {
            addCriterion("balance_date =", value, "balanceDate");
            return (Criteria) this;
        }

        public Criteria andBalanceDateNotEqualTo(String value) {
            addCriterion("balance_date <>", value, "balanceDate");
            return (Criteria) this;
        }

        public Criteria andBalanceDateGreaterThan(String value) {
            addCriterion("balance_date >", value, "balanceDate");
            return (Criteria) this;
        }

        public Criteria andBalanceDateGreaterThanOrEqualTo(String value) {
            addCriterion("balance_date >=", value, "balanceDate");
            return (Criteria) this;
        }

        public Criteria andBalanceDateLessThan(String value) {
            addCriterion("balance_date <", value, "balanceDate");
            return (Criteria) this;
        }

        public Criteria andBalanceDateLessThanOrEqualTo(String value) {
            addCriterion("balance_date <=", value, "balanceDate");
            return (Criteria) this;
        }

        public Criteria andBalanceDateLike(String value) {
            addCriterion("balance_date like", value, "balanceDate");
            return (Criteria) this;
        }

        public Criteria andBalanceDateNotLike(String value) {
            addCriterion("balance_date not like", value, "balanceDate");
            return (Criteria) this;
        }

        public Criteria andBalanceDateIn(List<String> values) {
            addCriterion("balance_date in", values, "balanceDate");
            return (Criteria) this;
        }

        public Criteria andBalanceDateNotIn(List<String> values) {
            addCriterion("balance_date not in", values, "balanceDate");
            return (Criteria) this;
        }

        public Criteria andBalanceDateBetween(String value1, String value2) {
            addCriterion("balance_date between", value1, value2, "balanceDate");
            return (Criteria) this;
        }

        public Criteria andBalanceDateNotBetween(String value1, String value2) {
            addCriterion("balance_date not between", value1, value2, "balanceDate");
            return (Criteria) this;
        }

        public Criteria andChannelNoIsNull() {
            addCriterion("channel_no is null");
            return (Criteria) this;
        }

        public Criteria andChannelNoIsNotNull() {
            addCriterion("channel_no is not null");
            return (Criteria) this;
        }

        public Criteria andChannelNoEqualTo(String value) {
            addCriterion("channel_no =", value, "channelNo");
            return (Criteria) this;
        }

        public Criteria andChannelNoNotEqualTo(String value) {
            addCriterion("channel_no <>", value, "channelNo");
            return (Criteria) this;
        }

        public Criteria andChannelNoGreaterThan(String value) {
            addCriterion("channel_no >", value, "channelNo");
            return (Criteria) this;
        }

        public Criteria andChannelNoGreaterThanOrEqualTo(String value) {
            addCriterion("channel_no >=", value, "channelNo");
            return (Criteria) this;
        }

        public Criteria andChannelNoLessThan(String value) {
            addCriterion("channel_no <", value, "channelNo");
            return (Criteria) this;
        }

        public Criteria andChannelNoLessThanOrEqualTo(String value) {
            addCriterion("channel_no <=", value, "channelNo");
            return (Criteria) this;
        }

        public Criteria andChannelNoLike(String value) {
            addCriterion("channel_no like", value, "channelNo");
            return (Criteria) this;
        }

        public Criteria andChannelNoNotLike(String value) {
            addCriterion("channel_no not like", value, "channelNo");
            return (Criteria) this;
        }

        public Criteria andChannelNoIn(List<String> values) {
            addCriterion("channel_no in", values, "channelNo");
            return (Criteria) this;
        }

        public Criteria andChannelNoNotIn(List<String> values) {
            addCriterion("channel_no not in", values, "channelNo");
            return (Criteria) this;
        }

        public Criteria andChannelNoBetween(String value1, String value2) {
            addCriterion("channel_no between", value1, value2, "channelNo");
            return (Criteria) this;
        }

        public Criteria andChannelNoNotBetween(String value1, String value2) {
            addCriterion("channel_no not between", value1, value2, "channelNo");
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

        public Criteria andInterfaceTypeIsNull() {
            addCriterion("interface_type is null");
            return (Criteria) this;
        }

        public Criteria andInterfaceTypeIsNotNull() {
            addCriterion("interface_type is not null");
            return (Criteria) this;
        }

        public Criteria andInterfaceTypeEqualTo(String value) {
            addCriterion("interface_type =", value, "interfaceType");
            return (Criteria) this;
        }

        public Criteria andInterfaceTypeNotEqualTo(String value) {
            addCriterion("interface_type <>", value, "interfaceType");
            return (Criteria) this;
        }

        public Criteria andInterfaceTypeGreaterThan(String value) {
            addCriterion("interface_type >", value, "interfaceType");
            return (Criteria) this;
        }

        public Criteria andInterfaceTypeGreaterThanOrEqualTo(String value) {
            addCriterion("interface_type >=", value, "interfaceType");
            return (Criteria) this;
        }

        public Criteria andInterfaceTypeLessThan(String value) {
            addCriterion("interface_type <", value, "interfaceType");
            return (Criteria) this;
        }

        public Criteria andInterfaceTypeLessThanOrEqualTo(String value) {
            addCriterion("interface_type <=", value, "interfaceType");
            return (Criteria) this;
        }

        public Criteria andInterfaceTypeLike(String value) {
            addCriterion("interface_type like", value, "interfaceType");
            return (Criteria) this;
        }

        public Criteria andInterfaceTypeNotLike(String value) {
            addCriterion("interface_type not like", value, "interfaceType");
            return (Criteria) this;
        }

        public Criteria andInterfaceTypeIn(List<String> values) {
            addCriterion("interface_type in", values, "interfaceType");
            return (Criteria) this;
        }

        public Criteria andInterfaceTypeNotIn(List<String> values) {
            addCriterion("interface_type not in", values, "interfaceType");
            return (Criteria) this;
        }

        public Criteria andInterfaceTypeBetween(String value1, String value2) {
            addCriterion("interface_type between", value1, value2, "interfaceType");
            return (Criteria) this;
        }

        public Criteria andInterfaceTypeNotBetween(String value1, String value2) {
            addCriterion("interface_type not between", value1, value2, "interfaceType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeIsNull() {
            addCriterion("platform_type is null");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeIsNotNull() {
            addCriterion("platform_type is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeEqualTo(String value) {
            addCriterion("platform_type =", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeNotEqualTo(String value) {
            addCriterion("platform_type <>", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeGreaterThan(String value) {
            addCriterion("platform_type >", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeGreaterThanOrEqualTo(String value) {
            addCriterion("platform_type >=", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeLessThan(String value) {
            addCriterion("platform_type <", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeLessThanOrEqualTo(String value) {
            addCriterion("platform_type <=", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeLike(String value) {
            addCriterion("platform_type like", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeNotLike(String value) {
            addCriterion("platform_type not like", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeIn(List<String> values) {
            addCriterion("platform_type in", values, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeNotIn(List<String> values) {
            addCriterion("platform_type not in", values, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeBetween(String value1, String value2) {
            addCriterion("platform_type between", value1, value2, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeNotBetween(String value1, String value2) {
            addCriterion("platform_type not between", value1, value2, "platformType");
            return (Criteria) this;
        }

        public Criteria andSettleCancelFlagIsNull() {
            addCriterion("settle_cancel_flag is null");
            return (Criteria) this;
        }

        public Criteria andSettleCancelFlagIsNotNull() {
            addCriterion("settle_cancel_flag is not null");
            return (Criteria) this;
        }

        public Criteria andSettleCancelFlagEqualTo(String value) {
            addCriterion("settle_cancel_flag =", value, "settleCancelFlag");
            return (Criteria) this;
        }

        public Criteria andSettleCancelFlagNotEqualTo(String value) {
            addCriterion("settle_cancel_flag <>", value, "settleCancelFlag");
            return (Criteria) this;
        }

        public Criteria andSettleCancelFlagGreaterThan(String value) {
            addCriterion("settle_cancel_flag >", value, "settleCancelFlag");
            return (Criteria) this;
        }

        public Criteria andSettleCancelFlagGreaterThanOrEqualTo(String value) {
            addCriterion("settle_cancel_flag >=", value, "settleCancelFlag");
            return (Criteria) this;
        }

        public Criteria andSettleCancelFlagLessThan(String value) {
            addCriterion("settle_cancel_flag <", value, "settleCancelFlag");
            return (Criteria) this;
        }

        public Criteria andSettleCancelFlagLessThanOrEqualTo(String value) {
            addCriterion("settle_cancel_flag <=", value, "settleCancelFlag");
            return (Criteria) this;
        }

        public Criteria andSettleCancelFlagLike(String value) {
            addCriterion("settle_cancel_flag like", value, "settleCancelFlag");
            return (Criteria) this;
        }

        public Criteria andSettleCancelFlagNotLike(String value) {
            addCriterion("settle_cancel_flag not like", value, "settleCancelFlag");
            return (Criteria) this;
        }

        public Criteria andSettleCancelFlagIn(List<String> values) {
            addCriterion("settle_cancel_flag in", values, "settleCancelFlag");
            return (Criteria) this;
        }

        public Criteria andSettleCancelFlagNotIn(List<String> values) {
            addCriterion("settle_cancel_flag not in", values, "settleCancelFlag");
            return (Criteria) this;
        }

        public Criteria andSettleCancelFlagBetween(String value1, String value2) {
            addCriterion("settle_cancel_flag between", value1, value2, "settleCancelFlag");
            return (Criteria) this;
        }

        public Criteria andSettleCancelFlagNotBetween(String value1, String value2) {
            addCriterion("settle_cancel_flag not between", value1, value2, "settleCancelFlag");
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