package ru.itis.Tyshenko.jdbc.criteria.predicate;


import ru.itis.Tyshenko.jdbc.criteria.Expression;
import ru.itis.Tyshenko.jdbc.criteria.SearchOperator;

public abstract class AbstractPredicateBuilder implements PredicateBuilder {

    public abstract SearchOperator getSearchOperator();

    @Override
    public Expression setValueAndKeyToExpression(Expression expression, Object object) {
        expression.setValue(object);
        expression.setOperator(getSearchOperator());
        return expression;
    }
}
