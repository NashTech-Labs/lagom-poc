package com.knoldus.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RcclLoggerFactory implements RcclLoggerService {

    private Logger logger;

    public RcclLoggerFactory(String className) {
        logger = LogManager.getLogger(className);
    }

    public void debug(String message) {
        logger.debug(message);
    }

    public void error(String message, Throwable throwable) {
        if (null == throwable) {
            logger.error(message);
        } else {
            logger.error(message, throwable);
        }
    }

    public void error(String message) {
        logger.error(message);
    }

    public void error(Throwable throwable) {
        logger.error("", throwable);
    }

    public void info(String message) {
        logger.info(message);

    }

    public void warn(String message) {
        logger.warn(message);
    }

    public static RcclLoggerService getLogService(final String className) {
        return new RcclLoggerFactory(className);
    }

}
