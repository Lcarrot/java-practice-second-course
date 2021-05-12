package ru.itis.Tyshenko.jdbc.criteria.predicate;

import ru.itis.Tyshenko.jdbc.criteria.CriteriaBuilder;
import ru.itis.Tyshenko.jdbc.criteria.JoinType;
import ru.itis.Tyshenko.jdbc.criteria.SearchOperator;
import ru.itis.Tyshenko.jdbc.criteria.SqlExpression;

public class MorePredicateBuilder implements PredicateBuilder {
    @Override
    public SearchOperator getSearchOperator() {
        return SearchOperator.MORE;
    }

    @Override
    public SqlExpression getExpression(CriteriaBuilder criteriaBuilder, JoinType joinType, Object object, String fieldName) {
        criteriaBuilder.getExpression().addCondition(" " + joinType.toString() + " " + fieldName + getSearchOperator() + object);
        return criteriaBuilder.getExpression();
    }
}
