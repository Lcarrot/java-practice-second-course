package ru.itis.Tyshenko.jdbc.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SearchCriteria {
    //Сравниваемое поле
    String key;
    //Оператор сравнения(больше, меньше и пр.)
    SearchOperator operator;
    //Значение для сравнения
    String value;
    //Тип примыкания дочерних выражений
    private JoinType joinType;
    //Список дочерних выражений
    private List<SearchCriteria> criteria;
}
