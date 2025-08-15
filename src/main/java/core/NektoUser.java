package core;

public class NektoUser {

    public NektoUser(){}

    private long userId;
    private long dialogId;
    private long opponentId;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
    public long getDialogId() {
        return dialogId;
    }
    public long getOpponentId() {
        return opponentId;
    }
    public void setDialogId(long id) {
        this.dialogId = id;
    }
    public void setOpponentId(long id) {
        this.opponentId = id;
    }
}
