package org.behrang.why.spock

/**
 * @author Behrang Saeedzadeh
 */
class RestService {

    private HttpClient httpClient

    RestService(HttpClient httpClient) {
        this.httpClient = httpClient
    }

    def getBook(isbn) {
        def book = httpClient.get("http://example.com/books?isbn=$isbn")
        if (book == null) {
            throw new NotFoundException(isbn as String)
        }
    }

    def getTextBook(isbn) {
        return httpClient.get("http://example.com/text-books?isbn=$isbn")
    }

    def getBookOrTextBook(isbn) {
        try {
            return getBook(isbn)
        } catch (NotFoundException e) {
            return getTextBook(isbn)
        }
    }

}
