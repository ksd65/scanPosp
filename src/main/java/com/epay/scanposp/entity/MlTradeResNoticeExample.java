package com.epay.scanposp.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MlTradeResNoticeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MlTradeResNoticeExample() {
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

        public Criteria andOrderIdIsNull() {
            addCriterion("ORDER_ID is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("ORDER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(String value) {
            addCriterion("ORDER_ID =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(String value) {
            addCriterion("ORDER_ID <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(String value) {
            addCriterion("ORDER_ID >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("ORDER_ID >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(String value) {
            addCriterion("ORDER_ID <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(String value) {
            addCriterion("ORDER_ID <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLike(String value) {
            addCriterion("ORDER_ID like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotLike(String value) {
            addCriterion("ORDER_ID not like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<String> values) {
            addCriterion("ORDER_ID in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<String> values) {
            addCriterion("ORDER_ID not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(String value1, String value2) {
            addCriterion("ORDER_ID between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(String value1, String value2) {
            addCriterion("ORDER_ID not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderAmtIsNull() {
            addCriterion("ORDER_AMT is null");
            return (Criteria) this;
        }

        public Criteria andOrderAmtIsNotNull() {
            addCriterion("ORDER_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andOrderAmtEqualTo(String value) {
            addCriterion("ORDER_AMT =", value, "orderAmt");
            return (Criteria) this;
        }

        public Criteria andOrderAmtNotEqualTo(String value) {
            addCriterion("ORDER_AMT <>", value, "orderAmt");
            return (Criteria) this;
        }

        public Criteria andOrderAmtGreaterThan(String value) {
            addCriterion("ORDER_AMT >", value, "orderAmt");
            return (Criteria) this;
        }

        public Criteria andOrderAmtGreaterThanOrEqualTo(String value) {
            addCriterion("ORDER_AMT >=", value, "orderAmt");
            return (Criteria) this;
        }

        public Criteria andOrderAmtLessThan(String value) {
            addCriterion("ORDER_AMT <", value, "orderAmt");
            return (Criteria) this;
        }

        public Criteria andOrderAmtLessThanOrEqualTo(String value) {
            addCriterion("ORDER_AMT <=", value, "orderAmt");
            return (Criteria) this;
        }

        public Criteria andOrderAmtLike(String value) {
            addCriterion("ORDER_AMT like", value, "orderAmt");
            return (Criteria) this;
        }

        public Criteria andOrderAmtNotLike(String value) {
            addCriterion("ORDER_AMT not like", value, "orderAmt");
            return (Criteria) this;
        }

        public Criteria andOrderAmtIn(List<String> values) {
            addCriterion("ORDER_AMT in", values, "orderAmt");
            return (Criteria) this;
        }

        public Criteria andOrderAmtNotIn(List<String> values) {
            addCriterion("ORDER_AMT not in", values, "orderAmt");
            return (Criteria) this;
        }

        public Criteria andOrderAmtBetween(String value1, String value2) {
            addCriterion("ORDER_AMT between", value1, value2, "orderAmt");
            return (Criteria) this;
        }

        public Criteria andOrderAmtNotBetween(String value1, String value2) {
            addCriterion("ORDER_AMT not between", value1, value2, "orderAmt");
            return (Criteria) this;
        }

        public Criteria andRespCodeIsNull() {
            addCriterion("RESP_CODE is null");
            return (Criteria) this;
        }

        public Criteria andRespCodeIsNotNull() {
            addCriterion("RESP_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andRespCodeEqualTo(String value) {
            addCriterion("RESP_CODE =", value, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeNotEqualTo(String value) {
            addCriterion("RESP_CODE <>", value, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeGreaterThan(String value) {
            addCriterion("RESP_CODE >", value, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeGreaterThanOrEqualTo(String value) {
            addCriterion("RESP_CODE >=", value, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeLessThan(String value) {
            addCriterion("RESP_CODE <", value, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeLessThanOrEqualTo(String value) {
            addCriterion("RESP_CODE <=", value, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeLike(String value) {
            addCriterion("RESP_CODE like", value, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeNotLike(String value) {
            addCriterion("RESP_CODE not like", value, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeIn(List<String> values) {
            addCriterion("RESP_CODE in", values, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeNotIn(List<String> values) {
            addCriterion("RESP_CODE not in", values, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeBetween(String value1, String value2) {
            addCriterion("RESP_CODE between", value1, value2, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeNotBetween(String value1, String value2) {
            addCriterion("RESP_CODE not between", value1, value2, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespDescIsNull() {
            addCriterion("RESP_DESC is null");
            return (Criteria) this;
        }

        public Criteria andRespDescIsNotNull() {
            addCriterion("RESP_DESC is not null");
            return (Criteria) this;
        }

        public Criteria andRespDescEqualTo(String value) {
            addCriterion("RESP_DESC =", value, "respDesc");
            return (Criteria) this;
        }

        public Criteria andRespDescNotEqualTo(String value) {
            addCriterion("RESP_DESC <>", value, "respDesc");
            return (Criteria) this;
        }

        public Criteria andRespDescGreaterThan(String value) {
            addCriterion("RESP_DESC >", value, "respDesc");
            return (Criteria) this;
        }

        public Criteria andRespDescGreaterThanOrEqualTo(String value) {
            addCriterion("RESP_DESC >=", value, "respDesc");
            return (Criteria) this;
        }

        public Criteria andRespDescLessThan(String value) {
            addCriterion("RESP_DESC <", value, "respDesc");
            return (Criteria) this;
        }

        public Criteria andRespDescLessThanOrEqualTo(String value) {
            addCriterion("RESP_DESC <=", value, "respDesc");
            return (Criteria) this;
        }

        public Criteria andRespDescLike(String value) {
            addCriterion("RESP_DESC like", value, "respDesc");
            return (Criteria) this;
        }

        public Criteria andRespDescNotLike(String value) {
            addCriterion("RESP_DESC not like", value, "respDesc");
            return (Criteria) this;
        }

        public Criteria andRespDescIn(List<String> values) {
            addCriterion("RESP_DESC in", values, "respDesc");
            return (Criteria) this;
        }

        public Criteria andRespDescNotIn(List<String> values) {
            addCriterion("RESP_DESC not in", values, "respDesc");
            return (Criteria) this;
        }

        public Criteria andRespDescBetween(String value1, String value2) {
            addCriterion("RESP_DESC between", value1, value2, "respDesc");
            return (Criteria) this;
        }

        public Criteria andRespDescNotBetween(String value1, String value2) {
            addCriterion("RESP_DESC not between", value1, value2, "respDesc");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("USER_ID is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("USER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("USER_ID =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("USER_ID <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("USER_ID >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("USER_ID >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("USER_ID <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("USER_ID <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("USER_ID like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("USER_ID not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("USER_ID in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("USER_ID not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("USER_ID between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("USER_ID not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andSignTypeIsNull() {
            addCriterion("SIGN_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andSignTypeIsNotNull() {
            addCriterion("SIGN_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andSignTypeEqualTo(String value) {
            addCriterion("SIGN_TYPE =", value, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeNotEqualTo(String value) {
            addCriterion("SIGN_TYPE <>", value, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeGreaterThan(String value) {
            addCriterion("SIGN_TYPE >", value, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeGreaterThanOrEqualTo(String value) {
            addCriterion("SIGN_TYPE >=", value, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeLessThan(String value) {
            addCriterion("SIGN_TYPE <", value, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeLessThanOrEqualTo(String value) {
            addCriterion("SIGN_TYPE <=", value, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeLike(String value) {
            addCriterion("SIGN_TYPE like", value, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeNotLike(String value) {
            addCriterion("SIGN_TYPE not like", value, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeIn(List<String> values) {
            addCriterion("SIGN_TYPE in", values, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeNotIn(List<String> values) {
            addCriterion("SIGN_TYPE not in", values, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeBetween(String value1, String value2) {
            addCriterion("SIGN_TYPE between", value1, value2, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeNotBetween(String value1, String value2) {
            addCriterion("SIGN_TYPE not between", value1, value2, "signType");
            return (Criteria) this;
        }

        public Criteria andBusCodeIsNull() {
            addCriterion("BUS_CODE is null");
            return (Criteria) this;
        }

        public Criteria andBusCodeIsNotNull() {
            addCriterion("BUS_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andBusCodeEqualTo(String value) {
            addCriterion("BUS_CODE =", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeNotEqualTo(String value) {
            addCriterion("BUS_CODE <>", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeGreaterThan(String value) {
            addCriterion("BUS_CODE >", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_CODE >=", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeLessThan(String value) {
            addCriterion("BUS_CODE <", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeLessThanOrEqualTo(String value) {
            addCriterion("BUS_CODE <=", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeLike(String value) {
            addCriterion("BUS_CODE like", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeNotLike(String value) {
            addCriterion("BUS_CODE not like", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeIn(List<String> values) {
            addCriterion("BUS_CODE in", values, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeNotIn(List<String> values) {
            addCriterion("BUS_CODE not in", values, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeBetween(String value1, String value2) {
            addCriterion("BUS_CODE between", value1, value2, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeNotBetween(String value1, String value2) {
            addCriterion("BUS_CODE not between", value1, value2, "busCode");
            return (Criteria) this;
        }

        public Criteria andCnyIsNull() {
            addCriterion("CNY is null");
            return (Criteria) this;
        }

        public Criteria andCnyIsNotNull() {
            addCriterion("CNY is not null");
            return (Criteria) this;
        }

        public Criteria andCnyEqualTo(String value) {
            addCriterion("CNY =", value, "cny");
            return (Criteria) this;
        }

        public Criteria andCnyNotEqualTo(String value) {
            addCriterion("CNY <>", value, "cny");
            return (Criteria) this;
        }

        public Criteria andCnyGreaterThan(String value) {
            addCriterion("CNY >", value, "cny");
            return (Criteria) this;
        }

        public Criteria andCnyGreaterThanOrEqualTo(String value) {
            addCriterion("CNY >=", value, "cny");
            return (Criteria) this;
        }

        public Criteria andCnyLessThan(String value) {
            addCriterion("CNY <", value, "cny");
            return (Criteria) this;
        }

        public Criteria andCnyLessThanOrEqualTo(String value) {
            addCriterion("CNY <=", value, "cny");
            return (Criteria) this;
        }

        public Criteria andCnyLike(String value) {
            addCriterion("CNY like", value, "cny");
            return (Criteria) this;
        }

        public Criteria andCnyNotLike(String value) {
            addCriterion("CNY not like", value, "cny");
            return (Criteria) this;
        }

        public Criteria andCnyIn(List<String> values) {
            addCriterion("CNY in", values, "cny");
            return (Criteria) this;
        }

        public Criteria andCnyNotIn(List<String> values) {
            addCriterion("CNY not in", values, "cny");
            return (Criteria) this;
        }

        public Criteria andCnyBetween(String value1, String value2) {
            addCriterion("CNY between", value1, value2, "cny");
            return (Criteria) this;
        }

        public Criteria andCnyNotBetween(String value1, String value2) {
            addCriterion("CNY not between", value1, value2, "cny");
            return (Criteria) this;
        }

        public Criteria andAdd1IsNull() {
            addCriterion("ADD1 is null");
            return (Criteria) this;
        }

        public Criteria andAdd1IsNotNull() {
            addCriterion("ADD1 is not null");
            return (Criteria) this;
        }

        public Criteria andAdd1EqualTo(String value) {
            addCriterion("ADD1 =", value, "add1");
            return (Criteria) this;
        }

        public Criteria andAdd1NotEqualTo(String value) {
            addCriterion("ADD1 <>", value, "add1");
            return (Criteria) this;
        }

        public Criteria andAdd1GreaterThan(String value) {
            addCriterion("ADD1 >", value, "add1");
            return (Criteria) this;
        }

        public Criteria andAdd1GreaterThanOrEqualTo(String value) {
            addCriterion("ADD1 >=", value, "add1");
            return (Criteria) this;
        }

        public Criteria andAdd1LessThan(String value) {
            addCriterion("ADD1 <", value, "add1");
            return (Criteria) this;
        }

        public Criteria andAdd1LessThanOrEqualTo(String value) {
            addCriterion("ADD1 <=", value, "add1");
            return (Criteria) this;
        }

        public Criteria andAdd1Like(String value) {
            addCriterion("ADD1 like", value, "add1");
            return (Criteria) this;
        }

        public Criteria andAdd1NotLike(String value) {
            addCriterion("ADD1 not like", value, "add1");
            return (Criteria) this;
        }

        public Criteria andAdd1In(List<String> values) {
            addCriterion("ADD1 in", values, "add1");
            return (Criteria) this;
        }

        public Criteria andAdd1NotIn(List<String> values) {
            addCriterion("ADD1 not in", values, "add1");
            return (Criteria) this;
        }

        public Criteria andAdd1Between(String value1, String value2) {
            addCriterion("ADD1 between", value1, value2, "add1");
            return (Criteria) this;
        }

        public Criteria andAdd1NotBetween(String value1, String value2) {
            addCriterion("ADD1 not between", value1, value2, "add1");
            return (Criteria) this;
        }

        public Criteria andAdd2IsNull() {
            addCriterion("ADD2 is null");
            return (Criteria) this;
        }

        public Criteria andAdd2IsNotNull() {
            addCriterion("ADD2 is not null");
            return (Criteria) this;
        }

        public Criteria andAdd2EqualTo(String value) {
            addCriterion("ADD2 =", value, "add2");
            return (Criteria) this;
        }

        public Criteria andAdd2NotEqualTo(String value) {
            addCriterion("ADD2 <>", value, "add2");
            return (Criteria) this;
        }

        public Criteria andAdd2GreaterThan(String value) {
            addCriterion("ADD2 >", value, "add2");
            return (Criteria) this;
        }

        public Criteria andAdd2GreaterThanOrEqualTo(String value) {
            addCriterion("ADD2 >=", value, "add2");
            return (Criteria) this;
        }

        public Criteria andAdd2LessThan(String value) {
            addCriterion("ADD2 <", value, "add2");
            return (Criteria) this;
        }

        public Criteria andAdd2LessThanOrEqualTo(String value) {
            addCriterion("ADD2 <=", value, "add2");
            return (Criteria) this;
        }

        public Criteria andAdd2Like(String value) {
            addCriterion("ADD2 like", value, "add2");
            return (Criteria) this;
        }

        public Criteria andAdd2NotLike(String value) {
            addCriterion("ADD2 not like", value, "add2");
            return (Criteria) this;
        }

        public Criteria andAdd2In(List<String> values) {
            addCriterion("ADD2 in", values, "add2");
            return (Criteria) this;
        }

        public Criteria andAdd2NotIn(List<String> values) {
            addCriterion("ADD2 not in", values, "add2");
            return (Criteria) this;
        }

        public Criteria andAdd2Between(String value1, String value2) {
            addCriterion("ADD2 between", value1, value2, "add2");
            return (Criteria) this;
        }

        public Criteria andAdd2NotBetween(String value1, String value2) {
            addCriterion("ADD2 not between", value1, value2, "add2");
            return (Criteria) this;
        }

        public Criteria andAdd3IsNull() {
            addCriterion("ADD3 is null");
            return (Criteria) this;
        }

        public Criteria andAdd3IsNotNull() {
            addCriterion("ADD3 is not null");
            return (Criteria) this;
        }

        public Criteria andAdd3EqualTo(String value) {
            addCriterion("ADD3 =", value, "add3");
            return (Criteria) this;
        }

        public Criteria andAdd3NotEqualTo(String value) {
            addCriterion("ADD3 <>", value, "add3");
            return (Criteria) this;
        }

        public Criteria andAdd3GreaterThan(String value) {
            addCriterion("ADD3 >", value, "add3");
            return (Criteria) this;
        }

        public Criteria andAdd3GreaterThanOrEqualTo(String value) {
            addCriterion("ADD3 >=", value, "add3");
            return (Criteria) this;
        }

        public Criteria andAdd3LessThan(String value) {
            addCriterion("ADD3 <", value, "add3");
            return (Criteria) this;
        }

        public Criteria andAdd3LessThanOrEqualTo(String value) {
            addCriterion("ADD3 <=", value, "add3");
            return (Criteria) this;
        }

        public Criteria andAdd3Like(String value) {
            addCriterion("ADD3 like", value, "add3");
            return (Criteria) this;
        }

        public Criteria andAdd3NotLike(String value) {
            addCriterion("ADD3 not like", value, "add3");
            return (Criteria) this;
        }

        public Criteria andAdd3In(List<String> values) {
            addCriterion("ADD3 in", values, "add3");
            return (Criteria) this;
        }

        public Criteria andAdd3NotIn(List<String> values) {
            addCriterion("ADD3 not in", values, "add3");
            return (Criteria) this;
        }

        public Criteria andAdd3Between(String value1, String value2) {
            addCriterion("ADD3 between", value1, value2, "add3");
            return (Criteria) this;
        }

        public Criteria andAdd3NotBetween(String value1, String value2) {
            addCriterion("ADD3 not between", value1, value2, "add3");
            return (Criteria) this;
        }

        public Criteria andAdd4IsNull() {
            addCriterion("ADD4 is null");
            return (Criteria) this;
        }

        public Criteria andAdd4IsNotNull() {
            addCriterion("ADD4 is not null");
            return (Criteria) this;
        }

        public Criteria andAdd4EqualTo(String value) {
            addCriterion("ADD4 =", value, "add4");
            return (Criteria) this;
        }

        public Criteria andAdd4NotEqualTo(String value) {
            addCriterion("ADD4 <>", value, "add4");
            return (Criteria) this;
        }

        public Criteria andAdd4GreaterThan(String value) {
            addCriterion("ADD4 >", value, "add4");
            return (Criteria) this;
        }

        public Criteria andAdd4GreaterThanOrEqualTo(String value) {
            addCriterion("ADD4 >=", value, "add4");
            return (Criteria) this;
        }

        public Criteria andAdd4LessThan(String value) {
            addCriterion("ADD4 <", value, "add4");
            return (Criteria) this;
        }

        public Criteria andAdd4LessThanOrEqualTo(String value) {
            addCriterion("ADD4 <=", value, "add4");
            return (Criteria) this;
        }

        public Criteria andAdd4Like(String value) {
            addCriterion("ADD4 like", value, "add4");
            return (Criteria) this;
        }

        public Criteria andAdd4NotLike(String value) {
            addCriterion("ADD4 not like", value, "add4");
            return (Criteria) this;
        }

        public Criteria andAdd4In(List<String> values) {
            addCriterion("ADD4 in", values, "add4");
            return (Criteria) this;
        }

        public Criteria andAdd4NotIn(List<String> values) {
            addCriterion("ADD4 not in", values, "add4");
            return (Criteria) this;
        }

        public Criteria andAdd4Between(String value1, String value2) {
            addCriterion("ADD4 between", value1, value2, "add4");
            return (Criteria) this;
        }

        public Criteria andAdd4NotBetween(String value1, String value2) {
            addCriterion("ADD4 not between", value1, value2, "add4");
            return (Criteria) this;
        }

        public Criteria andAdd5IsNull() {
            addCriterion("ADD5 is null");
            return (Criteria) this;
        }

        public Criteria andAdd5IsNotNull() {
            addCriterion("ADD5 is not null");
            return (Criteria) this;
        }

        public Criteria andAdd5EqualTo(String value) {
            addCriterion("ADD5 =", value, "add5");
            return (Criteria) this;
        }

        public Criteria andAdd5NotEqualTo(String value) {
            addCriterion("ADD5 <>", value, "add5");
            return (Criteria) this;
        }

        public Criteria andAdd5GreaterThan(String value) {
            addCriterion("ADD5 >", value, "add5");
            return (Criteria) this;
        }

        public Criteria andAdd5GreaterThanOrEqualTo(String value) {
            addCriterion("ADD5 >=", value, "add5");
            return (Criteria) this;
        }

        public Criteria andAdd5LessThan(String value) {
            addCriterion("ADD5 <", value, "add5");
            return (Criteria) this;
        }

        public Criteria andAdd5LessThanOrEqualTo(String value) {
            addCriterion("ADD5 <=", value, "add5");
            return (Criteria) this;
        }

        public Criteria andAdd5Like(String value) {
            addCriterion("ADD5 like", value, "add5");
            return (Criteria) this;
        }

        public Criteria andAdd5NotLike(String value) {
            addCriterion("ADD5 not like", value, "add5");
            return (Criteria) this;
        }

        public Criteria andAdd5In(List<String> values) {
            addCriterion("ADD5 in", values, "add5");
            return (Criteria) this;
        }

        public Criteria andAdd5NotIn(List<String> values) {
            addCriterion("ADD5 not in", values, "add5");
            return (Criteria) this;
        }

        public Criteria andAdd5Between(String value1, String value2) {
            addCriterion("ADD5 between", value1, value2, "add5");
            return (Criteria) this;
        }

        public Criteria andAdd5NotBetween(String value1, String value2) {
            addCriterion("ADD5 not between", value1, value2, "add5");
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