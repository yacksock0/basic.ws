package kr.onthelive.basic.ws.exception;

public enum ErrorCode {
    AuthenticationFail,
    NotAcceptableId,
    NotAcceptableEmail,
    NotComplete,
    CanNotFoundUser,
    CanNotFoundData,
    CanNotInsert,
    CanNotUpdate,
    CanNotDelete,
    CanNotBeNullSectionUsers,
    NotUser,
    NotAllowedExtension,
    Unknown,
    AlreadyExist,
    InvalidData,
    InvalidParameter,

    CanNotParsing,
    CanNotFoundImage,
    TooManyRows,

    DuplicatedId // 아이디 중복 검사 시 사용


}
