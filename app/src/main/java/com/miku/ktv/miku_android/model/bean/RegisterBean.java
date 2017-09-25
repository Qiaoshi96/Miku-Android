package com.miku.ktv.miku_android.model.bean;

/**
 * Created by 焦帆 on 2017/9/22.
 */

public class RegisterBean {


    /**
     * body : {"phone":"17839919268","headline":"","sex":0,"nick":"ajiao","token":"q4eKTd1bztUXqxj7Fmi2rJElS8dlkknGaIhipNOtbMfeDcmvLRcPhvY0zHxo9gsf","create_time":1506083007,"avatar":null,"chat":true,"fullname":425033,"id":152}
     * status : 1
     * msg : success
     */

    private BodyBean body;
    private int status;
    private String msg;

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class BodyBean {
        /**
         * phone : 17839919268
         * headline :
         * sex : 0
         * nick : ajiao
         * token : q4eKTd1bztUXqxj7Fmi2rJElS8dlkknGaIhipNOtbMfeDcmvLRcPhvY0zHxo9gsf
         * create_time : 1506083007
         * avatar : null
         * chat : true
         * fullname : 425033
         * id : 152
         */

        private String phone;
        private String headline;
        private int sex;
        private String nick;
        private String token;
        private int create_time;
        private Object avatar;
        private boolean chat;
        private int fullname;
        private int id;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getHeadline() {
            return headline;
        }

        public void setHeadline(String headline) {
            this.headline = headline;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public Object getAvatar() {
            return avatar;
        }

        public void setAvatar(Object avatar) {
            this.avatar = avatar;
        }

        public boolean isChat() {
            return chat;
        }

        public void setChat(boolean chat) {
            this.chat = chat;
        }

        public int getFullname() {
            return fullname;
        }

        public void setFullname(int fullname) {
            this.fullname = fullname;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
