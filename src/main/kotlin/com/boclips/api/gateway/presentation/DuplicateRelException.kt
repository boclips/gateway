package com.boclips.api.gateway.presentation

import java.lang.RuntimeException

class DuplicateRelException(override val message: String) : RuntimeException(message)
