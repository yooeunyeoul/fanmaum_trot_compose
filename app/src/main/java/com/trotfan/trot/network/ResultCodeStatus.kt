package com.trotfan.trot.network

enum class ResultCodeStatus(val code: Int, val message: String) {
    NumberAlreadyRegistered(code = 900, message = "This number is already registered"),
    Success(code = 1, "data있는 성공"),
    Fail(code = 0, "invalid path parameter"),
    StarRankNoResult(code = 2, " data가 없는 성공"),
    InvalidCode(code = 906, "Invalid code."),
    AlreadyInUse(code = 907, "Name already in use."),
    UnAcceptableName(code = 908, "Unacceptable name"),
    EmptySuccess(code = 3, "data가 있지만 비어있는 성공")


}