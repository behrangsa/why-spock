package org.behrang.why.spock

import spock.lang.Specification

/**
 * @author Behrang Saeedzadeh
 */
class RestServiceTest extends Specification {

    def theHttpClient
    def theRestService

    def setup() {
        theHttpClient = Mock(HttpClient)
        theRestService = new RestService(theHttpClient)
    }

    def "it should mock get"() {
        given:
        def httpClient = Mock(HttpClient)

        and:
        httpClient.get({ it.contains("/book") }) >> null
        httpClient.get({ it.contains("/text-book") }) >> "text-book"

        when:
        def result1 = httpClient.get("/book")
        def result2 = httpClient.get("/text-book")

        then:
        result1 == null
        result2 == "text-book"
    }

    def "it should mock and verify get"() {
        given:
        def httpClient = Mock(HttpClient)

        and:
        httpClient.get({ it.contains("/book") }) >> null
        httpClient.get({ it.contains("/text-book") }) >> "text-book"

        when:
        def result1 = httpClient.get("/book")
        def result2 = httpClient.get("/text-book")

        then:
        result1 == null
        result2 == "text-book"

        and:
        2 * httpClient.get(_)
    }

    def "out of line mocking should work, 1"() {
        given:
        def httpClient = Mock(HttpClient)
        def restService = new RestService(httpClient)

        and:
        httpClient.get({ it.contains("/book") }) >> null
        httpClient.get({ it.contains("/text-book") }) >> "text-book"

        when:
        restService.getBook("/123")

        then:
        thrown(NotFoundException)

        when:
        def result2 = restService.getTextBook("/123")

        then:
        result2 == "text-book"
    }

    def "out of line mocking should work, 2"() {
        given:
        def httpClient = Mock(HttpClient)
        def restService = new RestService(httpClient)

        and:
        httpClient.get({ it.contains("/book") }) >> null
        httpClient.get({ it.contains("/text-book") }) >> "text-book"

        when:
        def result = restService.getBookOrTextBook("/123")

        then:
        result == "text-book"
    }

    def "out of line mocking should work, 3"() {
        given:
        def httpClient = Mock(HttpClient)
        def restService = new RestService(httpClient)

        and:
        httpClient.get(_) >> { args ->
            def url = args[0] as String
            if (url.contains("/book")) {
                return null
            } else if (url.contains("/text-book")) {
                return "text-book"
            } else {
                throw new IllegalStateException("Mock not configured properly")
            }
        }

        when:
        def result = restService.getBookOrTextBook("/123")

        then:
        result == "text-book"
    }

    def "out of line mocking should work, 4"() {
        given:
        theHttpClient.get({ it.contains("/book") }) >> null
        theHttpClient.get({ it.contains("/text-book") }) >> "text-book"

        when:
        def result = theRestService.getBookOrTextBook("/123")

        then:
        result == "text-book"
    }

}
