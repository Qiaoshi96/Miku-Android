package com.miku.ktv.miku_android.model.bean;

import java.util.List;

/**
 * Created by 焦帆 on 2017/10/9.
 */

public class JoinRoomBean {

    /**
     * body : {"count":2,"participants":[{"phone":"13888888888","headline":"123","sex":1,"nick":"test-1","create_time":1502986423,"room_id":343,"modify_time":1507222862,"online":true,"fullname":"1000","id":124,"friend":4,"avatar":"/s/image/upload/150302693999avatar.jpg"},{"phone":"13888888885","headline":"test","sex":2,"nick":"test-5","create_time":1506075640,"room_id":343,"modify_time":1507551598,"online":true,"fullname":"425029","id":148,"friend":4,"avatar":"/s/image/upload/150607673745avatar.jpg"}]}
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
         * count : 2
         * participants : [{"phone":"13888888888","headline":"123","sex":1,"nick":"test-1","create_time":1502986423,"room_id":343,"modify_time":1507222862,"online":true,"fullname":"1000","id":124,"friend":4,"avatar":"/s/image/upload/150302693999avatar.jpg"},{"phone":"13888888885","headline":"test","sex":2,"nick":"test-5","create_time":1506075640,"room_id":343,"modify_time":1507551598,"online":true,"fullname":"425029","id":148,"friend":4,"avatar":"/s/image/upload/150607673745avatar.jpg"}]
         */

        private int count;
        private List<ParticipantsBean> participants;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<ParticipantsBean> getParticipants() {
            return participants;
        }

        public void setParticipants(List<ParticipantsBean> participants) {
            this.participants = participants;
        }

        public static class ParticipantsBean {
            /**
             * phone : 13888888888
             * headline : 123
             * sex : 1
             * nick : test-1
             * create_time : 1502986423
             * room_id : 343
             * modify_time : 1507222862
             * online : true
             * fullname : 1000
             * id : 124
             * friend : 4
             * avatar : /s/image/upload/150302693999avatar.jpg
             */

            private String phone;
            private String headline;
            private int sex;
            private String nick;
            private int create_time;
            private int room_id;
            private int modify_time;
            private boolean online;
            private String fullname;
            private int id;
            private int friend;
            private String avatar;

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

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public int getRoom_id() {
                return room_id;
            }

            public void setRoom_id(int room_id) {
                this.room_id = room_id;
            }

            public int getModify_time() {
                return modify_time;
            }

            public void setModify_time(int modify_time) {
                this.modify_time = modify_time;
            }

            public boolean isOnline() {
                return online;
            }

            public void setOnline(boolean online) {
                this.online = online;
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

            public int getFriend() {
                return friend;
            }

            public void setFriend(int friend) {
                this.friend = friend;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }
        }
    }
}
