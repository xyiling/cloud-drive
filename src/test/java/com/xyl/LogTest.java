package com.xyl;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class LogTest {
    public static void main(String[] args) {
        log.info("this is info log");
        log.error("this is error log");
        log.debug("this is debug log");
        log.warn("this is warn log");
        log.trace("this is trace log");
        log.fatal("this is fatal log");
    }
}
