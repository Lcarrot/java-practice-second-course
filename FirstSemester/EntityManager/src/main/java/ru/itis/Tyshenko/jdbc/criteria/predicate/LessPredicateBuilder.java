package ru.itis.Tyshenko.jdbc.criteria.predicate;

import ru.itis.Tyshenko.jdbc.criteria.*;

public class LessPredicateBuilder extends AbstractPredicateBuilder {
    @Override
    public SearchOperator getSearchOperator() {
        return SearchOperator.LESS;
    }
}
