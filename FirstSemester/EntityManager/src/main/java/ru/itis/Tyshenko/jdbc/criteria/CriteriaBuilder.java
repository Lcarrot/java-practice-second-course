package ru.itis.Tyshenko.jdbc.criteria;

import ru.itis.Tyshenko.jdbc.EntityManager;
import ru.itis.Tyshenko.jdbc.criteria.predicate.LessPredicateBuilder;
import ru.itis.Tyshenko.jdbc.criteria.predicate.MorePredicateBuilder;
import ru.itis.Tyshenko.jdbc.criteria.predicate.PredicateBuilder;

import java.util.HashMap;
import java.util.Map;

public class CriteriaBuilder<T> {

    private SqlExpression sqlExpression;
    private final Map<SearchOperator, PredicateBuilder> predicateBuilderMap = new HashMap<>();

    public CriteriaBuilder(EntityManager entityManager, T object, String tableName){
        sqlExpression = new SqlExpression(object, tableName, entityManager.getDatabase());
        predicateBuilderMap.put(SearchOperator.MORE, new MorePredicateBuilder());
        predicateBuilderMap.put(SearchOperator.LESS, new LessPredicateBuilder());
    }

    public CriteriaBuilder<T> addOr(SearchOperator searchOperator, Object object, String field) {
        PredicateBuilder predicateBuilder = predicateBuilderMap.get(searchOperator);
        sqlExpression = predicateBuilder.getExpression(this, JoinType.OR, object, field);
        return this;
    }

    public CriteriaBuilder<T> addAnd(SearchOperator searchOperator, Object object, String field) {
        PredicateBuilder predicateBuilder = predicateBuilderMap.get(searchOperator);
        sqlExpression = predicateBuilder.getExpression(this, JoinType.AND, object, field);
        return this;
    }

    public SqlExpression build() {
        return sqlExpression;
    }

    public SqlExpression getExpression() {
        return sqlExpression;
    }
}
