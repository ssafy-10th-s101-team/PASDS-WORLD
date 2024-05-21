# pasdsworld

## :book: 목차

- [프로젝트 소개](#walking-프로젝트-소개)
- [팀원 구성](#construction_worker-팀원-구성)
- [개발 기간](#calendar-개발-기간)
- [기술 스택](#hammer_and_wrench-기술-스택)
- [아키텍처](#building_construction-아키텍처)
- [문서](#memo-문서)
- [역할 분담](#pushpin-역할-분담)
- [기능 시연](#movie_camera-기능-시연)
- [구동 방법](#computer-구동-방법)
- [프로젝트 구조](#package-프로젝트-구조)

## :walking: 프로젝트 소개

-

## :construction_worker: 팀원 구성

|                                    김진용                                     |                                    신우섭                                     |                                     유광우                                     |                                    이준범                                     |                                     임덕기                                     |                                     이하은                                     |
|:--------------------------------------------------------------------------:|:--------------------------------------------------------------------------:|:---------------------------------------------------------------------------:|:--------------------------------------------------------------------------:|:---------------------------------------------------------------------------:|:---------------------------------------------------------------------------:|
| <img src="https://avatars.githubusercontent.com/u/88269663?v=4" width=150> | <img src="https://avatars.githubusercontent.com/u/87111673?v=4" width=150> | <img src="https://avatars.githubusercontent.com/u/129749206?v=4" width=150> | <img src="https://avatars.githubusercontent.com/u/77481223?v=4" width=150> | <img src="https://avatars.githubusercontent.com/u/130431922?v=4" width=150> | <img src="https://avatars.githubusercontent.com/u/101447960?v=4" width=150> |
|               [@jinyong3512](https://github.com/jinyong3512)               |                 [@Wooseobee](https://github.com/Wooseobee)                 |                [@godsun7892](https://github.com/godsun7892)                 |                     [@bum19](https://github.com/bum19)                     |                    [@DKIMDK](https://github.com/DKIMDK)                     |                 [@haisley77](https://github.com/haisley77)                  |

## :calendar: 개발 기간

2024.04.08 - 2024.05.20 (6주)

## :hammer_and_wrench: 기술 스택

#### Front-end

<img alt="JavaScript" src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=white"> <img alt="vue.js" src="https://img.shields.io/badge/vue.js-4FC08D?style=for-the-badge&logo=vue.js&logoColor=white"> <img alt="bootstrap" src="https://img.shields.io/badge/bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white">

#### Back-end

<img alt="Java" src ="https://img.shields.io/badge/Java-007396.svg?&style=for-the-badge&logo=Java&logoColor=white"/> <img alt="Spring Boot" src ="https://img.shields.io/badge/Spring Boot-6DB33F.svg?&style=for-the-badge&logo=Spring Boot&logoColor=white"/> <img alt="redis" src ="https://img.shields.io/badge/redis-DC382D.svg?&style=for-the-badge&logo=redis&logoColor=white"/> <img alt="android" src ="https://img.shields.io/badge/android-34A853.svg?&style=for-the-badge&logo=android&logoColor=white"/>

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

## :building_construction: 아키텍처

## :memo: 문서

- [ERD]()
- [API 명세서]()
- [코드 및 커밋 컨벤션]()

## :pushpin: 역할 분담

- 김진용

- 신우섭
  - UI
    - 메인: 팀, 비밀
    - 알림
  - Back
    - 알림: SSE
    - 권한: CRUD
    - 초대: CRUD
    - 조직: CRUD
    - 팀: CRUD
    - 역할: CRUD
    - 비밀: CRUD + Elasticsearch를 활용한 검색 
  - Monitoring
    - ELK + Filebeat 로깅 파이프라인 구축
  - Kms
    - 로깅 시스템 구축
- 유광우

- 이준범

- 임덕기

- 이하은

## :movie_camera: 기능 시연

<details>
<summary>회원가입</summary>
</details>

## :computer: 구동 방법

1. Clone Project

```bash
git clone https://lab.ssafy.com/s10-final/S10P31S101.git
```

2. change path to /front & npm install

```bash
npm i
```

3. front start

```bash
npm run dev
```

4. change path to /back/src/main

```bash
mkdir resources
```

5. change path to /back/src/main/resources

```bash
cd resources
```

6. create application.yml

```bash
server:
  port: 8080

spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://{mysql주소}:3306/{db이름}
    username: root
    password: {비밀번호}
  elasticsearch:
    uris: http://{elasticsearch주소}
    username: elastic
    password: {비밀번호}
  jpa:
    hibernate:
      ddl-auto: update
  mail:
    host: smtp.gmail.com
    port: 587
    accounts:
      - username: pasdsworld@gmail.com
        password: {비밀번호}
      - username: pasdsworld2@gmail.com
        password: {비밀번호}
      - username: wlsdyd4@gmail.com
        password: {비밀번호}
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
      host: pasds-redis # 내부 통신망
      #      host: pasds.world # public instance
      port: 6379
      password: {비밀번호}
      lettuce:
        pool:
          max-active: 10
          max-idle: 5
          min-idle: 1

security:
  pepper: {비밀번호}
  jwt:
    access-token-expiration-ms: 900000 # 15분
    refresh-token-expiration-ms: 86400000 # 1일
    temporary-token-expiration-ms: 900000 # 15분
    email-token-expiration-ms: 900000 # 15분

# logging:
#   level:
#     org.springframework.security: DEBUG

cookie:
  path: "/app/api"
  secure: true
  httpOnly: true
  sameSite: "None"
  # sameSite: "Strict" # 마지막 날 설정

kms-server:
  url: http://{kms주소}/kms/api # private instance 내부 통신망
  
management:
  health:
    mail:
      enabled: false # MailHealthIndicator 비활성화
  endpoints:
    web:
      cors:
        allowed-origins: http://{서버 주소}:9090
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

## :package: 프로젝트 구조

<details>
<summary>Frontend</summary>

```
```

</details>
<details>
<summary>Backend</summary>

```
```

</details>
<details>
<summary>Kms</summary>

```
```

</details>
<details>
<summary>Android</summary>

```
```

</details>