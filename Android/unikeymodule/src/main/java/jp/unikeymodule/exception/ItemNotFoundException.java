package jp.unikeymodule.exception;

public class ItemNotFoundException extends Exception {
    /**
     * declare constructor
     */
    public ItemNotFoundException() {
        super();
    }

    /**
     * declare constructor
     * @param msg
     */
    public ItemNotFoundException(String msg) {
        super(msg);
    }
}
