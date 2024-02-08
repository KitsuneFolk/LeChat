package com.aallam.openai.client

import com.aallam.openai.api.http.Timeout
import com.aallam.openai.api.logging.LogLevel
import com.aallam.openai.api.logging.Logger
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * OpenAI client configuration.
 *
 * @param token OpenAI Token
 * @param logging client logging configuration
 * @param timeout http client timeout
 * @param headers extra http headers
 * @param organization OpenAI organization ID
 * @param host OpenAI host configuration
 * @param proxy HTTP proxy url
 * @param host OpenAI host configuration.
 * @param retry rate limit retry configuration
 * @param engine explicit ktor engine for http requests.
 * @param httpClientConfig additional custom client configuration
 */
class OpenAIConfig(
    val token: String,
    val logging: LoggingConfig = LoggingConfig(),
    val timeout: Timeout = Timeout(socket = 30.seconds),
    val organization: String? = null,
    val headers: Map<String, String> = emptyMap(),
    val host: OpenAIHost = OpenAIHost.OpenAI,
    val proxy: ProxyConfig? = null,
    val retry: RetryStrategy = RetryStrategy(),
    val engine: HttpClientEngine? = null,
    val httpClientConfig: HttpClientConfig<*>.() -> Unit = {}
) {

    @Deprecated(
        message = "Use primary constructor with LoggingConfig instead.",
        replaceWith = ReplaceWith(
            expression = "OpenAIConfig(token, LoggingConfig(logLevel, logger), timeout, organization, headers, host, proxy, retry)",
            imports = ["com.aallam.openai.api.logging.Logger", "com.openai.config.LoggingConfig"],
        )
    )
    constructor(
        token: String,
        logLevel: LogLevel = LogLevel.Headers,
        logger: Logger = Logger.Simple,
        timeout: Timeout = Timeout(socket = 30.seconds),
        organization: String? = null,
        headers: Map<String, String> = emptyMap(),
        host: OpenAIHost = OpenAIHost.OpenAI,
        proxy: ProxyConfig? = null,
        retry: RetryStrategy = RetryStrategy(),
        engine: HttpClientEngine? = null,
        httpClientConfig: HttpClientConfig<*>.() -> Unit = {}
    ) : this(
        token = token,
        logging = LoggingConfig(
            logLevel = logLevel,
            logger = logger,
        ),
        timeout = timeout,
        organization = organization,
        headers = headers,
        host = host,
        proxy = proxy,
        retry = retry,
        engine = engine,
        httpClientConfig = httpClientConfig,
    )
}

/**
 * A class to configure the OpenAI host.
 * It provides a mechanism to customize the base URL and additional query parameters used in OpenAI API requests.
 */
class OpenAIHost(

    /**
     * Base URL configuration.
     * This is the root URL that will be used for all API requests to OpenAI.
     * The URL can include a base path, but in that case, the base path should always end with a `/`.
     * For example, a valid base URL would be "https://api.openai.com/v1/"
     */
    val baseUrl: String,

    /**
     * Additional query parameters to be appended to all API requests to OpenAI.
     * These can be used to provide additional configuration or context for the API requests.
     */
    val queryParams: Map<String, String> = emptyMap()
) {

    companion object {
        /**
         * A pre-configured instance of [OpenAIHost] with the base URL set as `https://api.openai.com/v1/`.
         */
        val OpenAI: OpenAIHost = OpenAIHost(baseUrl = "https://api.openai.com/v1/")
    }
}


/** Proxy configuration. */
sealed interface ProxyConfig {

    /** Creates an HTTP proxy from [url]. */
    class Http(val url: String) : ProxyConfig

    /** Create socks proxy from [host] and [port]. */
    class Socks(val host: String, val port: Int) : ProxyConfig
}


/**
 * Specifies the retry strategy
 *
 * @param maxRetries the maximum amount of retries to perform for a request
 * @param base retry base value
 * @param maxDelay max retry delay
 */
class RetryStrategy(
    val maxRetries: Int = 3,
    val base: Double = 2.0,
    val maxDelay: Duration = 60.seconds,
)

/**
 * Defines the configuration parameters for logging.
 *
 * @property logLevel the level of logging to be used by the HTTP client.
 * @property logger the logger instance to be used by the HTTP client.
 * @property sanitize flag indicating whether to sanitize sensitive information (i.e., authorization header) in the logs
 */
class LoggingConfig(
    val logLevel: LogLevel = LogLevel.Headers,
    val logger: Logger = Logger.Simple,
    val sanitize: Boolean = true,
)
