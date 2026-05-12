package org.example

import com.microsoft.azure.functions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.*
import java.util.Optional
import java.util.logging.Logger

/**
 * Unit test for Function class.
 */
class FunctionTest {

    private inline fun <reified T : Any> mock(): T = mock(T::class.java)

    private fun buildRequest(
        httpMethod: HttpMethod,
        queryName: String? = null,
        bodyName: String? = null,
    ): HttpRequestMessage<Optional<String>> {
        val req = mock<HttpRequestMessage<Optional<String>>>()

        doReturn(if (queryName != null) mapOf("name" to queryName) else emptyMap<String, String>())
            .`when`<HttpRequestMessage<Optional<String>>>(req).queryParameters

        doReturn(Optional.ofNullable(bodyName)).`when`<HttpRequestMessage<*>>(req).body
        doReturn(httpMethod).`when`<HttpRequestMessage<*>>(req).httpMethod

        doAnswer { invocation ->
            val status = invocation.arguments[0] as HttpStatus
            HttpResponseMessageMock.HttpResponseMessageBuilderMock().status(status)
        }.`when`<HttpRequestMessage<*>>(req).createResponseBuilder(any(HttpStatus::class.java))

        return req
    }

    private fun buildContext(): ExecutionContext {
        val context = mock(ExecutionContext::class.java)
        doReturn(Logger.getGlobal()).`when`(context).logger
        return context
    }

    @Test
    fun testHttpTriggerGetWithQueryParam() {
        val req = buildRequest(HttpMethod.GET, queryName = "Azure")
        val ret = Function().run(req, buildContext())
        assertEquals(HttpStatus.OK, ret.status)
        assertEquals("Hello, Azure!", ret.body)
    }

    @Test
    fun testHttpTriggerPostWithBody() {
        val req = buildRequest(HttpMethod.POST, bodyName = "Azure")
        val ret = Function().run(req, buildContext())
        assertEquals(HttpStatus.OK, ret.status)
        assertEquals("Hello, Azure!", ret.body)
    }

    @Test
    fun testHttpTriggerBodyTakesPrecedenceOverQuery() {
        val req = buildRequest(HttpMethod.POST, queryName = "Query", bodyName = "Body")
        val ret = Function().run(req, buildContext())
        assertEquals(HttpStatus.OK, ret.status)
        assertEquals("Hello, Body!", ret.body)
    }

    @Test
    fun testHttpTriggerMissingNameReturnsBadRequest() {
        val req = buildRequest(HttpMethod.GET)
        val ret = Function().run(req, buildContext())
        assertEquals(HttpStatus.BAD_REQUEST, ret.status)
    }

    @Test
    fun testHttpTriggerBlankQueryReturnsBadRequest() {
        val req = buildRequest(HttpMethod.GET, queryName = "   ")
        val ret = Function().run(req, buildContext())
        assertEquals(HttpStatus.BAD_REQUEST, ret.status)
    }

    @Test
    fun testHttpTriggerEmptyBodyFallsBackToQuery() {
        val req = buildRequest(HttpMethod.POST, queryName = "Azure", bodyName = null)
        val ret = Function().run(req, buildContext())
        assertEquals(HttpStatus.OK, ret.status)
        assertEquals("Hello, Azure!", ret.body)
    }

    @Test
    fun testHttpTriggerPostNoBodyOrQueryReturnsBadRequest() {
        val req = buildRequest(HttpMethod.POST)
        val ret = Function().run(req, buildContext())
        assertEquals(HttpStatus.BAD_REQUEST, ret.status)
    }

}
