package ru.itis.Tyshenko.jdbc.criteria.predicate;

import ru.itis.Tyshenko.jdbc.criteria.CriteriaBuilder;
import ru.itis.Tyshenko.jdbc.criteria.JoinType;
import ru.itis.Tyshenko.jdbc.criteria.SearchOperator;
import ru.itis.Tyshenko.jdbc.criteria.SqlExpression;

public class LessPredicateBuilder implements PredicateBuilder {
    @Override
    public SearchOperator getSearchOperator() {
        return SearchOperator.LESS;
    }

    @Override
    public SqlExpression getExpression(CriteriaBuilder criteriaBuilder, JoinType joinType, Object object, String field) {
        criteriaBuilder.getExpression().addCondition(" " + joinType.toString() + " " + field + getSearchOperator() + object);
        return criteriaBuilder.getExpression();
    }
}
