package core;

import core.network.WebSocketImpl;
import core.utils.LOG;
import okhttp3.*;
import okio.ByteString;
import org.json.JSONArray;
import org.json.JSONObject;


import javax.swing.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


import static core.NektoEnum.Notice.*;
/**
 * simple implementation nektome api
 */
public class NektoAPI {
    private NektoAPI api;
    private NektoGui ng;
    private NektoUser user;
    private Timer responseTimer;
    private long lastReadMessageId=0;
    public NektoAPI(NektoGui ng)
    {
        this.ng=ng;
        this.api=this;
    }
    private class PingTask extends TimerTask {
        @Override
        public void run() {

            try {
                send("2");
            }

            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    private class ReadTask extends TimerTask {
        @Override
        public void run() {

            try {
                if (lastReadMessageId>0) {
                    api.send(new NektoRequest.SendResponse(user.getDialogId(), lastReadMessageId));
                    lastReadMessageId=0;
                }
                else LOG.println("No Messages");
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    /**
     * nekto me base url for webSocket
     */
    private static String BASE_URL = "wss://im.nekto.me/socket.io/?EIO=3&transport=websocket";

    /**
     * webSocket implementation
     */
    private final WebSocketImpl socket = new WebSocketImpl();

    /**
     * set headers from mobile application
     * see "Untitled.chls"
     * @param
     */
    public void connect(NektoUser user){
        this.user=user;
        HashMap<String, String>  headers = new HashMap<>();
        headers.put("User-Agent", "NektoMe193/1.6.0 (Linux; U; Android 4.4.2; SM-G355H Build/KOT49H)");
        headers.put("Android-Language", "ru");
        headers.put("App-Android-Code", "193");
        headers.put("App-Android-Version", "4.0.7");
        headers.put("NektoMe-Chat-Version", "1");
        Request request = new Request.Builder().url(BASE_URL).headers(Headers.of(headers)).build();
        socket.connect(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                ng.setStatus(1);
                LOG.println("opened connection");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                LOG.println("<< From server[str] %s", text);
                try {
                    if (text.startsWith("42")) {
                        JSONArray body = new JSONArray(text.substring(2));
                        String notice = body.getString(0);
                        JSONObject bodyData = body.getJSONObject(1);
                        //LOG.println("data: "+bodyData.getString(notice));
                        if(bodyData.getString(notice).equals(ERROR)){
                            //LOG.println("message from server[str] %s", text);
                        }
                        switch (NektoEnum.Notice.from(bodyData.getString(notice))){
                            case AUTH_TOKEN:
                                JSONObject userData = bodyData.getJSONObject("data");
                                JSONObject tokenInfo = userData.getJSONObject("tokenInfo");
                                long userid = userData.getLong("id");
                                String token = tokenInfo.getString("authToken");
                                //LOG.println("user id [%d] token[%s]%n", userid, token);
                                ng.setUserID("UserID: "+userid);
                                user.setUserId(userid);
                                user.setToken(token);
                                Timer timer = new Timer();
                                timer.schedule(new PingTask(), 20 * 1000, 20 * 1000);
                                break;
                            case DIALOG_OPENED:
                                ng.addText("-- Chat opened --");
                                responseTimer = new Timer();
                                responseTimer.schedule(new ReadTask(), 5 * 1000, 5 * 1000);
                                JSONObject dialogData = bodyData.getJSONObject("data");
                                long chatid = dialogData.getLong("id");
                                JSONArray interlocutor = dialogData.getJSONArray("interlocutors");
                                long interlocutorID=interlocutor.getLong(1);
                                ng.setStatus(4);
                                ng.setInterlocutor(interlocutorID);
                                user.setOpponentId(interlocutorID);
                                ng.setDialogId(chatid);
                                user.setDialogId(chatid);
                                break;
                            case DIALOG_CLOSED:
                                ng.addText("-- Chat closed --");
                                responseTimer.cancel();
                                ng.setStatus(1);
                                ng.hideInterlocutor();
                                user.setDialogId(0);
                                user.setOpponentId(0);
                                ng.setDialogId(0);
                                api.send(new NektoRequest.OnlineTrack(true));
                                break;
                            case DIALOG_TYPING:
                                JSONObject typingData = bodyData.getJSONObject("data");
                                boolean isTyping=typingData.getBoolean("typing");
                                if (isTyping)
                                    ng.setUserCount("Typing...");
                                else
                                    ng.setUserCount("");
                                break;
                            case NEW_MESSAGE:
                                JSONObject messageData = bodyData.getJSONObject("data");
                                String messageText=messageData.getString("message");
                                long senderID=messageData.getLong("senderId");
                                if (senderID==user.getOpponentId()) {lastReadMessageId=messageData.getLong("id");
                                ng.addText("Nekto: "+messageText);}
                                else  ng.addText("I: "+messageText);
                                break;
                            case ONLINE_COUNT:
                                JSONObject serdarData = bodyData.getJSONObject("data");
                                long inChats = serdarData.optLong("inChats",0);
                                long inSearch = serdarData.optLong("inSearch",0);
                                long inServer = serdarData.optLong("inServer",0);
                                //LOG.println(String.format(Locale.US, "inChats[%d] inSearch[%d] inServer[%d]", inChats, inSearch, inServer));
                                ng.setUserCount(String.format(Locale.US, "inChats: %d inSearch: %d inServer: %d", inChats, inSearch, inServer));
                                break;
                            case SEARCH_SUCCESS:
                                ng.setStatus(3);
                                LOG.println("Search in progress");
                                break;
                            case SEARCH_OUT:
                                ng.setStatus(1);
                                break;
                            case CAPTCHA_VERIFY:
                                JSONObject captchaData = bodyData.getJSONObject("data");
                                //boolean solution = captchaData.getBoolean("solution");
                                //if (solution){
                                    api.send(new NektoRequest.SearchRun());
                                //}
                                break;
                            case ERROR:
                                JSONObject errorData = bodyData.getJSONObject("data");
                                long errorCode = errorData.optLong("id",0);
                                if (errorCode==600){
                                    ng.setStatus(2);
                                    JSONObject additional = errorData.getJSONObject("additional");
                                    String imageUrl = additional.getString("captchaImage");
                                    SwingUtilities.invokeLater(() -> new NektoCaptchaFrame(imageUrl,api));
                                }
                                break;
                            default:
                                LOG.println("something went wrong. Incorrect methodName %s", text);
                        }
                    }
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);
                LOG.println("message from server[bytes] %s", bytes.hex());
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                LOG.println("closing connection");
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                LOG.println("closed connection");
                LOG.verbose();
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);
                LOG.println("failure connection "+t);
            }
        });
    }

    public void send(String message){
        LOG.println(">> To server[str] %s", message);
        socket.send(message);
    }

    public void send(NektoRequest request){

        LOG.println(">> To server[request] %s", request.buildRequest());
        socket.send(request.buildRequest());
    }

    public void send(ByteString message){
        socket.send(message);
    }

    public void close(){
        socket.close();
    }

}
