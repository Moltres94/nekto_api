package core;

import core.network.WebSocketImpl;
import core.utils.LOG;
import okhttp3.*;
import okio.ByteString;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;

import static core.NektoEnum.Notice.*;

/**
 * simple implementation nektome api
 */
public class NektoAPI {

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
                LOG.println("opened connection");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                //LOG.println("message from server[str] %s", text);
                try {
                    if (text.startsWith("42")) {
                        JSONArray body = new JSONArray(text.substring(2));
                        String notice = body.getString(0);
                        JSONObject bodyData = body.getJSONObject(1);
                        LOG.println("data: "+bodyData.getString(notice));
                        if(bodyData.getString(notice).equals(ERROR)){
                            LOG.println("message from server[str] %s", text);
                        }
                        switch (NektoEnum.Notice.from(bodyData.getString(notice))){
                            case AUTH_TOKEN:
                                JSONObject userData = bodyData.getJSONObject("data");
                                JSONObject tokenInfo = userData.getJSONObject("tokenInfo");
                                long userid = userData.getLong("id");
                                String token = tokenInfo.getString("authToken");
                                LOG.println("user id [%d] token[%s]%n", userid, token);
                                user.setUserId(userid);
                                user.setToken(token);
                                break;
                            case ONLINE_COUNT:
                                JSONObject serdarData = bodyData.getJSONObject("data");
                                long inChats = serdarData.optLong("inChats",0);
                                long inSearch = serdarData.optLong("inSearch",0);
                                long inServer = serdarData.optLong("inServer",0);
                                LOG.println(String.format(Locale.US, "inChats[%d] inSearch[%d] inServer[%d]", inChats, inSearch, inServer));
                                break;
                            case ERROR:
                                LOG.println("message from server[str] %s", text);
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
        socket.send(message);
    }

    public void send(NektoRequest request){
        socket.send(request.buildRequest());
    }

    public void send(ByteString message){
        socket.send(message);
    }

    public void close(){
        socket.close();
    }

}
