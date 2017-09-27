package com.miku.ktv.miku_android.model.bean;

/**
 * Created by 焦帆 on 2017/9/27.
 */

public class UpLoadBean {

    /**
     * body : {"phone":"15608059720","nick":"RaPoSpectre","create_time":1469004454,"avatar":"/s/image/upload/149266818996v2-dfbc73a036407c674277f1005d072d6f_l.jpg","fullname":"建奇 张","id":1}
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
         * phone : 15608059720
         * nick : RaPoSpectre
         * create_time : 1469004454
         * avatar : /s/image/upload/149266818996v2-dfbc73a036407c674277f1005d072d6f_l.jpg
         * fullname : 建奇 张
         * id : 1
         */

        private String phone;
        private String nick;
        private int create_time;
        private String avatar;
        private String fullname;
        private int id;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
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
