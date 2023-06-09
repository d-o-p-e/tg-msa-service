# 갓생 MSA 리팩토링
갓생 프로젝트에 MSA 와 DDD를 적용하여 리팩토링합니다

갓생: 열심히 살아온 나를 인증하고 경품에 응모하는 서비스
 
### MSA로의 전환

- 마이크로 서비스 아키텍처로 전환하였습니다. 모듈 분리에 따라 도메인 또한 모듈에 적합하게 다시 정의하였습니다.
- Kafka를 도입하여 모듈 간 이벤트 전파와, Faset API 메일링 서버와의 통신을 비동기 처리하였습니다.
- 분산 세션 구현을 위해 Redis를 세션DB로 도입하였습니다.
- 쿠버네티스 기반의 수평확장성 높은 운영환경과, 모니터링, 젠킨스, EFK 스택 등을 적용하여 실제 실무 운영 환경에 가까운 인프라 환경을 구축하였습니다.


### Hexagonal Architecture
![스크린샷 2023-05-27 오전 11 03 00](https://github.com/d-o-p-e/tg-msa-service/assets/76773202/60a4085a-06a6-4a02-904c-991a3bed387e)


### Infrastructure
![image](https://github.com/d-o-p-e/tg-msa-service/assets/76773202/50c337ef-6b45-402e-9fb2-4f267c4e22d3)


### 공부하는데에 많이 참고했습니다 감사합니다.
- 최범균 - 도메인 주도 개발 시작하기
- https://github.com/msa-ez
- https://www.youtube.com/watch?v=0Ssx7jJJADI&ab_channel=%EC%B5%9C%EB%B2%94%EA%B7%A0
- https://www.youtube.com/watch?v=6w7SQ_1aJ0A&pp=ygUNZGRkIOuPhOuplOyduA%3D%3D
- https://www.youtube.com/watch?v=4QHvTeeTsj0&pp=ygUX64-E66mU7J24IOyjvOuPhCDqsJzrsJw%3D&ab_channel=kakaotech
