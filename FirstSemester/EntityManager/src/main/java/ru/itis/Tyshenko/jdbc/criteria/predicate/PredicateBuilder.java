package ru.itis.Tyshenko.jdbc.criteria.predicate;

import ru.itis.Tyshenko.jdbc.criteria.Expression;
import ru.itis.Tyshenko.jdbc.criteria.JoinType;
import ru.itis.Tyshenko.jdbc.criteria.SearchOperator;

public interface PredicateBuilder {

    SearchOperator getSearchOperator();

    Expression setValueAndKeyToExpression(Expression expression, Object object);
}
