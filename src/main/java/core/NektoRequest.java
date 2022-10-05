package core;

import core.utils.LOG;
import org.json.JSONArray;
import org.json.JSONObject;

import static core.NektoEnum.ActionEnum.*;

public abstract class NektoRequest {

    public static class SearchRun extends NektoRequest {
        private final NektoSearchFilter filter;
        public SearchRun(NektoSearchFilter filter){
            this.filter = filter;
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
        public String buildRequest(){
            JSONArray body = new JSONArray();
            JSONObject requestJson = new JSONObject();
            requestJson.put("action", ONLINE_TRACK.getMethodName());
            requestJson.put("on", true);
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

    public abstract String buildRequest();

}
