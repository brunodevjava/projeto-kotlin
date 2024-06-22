package br.com.project.kotlin_teste.exception

import lombok.Getter


@Getter
class RequestFieldException(errorMessage: String?, private val fieldName: String) : RuntimeException(errorMessage)