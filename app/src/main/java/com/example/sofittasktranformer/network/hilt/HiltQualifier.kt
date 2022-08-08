package com.example.sofittasktranformer.network.hilt

/**
 * @author Umer Bilal
 * Created 7/23/2022 at 11:24 AM
 */

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation  class HttpLoggerInterceptorBasic

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation  class HttpLoggerInterceptorHeader


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation  class HttpLoggerInterceptorBody
