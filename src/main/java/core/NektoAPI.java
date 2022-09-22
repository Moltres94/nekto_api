package core;

import core.network.WebSocketImpl;
import okhttp3.*;
import okio.ByteString;

import java.util.HashMap;

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
     * @param listener
     */
    public void connect(WebSocketListener listener){
        HashMap<String, String>  headers = new HashMap<>();
        headers.put("User-Agent", "NektoMe193/1.6.0 (Linux; U; Android 4.4.2; SM-G355H Build/KOT49H)");
        headers.put("Android-Language", "ru");
        headers.put("App-Android-Code", "193");
        headers.put("App-Android-Version", "4.0.7");
        headers.put("NektoMe-Chat-Version", "1");
        Request request = new Request.Builder().url(BASE_URL).headers(Headers.of(headers)).build();
        socket.connect(request, listener);
    }

    public void send(String message){
        socket.send(message);
    }

    public void send(ByteString message){
        socket.send(message);
    }

    public void close(){
        socket.close();
    }

}
