# 카카오페이 뿌리기 API 

## 뿌리기, 받기 , 조회 기능을 수행하는 REST API 
## 요구 사항
#### 뿌리기 API
- 요청값 : 뿌릴 금액, 뿌릴 인원 
- 요청건에 대한 고유 token을 발급하고, 응답값으로 내려줌 
- 뿌릴 금액을 인원수에 맞게 분배하여 저장 (로직은 자유롭게)
- token (3자리 문자열, 예측 불가능)

#### 받기 API
- 요청값 : token
- token에 해당하는 뿌리기 건 중 아직 할당되지 않은 분배건 하나를 API를 호출한 사람에게 할당하고, 그 금액을 응답값으로 내려줌
- 뿌리기 당 한 사용자는 한번만 받을 수 있음
- 자신이 뿌리기한 건은 자신이 받을 수 없음
- 뿌리기가 호출된 대화방과 동일한 대화방에 속한 사용자만이 받을 수 있음
- 뿌린 건은 10분만 유효 / 10분이 지난 요청에 대한 받기는 실패 응답

#### 조회 API
- 요청값 : token
- token에 해당하는 뿌리기 건의 현재 상태를 응답값으로 내려줌
- 현재 상태는 다음 정보를 포함 (뿌린 시각, 뿌린 금액, 받기 완료된 금액, 받기 완료된 정보([받은 금액, 받은 사용자 아이디] 리스트)
- 뿌린 사람은 자신만 조회가 가능하다
- 다른 사람의 뿌리기건이나 유효하지 않은 token에 대해서는 조회 실패 응답이 내려간다
- 뿌린 건에 대한 조회는 7일 동안 할 수 있다

## 상세 구현 정보
### Spring Rest Docs 
* Rest Docs URL : http://localhost:8080/docs/index.html
    1. 로컬 IDE 실행시, gradle-bootRun, 다시 어플리케이션 실행 (bootRun으로 static-docs 하위에 html 문서 생성)
    2. Jar파일 실행 시, gradle-builde, Jar 파일 실행

* 또는 아래 링크를 통해 확인할 수 있습니다 (html을 마크다운으로 변환)
<li><a href="https://github.com/shk3029/money/blob/master/index.md">  Index </a></li>
<li><a href="https://github.com/shk3029/money/blob/master/share.md">  뿌리기 API </a></li>
<li><a href="https://github.com/shk3029/money/blob/master/receive.md">  받기 API </a></li>
<li><a href="https://github.com/shk3029/money/blob/master/search.md">  조회 API </a></li>

### 핵심 문제해결 전략
1. RESTful 설계
   - HTTP methods (POST, GET, PUT)
   - HTTP status code
   - HATEOAS
   - REST DOCS 
2. 확장 가능한 기능은 인터페이스로 정의 (토큰 생성, 분배 방식)
3. 모든 예외는 ShareRestExceptionHandler에서 처리
4. 다량의 트래픽이 들어온다면?
   - 뿌리기, 받기의 트래픽이 높다면 WAS나 DB의 스케일 아웃이 필요하다
   - 하지만 받기나 뿌리기 요청은 돈의 제한이 있지만, 조회는 제한이 없다
   - 조회는 돈이 존재하지 않아도 1초에 수십번 조회할 수 있다
   - 따라서 조회 요청이 다량의 트래픽으로 들어올 확률이 높다
   - 조회 요청의 경우는 Redis를 사용하여 캐싱할 수 있다 (Key : token)
   - Redis는 Cluster로 구성하면 스케일 아웃도 간단히 할 수 있다 
   - 즉, DB 조회를 사용하는 로직에 캐싱전략을 사용하면 된다  

### 핵심 클래스 UML
![uml](https://user-images.githubusercontent.com/36438257/95719652-19fec500-0cab-11eb-8d8c-3874bbcea580.jpg)

### Database 
* 운영 환경 : postgres
* 테스트 환경 : H2 
* Install postgres
    ```c
    docker run —name postgres -p 5432:5432 -e POSTGRES_PASSWORD=1 -d postgres
    ```
* 테이블 생성
    1. 뿌리기
    ``` c

    create table share (
       token varchar(3) not null,
        count int4 not null,
        created_at timestamp,
        money int8 not null,
        room_id varchar(255) not null,
        share_type varchar(255),
        user_id int8 not null,
        primary key (token)
    )
    ```
    2. 받기
    ``` c
    create table receive (
       receive_id int8 generated by default as identity,
        is_received boolean,
        money int8 not null,
        room_id varchar(255),
        sequence int4 not null,
        token varchar(255),
        user_id int8,
        primary key (receive_id)
    )
    ```

* 테이블 관계
    ![table](https://user-images.githubusercontent.com/36438257/95745476-1df10e00-0cd0-11eb-84de-47ebc6cf5162.png)

### 예제
(자세한 요청/응답은 API REST DOCS 참고)
* 시나리오
    1. a방에서 1번 사용자가 10000원을 3명에게 뿌린다
    2. 2번 사용자가 받는다
    3. 1번 사용자가 뿌린 건을 조회한다

* 뿌리기 API 
    * 요청 
    ```
    HEADER : USER_ID : 1, ROOM_ID : a
    {
      "money" : 10000,
      "count" : 3
    }
    ```
    * 응답
    ```
    {
       "token":"LdY",
       "userId":1,
       "roomId":"a",
       "money":10000,
       "count":3,
       "createdAt":"2020-10-12T21:45:58.3104762",
       "shareType":"RANDOM",
       "receiveList":[],
       "_links":{
          "self":{
             "href":"http://localhost:8080/api/share"
          },
          "share":{
             "href":"http://localhost:8080/api/share/LdY"
          },
          "search":{
             "href":"http://localhost:8080/api/share/LdY/LdY"
          },
          "profile":{
             "href":"/docs/share/index.html#resources-share"
          }
       }
    }
    ```
* 받기 API
    * 요청
    ```
    PUT http://localhost:8080/api/share/LdY
    HEADER : USER_ID : 2, ROOM_ID : a
    ```
    * 응답
    ```
    {
         "userId":2,
         "money":2265,
         "_links":{
            "self":{
               "href":"http://localhost:8080/api/share/LdY"
            },
            "profile":{
               "href":"/docs/receive/index.html#resources-receive"
            }
         }
    }
    ```
* 조회 API
    * 요청
    ```
    GET http://localhost:8080/api/share/LdY
    HEADER : USER_ID : 1, ROOM_ID : a
    ```
    * 응답
    ```
    {
         "money":10000,
         "receivedMoney":2265,
         "receivedList":[
            {
               "userId":2,
               "money":2265
            }
         ],
         "createdAt":"2020-10-12T21:45:58.310476",
         "_links":{
            "self":{
               "href":"http://localhost:8080/api/share/LdY"
            },
            "profile":{
               "href":"/docs/search/index.html#resources-search"
            }
         }
     }
    ```
### Test Case
* API Controller Test

![image](https://user-images.githubusercontent.com/36438257/95745781-a2439100-0cd0-11eb-8626-28cd99e44c60.png)

* API Docs Test

![image](https://user-images.githubusercontent.com/36438257/95745878-d1f29900-0cd0-11eb-85eb-2003864ecffd.png)

* API Service Test

![image](https://user-images.githubusercontent.com/36438257/95746044-1f6f0600-0cd1-11eb-9dad-dcfc36fff7ad.png)

* 분배 Test

![image](https://user-images.githubusercontent.com/36438257/95745928-ecc50d80-0cd0-11eb-8c2c-120d09a2d9f4.png)

* 토큰발행 Test

![image](https://user-images.githubusercontent.com/36438257/95745979-036b6480-0cd1-11eb-8f72-f01413e537bc.png)





