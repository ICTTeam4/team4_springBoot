const stompClient = new StompJs.Client({
    webSocketFactory: () => new SockJS('http://localhost:8080/gs-guide-websocket'),
    reconnectDelay: 5000,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
});

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('WebSocket Connected:', frame);

    // 서버에서 메시지를 수신
    stompClient.subscribe('/topic/greetings', (message) => {
        try {
            const parsedData = JSON.parse(message.body); // JSON 데이터 파싱
            console.log('Received:', parsedData);

            // 화면에 표시
            showGreeting(parsedData);
        } catch (error) {
            console.error('Error parsing received message:', error, message.body);
        }
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('WebSocket Error:', error);
};

stompClient.onStompError = (frame) => {
    console.error('STOMP Error:', frame.headers['message']);
    console.error('Additional details:', frame.body);
};

// WebSocket 연결 상태를 UI에 반영
function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#greetings").html(""); // 화면 초기화
}

// WebSocket 연결
function connect() {
    console.log('Connecting to WebSocket...');
    stompClient.activate();
}

// WebSocket 연결 해제
function disconnect() {
    console.log('Disconnecting from WebSocket...');
    stompClient.deactivate();
    console.log('WebSocket disconnected successfully.');
    setConnected(false);
}

// 메시지 전송
function sendName() {
    const name = $("#name").val();       // 닉네임 입력값
    const message = $("#message").val(); // 메시지 입력값

    // 입력값 검증
    // if (!name || !message) {
    //     console.error('Name and message cannot be empty!');
    //     alert('Both name and message are required!');
    //     return;
    // }

    console.log('Sending:', { name, message });

    // WebSocket으로 데이터 전송
    stompClient.publish({
        destination: "/app/hello", // 서버의 MessageMapping 경로
        body: JSON.stringify({
            name: name,
            message: message
        }),
    });

    // 메시지 입력창 초기화
    $("#name").val("");
    $("#message").val("");
}

// 수신 메시지 화면에 표시
function showGreeting(data) {
    const { name, message } = data;

    // 화면에 닉네임과 메시지를 추가
    $("#greetings").append(
        `<tr><td><strong>${name}:</strong> ${message}</td></tr>`
    );
}

// DOM 이벤트 바인딩
$(function () {
    $("form").on('submit', (e) => e.preventDefault()); // 폼 제출 기본 동작 방지
    $("#connect").click(() => connect());
    $("#disconnect").click(() => disconnect());
    $("#send").click(() => sendName());
});
