package ru.itis.Tyshenko.jdbc.criteria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class Root {
    Class<?> tClass;
}
