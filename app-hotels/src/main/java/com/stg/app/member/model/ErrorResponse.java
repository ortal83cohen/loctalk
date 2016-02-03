package com.stg.app.member.model;

import java.util.HashMap;

/**
 * @author ortal
 * @date 2015-08-11
 */
public class ErrorResponse {

    public String message;
    public HashMap<String, Object> details;

    public boolean isInvalidGrant() {
        if (details == null) {
            return false;
        }
        Object error = details.get("error");
        if (error instanceof String) {
            return "invalid_grant".equals(error);
        } else if (error instanceof String[]) {
            String[] errors = (String[]) error;
            for (String item : errors) {
                if ("invalid_grant".equals(item)) {
                    return true;
                }
            }
        }
        return false;
    }
}
