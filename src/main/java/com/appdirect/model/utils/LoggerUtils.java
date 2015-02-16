package com.appdirect.model.utils;

import org.slf4j.Logger;

/**
 * Created by NENE on 2015-02-14.
 */
public class LoggerUtils {

    public static void logDebug(Logger logger, String format, String... args) {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug(String.format(format, args));
            }
        } catch (Exception e) {

        }

    }

    public static void logInfo(Logger logger, String format, String... args) {
        try {
            if (logger.isInfoEnabled()) {
                logger.info(String.format(format, args));
            }
        } catch (Exception e) {

        }
    }

    public static void logError(Logger logger, String format, String... args) {
        try {
            if (logger.isErrorEnabled()) {
                logger.error(String.format(format, args));
            }
        } catch (Exception e) {

        }
    }

    public static void logError(Logger logger,Throwable e, String format, String... args) {
        try {
            if (logger.isErrorEnabled()) {
                logger.error(String.format(format, args),e);
            }
        } catch (Exception ex) {

        }
    }
}
