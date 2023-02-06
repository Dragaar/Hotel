package com.rosivanyshyn.controller.other.apartment;

import com.rosivanyshyn.db.dao.constant.Field;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** Sorting Fields enum.
 * <br> fields by which to sort apartments
 *
 * @author Rostyslav Ivanyshyn.
 */
public enum SortingFields {
    PRICE (Field.APARTMENT_PRICE),
    MAX_GUESTS_NUMBER(Field.APARTMENT_MAX_GUEST_NUMBER),
    CLASS(Field.APARTMENT_CLASS),
    STATUS("status"),
    STATUS_AVAILABILITY(Field.ENTITY_STATE);
    private String field;

    private static final Map<String, SortingFields> ENUM_MAP;

    SortingFields(String field) {
        this.field=field;
    }
    public String getField(){
        return field;
    }

    static {
        Map<String,SortingFields> map = new ConcurrentHashMap<>();
        for (SortingFields instance : SortingFields.values()) {
            map.put(instance.getField().toLowerCase(),instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    /**
     * Get enum value from string
     * @param input role name in string format
     * @return role name in enum format
     */
    public static SortingFields get (String input) {
        return ENUM_MAP.get(input.toLowerCase());
    }
}
