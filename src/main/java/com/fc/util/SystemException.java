package com.fc.util;



public class SystemException extends BaseException {

    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * 无参构造函数
     */
    public SystemException() {
        super();
    }

    /**
     * 带Message的构造函数
     * 
     * @param message
     */
    public SystemException(final String message) {
        super(message);
    }

    /**
     * 带Exception的构造函数
     * 
     * @param cause
     */
    public SystemException(final Throwable cause) {
        super(cause);
    }

    /**
     * 带message和Exception的构造函数
     * 
     * @param message
     * @param cause
     */
    public SystemException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
