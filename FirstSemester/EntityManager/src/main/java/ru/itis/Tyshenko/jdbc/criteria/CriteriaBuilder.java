package ru.itis.Tyshenko.jdbc.criteria;

import ru.itis.Tyshenko.jdbc.EntityManager;
import ru.itis.Tyshenko.jdbc.criteria.predicate.LessPredicateBuilder;
import ru.itis.Tyshenko.jdbc.criteria.predicate.MorePredicateBuilder;
import ru.itis.Tyshenko.jdbc.criteria.predicate.PredicateBuilder;

import java.util.*;

public class CriteriaBuilder {

    private final List<Expression> expressions = new LinkedList<>();
    private final Root root;
    private final Map<SearchOperator, PredicateBuilder> predicateBuilderMap = new HashMap<>();
    private final CriteriaBuilderColumn criteriaBuilderColumn;
    private EntityManager entityManager;

    private CriteriaBuilder(Root root){
        this.root = root;
        predicateBuilderMap.put(SearchOperator.MORE, new MorePredicateBuilder());
        predicateBuilderMap.put(SearchOperator.LESS, new LessPredicateBuilder());
        criteriaBuilderColumn = new CriteriaBuilderColumn(this);
    }

    public CriteriaBuilder(EntityManager entityManager, Root root) {
        this(root);
        this.entityManager = entityManager;
    }

    public static CriteriaBuilder select(Root root) {
        return new CriteriaBuilder(root);
    }

    public CriteriaBuilderColumn and() {
        criteriaBuilderColumn.setExpression(Expression.builder().joinType(JoinType.AND).build());
        return criteriaBuilderColumn;
    }

    public CriteriaBuilderColumn or() {
        criteriaBuilderColumn.setExpression(Expression.builder().joinType(JoinType.OR).build());
        return criteriaBuilderColumn;
    }

    public Query build() {
        return new Query(expressions, root);
    }

    public Optional<Object> find(String tableName) {
        if (entityManager != null) {
            return Optional.of(entityManager.find(tableName, root.tClass, entityManager.createSqlStringFromCriteria(build(), tableName)));
        }
        throw new IllegalStateException("can't find Entity Manager");
    }

    public class CriteriaBuilderColumn {
        private Expression expression;
        private final CriteriaBuilderValue criteriaBuilderValue;

        private CriteriaBuilderColumn(CriteriaBuilder criteriaBuilder) {
            this.criteriaBuilderValue = new CriteriaBuilderValue(criteriaBuilder);
        }

        public CriteriaBuilderValue column(String field) {
            expression.setKey(field);
            return criteriaBuilderValue;
        }

        private void setExpression(Expression expression) {
            this.expression = expression;
        }

        public class CriteriaBuilderValue {

            private final CriteriaBuilder criteriaBuilder;

            private CriteriaBuilderValue(CriteriaBuilder criteriaBuilder) {
                this.criteriaBuilder = criteriaBuilder;
            }

            public CriteriaBuilder moreThan(Object value) {
                expressions.add(predicateBuilderMap.get(SearchOperator.MORE).setValueAndKeyToExpression(expression, value));
                return criteriaBuilder;
            }

            public CriteriaBuilder lessThan(Object value) {
                expressions.add(predicateBuilderMap.get(SearchOperator.LESS).setValueAndKeyToExpression(expression, value));
                return criteriaBuilder;
            }
        }
    }
}
