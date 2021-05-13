package ru.itis.Tyshenko.jdbc.criteria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class Expression {

    String key;

    SearchOperator operator;

    Object value;

    private JoinType joinType;
}
