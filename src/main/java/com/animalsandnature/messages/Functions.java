package com.animalsandnature.messages;

import java.time.LocalDateTime;
import org.apache.commons.logging.Log;

class Functions {

    static final String WARN="WARN";
    static final String DEBUG="DEBUG";
    static final String ERROR="ERROR";
    static final String FATAL="FATAL";
    static final String TRACE="TRACE";
    static final String INFO="INFO";


    static void print(Log log, String level, Object msg){
        switch(level){
            case WARN:
                log.warn(LocalDateTime.now()+": "+msg);
                break;
            case DEBUG:
                log.debug(LocalDateTime.now()+": "+msg);
                break;
            case ERROR:
                log.error(LocalDateTime.now()+": "+msg);
                break;
            case FATAL:
                log.fatal(LocalDateTime.now()+": "+msg);
                break;
            case TRACE:
                log.trace(LocalDateTime.now()+": "+msg);
                break;
            default:
                log.info(LocalDateTime.now()+": "+msg);
                break;
        }

    }

}
