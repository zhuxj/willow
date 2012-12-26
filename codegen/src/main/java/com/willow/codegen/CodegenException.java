/**
 *  * Book软件公司, 版权所有 违者必究
 * Copyright 2010 
 * 2010-3-30
 */
package com.willow.codegen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 朱贤俊
 * @version 1.0
 */
public class CodegenException extends RuntimeException {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public CodegenException() {
        super();
    }

    public CodegenException(String message) {
        super(message);
        logger.error(message);
    }

    public CodegenException(String message, Throwable cause) {
        super(message, cause);
        logger.error(message+"\n"+cause.getMessage());
    }

    public CodegenException(Throwable cause) {
        super(cause);
        logger.error(cause.getMessage());
    }
}
