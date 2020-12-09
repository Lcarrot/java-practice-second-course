package ru.itis.Tyshenko.repositories;

import java.lang.reflect.Field;
import java.util.*;

public abstract class ReflectionCrudRepository<T> implements CrudRepository<T> {

    protected List<Object> getParameters(T entity,String[] fieldNames, Object ... args) throws NoSuchFieldException, IllegalAccessException {
        Map<String, Object> map = getUserFields(entity, fieldNames);
        List<Object> params = getEntityParam(map);
        params = getPreparedParamFromEntityAndArgs(params, args);
        return params;
    }

    protected List<Object> getEntityParam(Map<String, Object> map) {
        List<Object> objects = new ArrayList<>();
        Set<Map.Entry<String, Object>> entrySet = map.entrySet();
        for (Map.Entry<String, Object> entry: entrySet) {
            Object object;
            if ((object = entry.getValue()) != null) objects.add(object);
        }
        return objects;
    }

    protected List<Object> getPreparedParamFromEntityAndArgs(List<Object> objects, Object ... args) {
        objects.addAll(Arrays.asList(args));
        return objects;
    }

    protected Map<String, Object> getUserFields(T entity, String[] fieldNames) throws NoSuchFieldException, IllegalAccessException {
        Map<String, Object> fieldsMap = new LinkedHashMap<>();
        for (String fieldName : fieldNames) {
            Field field = entity.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            fieldsMap.put(fieldName, field.get(entity));
        }
        return fieldsMap;
    }
}
