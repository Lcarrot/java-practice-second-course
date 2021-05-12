package ru.itis.Tyshenko.jdbc.criteria;

public enum SearchOperator {
    MORE(">"), LESS("<");

    private final String value;
    SearchOperator(String s) {
        value = s;
    }

    @Override
    public String toString() {
        return value;
    }
}
