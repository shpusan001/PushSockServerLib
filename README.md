# Pusha

---

## 개요
>TCP소켓통신기반의 푸시서버구축 라이브러리입니다. 이 라이브러리는 푸시 서버 구축을 쉽게 만들어 줍니다.  
>   
> + 준수한 성능 ( [CPU : i5-7200U (2.5GHz), IntelliJ IDEA 2GB 환경]
    에서 테스트한 결과 최대 7800클라이언트 동시 접속 및 등록 26초 내에 처리) 
> + 매우 쉽고 편리한 구축 및 사용

---

## 공통

---
### 구성
> + SockConfiguration : 서버 또는 클라이언트 설정
> + ServerManager : 서버 구축 매니저 (싱글톤)
> + ClientManager : 클라이언트 구축 매니저 (싱글톤)
> + ServerObjectRecieveService : 서버의 수신데이터 처리 정의
> + ClientObjectRecieveService : 클라이언트의 수신 데이터 처리 정의
    
### 초기 설정
1. SockConfiguration 클래스에서 ip, port, id를 설정한다.
>+ 서버의 경우 : 바운드를 위한 port를 정한다.
```java
SockConfiguration.instance.port = 7777; // int
```
#
>+ 클라이언트의 경우 : 서버 접속을 위한 ip, port, id(고유식별자)를 정한다.
```java
SockConfiguration.instance.ip = "127.0.0.1"; // String
SockConfiguration.instance.port = 7777; // int
SockConfiguration.instance.id = "475b8eeb-a7ee-4cb4-ab2f-788ae161c7ba"; // String
```

### 수신 데이터 처리 설정
> 클래스를 변경해서 해당 명령 처리에 대해 정의한다. 
> 1. process 함수의 case에 처리하고 싶은 명령어를 정한다. 
>   + ex) case "NOTICE"
> 2. 'order_명령어()' 의 형태로 함수를 만든다.
>   + ex) order_NOTICE(WrappedSocket wrappedSocket, Packet packet)
> 3. 함수 내부에 명령에 따른 처리를 정의한다.
>   + ex)  new LogFormat(packet.getTag(),
      "{" + wrappedSocket.getSocketId() + "} 
      > => Server : " + packet.getMessage()).log();

> + 설정 시 변경이 필요한 클래스
>  + 서버측 설정 : push.server.service.ServerObjectRecieveService 클래스
>  + 클라이언트측 설정 : push.client.service.ClientObjectRecieveService 클래스
 
```java
public class ServerObjectRecieveService {

    ServerManager serverManager = ServerManager.instance;

    public static ServerObjectRecieveService instance = new ServerObjectRecieveService();

    private ServerObjectRecieveService(){}

    /**
     * Orders can be added with switch statements and functions
     *
     * --ex
     * If :: order to be added is BROADCAST
     * Then :: add a order to a switch statement
     *         create and add a function in this form
     *         function : order_BROADCAST(WrappedSocket wrappedSocket, Packet packet)
     */

    public void process(WrappedSocket wrappedSocket, Packet packet){
        switch (packet.getOrder()) {
            case "UUID" : order_UUID(wrappedSocket, packet); break;
            case "NOTICE" : order_NOTICE(wrappedSocket, packet); break;
        }
    }

    private void order_UUID(WrappedSocket wrappedSocket, Packet packet){
        wrappedSocket.setSocketId(packet.getMessage());
        serverManager.repository.RegisteredSocketMap.put(packet.getMessage(), wrappedSocket);
        new LogFormat(packet.getTag(), "{" + wrappedSocket.getSocketId() + "} is registered").log();
    }

    private void order_NOTICE(WrappedSocket wrappedSocket, Packet packet){
        new LogFormat(packet.getTag(), "{" + wrappedSocket.getSocketId() + "} => Server : " + packet.getMessage()).log();
    }
}
```

### 서버 와 클라이언트
> 본 라이브러리는 '서버구축' 과 '클라이언트구축' 이 모두 가능합니다.
> 
> 해당 라이브러리 사용으로 서버구축을 원한다면 서버 구축을, 클라이언트 구축을 원한다면
> 클라이언트 구축을 하면 됩니다.

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
> 1. WrappedSocketRepository

```java
ServerManager.instance.setRepository(new WrappedSocketRepository());
```

3.서버 바운드 (bound)
> SockCofiguration의 port를 사용한다.
```java
ServerManager.instance.bound(SockConfiguration.instance.port);
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
> + DataPacket에 tag, order message를 주입해서 생성
>  + tag : 태그 
>  + order : 명령, 클라이언트에서 식별 후 명령 실행에 사용
>  + message : 전송할 메시지 
> 
> 
> + 패킷 종류
>  + NullPacket : 내부에 공백이 들어간 패킷
>  + DataPacket : 데이터가 들어있는 패킷, 생성할 때 데이터 주입 가능
```java
ServerManager.intance.sendTarget(id, new DataPacket(tag, order, message));
```

---

##클라이언트

---

### 클라이언트 구축
1. 클라이언트 사용을 선언 
```java
ClientManager.use();
```

2. 서버에 연결 (connect)
> SockConfiguration의 ip, port, id를 사용하여 연결
```java
clientManager.connect(SockConfiguration.instance.ip
        , SockConfiguration.instance.port
        , SockConfiguration.instance.id);
```

3. 클라이언트 처리 시작
> 클라이언트 처리 스레드 실행
> + 서버 데이터 수신
> + 수신 데이터 해석 및 처리
```java
ClientManager.instance.process();
```

---

### 클라이언트 사용
1. 서버에 데이터 전송
> + DataPacket에 tag, order message를 주입해서 생성
    >  + tag : 태그
>  + order : 명령, 클라이언트에서 식별 후 명령 실행에 사용
>  + message : 전송할 메시지
>
>
> + 패킷 종류 
>  + NullPacket : 내부에 공백이 들어간 패킷
>  + DataPacket : 데이터가 들어있는 패킷, 생성할 때 데이터 주입 가능
```java
ClientManager.instance.send(id, new DataPacket(tag, order, message));
```

---
Copyright 2021.Sanghoon.All rights reserved 
