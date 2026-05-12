package org.example

import com.microsoft.azure.functions.*

/**
 * Mock for [HttpResponseMessage]. Used in unit tests to verify the response
 * returned by an HTTP-triggered function.
 */
class HttpResponseMessageMock(
    private val httpStatus: HttpStatusType,
    private val headers: Map<String, String>,
    private val body: Any?,
) : HttpResponseMessage {

    override fun getStatus(): HttpStatusType = httpStatus

    override fun getStatusCode(): Int = httpStatus.value()

    override fun getHeader(key: String): String? = headers[key]

    override fun getBody(): Any? = body

    class HttpResponseMessageBuilderMock : HttpResponseMessage.Builder {
        private var body: Any? = null
        private val headers: MutableMap<String, String> = mutableMapOf()
        private var httpStatus: HttpStatusType? = null

        override fun status(httpStatusType: HttpStatusType): HttpResponseMessage.Builder {
            this.httpStatus = httpStatusType
            return this
        }

        override fun header(key: String, value: String): HttpResponseMessage.Builder {
            this.headers[key] = value
            return this
        }

        override fun body(body: Any): HttpResponseMessage.Builder {
            this.body = body
            return this
        }

        override fun build(): HttpResponseMessage {
            val status = checkNotNull(httpStatus) { "status() must be called before build()" }
            return HttpResponseMessageMock(status, headers.toMap(), body)
        }
    }
}
