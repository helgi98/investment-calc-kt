package org.helgi.investment.model

import java.time.Instant

class ApiError(val message: String, val debugMessage: String?, val timestamp: Instant) {
    constructor(message: String) : this(message, null, Instant.now())
    constructor(message: String, debugMessage: String?) : this(message, debugMessage, Instant.now())
}