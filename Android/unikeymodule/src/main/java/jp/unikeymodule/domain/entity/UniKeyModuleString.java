package jp.unikeymodule.domain.entity;

import jp.unikeymodule.domain.enums.ErrorCode;

public class UniKeyModuleString {
    // value
    public String value;
    // error code
    public int errorCode;

    /**
     * Constructor
     */
    public UniKeyModuleString() {
        value = null;
        errorCode = ErrorCode.None.getId();
    }
}
