# <img src="docs/jpg/logo.png" weight=30 height=30> PASDS-WORLD

<div align=center>
<img src="docs/jpg/PPT표지.JPG" height=600>
</div>

## :book: 목차

- [프로젝트 소개](#walking-프로젝트-소개)
- [주요 기능](#walking-주요-기능)
- [개발 노션](#memo-개발-노션)
- [팀원 구성](#construction_worker-팀원-구성)
- [개발 기간](#calendar-개발-기간)
- [기술 스택](#hammer_and_wrench-기술-스택)
- [아키텍처](#building_construction-아키텍처)
- [ERD](#memo-ERD)
- [역할 분담](#pushpin-역할-분담)
- [기능 시연](#movie_camera-기능-시연)
- [구동 방법](#computer-구동-방법)

## :walking: 프로젝트 소개

- PASDS-WORLD는 공통 비밀번호와 같은 팀별 민감데이터를 공유해주는 서비스입니다.

## :walking: 주요 기능

- <b>TOTP</b> 기반 2차 인증을 통해 안전한 로그인을 제공합니다.
- <b>RBAC</b>를 통한 민감 데이터를 공유합니다.
- <b>KMS</b> 서버를 통해 발급받은 키로 민감 데이터를 안전하게 암호화 합니다.

## :memo: 개발 노션

- [Notion](https://wooseobee.notion.site/a2fa71cf3c5f481da7e553f637de53e0)

## :construction_worker: 팀원 구성

|                                   김진용                                   |                                   신우섭                                   |                                   유광우                                    |                                   이준범                                   |                                   임덕기                                    |                                   이하은                                    |
| :------------------------------------------------------------------------: | :------------------------------------------------------------------------: | :-------------------------------------------------------------------------: | :------------------------------------------------------------------------: | :-------------------------------------------------------------------------: | :-------------------------------------------------------------------------: |
| <img src="https://avatars.githubusercontent.com/u/88269663?v=4" width=150> | <img src="https://avatars.githubusercontent.com/u/87111673?v=4" width=150> | <img src="https://avatars.githubusercontent.com/u/129749206?v=4" width=150> | <img src="https://avatars.githubusercontent.com/u/77481223?v=4" width=150> | <img src="https://avatars.githubusercontent.com/u/130431922?v=4" width=150> | <img src="https://avatars.githubusercontent.com/u/101447960?v=4" width=150> |
|               [@jinyong3512](https://github.com/jinyong3512)               |                 [@Wooseobee](https://github.com/Wooseobee)                 |                [@godsun7892](https://github.com/godsun7892)                 |                     [@bum19](https://github.com/bum19)                     |                    [@DKIMDK](https://github.com/DKIMDK)                     |                 [@haisley77](https://github.com/haisley77)                  |

## :calendar: 개발 기간

2024.04.08 - 2024.05.20 (6주)

## :hammer_and_wrench: 기술 스택

#### Front-end

<img alt="JavaScript" src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=white"> <img alt="vue.js" src="https://img.shields.io/badge/vue.js-4FC08D?style=for-the-badge&logo=vue.js&logoColor=white"> <img alt="tailwind" src="https://img.shields.io/badge/tailwindcss-06B6D4?style=for-the-badge&logo=tailwindcss&logoColor=white"> <img alt="android" src ="https://img.shields.io/badge/android-34A853.svg?&style=for-the-badge&logo=android&logoColor=white"/>

#### Back-end

<img alt="Java" src ="https://img.shields.io/badge/Java-007396.svg?&style=for-the-badge&logo=Java&logoColor=white"/> <img alt="Spring Boot" src ="https://img.shields.io/badge/Spring Boot-6DB33F.svg?&style=for-the-badge&logo=Spring Boot&logoColor=white"/> <img alt="redis" src ="https://img.shields.io/badge/redis-DC382D.svg?&style=for-the-badge&logo=redis&logoColor=white"/> 

#### Monitoring

<img alt="elasticsearch" src ="https://img.shields.io/badge/elasticsearch-005571.svg?&style=for-the-badge&logo=elasticsearch&logoColor=white"/> <img alt="Logstash" src ="https://img.shields.io/badge/Logstash-005571.svg?&style=for-the-badge&logo=Logstash&logoColor=white"/> <img alt="kibana" src ="https://img.shields.io/badge/kibana-005571.svg?&style=for-the-badge&logo=kibana&logoColor=white"/> <img alt="beats" src ="https://img.shields.io/badge/beats-005571.svg?&style=for-the-badge&logo=beats&logoColor=white"/>

<img alt="prometheus" src ="https://img.shields.io/badge/prometheus-E6522C.svg?&style=for-the-badge&logo=prometheus&logoColor=white"/> <img alt="grafana" src ="https://img.shields.io/badge/grafana-F46800.svg?&style=for-the-badge&logo=grafana&logoColor=white"/>

#### Database

<img alt="MySQL" src ="https://img.shields.io/badge/mysql-4479A1.svg?&style=for-the-badge&logo=mysql&logoColor=white"/>

#### 버전 및 이슈관리

<img alt="gitlab" src ="https://img.shields.io/badge/gitlab-FC6D26.svg?&style=for-the-badge&logo=gitlab&logoColor=white"/>

#### 협업 툴

<img alt="discord" src ="https://img.shields.io/badge/discord-5865F2.svg?&style=for-the-badge&logo=discord&logoColor=white"/> <img alt="jira" src ="https://img.shields.io/badge/jira-0052CC.svg?&style=for-the-badge&logo=jira&logoColor=white"/> <img alt="notion" src ="https://img.shields.io/badge/notion-000000.svg?&style=for-the-badge&logo=notion&logoColor=white"/> <img alt="mattermost" src ="https://img.shields.io/badge/mattermost-0058CC.svg?&style=for-the-badge&logo=mattermost&logoColor=white"/>

#### 서비스 배포 환경

<img alt="amazon ec2" src ="https://img.shields.io/badge/amazon ec2-FF9900.svg?&style=for-the-badge&logo=amazonec2&logoColor=white"/> <img alt="docker" src ="https://img.shields.io/badge/docker-2496ED.svg?&style=for-the-badge&logo=docker&logoColor=white"/> <img alt="nginx" src ="https://img.shields.io/badge/nginx-009639.svg?&style=for-the-badge&logo=nginx&logoColor=white"/>

#### CI/CD

<img alt="jenkins" src ="https://img.shields.io/badge/jenkins-D24939.svg?&style=for-the-badge&logo=jenkins&logoColor=white"/>

#### 보안 테스트

<img alt="jenkins" src ="https://img.shields.io/badge/zap-00549e.svg?&style=for-the-badge&logo=zap&logoColor=white"/>

## :building_construction: 아키텍처

<img src="img/pasds-world-architecture.png">

## :memo: ERD

<img src="img/pasds-erd.png">

## :pushpin: 역할 분담

- 김진용
  - Front
    - 회원 도메인
    - 라우터가드, 인터셉터, 쿠키
  - Back
    - 이메일 도메인
      - Async, 이메일 계정 회전
    - SpringSecurity
      - 인증, 인가
      - pepper + BCrypt
      - JwtCustomFilter
      - 보안관련 헤더 설정
    - 취약점 진단
      - OWASP (Zed Attack Proxy)
      - Cookie
  - Android
    - 전체적인 UI/UX
    - QR 코드 인식
    - TOTP기반 인증코드 발급
    - 내부테스트 배포
- 신우섭
  - Front
    - 메인: 팀, 민감데이터
    - 알림
  - Back
    - 알림: SSE를 활용해 조직 초대, 팀 초대 알림 구현
    - 권한: CRUD
    - 초대: CRUD
    - 조직: CRUD
    - 팀: CRUD
    - 역할: CRUD
    - 민감데이터: CRUD + Elasticsearch를 활용한 검색
  - Monitoring
    - ELK + Filebeat 로깅 파이프라인 구축
  - Kms
    - 로깅 시스템 구축
- 유광우

  - Infra
    - VPC 내에 private, private subnet 인프라 환경 구축
    - Route, NACL, 보안 그룹을 활용한 네트워크 환경 구축
    - Jenkins를 사용한 무중단, 자동화 배포 구현
  - Back
    - 사용자 대시보드를 위한 월별 사용량 log 저장 및 조회 기능 구현
  - Monitoring
    - Actuator, Prometheus, Grafana를 사용한 Spring Boot 모니터링 환경 구축

- 이준범

  - FRONT
    - 조직별 팀목록 조회 기능 구현
    - 조직별 조직 구성원 조회 기능 구현
    - 조직 구성원 초대 기능 구현
  - KMS
    - Jwt Secret Key 발급 및 회전 로직 구현
    - Data Key 발급 및 회전 로직 구현
    - Master Key 발급 및 회전 로직 구현

- 임덕기
  - front
    - Vue.js 설정 및 Tailwind CSS 통합, 폰트 설정
    - 공통 컴포넌트 및 페이지 별 컴포넌트 작성
    - 레이아웃 구성
    - 반응형 디자인 및 다크모드 구현
    - 페이지 라우팅
- 이하은
  - Back
    - 로그인 브루트포스 공격 방어 로직 (IP 차단, 계정 LOCK)
    - 2FA 사용자별 고유 패스키 발급 로직
    - TOTP 알고리즘 (HMAC, HOTP 활용) 활용 2FA 로직
    - 이메일 인증 서버 로직
  - Front
    - 조직별 대시보드 차트 구성 및 페이지 기능 구현
    - 회원 기능 (이메일 인증, 검증 기능, 비밀번호 재설정, 앱 재연동)

## :movie_camera: 기능 시연

### 회원

<details>
<summary>회원가입</summary>
<img src="docs/gif/회원_회원가입앞.gif">
<img src="docs/gif/회원_회원가입뒤.gif">
</details>

<details>
<summary>로그인</summary>
<img src="docs/gif/회원_로그인.gif">
</details>

<details>
<summary>비밀번호 재설정</summary>
<img src="docs/gif/회원_비밀번호재설정.gif">
</details>

<details>
<summary>패스키 재발급</summary>
<img src="docs/gif/회원_패스키재발급.gif">
</details>

<details>
<summary>1차 로그인 실패</summary>
<img src="docs/gif/회원_1차로그인실패.gif">
</details>

<details>
<summary>2차 로그인 실패</summary>
<img src="docs/gif/회원_2차로그인실패.gif">
</details>

<details>
<summary>패스키 스캔</summary>
<img src="docs/gif/회원_패스키스캔.gif">
</details>

<details>
<summary>패스코드 발급</summary>
<img src="docs/gif/회원_패스코드발급.gif">
</details>

### 조직

<details>
<summary>조직 생성</summary>
<img src="docs/gif/조직_생성.gif">
</details>

<details>
<summary>조직 초대</summary>
<img src="docs/gif/조직_초대.gif">
</details>

<details>
<summary>조직 초대 수락</summary>
<img src="docs/gif/조직_초대수락.gif">
</details>

<details>
<summary>조직 추방</summary>
<img src="docs/gif/조직_추방.gif">
</details>

<details>
<summary>조직 이름 변경</summary>
<img src="docs/gif/조직_이름변경.gif">
</details>

<details>
<summary>조직 역할 변경</summary>
<img src="docs/gif/조직_역할변경.gif">
</details>

<details>
<summary>조직 대시보드</summary>
<img src="docs/gif/조직_대시보드.gif">
</details>

### 팀 관리

<details>
<summary>팀 생성</summary>
<img src="docs/gif/팀_생성.gif">
</details>

<details>
<summary>팀 이름변경</summary>
<img src="docs/gif/팀_이름변경.gif">
</details>

<details>
<summary>팀 추방</summary>
<img src="docs/gif/팀_추방.gif">
</details>

<details>
<summary>팀 삭제</summary>
<img src="docs/gif/팀_삭제.gif">
</details>

<details>
<summary>팀원목록</summary>
<img src="docs/gif/팀_팀원목록.gif">
</details>

<details>
<summary>팀장변경</summary>
<img src="docs/gif/팀_팀장변경.gif">
</details>

<details>
<summary>역할 배정</summary>
<img src="docs/gif/역할_사람역할배정.gif">
</details>

<details>
<summary>역할 삭제</summary>
<img src="docs/gif/역할_삭제.gif">
</details>

<details>
<summary>역할 수정</summary>
<img src="docs/gif/역할_수정.gif">
</details>

<details>
<summary>역할 추가</summary>
<img src="docs/gif/역할_추가.gif">
</details>

### 민감 데이터

<details>
<summary>로그인 형식 저장</summary>
<img src="docs/gif/민감데이터_로그인저장.gif">
</details>

<details>
<summary>텍스트 형식 저장</summary>
<img src="docs/gif/민감데이터_텍스트저장.gif">
</details>

<details>
<summary>텍스트 조회</summary>
<img src="docs/gif/민감데이터_조회.gif">
</details>

<details>
<summary>텍스트 수정</summary>
<img src="docs/gif/민감데이터_수정.gif">
</details>

<details>
<summary>텍스트 삭제</summary>
<img src="docs/gif/민감데이터_삭제.gif">
</details>

<details>
<summary>검색</summary>
<img src="docs/gif/민감데이터_검색.gif">
</details>

## :computer: 사용 기술
### TOTP
- 2FA(2단계 인증)
  - 리소스 및 데이터에 액세스할 때 두 가지 형태의 인증이 요구되는 ID 및 액세스 관리 보안 방법으로, 다양한 구현 방법이 있으며 TOTP를 활용한 2FA를 구현했습니다.
- OTP vs TOTP
  - OTP : 일반적으로 각 인증 시도마다 고유한 비밀번호를 생성(ex. 이메일 인증코드, SMS 인증코드)
  - TOTP : 사용자와 서비스 제공자가 공유한 비밀 키와 현재 시간을 결합하여 주기적으로 변경되는 OTP
- TOTP 인증 과정
  1. 사용자 디바이스 앱에서 현재 시간과 비밀 키를 결합해 고유 비밀번호(TOTP)를 생성
  2. 생성한 TOTP로 사용자 인증 요청
  3. 인증 요청을 받은 서버는 사용자 디바이스와 사전에 공유한 고유 비밀 키와 현재 시간을 결합해 TOTP 생성
  4. 인증 요청 받은 서버가 생성한 TOTP와 사용자로부터 받은 TOTP 일치 여부 확인
     - 일치 : 인증 성공
     - 불일치 : 인증 실패

## :computer: 구동 방법

### 0. mysql, elk, prometheus 개별적으로 설치 해주셔야 합니다.

### 1. Clone Project

```bash
git clone https://lab.ssafy.com/s10-final/S10P31S101.git
```

### front

1. change path to /front & npm install

```bash
npm i
```

2. <b>front start</b>

```bash
npm run dev
```

### back

1. change path to /back/src/main & make `resources` directory

```bash
mkdir resources
```

2. change path to /back/src/main/resources & make `application.yml` file

```bash
server:
  port: {spring boot application port number}

spring:
  application:
    name: back
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: {back database url}
    username: {back database username}
    password: {back database userpassword}
  elasticsearch:
    uris: {elasticsearch url}
    username: {elasticsearch username}
    password: {elasticserach password}
  jpa:
    hibernate:
      ddl-auto: update
  mail:
    host: smtp.{email domain}
    port: 587
    accounts:
      - username: {sender email address}
        password: {sender email password}
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
            required: true
          connectiontimeout: 5000
          timeout: 5000
          writetimeout: 5000
    auth-code-expiration-millis: 180000 # 3분
  data:
    redis:
      host: {redis address} # 내부 통신망
      port: 6379
      password: {redis password}
      lettuce:
        pool:
          max-active: 10
          max-idle: 5
          min-idle: 1

security:
  pepper: {jwt secret pepper}
  jwt:
    access-token-expiration-ms: 900000 # 15분
    refresh-token-expiration-ms: 86400000 # 1일
    temporary-token-expiration-ms: 900000 # 15분
    email-token-expiration-ms: 900000 # 15분

cookie:
  path: "/app/api"
  secure: true
  httpOnly: true
  sameSite: "Strict"

kms-server:
  url: {kms url} # you should deploy this server in private-subnet

management:
  health:
    mail:
      enabled: false # MailHealthIndicator 비활성화
  endpoints:
    web:
      cors:
        allowed-origins: {prometheus url}
      exposure:
        include: "*"
  metrics:
    export:
      prometheus:
        enabled: true
  endpoint:
    health:
      show-details: ALWAYS
```

3. change path to /back & build

```bash
./gradlew build
```

4. <b>back start</b>

```
java -jar back-0.0.1-SNAPSHOT.jar
```

### KMS

1.  change path to /kms/src/main & make `resources` directory

```bash
mkdir resources
```

2. change path to /kms/src/main/resources & make `application.yml` file

```bash
server:
  port: {kms spring application port number}

spring:
  application:
    name: kms
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: {kms database url}
    username: {kms database username}
    password: {kms database password}
  jpa:
    hibernate:
      ddl-auto: update

logging:
  config: classpath:logback-spring.xml

main-server:
    url: {back server address}/app/api/key-rotate/handle-masterkey-change
```

3. change path to /kms & build

```bash
./gradlew build
```

4. <b>kms start</b>

```
java -jar kms-0.0.1-SNAPSHOT.jar
```


## :package: 프로젝트 구조

<details>
<summary>Front</summary>
  
```

├── node_modules
├── README.md
├── certificate.pem
├── index.html
├── jsconfig.json
├── package-lock.json
├── package.json
├── postcss.config.js
├── privatekey.pem
├── project_structure.txt
├── public
│ └── favicon.ico
├── src
│ ├── App.vue
│ ├── api
│ │ ├── data.js
│ │ ├── invitation.js
│ │ ├── notification.js
│ │ ├── organization.js
│ │ ├── role.js
│ │ └── team.js
│ ├── assets
│ │ ├── fonts
│ │ │ └── SamsungOneKorean-700C.ttf
│ │ ├── fonts.css
│ │ ├── images
│ │ │ ├── QR_example.png
│ │ │ ├── background1-example.jpg
│ │ │ ├── pasdsworld_v1_qr.png
│ │ │ ├── pasdsworld_v2_qr.png
│ │ │ ├── pasdsworld_v3_qr.png
│ │ │ └── secret_key_qr_example.png
│ │ ├── logo.png
│ │ └── main.css
│ ├── components
│ │ ├── common
│ │ │ ├── BaseAlert.vue
│ │ │ ├── BaseButton.vue
│ │ │ ├── BaseFailAlert.vue
│ │ │ ├── BaseModal.vue
│ │ │ ├── BasePagination.vue
│ │ │ ├── BaseSearchBar.vue
│ │ │ ├── BaseSpinner.vue
│ │ │ ├── BaseTimer.vue
│ │ │ ├── HeaderNavigation.vue
│ │ │ ├── MainAuthorizationModal.vue
│ │ │ ├── MainMemberRoleModal.vue
│ │ │ ├── MainPrivateDataCreate.vue
│ │ │ ├── MainPrivateDataDetail.vue
│ │ │ ├── MainSidebar.vue
│ │ │ ├── MainTable.vue
│ │ │ ├── MainTeamButtonGroup.vue
│ │ │ ├── MemberChangePasswordModal.vue
│ │ │ ├── OrganizationAuthorizationModal.vue
│ │ │ ├── OrganizationChangeHeaderModal.vue
│ │ │ ├── OrganizationChangeNameModal.vue
│ │ │ ├── OrganizationCounts.vue
│ │ │ ├── OrganizationCreationModal.vue
│ │ │ ├── OrganizationInvitationModal.vue
│ │ │ ├── OrganizationKeyRotations.vue
│ │ │ ├── OrganizationSidebar.vue
│ │ │ ├── OrganizationViewCounts.vue
│ │ │ ├── TeamChangeLeaderModal.vue
│ │ │ ├── TeamChangeNameModal.vue
│ │ │ ├── TeamCreationModal.vue
│ │ │ ├── TeamInvitationModal.vue
│ │ │ └── TeamRoleCreationModal.vue
│ │ ├── dashboard
│ │ │ ├── CircleChart.vue
│ │ │ └── CircleChart2.vue
│ │ ├── main
│ │ │ ├── MainMainpage.vue
│ │ │ └── MainTeamManagement.vue
│ │ ├── member
│ │ │ ├── MemberForgotPassword.vue
│ │ │ ├── MemberForgotTotpKey.vue
│ │ │ ├── MemberLogin.vue
│ │ │ ├── MemberLogin2.vue
│ │ │ ├── MemberMyPage.vue
│ │ │ ├── MemberSignup.vue
│ │ │ ├── MemberSignup2.vue
│ │ │ ├── MemberSignup3.vue
│ │ │ └── MemberSignup4.vue
│ │ └── organization
│ │ ├── OrganizationDashboard.vue
│ │ ├── OrganizationMember.vue
│ │ ├── OrganizationMemberRoleModal.vue
│ │ ├── OrganizationSetting.vue
│ │ └── OrganizationTeam.vue
│ ├── main.js
│ ├── router
│ │ └── index.js
│ ├── stores
│ │ ├── common.js
│ │ └── user.js
│ ├── utils
│ │ ├── cookie.js
│ │ └── http-commons.js
│ └── views
│ ├── HomeView.vue
│ ├── HomeViewOnePageScrollTest.vue
│ ├── MainView.vue
│ ├── MemberView.vue
│ └── OrganizationView.vue
├── tailwind.config.js
└── vite.config.js
```

</details>
<details>
<summary>Back</summary>
  
```

├── Dockerfile
├── back_structure.txt
├── build.gradle
├── gradle
│ └── wrapper
│ ├── gradle-wrapper.jar
│ └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── settings.gradle
└── src
├── main
│ └── java
│ └── world
│ └── pasds
│ └── back
│ ├── BackApplication.java
│ ├── authority
│ │ ├── controller
│ │ │ └── AuthorityController.java
│ │ ├── entity
│ │ │ ├── Authority.java
│ │ │ ├── AuthorityDto.java
│ │ │ ├── AuthorityName.java
│ │ │ └── response
│ │ │ └── GetAuthoritiesResponseDto.java
│ │ ├── repository
│ │ │ └── AuthorityRepository.java
│ │ └── service
│ │ └── AuthorityService.java
│ ├── common
│ │ ├── BaseEntity.java
│ │ ├── ControllerAdvice.java
│ │ ├── DataKeyRotationTask.java
│ │ ├── config
│ │ │ ├── AppConfig.java
│ │ │ ├── ElasticsearchConfig.java
│ │ │ ├── ElasticsearchRepositoryConfig.java
│ │ │ ├── EmailConfig.java
│ │ │ ├── JpaRepositoryConfig.java
│ │ │ ├── RedisConfig.java
│ │ │ └── SecurityConfig.java
│ │ ├── controller
│ │ │ ├── EmailController.java
│ │ │ ├── KeyRotateController.java
│ │ │ └── RedisController.java
│ │ ├── dto
│ │ │ ├── EmailCodeGeneralVerificationRequestDto.java
│ │ │ ├── EmailSendGeneralRequestDto.java
│ │ │ ├── ErrorResponse.java
│ │ │ ├── KmsDecryptionKeysResponseDto.java
│ │ │ ├── KmsEncryptionKeysResponseDto.java
│ │ │ ├── KmsKeyDto.java
│ │ │ └── KmsReGenerationKeysResponseDto.java
│ │ ├── exception
│ │ │ ├── BusinessException.java
│ │ │ └── ExceptionCode.java
│ │ ├── filter
│ │ │ └── CustomAuthenticationFilter.java
│ │ ├── service
│ │ │ ├── EmailAsyncService.java
│ │ │ ├── EmailService.java
│ │ │ ├── KeyService.java
│ │ │ ├── RedisJwtSecretKeyListener.java
│ │ │ └── RedisService.java
│ │ └── util
│ │ ├── AesUtil.java
│ │ ├── CookieProvider.java
│ │ ├── JwtTokenProvider.java
│ │ └── LoginUserAuditorAware.java
│ ├── dashboard
│ │ ├── controller
│ │ │ └── DashboardController.java
│ │ ├── entity
│ │ │ ├── OrganizationDashboard.java
│ │ │ ├── TeamDashboard.java
│ │ │ └── dto
│ │ │ └── response
│ │ │ ├── MainDashboardResponseDto.java
│ │ │ └── TeamDashboardResponseDto.java
│ │ ├── repository
│ │ │ ├── OrganizationDashboardRepository.java
│ │ │ └── TeamDashboardRepository.java
│ │ └── service
│ │ ├── OrganizationDashboardService.java
│ │ └── TeamDashboardService.java
│ ├── invitaion
│ │ ├── controller
│ │ │ └── InvitationController.java
│ │ ├── entity
│ │ │ ├── Invitation.java
│ │ │ └── dto
│ │ │ ├── request
│ │ │ │ ├── AcceptOrganizationInviteRequestDto.java
│ │ │ │ └── AcceptTeamInviteRequestDto.java
│ │ │ └── response
│ │ │ ├── AcceptResponseDto.java
│ │ │ ├── GetInvitationsResponseDto.java
│ │ │ ├── RejectOrganizationInviteRequestDto.java
│ │ │ ├── RejectResponseDto.java
│ │ │ └── RejectTeamInviteRequestDto.java
│ │ ├── repository
│ │ │ └── InvitationRepository.java
│ │ └── service
│ │ └── InvitationService.java
│ ├── member
│ │ ├── controller
│ │ │ └── MemberController.java
│ │ ├── dto
│ │ │ ├── request
│ │ │ │ ├── ChangeNicknameRequestDto.java
│ │ │ │ ├── ChangePasswordRequestDto.java
│ │ │ │ ├── ResetPasswordRequestDto.java
│ │ │ │ ├── SecondLoginRequestDto.java
│ │ │ │ └── SignupRequestDto.java
│ │ │ └── response
│ │ │ └── FirstLoginResponseDto.java
│ │ ├── entity
│ │ │ ├── CustomUserDetails.java
│ │ │ ├── Member.java
│ │ │ ├── MemberOrganization.java
│ │ │ ├── MemberRole.java
│ │ │ └── MemberTeam.java
│ │ ├── repository
│ │ │ ├── MemberOrganizationRepository.java
│ │ │ ├── MemberRepository.java
│ │ │ ├── MemberRoleRepository.java
│ │ │ └── MemberTeamRepository.java
│ │ └── service
│ │ ├── CustomUserDetailsService.java
│ │ └── MemberService.java
│ ├── notification
│ │ ├── controller
│ │ │ └── NotificationController.java
│ │ ├── entity
│ │ │ ├── Notification.java
│ │ │ ├── NotificationStatus.java
│ │ │ ├── NotificationType.java
│ │ │ └── dto
│ │ │ └── response
│ │ │ └── NotificationResponseDto.java
│ │ ├── repository
│ │ │ └── NotificationRepository.java
│ │ └── service
│ │ ├── NotificationEventPublisher.java
│ │ └── NotificationService.java
│ ├── organization
│ │ ├── controller
│ │ │ └── OrganizationController.java
│ │ ├── entity
│ │ │ ├── Organization.java
│ │ │ ├── OrganizationRole.java
│ │ │ └── dto
│ │ │ ├── request
│ │ │ │ ├── AssignNewHeaderRequestDto.java
│ │ │ │ ├── CreateOrganizationRequestDto.java
│ │ │ │ ├── DeleteOrganizationRequestDto.java
│ │ │ │ ├── InviteMemberToOrganizationRequestDto.java
│ │ │ │ ├── LeaveOrganizationRequestDto.java
│ │ │ │ ├── RemoveMemberFromOrganizationRequestDto.java
│ │ │ │ ├── RenameOrganizationRequestDto.java
│ │ │ │ └── UpdateRoleRequestDto.java
│ │ │ └── response
│ │ │ ├── GetOrganizationMemberDto.java
│ │ │ ├── GetOrganizationMemberResponseDto.java
│ │ │ └── GetOrganizationsResponseDto.java
│ │ ├── repository
│ │ │ └── OrganizationRepository.java
│ │ └── service
│ │ └── OrganizationService.java
│ ├── privateData
│ │ ├── controller
│ │ │ └── PrivateDataController.java
│ │ ├── entity
│ │ │ ├── DataType.java
│ │ │ ├── PrivateData.java
│ │ │ ├── PrivateDataDocument.java
│ │ │ ├── PrivateDataRole.java
│ │ │ └── dto
│ │ │ ├── PrivateDataRoleDto.java
│ │ │ ├── request
│ │ │ │ ├── CreatePrivateDataRequestDto.java
│ │ │ │ ├── DeletePrivateDataRequestDto.java
│ │ │ │ ├── GetPrivateDataRequestDto.java
│ │ │ │ ├── UpdatePrivateDataRequestDto.java
│ │ │ │ └── UpdatePrivateDataRoleRequestDto.java
│ │ │ └── response
│ │ │ ├── GetPrivateDataListResponseDto.java
│ │ │ ├── GetPrivateDataResponseDto.java
│ │ │ └── PrivateDataResponse.java
│ │ ├── repository
│ │ │ ├── elasticsearch
│ │ │ │ └── PrivateDataSearchRepository.java
│ │ │ └── jpa
│ │ │ ├── PrivateDataCustomRepository.java
│ │ │ ├── PrivateDataCustomRepositoryImpl.java
│ │ │ ├── PrivateDataRepository.java
│ │ │ └── PrivateDataRoleRepository.java
│ │ └── service
│ │ ├── PrivateDataSearchService.java
│ │ └── PrivateDataService.java
│ ├── role
│ │ ├── controller
│ │ │ └── RoleController.java
│ │ ├── entity
│ │ │ ├── Role.java
│ │ │ ├── RoleAuthority.java
│ │ │ └── dto
│ │ │ ├── request
│ │ │ │ ├── CreateRoleRequestDto.java
│ │ │ │ ├── DeleteRoleRequestDto.java
│ │ │ │ └── UpdateRoleRequestDto.java
│ │ │ └── response
│ │ │ ├── GetRoleDetailResponseDto.java
│ │ │ └── GetRoleResponseDto.java
│ │ ├── repository
│ │ │ ├── RoleAuthorityCustomRepository.java
│ │ │ ├── RoleAuthorityCustomRepositoryImpl.java
│ │ │ ├── RoleAuthorityRepository.java
│ │ │ └── RoleRepository.java
│ │ └── service
│ │ └── RoleService.java
│ ├── team
│ │ ├── controller
│ │ │ └── TeamController.java
│ │ ├── entity
│ │ │ ├── Team.java
│ │ │ └── dto
│ │ │ ├── request
│ │ │ │ ├── AssignNewTeamHeaderRequestDto.java
│ │ │ │ ├── AssignRoleRequestDto.java
│ │ │ │ ├── CreateTeamRequestDto.java
│ │ │ │ ├── DeleteTeamRequestDto.java
│ │ │ │ ├── InviteMemberToTeamRequestDto.java
│ │ │ │ ├── LeaveTeamRequestDto.java
│ │ │ │ ├── RemoveMemberFromTeamRequestDto.java
│ │ │ │ ├── RenameTeamRequestDto.java
│ │ │ │ └── RotateTeamDataKeyRequestDto.java
│ │ │ └── response
│ │ │ ├── GetAdminTeamsResponseDto.java
│ │ │ ├── GetTeamLeaderResponseDto.java
│ │ │ ├── GetTeamMemberDto.java
│ │ │ ├── GetTeamMemberResponseDto.java
│ │ │ └── GetTeamsResponseDto.java
│ │ ├── repository
│ │ │ └── TeamRepository.java
│ │ └── service
│ │ └── TeamService.java
│ └── totp
│ ├── controller
│ │ └── TotpController.java
│ ├── dto
│ │ ├── EmailCodeKeyVerificationRequestDto.java
│ │ └── TotpCodeVerificationRequestDto.java
│ ├── repository
│ │ └── TotpRepository.java
│ └── service
│ └── TotpService.java
└── test
└── java
└── world
└── pasds
└── back
└── BackApplicationTests.java

```

</details>
<details>
<summary>KMS</summary>
  
```

├── build.gradle
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── kms_structure.txt
├── settings.gradle
└── src
    ├── main
    │   ├── java
    │   │   └── world
    │   │       └── pasds
    │   │           └── kms
    │   │               ├── KmsApplication.java
    │   │               ├── common
    │   │               │   ├── BaseEntity.java
    │   │               │   ├── ControllerAdvice.java
    │   │               │   ├── dto
    │   │               │   │   └── ErrorResponse.java
    │   │               │   ├── exception
    │   │               │   │   ├── BusinessException.java
    │   │               │   │   └── ExceptionCode.java
    │   │               │   └── logging
    │   │               │       ├── GlobalExceptionHandler.java
    │   │               │       ├── LogInfo.java
    │   │               │       └── LoggingAspect.java
    │   │               ├── config
    │   │               │   └── RestTemplateConfig.java
    │   │               ├── datakey
    │   │               │   ├── controller
    │   │               │   │   └── DataKeyController.java
    │   │               │   ├── dto
    │   │               │   │   ├── DecryptionKeysResponseDto.java
    │   │               │   │   ├── EncryptedDataKeyDto.java
    │   │               │   │   ├── EncryptionKeysResponseDto.java
    │   │               │   │   └── RegenerateKeysResponseDto.java
    │   │               │   ├── model
    │   │               │   │   └── MasterKeyData.java
    │   │               │   └── service
    │   │               │       └── DataKeyService.java
    │   │               ├── jwtsecretkey
    │   │               │   ├── controller
    │   │               │   │   └── JwtSecretKeyController.java
    │   │               │   └── service
    │   │               │       └── JwtSecretKeyService.java
    │   │               ├── masterkey
    │   │               │   ├── entity
    │   │               │   │   └── MasterKey.java
    │   │               │   ├── repository
    │   │               │   │   └── MasterKeyRepository.java
    │   │               │   └── service
    │   │               │       └── MasterKeyService.java
    │   │               └── util
    │   │                   ├── AesUtil.java
    │   │                   └── HmacUtil.java
    │   └── resources
    │       └── logback-spring.xml
    └── test
        └── java
            └── world
                └── pasds
                    └── kms
                        ├── KmsApplicationTests.java
                        └── MasterKeyServiceTest.java

```

</details>
<details>
<summary>Android</summary>

```

├── and_structure.txt
├── app
│   ├── build.gradle.kts
│   ├── proguard-rules.pro
│   └── src
│       └── main
│           ├── AndroidManifest.xml
│           ├── java
│           │   └── com
│           │       └── world
│           │           └── pasds
│           │               ├── GenerateTotpActivity.kt
│           │               ├── MainActivity.kt
│           │               └── ui
│           │                   └── theme
│           │                       ├── Color.kt
│           │                       ├── Theme.kt
│           │                       └── Type.kt
│           └── res
│               ├── drawable
│               │   ├── ic_launcher.xml
│               │   ├── ic_launcher_background.xml
│               │   ├── ic_launcher_foreground.xml
│               │   ├── qr_code_scan.png
│               │   └── two_factor_authentication.png
│               ├── mipmap-hdpi
│               │   ├── ic_launcher.png
│               │   ├── ic_launcher_background.png
│               │   ├── ic_launcher_foreground.png
│               │   ├── ic_launcher_monochrome.png
│               │   └── ic_launcher_round.png
│               ├── mipmap-mdpi
│               │   ├── ic_launcher.png
│               │   ├── ic_launcher_background.png
│               │   ├── ic_launcher_foreground.png
│               │   ├── ic_launcher_monochrome.png
│               │   └── ic_launcher_round.png
│               ├── mipmap-xhdpi
│               │   ├── ic_launcher.png
│               │   ├── ic_launcher_background.png
│               │   ├── ic_launcher_foreground.png
│               │   ├── ic_launcher_monochrome.png
│               │   └── ic_launcher_round.png
│               ├── mipmap-xxhdpi
│               │   ├── ic_launcher.png
│               │   ├── ic_launcher_background.png
│               │   ├── ic_launcher_foreground.png
│               │   ├── ic_launcher_monochrome.png
│               │   └── ic_launcher_round.png
│               ├── mipmap-xxxhdpi
│               │   ├── ic_launcher.png
│               │   ├── ic_launcher_background.png
│               │   ├── ic_launcher_foreground.png
│               │   ├── ic_launcher_monochrome.png
│               │   └── ic_launcher_round.png
│               ├── values
│               │   ├── colors.xml
│               │   ├── strings.xml
│               │   └── themes.xml
│               └── xml
│                   ├── backup_rules.xml
│                   └── data_extraction_rules.xml
├── build.gradle.kts
├── gradle
│   ├── libs.versions.toml
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradle.properties
├── gradlew
├── gradlew.bat
└── settings.gradle.kts
```

</details>
