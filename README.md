# 📉 TaskFlow Outsourcing Project

## 🧾 프로젝트 소개

- 기업용 업무 관리 시스템
- 이미 개발이 완료된 프론트엔드에 맞추어 REST API 기반의 안정적인 백엔드 서버 구축
- 프로젝트 기간: 2025.12.08 ~ 2025.12.15

## 🌏 개발 환경

- **OS**: Windows 11
- **IDE**: IntelliJ IDEA
- **Language**: Java 17
- **Build Tool**: Gradle
- **Version Control**: Git, GitHub
- **Test Tool**: Postman

## 🛠️ 기술 스택

- **Language**: Java
- **Framework**: Spring Boot 3
- **ORM**: Spring Data JPA, Hibernate
- **Database**: MySQL 8
- **Security:** Spring Security
- **Validation:** Bean Validation

## 🧩 프로젝트 구조
```
com.example.outsourcing
             ├── common
                    ├── aop
                           └── LoggingAspect
                    ├── config
                           ├── CorsConfig
                           ├── JpaConfig
                           ├── QueryDslConfig
                           └── SecurityConfig
                    ├── dto
                           ├── ApiResponse
                           ├── PageCondition
                           └── PagedResponse
                    ├── entity
                           ├── task
                                   ├── Task
                                   ├── TaskPriority
                                   └── TaskStaus
                           ├── Activity
                           ├── BaseTimeEntity
                           ├── Comment
                           ├── Team
                           ├── TeamMember
                           └── User
                    ├── enums
                           ├── DataStatus
                           ├── IsDeleted
                           └── UserRole
                    ├── dto
                           ├── ApiResponse
                           ├── PageCondition
                           └── PagedResponse
                    ├── filter
                           ├── CustomUserDetails
                           └── JwtFilter                              
                    └── utils
                           └── JwtUtil
             ├── domain
                    ├── activities
                    ├── auth
                    ├── comment
                    ├── dashboard
                    ├── search
                    ├── task
                    ├── team
                    ├── teamMember                           
                    └── user
             └── OutsourcingApplication
```

## 🌠 주요 기능

### 1. 사용자 (User)

- 회원 가입
- 프로필 정보 조회, 수정
- 사용자 목록 조회
- 회원 탈퇴
  - 탈퇴 시 비밀번호 확인
  - Soft Delete 처리
  - 계정 복구 불가
- 추가 가능한 사용자 조회

### 2. 작업 (Task)

- 작업 목록 조회
- 작업 상세 조회
- 작업 생성, 수정, 삭제
  - Soft Delete 처리
- 작업 상태 변경
- 페이징

### 4. 팀 (Team)

- 팀 생성, 수정, 삭제
- 팀 목록 조회
- 팀 상세 조회
- 팀 멤버 조회
  - 중복 방지
  - 존재 여부 검증
- 팀 멤버 추가, 제거

### 5. 댓글 (Comment)

- 댓글 조회, 생성, 수정, 삭제
  - 답글은 1개까지만 작성 가능
  - 최신순, 오래된순 정렬 가능
  - 댓글 삭제 시 답글도 함께 삭제 처리
  - 페이징

### 6. 대시보드 (DashBoard)

- 대시보드 통계
  - 전체 Task 수, 상태별 Task 수, 완료율, 기한 초과 Task 수
- 내 작업 요약
- 주간 작업 추세

### 7. 활동 로그 (Activities)

- 전체 활동 로그 조회
- 내 활동 로그 조회
- 활동 로그 자동 생성
- 로그 기록 대상
  - Task: 생성, 수정, 삭제, 상태 변경
  - Comment: 생성, 수정, 삭제
- 페이징

### 8. 검색 (Search)

- 통합 검색
  - 제목과 본문 또는 사용자 이름에 키워드가 포함되면 검색됨

### 9. 인증 및 보안(Auth)
- JWT 기반 인증
    - 로그인 시 액세스 토큰 발급
- 인증 필요 API
    - 회원가입, 로그인 제외 전부
- 로그인 
- 비밀번호 검증

## 💫 ERD 설계 및 API 명세서

- ERD 설계
<img width="1384" height="705" alt="image" src="https://github.com/user-attachments/assets/a32c3267-f09e-4b41-b27e-2757f011ef66" />

- 주요 테이블
    - `users`: 사용자
    - `tasks`: 작업
    - `teams`: 팀
    - `team_members`: 팀 멤버
    - `comments`: 댓글
    - `activities`: 활동 로그


- API 상세설명 : [API 명세서 링크](https://teamsparta.notion.site/TaskFlow-API-2c32dc3ef51481139566e0201d71fe44)
