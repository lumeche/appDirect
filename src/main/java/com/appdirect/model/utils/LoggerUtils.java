package com.appdirect.model.utils;

import org.slf4j.Logger;

/**
 * Created by NENE on 2015-02-14.
 */
public class LoggerUtils {

    public static void logDebug(Logger logger, String format, String ... args){
        if (logger.isDebugEnabled()){
            logger.debug(String.format(format,args));
        }
    }

    public static void logInfo(Logger logger, String format, String ... args){
        if (logger.isInfoEnabled()){
            logger.info(String.format(format,args));
        }
    }

    public static void logError(Logger logger, String format, String ... args){
        if (logger.isErrorEnabled()){
            logger.error(String.format(format,args));
        }
    }
}
