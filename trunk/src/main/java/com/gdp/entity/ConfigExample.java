package com.gdp.entity;

import java.util.ArrayList;
import java.util.List;

public class ConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ConfigExample() {
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

        public Criteria andKeyesIsNull() {
            addCriterion("keyes is null");
            return (Criteria) this;
        }

        public Criteria andKeyesIsNotNull() {
            addCriterion("keyes is not null");
            return (Criteria) this;
        }

        public Criteria andKeyesEqualTo(String value) {
            addCriterion("keyes =", value, "keyes");
            return (Criteria) this;
        }

        public Criteria andKeyesNotEqualTo(String value) {
            addCriterion("keyes <>", value, "keyes");
            return (Criteria) this;
        }

        public Criteria andKeyesGreaterThan(String value) {
            addCriterion("keyes >", value, "keyes");
            return (Criteria) this;
        }

        public Criteria andKeyesGreaterThanOrEqualTo(String value) {
            addCriterion("keyes >=", value, "keyes");
            return (Criteria) this;
        }

        public Criteria andKeyesLessThan(String value) {
            addCriterion("keyes <", value, "keyes");
            return (Criteria) this;
        }

        public Criteria andKeyesLessThanOrEqualTo(String value) {
            addCriterion("keyes <=", value, "keyes");
            return (Criteria) this;
        }

        public Criteria andKeyesLike(String value) {
            addCriterion("keyes like", value, "keyes");
            return (Criteria) this;
        }

        public Criteria andKeyesNotLike(String value) {
            addCriterion("keyes not like", value, "keyes");
            return (Criteria) this;
        }

        public Criteria andKeyesIn(List<String> values) {
            addCriterion("keyes in", values, "keyes");
            return (Criteria) this;
        }

        public Criteria andKeyesNotIn(List<String> values) {
            addCriterion("keyes not in", values, "keyes");
            return (Criteria) this;
        }

        public Criteria andKeyesBetween(String value1, String value2) {
            addCriterion("keyes between", value1, value2, "keyes");
            return (Criteria) this;
        }

        public Criteria andKeyesNotBetween(String value1, String value2) {
            addCriterion("keyes not between", value1, value2, "keyes");
            return (Criteria) this;
        }

        public Criteria andValueesIsNull() {
            addCriterion("valuees is null");
            return (Criteria) this;
        }

        public Criteria andValueesIsNotNull() {
            addCriterion("valuees is not null");
            return (Criteria) this;
        }

        public Criteria andValueesEqualTo(String value) {
            addCriterion("valuees =", value, "valuees");
            return (Criteria) this;
        }

        public Criteria andValueesNotEqualTo(String value) {
            addCriterion("valuees <>", value, "valuees");
            return (Criteria) this;
        }

        public Criteria andValueesGreaterThan(String value) {
            addCriterion("valuees >", value, "valuees");
            return (Criteria) this;
        }

        public Criteria andValueesGreaterThanOrEqualTo(String value) {
            addCriterion("valuees >=", value, "valuees");
            return (Criteria) this;
        }

        public Criteria andValueesLessThan(String value) {
            addCriterion("valuees <", value, "valuees");
            return (Criteria) this;
        }

        public Criteria andValueesLessThanOrEqualTo(String value) {
            addCriterion("valuees <=", value, "valuees");
            return (Criteria) this;
        }

        public Criteria andValueesLike(String value) {
            addCriterion("valuees like", value, "valuees");
            return (Criteria) this;
        }

        public Criteria andValueesNotLike(String value) {
            addCriterion("valuees not like", value, "valuees");
            return (Criteria) this;
        }

        public Criteria andValueesIn(List<String> values) {
            addCriterion("valuees in", values, "valuees");
            return (Criteria) this;
        }

        public Criteria andValueesNotIn(List<String> values) {
            addCriterion("valuees not in", values, "valuees");
            return (Criteria) this;
        }

        public Criteria andValueesBetween(String value1, String value2) {
            addCriterion("valuees between", value1, value2, "valuees");
            return (Criteria) this;
        }

        public Criteria andValueesNotBetween(String value1, String value2) {
            addCriterion("valuees not between", value1, value2, "valuees");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
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