package ru.itis.Tyshenko.jdbc.criteria.predicate;

import ru.itis.Tyshenko.jdbc.criteria.*;

public class MorePredicateBuilder extends AbstractPredicateBuilder {
    @Override
    public SearchOperator getSearchOperator() {
        return SearchOperator.MORE;
    }
}
