package com.fc.util;


public class BaseException extends RuntimeException {

    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * 无参构造
     */
    public BaseException() {
    }

    /**
     * 带message的构造函数
     * 
     * @param message
     */
    public BaseException(final String message) {
        super(message);
    }

    /**
     * 带Exception的构造函数
     * 
     * @param cause
     */
    public BaseException(final Throwable cause) {
        super(cause);
    }

    /**
     * 带message和Exception的构造函数
     * 
     * @param message
     * @param cause
     */
    public BaseException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
