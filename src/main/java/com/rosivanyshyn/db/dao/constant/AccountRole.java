package com.rosivanyshyn.db.dao.constant;

public enum AccountRole {
        USER ("user"), MANAGER("manager"), UNKNOWN("unknown");
        private String role;
        AccountRole(String role) {
                this.role=role;
        }
        public String getRole(){
                return role;
        }
}
