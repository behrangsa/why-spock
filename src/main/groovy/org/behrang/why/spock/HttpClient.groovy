package org.behrang.why.spock

/**
 * @author Behrang Saeedzadeh
 */
class HttpClient {

    def get(String url) {
        return "HTTP Response for: ${url}"
    }

}
