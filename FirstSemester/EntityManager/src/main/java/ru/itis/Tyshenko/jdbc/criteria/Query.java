package ru.itis.Tyshenko.jdbc.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class Query {

    private List<Expression> expressions;
    private Root root;
}
