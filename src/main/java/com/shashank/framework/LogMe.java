package com.shashank.framework;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogMe {

    private Logger log;

    public LogMe(String name) {
        log = LogManager.getLogger(name);
    }

    /**
     * @param message This level will log the progress of the application.
     */
    public void info (String message) {
        log.info(message);
    }


    /**
     * @param message This level will show information regarding warnings, that may not stop the execution but may still cause problems.
     */
    public void warn (String message) {
        log.warn(message);
    }


    /**
     * @param message This level will show messages that inform users about error events that may not stop the application.
     */
    public void error (String message) {
        log.error(message);
    }


    /**
     * @param message This will print information critical to the system that may even crash the application
     */
    public void fatal (String message) {
        log.fatal(message);
    }


    /**
     * @param message This level will log debugging information
     */
    public void debug (String message) {
        log.debug(message);
    }


}
