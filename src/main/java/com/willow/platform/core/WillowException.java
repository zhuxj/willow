/**
 *  * Book软件公司, 版权所有 违者必究
 * Copyright 2010 
 * 2010-3-30
 */
package com.willow.platform.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 朱贤俊
 * @version 1.0
 */
public class WillowException extends RuntimeException {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public WillowException() {
        super();
    }

    public WillowException(String message) {
        super(message);
        logger.error(message);
    }

    public WillowException(String message, Throwable cause) {
        super(message, cause);
        logger.error(message+"\n"+cause.getMessage());
    }

    public WillowException(Throwable cause) {
        super(cause);
        logger.error(cause.getMessage());
    }
}
