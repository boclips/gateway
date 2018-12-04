package com.boclips.api.gateway.config.links

import org.springframework.beans.factory.annotation.Qualifier
import kotlin.annotation.AnnotationTarget.*


@Target(allowedTargets = [
    FIELD,
    FUNCTION,
    TYPE_PARAMETER,
    VALUE_PARAMETER,
    TYPE,
    ANNOTATION_CLASS])
@Qualifier
annotation class CustomerFacing