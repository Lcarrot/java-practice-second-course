package ru.itis.Tyshenko.jdbc.criteria.predicate;


import ru.itis.Tyshenko.jdbc.criteria.CriteriaBuilder;
import ru.itis.Tyshenko.jdbc.criteria.JoinType;
import ru.itis.Tyshenko.jdbc.criteria.SearchOperator;
import ru.itis.Tyshenko.jdbc.criteria.SqlExpression;

public interface PredicateBuilder {

    SearchOperator getSearchOperator();

    SqlExpression getExpression(CriteriaBuilder criteriaBuilder, JoinType joinType, Object object, String field);
}
