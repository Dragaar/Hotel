package com.rosivanyshyn.db.dao.constant;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Account role enum.
 *
 * @author Rostyslav Ivanyshyn.
 */
public enum AccountRole {
        USER ("user"), MANAGER("manager"), UNKNOWN("unknown");
        private String role;

        private static final Map<String,AccountRole> ENUM_MAP;

        AccountRole(String role) {
                this.role=role;
        }
        public String getRole(){
                return role;
        }

        static {
                Map<String,AccountRole> map = new ConcurrentHashMap<String, AccountRole>();
                for (AccountRole instance : AccountRole.values()) {
                        map.put(instance.getRole().toLowerCase(),instance);
                }
                ENUM_MAP = Collections.unmodifiableMap(map);
        }

        /**
         * Get enum value from string
         * @param input role name in string format
         * @return role name in enum format
         */
        public static AccountRole get (String input) {
                return ENUM_MAP.get(input.toLowerCase());
        }
}
