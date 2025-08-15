package core;

import core.utils.LOG;
import org.json.JSONArray;
import org.json.JSONObject;

import static core.NektoEnum.ActionEnum.*;

public abstract class NektoRequest {

    public static class SearchRun extends NektoRequest {
        private final NektoSearchFilter filter;
        private static NektoSearchFilter lastFilter;
        public SearchRun(NektoSearchFilter filter){
            this.filter = filter; lastFilter=filter;
        }
        public SearchRun(){
            this(lastFilter);
        }
        public String buildRequest(){
            JSONArray myAge = new JSONArray();
            myAge.put(filter.getMyAgeMin());
            myAge.put(filter.getMyAgeMax());
            JSONArray wishAgeWrapper = new JSONArray();
            JSONArray wishAge = new JSONArray();
            wishAge.put(filter.getWishAgeMin());
            wishAge.put(filter.getWishAgeMax());
            wishAgeWrapper.put(wishAge);
            JSONArray body = new JSONArray();
            JSONObject requestJson = new JSONObject();
            requestJson.put("isAdult", filter.isAdult());
            requestJson.put("role", filter.isRole());
            requestJson.put("isVoiceDisable", filter.isVoiceDisable());
            requestJson.put("myAge", myAge);
            requestJson.put("mySex", filter.getMySex());
            requestJson.put("wishAge", wishAgeWrapper);
            requestJson.put("wishSex", filter.getWishSex());
            requestJson.put("action", SEARCH_RUN.getMethodName());
            body.put("action");
            body.put(requestJson);
            return "42"+body;
        }
    }

    public static class OnlineTrack extends NektoRequest {
        private final boolean state;
        public OnlineTrack(boolean state){
            this.state = state;
        }
        public String buildRequest(){
            JSONArray body = new JSONArray();
            JSONObject requestJson = new JSONObject();
            requestJson.put("action", ONLINE_TRACK.getMethodName());
            requestJson.put("on", state);
            body.put("action");
            body.put(requestJson);
            return "42"+body;
        }
    }

    public static class SendToken extends NektoRequest {
        private NektoDeivce deivce;
        private String token;
        public SendToken(NektoDeivce deivce, String token){
            this.deivce = deivce;
            this.token = token;
        }
        public String buildRequest(){
            JSONArray body = new JSONArray();
            JSONObject requestJson = new JSONObject();
            requestJson.put("pushToken", "null");
            requestJson.put("token", token);
            requestJson.put("insKey", deivce.getInsKey());
            requestJson.put("vending", deivce.getVending());
            requestJson.put("action", AUTH_TOKEN.getMethodName());
            requestJson.put("sigKey", deivce.getSigKey());
            requestJson.put("pType", 101);
            requestJson.put("key", deivce.getKey());
            body.put("action");
            body.put(requestJson);
            return "42"+body;
        }
    }

    public static class GetToken extends NektoRequest{
        private NektoDeivce deivce;
        public GetToken(NektoDeivce deivce){
            this.deivce = deivce;
        }
        public String buildRequest(){
            JSONArray body = new JSONArray();
            JSONObject requestJson = new JSONObject();
            requestJson.put("deviceId", deivce.getDeviceId());
            requestJson.put("deviceName", deivce.getDeviceName());
            requestJson.put("deviceType", 1);
            requestJson.put("insKey", deivce.getInsKey());
            requestJson.put("key", deivce.getKey());
            requestJson.put("pType", 0);
            requestJson.put("sigKey", deivce.getSigKey());
            requestJson.put("vending", deivce.getVending());
            requestJson.put("action", GET_TOKEN.getMethodName());
            body.put("action");
            body.put(requestJson);
            return "42"+body;
        }
    }

    public static class SendCaptcha extends NektoRequest{
        private String solutionStr;
        public SendCaptcha(String solutionStr){
            this.solutionStr = solutionStr;
        }
        public String buildRequest(){
            JSONArray body = new JSONArray();
            JSONObject requestJson = new JSONObject();
            //requestJson.put("hard", false);
            requestJson.put("solution", solutionStr);
            requestJson.put("action", CAPTCHA_VERIFY.getMethodName());
            body.put("action");
            body.put(requestJson);
            return "42"+body;
        }
    }
    public static class SendResponse extends NektoRequest{
        private long dialogId;
        private long lastMessageId;
        public SendResponse(long dialogId,long lastMessageId){
            this.dialogId = dialogId;this.lastMessageId = lastMessageId;
        }
        public String buildRequest(){
            JSONArray body = new JSONArray();
            JSONObject requestJson = new JSONObject();
            //requestJson.put("hard", false);
            requestJson.put("dialogId", dialogId);
            requestJson.put("lastMessageId", lastMessageId);
            requestJson.put("action", ANON_READ_MESSAGES.getMethodName());
            body.put("action");
            body.put(requestJson);
            return "42"+body;
        }
    }
    public static class SendMessage extends NektoRequest{
        private String msg;
        private long dialogId;
        public SendMessage(String msg, long dialogId){
            this.msg = msg;
            this.dialogId=dialogId;
        }
        private String getRandom()
        {
            long max = 9223372036854775807L;
            long min = 1L;
            long var3 = (long)(Math.random() * (max - min) + min);
            return (String.valueOf(var3));
        }
        public String buildRequest(){
            JSONArray body = new JSONArray();
            JSONObject requestJson = new JSONObject();
            //requestJson.put("hard", false);
            requestJson.put("dialogId", dialogId);
            requestJson.put("message", msg);
            requestJson.put("randomId", getRandom());
            requestJson.put("action", ANON_MESSAGE.getMethodName());
            requestJson.put("fileId", JSONObject.NULL);
            body.put("action");
            body.put(requestJson);
            return "42"+body;
        }
    }
    public static class ExitChat extends NektoRequest{
        private Long dialogID;
        public ExitChat(Long dialogID){
            this.dialogID = dialogID;
        }
        public String buildRequest(){
            JSONArray body = new JSONArray();
            JSONObject requestJson = new JSONObject();
            //requestJson.put("hard", false);
            requestJson.put("action", ANON_LEAVE.getMethodName());
            requestJson.put("dialogId", dialogID);
            body.put("action");
            body.put(requestJson);
            return "42"+body;
        }
    }
    public static class SearchStop extends NektoRequest{

        public SearchStop(){}
        public String buildRequest(){
            JSONArray body = new JSONArray();
            JSONObject requestJson = new JSONObject();
            requestJson.put("action", SEARCH_OUT.getMethodName());
            body.put("action");
            body.put(requestJson);
            return "42"+body;
        }
    }
    public static class ReadMessages extends NektoRequest{

        public ReadMessages(){}
        public String buildRequest(){
            JSONArray body = new JSONArray();
            JSONObject requestJson = new JSONObject();
            requestJson.put("action", SEARCH_OUT.getMethodName());
            body.put("action");
            body.put(requestJson);
            return "42"+body;
        }
    }

    public abstract String buildRequest();

}
