package dcom.nearbuybackend.api.global.websocket;

import dcom.nearbuybackend.api.domain.chat.service.ChatService;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private final ChatService chatService;

    public static Map<String, WebSocketSession> userSession = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session){
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payLoadMessage = message.getPayload();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(payLoadMessage);

        if(jsonObject.get("eventType").equals("getChatRoomList"))
            chatService.getChatRoomListSocket(session,jsonObject.get("accessToken").toString().substring(7));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status){
        userSession.keySet().remove(session);
    }
}
