package com.outsourcing.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    // 400
    BAD_REQUEST_NOT_NULL_LOGIN(HttpStatus.BAD_REQUEST, "username과 password는 필수입니다."),
    BAD_REQUEST_NOT_NULL_TASK(HttpStatus.BAD_REQUEST, "제목과 담당자는 필수입니다."),
    BAD_REQUEST_NOT_NULL_TEAM(HttpStatus.BAD_REQUEST, "팀 이름은 필수입니다."),
    BAD_REQUEST_NOT_NULL_COMMENT(HttpStatus.BAD_REQUEST, "댓글 내용은 필수입니다."),
    BAD_REQUEST_NOT_NULL_SEARCH_KEYWORD(HttpStatus.BAD_REQUEST, "검색어를 입력해주세요."),
    BAD_REQUEST_WRONG_STATUS(HttpStatus.BAD_REQUEST, "유효하지 않은 상태 값입니다."),
    BAD_REQUEST_WRONG_EMAIL_FORM(HttpStatus.BAD_REQUEST, "올바른 이메일 형식이 아닙니다."),
    BAD_REQUEST_WRONG_PARAM(HttpStatus.BAD_REQUEST, "잘못된 요청 파라미터입니다."),
    BAD_REQUEST_REPLY_TO_REPLY_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "답글에 또 다시 답글을 달 수 없습니다."),
    BAD_REQUEST_PARENT_COMMENT_TASK_MISMATCH(HttpStatus.BAD_REQUEST, "작업이 일치하지 않습니다."),

    // 401
    UNAUTHORIZED_WRONG_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 올바르지 않습니다."),
    UNAUTHORIZED_WRONG_ID_PASSWORD(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않습니다."),
    UNAUTHORIZED_NEED_CERTIFICATION(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),

    // 403
    FORBIDDEN_NO_PERMISSION(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    FORBIDDEN_NO_PERMISSION_UPDATE(HttpStatus.FORBIDDEN, "수정 권한이 없습니다."),
    FORBIDDEN_NO_PERMISSION_DELETE(HttpStatus.FORBIDDEN, "삭제 권한이 없습니다."),
    FORBIDDEN_NO_PERMISSION_REMOVE(HttpStatus.FORBIDDEN, "제거 권한이 없습니다."),
    FORBIDDEN_NO_PERMISSION_UPDATE_COMMENT(HttpStatus.FORBIDDEN, "댓글을 수정할 권한이 없습니다."),
    FORBIDDEN_NO_PERMISSION_REMOVE_COMMENT(HttpStatus.FORBIDDEN, "댓글을 삭제할 권한이 없습니다."),
    FORBIDDEN_NO_PERMISSION_DELETE_TASK(HttpStatus.FORBIDDEN, "작업을 삭제할 권한이 없습니다."),

    // 404
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    NOT_FOUND_TASK(HttpStatus.NOT_FOUND, "작업을 찾을 수 없습니다."),
    NOT_FOUND_ASSIGNEE(HttpStatus.NOT_FOUND, "담당자를 찾을 수 없습니다."),
    NOT_FOUND_TEAM(HttpStatus.NOT_FOUND, "팀을 찾을 수 없습니다."),
    NOT_FOUND_TEAM_MEMBER(HttpStatus.FOUND, "팀 멤버를 찾을 수 없습니다."),
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),

    // 409
    CONFLICT_EXIST_USERNAME(HttpStatus.CONFLICT, "이미 존재하는 사용자명입니다."),
    CONFLICT_EXIST_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    CONFLICT_EXIST_TEAM_NAME(HttpStatus.CONFLICT, "이미 존재하는 팀 이름입니다."),
    CONFLICT_EXIST_MEMBER_IN_TEAM(HttpStatus.CONFLICT, "팀에 멤버가 존재하여 삭제할 수 없습니다."),
    CONFLICT_ALREADY_IN_TEAM(HttpStatus.CONFLICT, "이미 팀에 속한 멤버입니다.")

    ;

    private final HttpStatus status;
    private final String message;
}
