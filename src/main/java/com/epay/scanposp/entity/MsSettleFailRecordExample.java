package com.epay.scanposp.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MsSettleFailRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitSize;

    public MsSettleFailRecordExample() {
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

        public Criteria andCooperatorIsNull() {
            addCriterion("cooperator is null");
            return (Criteria) this;
        }

        public Criteria andCooperatorIsNotNull() {
            addCriterion("cooperator is not null");
            return (Criteria) this;
        }

        public Criteria andCooperatorEqualTo(String value) {
            addCriterion("cooperator =", value, "cooperator");
            return (Criteria) this;
        }

        public Criteria andCooperatorNotEqualTo(String value) {
            addCriterion("cooperator <>", value, "cooperator");
            return (Criteria) this;
        }

        public Criteria andCooperatorGreaterThan(String value) {
            addCriterion("cooperator >", value, "cooperator");
            return (Criteria) this;
        }

        public Criteria andCooperatorGreaterThanOrEqualTo(String value) {
            addCriterion("cooperator >=", value, "cooperator");
            return (Criteria) this;
        }

        public Criteria andCooperatorLessThan(String value) {
            addCriterion("cooperator <", value, "cooperator");
            return (Criteria) this;
        }

        public Criteria andCooperatorLessThanOrEqualTo(String value) {
            addCriterion("cooperator <=", value, "cooperator");
            return (Criteria) this;
        }

        public Criteria andCooperatorLike(String value) {
            addCriterion("cooperator like", value, "cooperator");
            return (Criteria) this;
        }

        public Criteria andCooperatorNotLike(String value) {
            addCriterion("cooperator not like", value, "cooperator");
            return (Criteria) this;
        }

        public Criteria andCooperatorIn(List<String> values) {
            addCriterion("cooperator in", values, "cooperator");
            return (Criteria) this;
        }

        public Criteria andCooperatorNotIn(List<String> values) {
            addCriterion("cooperator not in", values, "cooperator");
            return (Criteria) this;
        }

        public Criteria andCooperatorBetween(String value1, String value2) {
            addCriterion("cooperator between", value1, value2, "cooperator");
            return (Criteria) this;
        }

        public Criteria andCooperatorNotBetween(String value1, String value2) {
            addCriterion("cooperator not between", value1, value2, "cooperator");
            return (Criteria) this;
        }

        public Criteria andTxlxIsNull() {
            addCriterion("txlx is null");
            return (Criteria) this;
        }

        public Criteria andTxlxIsNotNull() {
            addCriterion("txlx is not null");
            return (Criteria) this;
        }

        public Criteria andTxlxEqualTo(String value) {
            addCriterion("txlx =", value, "txlx");
            return (Criteria) this;
        }

        public Criteria andTxlxNotEqualTo(String value) {
            addCriterion("txlx <>", value, "txlx");
            return (Criteria) this;
        }

        public Criteria andTxlxGreaterThan(String value) {
            addCriterion("txlx >", value, "txlx");
            return (Criteria) this;
        }

        public Criteria andTxlxGreaterThanOrEqualTo(String value) {
            addCriterion("txlx >=", value, "txlx");
            return (Criteria) this;
        }

        public Criteria andTxlxLessThan(String value) {
            addCriterion("txlx <", value, "txlx");
            return (Criteria) this;
        }

        public Criteria andTxlxLessThanOrEqualTo(String value) {
            addCriterion("txlx <=", value, "txlx");
            return (Criteria) this;
        }

        public Criteria andTxlxLike(String value) {
            addCriterion("txlx like", value, "txlx");
            return (Criteria) this;
        }

        public Criteria andTxlxNotLike(String value) {
            addCriterion("txlx not like", value, "txlx");
            return (Criteria) this;
        }

        public Criteria andTxlxIn(List<String> values) {
            addCriterion("txlx in", values, "txlx");
            return (Criteria) this;
        }

        public Criteria andTxlxNotIn(List<String> values) {
            addCriterion("txlx not in", values, "txlx");
            return (Criteria) this;
        }

        public Criteria andTxlxBetween(String value1, String value2) {
            addCriterion("txlx between", value1, value2, "txlx");
            return (Criteria) this;
        }

        public Criteria andTxlxNotBetween(String value1, String value2) {
            addCriterion("txlx not between", value1, value2, "txlx");
            return (Criteria) this;
        }

        public Criteria andSmzfMsgIdIsNull() {
            addCriterion("smzf_msg_id is null");
            return (Criteria) this;
        }

        public Criteria andSmzfMsgIdIsNotNull() {
            addCriterion("smzf_msg_id is not null");
            return (Criteria) this;
        }

        public Criteria andSmzfMsgIdEqualTo(String value) {
            addCriterion("smzf_msg_id =", value, "smzfMsgId");
            return (Criteria) this;
        }

        public Criteria andSmzfMsgIdNotEqualTo(String value) {
            addCriterion("smzf_msg_id <>", value, "smzfMsgId");
            return (Criteria) this;
        }

        public Criteria andSmzfMsgIdGreaterThan(String value) {
            addCriterion("smzf_msg_id >", value, "smzfMsgId");
            return (Criteria) this;
        }

        public Criteria andSmzfMsgIdGreaterThanOrEqualTo(String value) {
            addCriterion("smzf_msg_id >=", value, "smzfMsgId");
            return (Criteria) this;
        }

        public Criteria andSmzfMsgIdLessThan(String value) {
            addCriterion("smzf_msg_id <", value, "smzfMsgId");
            return (Criteria) this;
        }

        public Criteria andSmzfMsgIdLessThanOrEqualTo(String value) {
            addCriterion("smzf_msg_id <=", value, "smzfMsgId");
            return (Criteria) this;
        }

        public Criteria andSmzfMsgIdLike(String value) {
            addCriterion("smzf_msg_id like", value, "smzfMsgId");
            return (Criteria) this;
        }

        public Criteria andSmzfMsgIdNotLike(String value) {
            addCriterion("smzf_msg_id not like", value, "smzfMsgId");
            return (Criteria) this;
        }

        public Criteria andSmzfMsgIdIn(List<String> values) {
            addCriterion("smzf_msg_id in", values, "smzfMsgId");
            return (Criteria) this;
        }

        public Criteria andSmzfMsgIdNotIn(List<String> values) {
            addCriterion("smzf_msg_id not in", values, "smzfMsgId");
            return (Criteria) this;
        }

        public Criteria andSmzfMsgIdBetween(String value1, String value2) {
            addCriterion("smzf_msg_id between", value1, value2, "smzfMsgId");
            return (Criteria) this;
        }

        public Criteria andSmzfMsgIdNotBetween(String value1, String value2) {
            addCriterion("smzf_msg_id not between", value1, value2, "smzfMsgId");
            return (Criteria) this;
        }

        public Criteria andReqMsgIdIsNull() {
            addCriterion("req_msg_id is null");
            return (Criteria) this;
        }

        public Criteria andReqMsgIdIsNotNull() {
            addCriterion("req_msg_id is not null");
            return (Criteria) this;
        }

        public Criteria andReqMsgIdEqualTo(String value) {
            addCriterion("req_msg_id =", value, "reqMsgId");
            return (Criteria) this;
        }

        public Criteria andReqMsgIdNotEqualTo(String value) {
            addCriterion("req_msg_id <>", value, "reqMsgId");
            return (Criteria) this;
        }

        public Criteria andReqMsgIdGreaterThan(String value) {
            addCriterion("req_msg_id >", value, "reqMsgId");
            return (Criteria) this;
        }

        public Criteria andReqMsgIdGreaterThanOrEqualTo(String value) {
            addCriterion("req_msg_id >=", value, "reqMsgId");
            return (Criteria) this;
        }

        public Criteria andReqMsgIdLessThan(String value) {
            addCriterion("req_msg_id <", value, "reqMsgId");
            return (Criteria) this;
        }

        public Criteria andReqMsgIdLessThanOrEqualTo(String value) {
            addCriterion("req_msg_id <=", value, "reqMsgId");
            return (Criteria) this;
        }

        public Criteria andReqMsgIdLike(String value) {
            addCriterion("req_msg_id like", value, "reqMsgId");
            return (Criteria) this;
        }

        public Criteria andReqMsgIdNotLike(String value) {
            addCriterion("req_msg_id not like", value, "reqMsgId");
            return (Criteria) this;
        }

        public Criteria andReqMsgIdIn(List<String> values) {
            addCriterion("req_msg_id in", values, "reqMsgId");
            return (Criteria) this;
        }

        public Criteria andReqMsgIdNotIn(List<String> values) {
            addCriterion("req_msg_id not in", values, "reqMsgId");
            return (Criteria) this;
        }

        public Criteria andReqMsgIdBetween(String value1, String value2) {
            addCriterion("req_msg_id between", value1, value2, "reqMsgId");
            return (Criteria) this;
        }

        public Criteria andReqMsgIdNotBetween(String value1, String value2) {
            addCriterion("req_msg_id not between", value1, value2, "reqMsgId");
            return (Criteria) this;
        }

        public Criteria andQsDateIsNull() {
            addCriterion("qs_date is null");
            return (Criteria) this;
        }

        public Criteria andQsDateIsNotNull() {
            addCriterion("qs_date is not null");
            return (Criteria) this;
        }

        public Criteria andQsDateEqualTo(String value) {
            addCriterion("qs_date =", value, "qsDate");
            return (Criteria) this;
        }

        public Criteria andQsDateNotEqualTo(String value) {
            addCriterion("qs_date <>", value, "qsDate");
            return (Criteria) this;
        }

        public Criteria andQsDateGreaterThan(String value) {
            addCriterion("qs_date >", value, "qsDate");
            return (Criteria) this;
        }

        public Criteria andQsDateGreaterThanOrEqualTo(String value) {
            addCriterion("qs_date >=", value, "qsDate");
            return (Criteria) this;
        }

        public Criteria andQsDateLessThan(String value) {
            addCriterion("qs_date <", value, "qsDate");
            return (Criteria) this;
        }

        public Criteria andQsDateLessThanOrEqualTo(String value) {
            addCriterion("qs_date <=", value, "qsDate");
            return (Criteria) this;
        }

        public Criteria andQsDateLike(String value) {
            addCriterion("qs_date like", value, "qsDate");
            return (Criteria) this;
        }

        public Criteria andQsDateNotLike(String value) {
            addCriterion("qs_date not like", value, "qsDate");
            return (Criteria) this;
        }

        public Criteria andQsDateIn(List<String> values) {
            addCriterion("qs_date in", values, "qsDate");
            return (Criteria) this;
        }

        public Criteria andQsDateNotIn(List<String> values) {
            addCriterion("qs_date not in", values, "qsDate");
            return (Criteria) this;
        }

        public Criteria andQsDateBetween(String value1, String value2) {
            addCriterion("qs_date between", value1, value2, "qsDate");
            return (Criteria) this;
        }

        public Criteria andQsDateNotBetween(String value1, String value2) {
            addCriterion("qs_date not between", value1, value2, "qsDate");
            return (Criteria) this;
        }

        public Criteria andAccNoIsNull() {
            addCriterion("acc_no is null");
            return (Criteria) this;
        }

        public Criteria andAccNoIsNotNull() {
            addCriterion("acc_no is not null");
            return (Criteria) this;
        }

        public Criteria andAccNoEqualTo(String value) {
            addCriterion("acc_no =", value, "accNo");
            return (Criteria) this;
        }

        public Criteria andAccNoNotEqualTo(String value) {
            addCriterion("acc_no <>", value, "accNo");
            return (Criteria) this;
        }

        public Criteria andAccNoGreaterThan(String value) {
            addCriterion("acc_no >", value, "accNo");
            return (Criteria) this;
        }

        public Criteria andAccNoGreaterThanOrEqualTo(String value) {
            addCriterion("acc_no >=", value, "accNo");
            return (Criteria) this;
        }

        public Criteria andAccNoLessThan(String value) {
            addCriterion("acc_no <", value, "accNo");
            return (Criteria) this;
        }

        public Criteria andAccNoLessThanOrEqualTo(String value) {
            addCriterion("acc_no <=", value, "accNo");
            return (Criteria) this;
        }

        public Criteria andAccNoLike(String value) {
            addCriterion("acc_no like", value, "accNo");
            return (Criteria) this;
        }

        public Criteria andAccNoNotLike(String value) {
            addCriterion("acc_no not like", value, "accNo");
            return (Criteria) this;
        }

        public Criteria andAccNoIn(List<String> values) {
            addCriterion("acc_no in", values, "accNo");
            return (Criteria) this;
        }

        public Criteria andAccNoNotIn(List<String> values) {
            addCriterion("acc_no not in", values, "accNo");
            return (Criteria) this;
        }

        public Criteria andAccNoBetween(String value1, String value2) {
            addCriterion("acc_no between", value1, value2, "accNo");
            return (Criteria) this;
        }

        public Criteria andAccNoNotBetween(String value1, String value2) {
            addCriterion("acc_no not between", value1, value2, "accNo");
            return (Criteria) this;
        }

        public Criteria andAccNameIsNull() {
            addCriterion("acc_name is null");
            return (Criteria) this;
        }

        public Criteria andAccNameIsNotNull() {
            addCriterion("acc_name is not null");
            return (Criteria) this;
        }

        public Criteria andAccNameEqualTo(String value) {
            addCriterion("acc_name =", value, "accName");
            return (Criteria) this;
        }

        public Criteria andAccNameNotEqualTo(String value) {
            addCriterion("acc_name <>", value, "accName");
            return (Criteria) this;
        }

        public Criteria andAccNameGreaterThan(String value) {
            addCriterion("acc_name >", value, "accName");
            return (Criteria) this;
        }

        public Criteria andAccNameGreaterThanOrEqualTo(String value) {
            addCriterion("acc_name >=", value, "accName");
            return (Criteria) this;
        }

        public Criteria andAccNameLessThan(String value) {
            addCriterion("acc_name <", value, "accName");
            return (Criteria) this;
        }

        public Criteria andAccNameLessThanOrEqualTo(String value) {
            addCriterion("acc_name <=", value, "accName");
            return (Criteria) this;
        }

        public Criteria andAccNameLike(String value) {
            addCriterion("acc_name like", value, "accName");
            return (Criteria) this;
        }

        public Criteria andAccNameNotLike(String value) {
            addCriterion("acc_name not like", value, "accName");
            return (Criteria) this;
        }

        public Criteria andAccNameIn(List<String> values) {
            addCriterion("acc_name in", values, "accName");
            return (Criteria) this;
        }

        public Criteria andAccNameNotIn(List<String> values) {
            addCriterion("acc_name not in", values, "accName");
            return (Criteria) this;
        }

        public Criteria andAccNameBetween(String value1, String value2) {
            addCriterion("acc_name between", value1, value2, "accName");
            return (Criteria) this;
        }

        public Criteria andAccNameNotBetween(String value1, String value2) {
            addCriterion("acc_name not between", value1, value2, "accName");
            return (Criteria) this;
        }

        public Criteria andBankTypeIsNull() {
            addCriterion("bank_type is null");
            return (Criteria) this;
        }

        public Criteria andBankTypeIsNotNull() {
            addCriterion("bank_type is not null");
            return (Criteria) this;
        }

        public Criteria andBankTypeEqualTo(String value) {
            addCriterion("bank_type =", value, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeNotEqualTo(String value) {
            addCriterion("bank_type <>", value, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeGreaterThan(String value) {
            addCriterion("bank_type >", value, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeGreaterThanOrEqualTo(String value) {
            addCriterion("bank_type >=", value, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeLessThan(String value) {
            addCriterion("bank_type <", value, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeLessThanOrEqualTo(String value) {
            addCriterion("bank_type <=", value, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeLike(String value) {
            addCriterion("bank_type like", value, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeNotLike(String value) {
            addCriterion("bank_type not like", value, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeIn(List<String> values) {
            addCriterion("bank_type in", values, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeNotIn(List<String> values) {
            addCriterion("bank_type not in", values, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeBetween(String value1, String value2) {
            addCriterion("bank_type between", value1, value2, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeNotBetween(String value1, String value2) {
            addCriterion("bank_type not between", value1, value2, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankNameIsNull() {
            addCriterion("bank_name is null");
            return (Criteria) this;
        }

        public Criteria andBankNameIsNotNull() {
            addCriterion("bank_name is not null");
            return (Criteria) this;
        }

        public Criteria andBankNameEqualTo(String value) {
            addCriterion("bank_name =", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotEqualTo(String value) {
            addCriterion("bank_name <>", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameGreaterThan(String value) {
            addCriterion("bank_name >", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameGreaterThanOrEqualTo(String value) {
            addCriterion("bank_name >=", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameLessThan(String value) {
            addCriterion("bank_name <", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameLessThanOrEqualTo(String value) {
            addCriterion("bank_name <=", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameLike(String value) {
            addCriterion("bank_name like", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotLike(String value) {
            addCriterion("bank_name not like", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameIn(List<String> values) {
            addCriterion("bank_name in", values, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotIn(List<String> values) {
            addCriterion("bank_name not in", values, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameBetween(String value1, String value2) {
            addCriterion("bank_name between", value1, value2, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotBetween(String value1, String value2) {
            addCriterion("bank_name not between", value1, value2, "bankName");
            return (Criteria) this;
        }

        public Criteria andDrawAmountIsNull() {
            addCriterion("draw_amount is null");
            return (Criteria) this;
        }

        public Criteria andDrawAmountIsNotNull() {
            addCriterion("draw_amount is not null");
            return (Criteria) this;
        }

        public Criteria andDrawAmountEqualTo(BigDecimal value) {
            addCriterion("draw_amount =", value, "drawAmount");
            return (Criteria) this;
        }

        public Criteria andDrawAmountNotEqualTo(BigDecimal value) {
            addCriterion("draw_amount <>", value, "drawAmount");
            return (Criteria) this;
        }

        public Criteria andDrawAmountGreaterThan(BigDecimal value) {
            addCriterion("draw_amount >", value, "drawAmount");
            return (Criteria) this;
        }

        public Criteria andDrawAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("draw_amount >=", value, "drawAmount");
            return (Criteria) this;
        }

        public Criteria andDrawAmountLessThan(BigDecimal value) {
            addCriterion("draw_amount <", value, "drawAmount");
            return (Criteria) this;
        }

        public Criteria andDrawAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("draw_amount <=", value, "drawAmount");
            return (Criteria) this;
        }

        public Criteria andDrawAmountIn(List<BigDecimal> values) {
            addCriterion("draw_amount in", values, "drawAmount");
            return (Criteria) this;
        }

        public Criteria andDrawAmountNotIn(List<BigDecimal> values) {
            addCriterion("draw_amount not in", values, "drawAmount");
            return (Criteria) this;
        }

        public Criteria andDrawAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("draw_amount between", value1, value2, "drawAmount");
            return (Criteria) this;
        }

        public Criteria andDrawAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("draw_amount not between", value1, value2, "drawAmount");
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