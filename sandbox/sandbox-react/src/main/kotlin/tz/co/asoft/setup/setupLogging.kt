package tz.co.asoft.setup

import tz.co.asoft.*

fun setupLogging() {
    Logging.init(ConsoleAppender())
}