package com.trotfan.trot.network

enum class ResultCodeStatus(val code: Int, val message: String) {
    NumberAlreadyRegistered(code = 900, message = "This number is already registered"),
    Success(code = 1, ""),
    Fail(code = 0, "invalid path parameter"),
    StarRankNoResult(code = 2, "스타 순위가 조회되지 않습니다."),
    InvalidCode(code = 906, "Invalid code."),
    AlreadyInUse(code = 907, "Name already in use."),
    UnAcceptableName(code = 908, "Unacceptable name")


}