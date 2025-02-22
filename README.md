# ittory-server
<div align="center">
<img width="300" alt="sluvMain" src="https://github.com/CELEBIT/sluv-springboot-server/assets/101792740/a3799048-0fe7-4096-ae8c-0b64a8b7b48a">
</div>
<br/><br/>

## Workers
<div align="center">

|김준기|최재민|
|---|---|
| Backend, DevOps <br>   <div align="center">[GitHub](https://github.com/KJBig)</div>| Backend <br>  <div align="center">[GitHub](https://github.com/jamjamyy)</div>|

</div>
<br/><br/>

## Detailed Roles
[김준기](https://github.com/KJBig)
- Backend, Devlops
- DB 설계
- API 설계 및 구축
    - 로그인, 편지 작성, 편지 공유, 착성 대기실 입장
- Prod, Dev 환경 구축
- AWS 인프라 구축

[최재민](https://github.com/jamjamyy)
- Backend
- DB 설계
- API 설계 및 구축
    - 마이페이지, 편지함, 편지 생성 과정

<br/><br/>



## Language
<img alt="Java" src ="https://img.shields.io/badge/Java-007396.svg?&style=for-the-badge&logo=Java&logoColor=white"/>

<br/><br/>

## Skills
<img alt="Git" src ="https://img.shields.io/badge/Git-F05032.svg?&style=for-the-badge&logo=Git&logoColor=white"/><img alt="Jira" src ="https://img.shields.io/badge/Jira-0052CC.svg?&style=for-the-badge&logo=jira&logoColor=white"/>
<img alt="Spring Boot" src ="https://img.shields.io/badge/Spring Boot-6DB33F.svg?&style=for-the-badge&logo=springboot&logoColor=white"/>
<img alt="JPA" src ="https://img.shields.io/badge/jpa-6DB33F.svg?&style=for-the-badge&logo=jpa&logoColor=white"/>
<img alt="queryDsl" src ="https://img.shields.io/badge/querydsl-4479A1.svg?&style=for-the-badge&logo=querydsl&logoColor=white"/>
<img alt="mysql" src ="https://img.shields.io/badge/mysql-4479A1.svg?&style=for-the-badge&logo=mysql&logoColor=white"/>
<img alt="AWS" src ="https://img.shields.io/badge/AWS-232F3E.svg?&style=for-the-badge&logo=amazonaws&logoColor=white"/>
<img alt="Linux" src ="https://img.shields.io/badge/Linux-FCC624.svg?&style=for-the-badge&logo=linux&logoColor=white"/>
<img alt="Docker" src ="https://img.shields.io/badge/Docker-4479A1.svg?&style=for-the-badge&logo=Docker&logoColor=white"/>

<br/><br/>

## Project Structure
### API 모듈과 Socket 모듈이 분리된 멀티모듈 구조
<img width="500" alt="ittory-structure" src="https://github.com/user-attachments/assets/637258d4-e3fe-46b7-857a-b5e327641200">

- API와 Socket 기능을 하나의 프로젝트에서 통합으로 관리할 수 있으며, 각각 다른 Docker 컨테이너에 독립 적으로 배포가 가능한 구조

<br/><br/>

---

# :memo: [목차](#index) <a name = "index"></a>

- [개요](#outline)
- [Solution](#solution)
- [Architecture](#architecture)
- [결과물](#result)


<br>

# :bookmark: 개요 <a name = "outline"></a>

<br />

- 잇토리는 전통적인 편지 쓰기가 부담스럽게 느껴지는 사람들도 쉽게 감정을 표현할 수 있도록 만들기 위해 시작되었습니다.
- 단순한 문자 전달을 넘어, 창의적이고 재미있는 방식으로 소통할 수 있는 새로운 경험을 제공하고자 했습니다.
- 이를 통해 사용자들이 가볍게 시작해도 진심이 담긴 메시지를 주고받을 수 있는 즐거운 소통의 공간을 만들고자 했습니다.
  
<br>

# :bulb: Solution <a name = "solution"></a>

<br />

### 그림을 보면서 적는 내용
- 서비스에서 그림을 제공하므로써, 사용자가 편지 내용을 작성하는 부담을 낮췄습니다.

### 작성시간 제한 및 실시간 작성
<img width="150" alt="write" src="https://github.com/user-attachments/assets/1dd1e8b7-ccbb-41d9-9b2a-c2d9bbe8f390">
<br />

- 1턴의 편지 작성 시간을 제한하여, 다이나믹한 내용이 나오게 유도하였습니다.
- 편지를 실시간으로 작성하게 하여, 작성시간 제한을 더 세밀히 조정하였습니다.

### 작성한 편지는 링크를 통해 전달.
<img width="150" alt="share" src="https://github.com/user-attachments/assets/b1afc878-983b-43a8-b60d-cb42d3a0efb7">
<br />

- 작성한 편지는 링크를 통해 전달할 수 있도록 했습니다.

<br><br/>


<br>

# :building_construction: Architecture <a name = "architecture"></a>

<br />

### Service Architecture
<br />
<img width="700" alt="service-arch" src="https://github.com/user-attachments/assets/9f324c69-e470-4a36-971f-a9e584160b3a">

<br />

### CD Pipe-Line Architecture

<br />
<img width="700" alt="cd-arch" src="https://github.com/user-attachments/assets/9cba79eb-baab-494e-9894-77c2634fae17">

<br>

# :tada: 결과물 <a name = "result"></a>

<br/>

<img width="700" alt="service" src="https://github.com/user-attachments/assets/c91f1552-68e8-400a-934b-32507f4a989c">


<br>
