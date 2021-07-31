
<div id="title" align="center"><img width="400px" src="https://user-images.githubusercontent.com/35298140/127691911-8908a7fa-ce8e-4235-9b96-e90343221b9b.png"></div>

---

## 개요
> TCP소켓통신기반의 서버구축 라이브러리입니다.  
> 푸시서버 구축을 목표로 개발되었으며, 이외 용도로 다양하게 응용할 수 있습니다. 
>
> 
> 특징
> + 매우 빠름
> + 쉽고 편리한 구축 및 사용
> + 객체 전송에 특화

---

## 공통

---
### 구성
> + ServerConfiguration : 서버 설정 (싱글톤)
> + ClientConfiguration : 클라이언트 설정 (싱글톤)
> + ServerManager : 서버 구축 매니저 (싱글톤)
> + ClientManager : 클라이언트 구축 매니저 (선택적 전역 접근점)
> + ServerObjectRecieveService : 서버의 수신데이터 처리 (싱글톤)
> + ClientObjectRecieveService : 클라이언트의 수신 데이터 처리 (싱글톤)
> + SoutLog : 콘솔창에 로그를 출력
    
### 초기 설정
1. ServerConfiguration 클래스, ClientConfiguration 클래스의 싱글톤 객체에 접근하여
필요한 설정을 정의한다.
>+ 서버의 경우 : 바운드를 위한 port를 정한다.
```java
ServerConfiguration.instance.port = 7777; // int
```

>+ 클라이언트의 경우 : 서버 접속을 위한 ip, port, id(고유식별자)를 정한다.
```java
ClientConfiguration.instance.ip = "127.0.0.1"; // String
ClientConfiguration.instance.port = 7777; // int
ClientConfiguration.instance.id = "475b8eeb-a7ee-4cb4-ab2f-788ae161c7ba"; // String
```

### 수신 데이터 처리 설정
> 1. Order 인터페이스의 구상클래스를 작성하여, 해야할 일을 정의한다.
>>  Packet에 tag, order message를 주입해서 생성
>>  + order : 명령, 클라이언트에서 식별 후 명령 실행에 사용 (type : String)
>>  + tag : 태그 (type : String)
>>  + data : 전송할 메시지 (type : Object)
>
>
>> 패킷 종류
>>  + NullPacket : 내부에 공백이 들어간 패킷
>>  + StringPacket : String타입 데이터가 들어있는 패킷, 생성할 때 데이터 주입 가능
>>  + ObjectPacket : Object타입 데이터가 들어있는 패킷, 생성할 때 데이터 주입 가능
>
> 2. 매개변수로 받은 object는 형 변환하여 활용한다. 해당 예시에는 Packet으로 형변환했다.
```java
public class StringPacketNotice implements Order {
      @Override
      public void excute(WrappedSocket wrappedSocket, Object object) {
            Packet packet = (Packet) object;
            new SoutLog(packet.getTag(), "{" + wrappedSocket.getSocketId() + "} => Server : " + packet.getData()).log();
      }
}
```

>2. 서버의 경우, ServerPacketReceiveService 클래스의 '명령맵'에 작성한 구상클래스를 추가한다.
> 
> 
> + addOrder(명령어, 구상클래스 객체);
> + 해당 명령어를 가진 패킷이 들어오면 해당 구상클래스에 작성한 작업이 실행된다.
>
> 
> + 같은 이름으로 중복되는 명령어를 넣어서는 안된다.
```java
ServerPacketReceiveService.instance.addOrder("NOTICE", new StringPacketNotice());
```

>3.이제 서버에 NOTICE 라는 명령을 가진 패킷이 들어오면, 앞서 정의한대로 작업이 실행된다.

>4.클라이언트의 경우, ClientPacketRecieveService 클래스에 앞선 과정을 진행하면 된다.

### 로그
> SoutLog 클래스를 활용하여 로그를 출력할 수 있다.   
> '[시간] [태그] 로그' 형식으로 콘솔에 출력한다.
```java
new SoutLog("tag", "log").log();
```

### 서버 와 클라이언트
> 본 라이브러리는 '서버구축' 과 '클라이언트구축' 이 모두 가능합니다.
> 
> 해당 라이브러리 사용으로 서버구축을 원한다면 서버 구축을, 클라이언트 구축을 원한다면
> 클라이언트 구축을 하면 됩니다.

### 테스트 드라이브
>pusha.testdrive 패키지에 간단한 테스트 프로그램이 있다.
> 
> + ServerTestDrive : 서버용 프로그램
> + ClientTestDrive : 단일 클라이언트 프로그램
> + MultiConnectClientTestDrive : 다수 클라이언트 프로그램
>> + MultiConnectClientTestDrive 에서 버퍼이상으로 소켓 생성 시 
>> 서버와 클라이언트 에 오작동 발생 -> 서버를 리부트 해야함.   
>> + 1디바이스당 클라이언트 소켓 1000이하로 생성하는 것을 권장함. 

---

## 서버

---

### 서버 구축 

1.서버 사용을 선언
```java
ServerManager.use(); // 서버매니저 사용을 선언 >>> 서버 매니저 객체 생성

ServerManager.instance; // 해당 변수로 접근
```

2.서버 소켓 저장소를 주입
> 저장소 종류 (총 1개)
> 1. MemorySocketRepository

```java
serverManager.setRepository(new MemorySocketRepository());
```

3.서버 바운드 (bound)
> SockCofiguration의 port를 사용한다.
```java
ServerManager.instance.bound(ServerConfiguration.instance.port);
```

4.서버 리슨 (listen)
> 서버 리슨 스레드 실행
```java
ServerManager.instance.listen();
```

5.서버 처리 시작 
> 서버 처리 스레드 실행 
> + 서버 클라이언트 접속 확인
> + 클라이언트 데이터 수신 
> + 수신 데이터 해석 및 처리
```java
ServerManager.instance.process();
```

---

### 서버 사용

1. 특정 클라이언트에 데이터 전송
>  Packet에 tag, order message를 주입해서 생성
>  + order : 명령, 클라이언트에서 식별 후 명령 실행에 사용 (type : String)
>  + tag : 태그 (type : String)
>  + data : 전송할 메시지 (type : Object)


> 패킷 종류
>  + NullPacket : 내부에 공백이 들어간 패킷
>  + StringPacket : String타입 데이터가 들어있는 패킷, 생성할 때 데이터 주입 가능
>  + ObjectPacket : Object타입 데이터가 들어있는 패킷, 생성할 때 데이터 주입 가능


>Packet의 구상클래스의 객체를 생성해서 주입한다.
```java
ServerManager.intance.sendTarget(id, new StringPacket(tag, order, data));
ServerManager.intance.sendTarget(id, new ObjectPacket(tag, order, data)); 
```

---

## 클라이언트

---

### 클라이언트 구축 (전역 접근점)
1. 클라이언트 사용을 선언 
```java
ClientManager.use();
```

2. 서버에 연결 (connect)
> SockConfiguration의 ip, port, id를 사용하여 연결
```java
clientManager.instance.connect(ClientConfiguration.instance.ip
        , ClientConfiguration.instance.port
        , ClientConfiguration.instance.id);
```

3. 클라이언트 처리 시작
> 클라이언트 처리 스레드 실행
> + 서버 데이터 수신
> + 수신 데이터 해석 및 처리
```java
ClientManager.instance.process();
```
---

### 클라이언트 구축 (클라이언트 객체 생성)
1. 클라이언트 객체를 생성
> 객체가 생성될 때 ClientManager.clientMap에 <id, ClientManager> 형식으로 등록된다.
```java
ClientManager clientManager = new ClientManager();
```

2. 서버에 연결 (connect)
> SockConfiguration의 ip, port, id를 사용하여 연결
```java
clientManager.connect(ClientConfiguration.instance.ip
        , ClientConfiguration.instance.port
        , ClientConfiguration.instance.id);
```

3. 클라이언트 처리 시작
> 클라이언트 처리 스레드 실행
> + 서버 데이터 수신
> + 수신 데이터 해석 및 처리
```java
clientManager.instance.process();
```
---

### 클라이언트 사용 (전역 접근점)
1. 서버에 데이터 전송
>  Packet에 tag, order message를 주입해서 생성
>  + order : 명령, 클라이언트에서 식별 후 명령 실행에 사용 (type : String)
>  + tag : 태그 (type : String)
>  + data : 전송할 메시지 (type : Object)


> 패킷 종류
>  + NullPacket : 내부에 공백이 들어간 패킷
>  + StringPacket : String타입 데이터가 들어있는 패킷, 생성할 때 데이터 주입 가능
>  + ObjectPacket : Object타입 데이터가 들어있는 패킷, 생성할 때 데이터 주입 가능


```java
ClientManager.instance.send(new DataPacket(tag, order, message));
```

---

### 클라이언트 사용 (클라이언트 객체 생성)
1. 서버에 데이터 전송
>  Packet에 tag, order message를 주입해서 생성
>  + order : 명령, 클라이언트에서 식별 후 명령 실행에 사용 (type : String)
>  + tag : 태그 (type : String)
>  + data : 전송할 메시지 (type : Object)


> 패킷 종류
>  + NullPacket : 내부에 공백이 들어간 패킷
>  + StringPacket : String타입 데이터가 들어있는 패킷, 생성할 때 데이터 주입 가능
>  + ObjectPacket : Object타입 데이터가 들어있는 패킷, 생성할 때 데이터 주입 가능

>ClientManager.clientMap에서 id로 클라이언트 객체를 조회하고, send메서드로 전송한다.
```java
if(ClientManager.clientMap.containsKey(id)) {
        ClientManager.clientMap.get(id).send(new StringPacket(order, tag, data));
}
```

---

Copyright 2021.Sanghoon.All rights reserved 
