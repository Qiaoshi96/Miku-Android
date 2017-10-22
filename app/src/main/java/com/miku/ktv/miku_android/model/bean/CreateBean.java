package com.miku.ktv.miku_android.model.bean;

/**
 * Created by 焦帆 on 2017/10/23.
 */

public class CreateBean {

    /**
     * body : {"index":0,"name":"123456","cover":"/s/image/upload/150868985088mmexport1500036814083.jpg","creator_id":"425046","create_time":1508689996,"room_id":"R1508689996079155","modify_time":1508689996,"progress":0,"id":455,"creator_nick":"123456"}
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
         * index : 0
         * name : 123456
         * cover : /s/image/upload/150868985088mmexport1500036814083.jpg
         * creator_id : 425046
         * create_time : 1508689996
         * room_id : R1508689996079155
         * modify_time : 1508689996
         * progress : 0
         * id : 455
         * creator_nick : 123456
         */

        private int index;
        private String name;
        private String cover;
        private String creator_id;
        private int create_time;
        private String room_id;
        private int modify_time;
        private int progress;
        private int id;
        private String creator_nick;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getCreator_id() {
            return creator_id;
        }

        public void setCreator_id(String creator_id) {
            this.creator_id = creator_id;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public String getRoom_id() {
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }

        public int getModify_time() {
            return modify_time;
        }

        public void setModify_time(int modify_time) {
            this.modify_time = modify_time;
        }

        public int getProgress() {
            return progress;
        }

        public void setProgress(int progress) {
            this.progress = progress;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCreator_nick() {
            return creator_nick;
        }

        public void setCreator_nick(String creator_nick) {
            this.creator_nick = creator_nick;
        }
    }
}
