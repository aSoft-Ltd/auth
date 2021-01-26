package tz.co.asoft.setup

import tz.co.asoft.DarkGrayTheme
import tz.co.asoft.ReactTheme
import tz.co.asoft.currentTheme

fun setupTheme(value: ReactTheme = DarkGrayTheme()) {
    currentTheme.value = value
}