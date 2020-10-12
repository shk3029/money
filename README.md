# 카카오페이 뿌리기 API 

## 뿌리기, 받기 , 조회 기능을 수행하는 REST API 

### Spring Rest Docs 
* Rest Docs URL : http://localhost:8080/docs/index.html
- 로컬 IDE 실행시, gradle-bootRun후, 다시 어플리케이션 실행 (bootRun으로 static-docs 하위에 html 문서 생성)
- Jar파일 실행 시, gradle-builde후, Jar 파일 실행
- 또는 아래 링크를 통해 확인 (html을 마크다운으로 변환)
<li><a href="https://github.com/shk3029/money/blob/master/index.md">  Index </a></li>
<li><a href="https://github.com/shk3029/money/blob/master/share.md">  뿌리기 API </a></li>
<li><a href="https://github.com/shk3029/money/blob/master/receive.md">  받기 API </a></li>
<li><a href="https://github.com/shk3029/money/blob/master/search.md">  조회 API </a></li>

### 요구 사항
* 뿌리기 API
- 요청값 : 뿌릴 금액, 뿌릴 인원 
- 요청건에 대한 고유 token을 발급하고, 응답값으로 내려줌 
- 뿌릴 금액을 인원수에 맞게 분배하여 저장 (로직은 자유롭게)
- token (3자리 문자열, 예측 불가능)

* 받기 API
- 요청값 : token
- token에 해당하는 뿌리기 건 중 아직 할당되지 않은 분배건 하나를 API를 호출한 사람에게 할당하고, 그 금액을 응답값으로 내려줌
- 뿌리기 당 한 사용자는 한번만 받을 수 있음
- 자신이 뿌리기한 건은 자신이 받을 수 없음
- 뿌리기가 호출된 대화방과 동일한 대화방에 속한 사용자만이 받을 수 있음
- 뿌린 건은 10분만 유효 / 10분이 지난 요청에 대한 받기는 실패 응답

* 조회 API

### 상세 구현 요건 및 제약사항

## 상세 구현 정보

### 핵심 문제해결 전략


### 핵심 클래스 UML
![uml](https://user-images.githubusercontent.com/36438257/95719652-19fec500-0cab-11eb-8d8c-3874bbcea580.jpg)

### Database 

