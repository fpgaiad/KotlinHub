package br.com.kotlinhub.util

object NetworkConstants {
    const val BASE_URL = "https://api.github.com"
    const val URL_PATH_SEARCH_REPOS = "search/repositories"
    const val KOTLIN_LANGUAGE = "language:kotlin"
    const val STARS = "stars"

    const val NETWORK_IO_EXCEPTION_ERROR = -1
    const val GENERIC_ERROR = 0
    const val CLIENT_ERROR = 1
    const val SERVER_ERROR = 2

    // ClientErrors
    private const val BAD_REQUEST_CLIENT_ERROR = 400
    private const val FORBIDDEN_CLIENT_ERROR = 403
    private const val NOT_FOUND_CLIENT_ERROR = 404
    private const val REQUEST_TIMEOUT_CLIENT_ERROR = 408
    private const val GONE_CLIENT_ERROR = 410
    private const val TOO_MANY_REQUESTS_CLIENT_ERROR = 429

    // ServerErrors
    private const val INTERNAL_SERVER_ERROR = 500
    private const val NOT_IMPLEMENTED_SERVER_ERROR = 501
    private const val BAD_GATEWAY_SERVER_ERROR = 502
    private const val SERVICE_UNAVAILABLE_SERVER_ERROR = 503
    private const val GATEWAY_TIMEOUT_SERVER_ERROR = 504

    fun getErrorCode(code: Int?): Int {
        return when (code) {
            BAD_REQUEST_CLIENT_ERROR,
            FORBIDDEN_CLIENT_ERROR,
            NOT_FOUND_CLIENT_ERROR,
            REQUEST_TIMEOUT_CLIENT_ERROR,
            GONE_CLIENT_ERROR,
            TOO_MANY_REQUESTS_CLIENT_ERROR -> {
                CLIENT_ERROR
            }
            INTERNAL_SERVER_ERROR,
            NOT_IMPLEMENTED_SERVER_ERROR,
            BAD_GATEWAY_SERVER_ERROR,
            SERVICE_UNAVAILABLE_SERVER_ERROR,
            GATEWAY_TIMEOUT_SERVER_ERROR -> {
                SERVER_ERROR
            }
            NETWORK_IO_EXCEPTION_ERROR,
            GENERIC_ERROR -> {
                code
            }
            else -> GENERIC_ERROR
        }
    }
}
