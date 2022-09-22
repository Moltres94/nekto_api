import core.NektoAPI;
import core.utils.LOG;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class Nekto {

    public static void main(String[] args) {
        NektoAPI api = new NektoAPI();
        api.connect(new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                LOG.println("opened connection");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                LOG.println("message from server[str] %s", text);
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

        //api.send("42[\"action\",{\"pushToken\":****************");
        //...chatiing...
        //api.close();

        //Call close to exit from application
        //System.close(0);
    }

}
