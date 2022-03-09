# StockPrediction

![image](https://user-images.githubusercontent.com/56579239/157480336-1401fc2a-d04a-4c11-8ea5-3bb27aa78852.png)

## 개요
전공종합설계 프로젝트에서 진행하여 만들었던 주가 예측 서비스입니다.

웹&앱의 플랫폼을 제공하며, 
4인의 구성으로 웹 2인, 앱 2인, 머신러닝 1인, 백엔드 1인으로 구성하여 진행하였습니다.

웹, 앱, 머신러닝 파트를 맡아 구현하였습니다.
 
## 기능
- 크롤링: 네이버 주식과, 공시 페이지에서 뉴스 기사와 각 주식 종목들의 현재가를 크롤링하여 데이터를 사용하였습니다.
- 머신러닝: 크롤링 해 온 주식 데이터의 현재가를 이용하여 LSTM 시계열 기계학습을 사용해 주가 예측을 시도하였습니다.
- 앱 프런트: 앱의 전반적인 기능과 API 통신을 맡아 구현하였으며 해당 레포지토리의 소스코드 항목입니다.
- 모의 투자: 가상 머니를 00:00시마다 지급하여 유저들이 현재가를 기반으로 모의 투자를 할 수 있게 구현하였습니다.
- 랭킹: 유저들의 가상 머니를 통한 투자 실적을 통한 랭킹을 구현하였습니다.
- 주식 정보: 주가 그래프, 뉴스 데이터 출력 등을 통하여 주식 정보를 표현하였습니다.

## 기술 스택
- HTML, CSS, JS, Android Studio
- tensorflow, Selenium, Pandas
- Django, DjangoRestFrameWork, PostgreSQL
- AWS EC2, AWS RDS, nginx, Gunicorn, Git

![image](https://user-images.githubusercontent.com/56579239/157480189-e736bb72-bab8-40f4-bde9-225edaa0948a.png)
