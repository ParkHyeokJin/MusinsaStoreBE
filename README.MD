# 무신사 스토어 B/E 서비스

## 서비스 소개

---

무신사 스토어 B/E 서비스는 스토어 관리 서비스로 다음과 같은 기능을 제공 합니다.

1. 카테고리 관리
   - 카테고리 등록
   - 카테고리 수정
   - 카테고리 삭제
   - 카테고리 조회(아이디, 이름, 리스트)
2. 브랜드 관리
   - 브랜드 등록
   - 브랜드 수정
   - 브랜드 삭제
   - 브랜드 조회(아이디, 이름, 리스트)
3. 상품 관리
   - 상품 등록
   - 상품 수정
   - 상품 삭제
   - 상품 조회(아이디, 이름, 리스트)
4. 상품 추천
   - 카테고리별 최저가 상품 추천
   - 모든 카테고리 상품 구매시 최저가 브랜드 추천
   - 카테고리의 최고가 상품과 최저가 상품 추천

## 기술 스택

<img width="634" alt="stackimg" src="https://github.com/ParkHyeokJin/MusinsaStoreBE/assets/19565772/eae05f4f-a015-4111-99c0-977908ad6bb9">

## 개발 환경

* Springboot Framework 3.2.5
* Java 17+
* Gradle 8.7
* H2 db

## 빌드 방법

1. Git 프로젝트 저장소 복제

```text
$ git clone https://github.com/ParkHyeokJin/MusinsaStoreBE.git
```

2. Gradle build

```text
$ ./gradlew build 
```

3. Jar 실행

```text
$ cd ~/build/libs
$ java -jar MusinsaShop-0.0.1-SNAPSHOT.jar --spring.profiles.active=local //local & test options
```

* Profile modes
  * local : Test DML 수행
  * test  : Test DML 제외

4. 서비스 접속

* 서비스 서버 : [http://localhost:8081]()
* API Docs : [http://localhost:8081/docs/api.html]()


## API Docs 제공

<img width="1601" alt="apidocsImg" src="https://github.com/ParkHyeokJin/MusinsaStoreBE/assets/19565772/edc1e960-4193-4008-87a1-237f8970a15d">

