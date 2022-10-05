package core;

import java.io.Serializable;

public class NektoEnum {

    public enum ActionEnum implements Serializable {
        AUTH_TOKEN("auth.sendToken", true),
        GET_TOKEN("auth.getToken", true),
        AUTH_SOCIAL("auth.sendSocialToken", true),
        AUTH_PHONE("auth.sendPhone", true),
        PUSH_TOKEN("push.setToken"),
        SEARCH_RUN("search.run"),
        SEARCH_OUT("search.sendOut"),
        DIALOG_TYPING("dialog.setTyping"),
        DIALOG_INFO("dialog.info"),
        ANON_LEAVE("anon.leaveDialog"),
        ANON_MESSAGE("anon.message"),
        ANON_READ_MESSAGES("anon.readMessages"),
        ONLINE_TRACK("online.track"),
        CAPTCHA_VERIFY("captcha.verify"),
        REPORT_DIALOG("antispam.report");

        private final String methodName;
        private final boolean withoutAuth;

        ActionEnum(String str) {
            this(str, false);
        }

        public String getMethodName() {
            return this.methodName;
        }

        public boolean isWithoutAuth() {
            return this.withoutAuth;
        }

        ActionEnum(String str, boolean z) {
            this.methodName = str;
            this.withoutAuth = z;
        }

        @Override
        public String toString() {
            return methodName;
        }
    }

    public enum Notice implements Serializable {
        ERROR("error.code"),
        AUTH_TOKEN("auth.successToken"),
        AUTH_USER("auth.successUser"),
        DIALOG_OPENED("dialog.opened"),
        DIALOG_CLOSED("dialog.closed"),
        DIALOG_TYPING("dialog.typing"),
        SEARCH_SUCCESS("search.success"),
        SEARCH_OUT("search.out"),
        NEW_MESSAGE("messages.new"),
        READ_MESSAGES("messages.reads"),
        DIALOG_INFO("dialog.info"),
        DIALOG_PAID("dialog.paid"),
        ONLINE_COUNT("online.count"),
        CAPTCHA_VERIFY("captcha.verify"),
        SOCKET_CLOSE("socket.close"),
        PURCHASE_CHANGED("purchase.changed"),
        SOCKET_EXCEPTION("internal.socket.exception");

        private final String methodName;

        Notice(String str) { this.methodName = str; }

        public String getMethodName() { return this.methodName; }

        @Override
        public String toString() { return this.methodName; }

        public static Notice from(String methodName){
            for(Notice notice : values()){
                if(notice.methodName.equals(methodName))
                    return notice;
            }
            return null;
        }

    }

    public enum ErrorCode {
        ActionNotExist(53, "Данный метод отсутствует."),
        FirstAuth(54, "Вначале авторизуйтесь."),
        BadData(55, "Не правильные параметры."),
        DbError(56, "Проблемы с базой данных."),
        WrongToken(100, "Не правильный токен пользователя."),
        NotYourDialog(200, "Диалог не открыт вами, либо его не существет."),
        LimitOpenlyDialogs(201, "У вас открыто максимальное количество диалогов."),
        LimitUsersInDialog(202, "Users limit is exceeded in the dialog."),
        TooShortMessage(203, "Слишком короткое сообщение."),
        TooLongMessage(204, "Слишком длинное сообщение."),
        ReservedIp(256, "С данного IP адреса уже есть открытые диалоги."),
        RequireCaptcha(400, "Требуется ввода каптчи."),
        UpdateCaptcha(401, "Срок каптчи истёк."),
        BadInputCaptcha(402, "Ошибка ввода каптчи."),
        YourAreBanned(403, "Вы заблокированы."),
        LimitComplaints(404, "Limit Complaints is exceeded"),
        YourAlreadyComplaint(405, "Your are already complaint this user"),
        LimitFriends(501, "Уже добавлено максимальное количество друзей."),
        AlreadyFriends(502, "Вы уже друзья"),
        AlreadySignIn(503, "Вы уже авторизованы"),
        InvalidPinActivation(504, "Указан неверный код"),
        BeforeSignIn(505, "Прежде чем высылать пин, вышлите SIGN IN"),
        InviteNotFound(506, "Заявка на приглашение не найдена"),
        UserNoActive(507, "Пользователь не зарегистрирован"),
        AlreadyExistInvite(508, "Заявка уже существует"),
        DialogIsClosed(509, "Диолог уже закрыт"),
        NotFriend(510, "Он вам не друг");

        private final Integer code;
        private final String description;

        ErrorCode(Integer num, String str) {
            this.code = num;
            this.description = str;
        }

        public int getCode() {
            return this.code.intValue();
        }
    }

}
