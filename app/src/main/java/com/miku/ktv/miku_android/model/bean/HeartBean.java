package com.miku.ktv.miku_android.model.bean;

import java.util.List;

/**
 * Created by 焦帆 on 2017/10/19.
 */

public class HeartBean {

    /**
     * body : {"room":null,"phone":"17600772135","nick":"哈哈哈哈哈","version":"1.0.3","room_id":null,"avatar":null,"online":true,"fullname":"425054","deletes":[],"friends":[{"participants":[{"nick":"哈哈哈哈哈","fullname":"425054","room":null,"phone":"17600772135","id":173}],"room":"free"}],"id":173,"modify_time":1.508415986E9}
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
         * room : null
         * phone : 17600772135
         * nick : 哈哈哈哈哈
         * version : 1.0.3
         * room_id : null
         * avatar : null
         * online : true
         * fullname : 425054
         * deletes : []
         * friends : [{"participants":[{"nick":"哈哈哈哈哈","fullname":"425054","room":null,"phone":"17600772135","id":173}],"room":"free"}]
         * id : 173
         * modify_time : 1.508415986E9
         */

        private Object room;
        private String phone;
        private String nick;
        private String version;
        private Object room_id;
        private Object avatar;
        private boolean online;
        private String fullname;
        private int id;
        private double modify_time;
        private List<?> deletes;
        private List<FriendsBean> friends;

        public Object getRoom() {
            return room;
        }

        public void setRoom(Object room) {
            this.room = room;
        }

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

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public Object getRoom_id() {
            return room_id;
        }

        public void setRoom_id(Object room_id) {
            this.room_id = room_id;
        }

        public Object getAvatar() {
            return avatar;
        }

        public void setAvatar(Object avatar) {
            this.avatar = avatar;
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

        public double getModify_time() {
            return modify_time;
        }

        public void setModify_time(double modify_time) {
            this.modify_time = modify_time;
        }

        public List<?> getDeletes() {
            return deletes;
        }

        public void setDeletes(List<?> deletes) {
            this.deletes = deletes;
        }

        public List<FriendsBean> getFriends() {
            return friends;
        }

        public void setFriends(List<FriendsBean> friends) {
            this.friends = friends;
        }

        public static class FriendsBean {
            /**
             * participants : [{"nick":"哈哈哈哈哈","fullname":"425054","room":null,"phone":"17600772135","id":173}]
             * room : free
             */

            private String room;
            private List<ParticipantsBean> participants;

            public String getRoom() {
                return room;
            }

            public void setRoom(String room) {
                this.room = room;
            }

            public List<ParticipantsBean> getParticipants() {
                return participants;
            }

            public void setParticipants(List<ParticipantsBean> participants) {
                this.participants = participants;
            }

            public static class ParticipantsBean {
                /**
                 * nick : 哈哈哈哈哈
                 * fullname : 425054
                 * room : null
                 * phone : 17600772135
                 * id : 173
                 */

                private String nick;
                private String fullname;
                private Object room;
                private String phone;
                private int id;

                public String getNick() {
                    return nick;
                }

                public void setNick(String nick) {
                    this.nick = nick;
                }

                public String getFullname() {
                    return fullname;
                }

                public void setFullname(String fullname) {
                    this.fullname = fullname;
                }

                public Object getRoom() {
                    return room;
                }

                public void setRoom(Object room) {
                    this.room = room;
                }

                public String getPhone() {
                    return phone;
                }

                public void setPhone(String phone) {
                    this.phone = phone;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }
            }
        }
    }
}
