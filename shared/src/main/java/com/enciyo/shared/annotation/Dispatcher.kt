package com.enciyo.shared.annotation

import com.enciyo.shared.GitDispatchers
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val gitDispatcher: GitDispatchers)