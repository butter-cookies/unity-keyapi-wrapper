package jp.unikeymodule.domain.entity;

import jp.unikeymodule.domain.enums.ErrorCode;

public class UniKeyModuleBoolean {
    // value
    public boolean value;
    // error code
    public int errorCode;

    /**
     * Constructor
     */
    public UniKeyModuleBoolean() {
        value = false;
        errorCode = ErrorCode.None.getId();
    }
}
