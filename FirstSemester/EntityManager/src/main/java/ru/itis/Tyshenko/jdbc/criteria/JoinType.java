package ru.itis.Tyshenko.jdbc.criteria;

public enum JoinType {
    AND("AND"),OR("OR");


    private final String value;
    JoinType(String s) {
        value = s;
    }

    @Override
    public String toString() {
        return value;
    }
}
