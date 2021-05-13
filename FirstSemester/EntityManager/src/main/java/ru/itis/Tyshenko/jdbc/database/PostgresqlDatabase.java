package ru.itis.Tyshenko.jdbc.database;

import ru.itis.Tyshenko.converter.DatabaseType;
import ru.itis.Tyshenko.converter.UnknownFieldTypeException;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

public class PostgresqlDatabase extends ru.itis.Tyshenko.jdbc.database.Database {

    private String SQL_CREATE_TABLE = "create table " + TABLE_NAME_PATTERN + " (" + FIELD_NAME_WITH_TYPE_PATTERN + ")";

    public PostgresqlDatabase(DataSource dataSource) {
        super(dataSource, new PostgreSQL());
    }

    public <T> void createTable(String tableName, Class<T> entityClass) {
        createTable(tableName, entityClass, SQL_CREATE_TABLE);
    }

    private final String SQL_SELECT_ALL = "select " + FIELD_NAME_PATTERN + " from " + TABLE_NAME_PATTERN;

    @Override
    public <T> List<T> findAll(String table, Class<T> entityClass) {
        return findAll(table, entityClass, SQL_SELECT_ALL);
    }

    private final String SQL_INSERT = "insert into " + TABLE_NAME_PATTERN + " (" + FIELD_NAME_PATTERN + ") " + "values" + " (" + FIELD_VALUES + ")";

    public void save(String tableName, Object entity) {
        save(tableName, entity, SQL_INSERT);
    }

    private final String SQL_SELECT_BY_ID = "select " + FIELD_NAME_PATTERN + " from " + TABLE_NAME_PATTERN + " where id = ?";

    public  <T, ID> Optional<T> findById(String tableName, Class<T> resultType, Class<ID> idType, ID idValue) {
        return findById(tableName, resultType, idType, idValue, SQL_SELECT_BY_ID);
    }

    public static class PostgreSQL implements DatabaseType {
        public String addFieldType(Field field) throws UnknownFieldTypeException {
            String type;
            if (Long.class.equals(field.getType())) {
                type = "bigint";
            } else if (Integer.class.equals(field.getType())) {
                type = "int";
            } else if (String.class.equals(field.getType()) || Character.class.equals(field.getType())) {
                type = "varchar";
            } else if (Boolean.class.equals(field.getType())) {
                type = "boolean";
            }
            else {
                throw new UnknownFieldTypeException();
            }
            return type;
        }

        @Override
        public String getSeparatorBetweenValueAndType() {
            return " ";
        }

        @Override
        public String getSeparatorBetweenValues() {
            return ", ";
        }
    }
}
