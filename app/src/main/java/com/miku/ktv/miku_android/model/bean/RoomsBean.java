package com.miku.ktv.miku_android.model.bean;

import java.util.List;

/**
 * Created by 焦帆 on 2017/9/29.
 */

public class RoomsBean {


    /**
     * body : {"page_obj":{},"is_paginated":false,"room_list":[{"index":0,"name":"哈哈哈","cover":"/s/image/upload/150607477969avatar.jpg","participants":[{"phone":"17600772135","headline":"","sex":0,"nick":"哈哈哈","create_time":"2017-09-27 11:20:12","room_id":289,"modify_time":"2017-09-28 18:38:23","online":true,"fullname":"425054","id":173,"avatar":null},{"phone":"15010814396","headline":"","sex":0,"nick":"哈哈哈","create_time":"2017-09-26 20:15:00","room_id":289,"modify_time":"2017-09-28 15:43:43","online":true,"fullname":"425045","id":164,"avatar":null},{"phone":"13888888887","headline":"test","sex":2,"nick":"test-7","create_time":"2017-09-22 18:06:19","room_id":289,"modify_time":"2017-09-28 20:56:59","online":true,"fullname":"425027","id":146,"avatar":"/s/image/upload/150607477969avatar.jpg"}],"creator_id":425027,"create_time":"2017-09-25 00:57:57","room_id":"R1506272277548245","modify_time":"2017-09-25 00:57:57","progress":0,"id":289,"creator_nick":"test-7","numbers":3},{"index":0,"name":"唱给你听","cover":"http://cdn.fibar.cn/xiaoxiong1.png","participants":[{"phone":"","headline":"好的","sex":1,"nick":"朱健","create_time":"2017-08-17 22:15:25","room_id":212,"modify_time":"2017-09-20 10:49:41","online":true,"fullname":"425003","id":120,"avatar":"https://wx.qlogo.cn/mmopen/Q3auHgzwzM5UGialn88ibZFWhFJanNTsllJia4qWmogddcBBAVC8G52XrpaFqTkdic4ZR0Z2ic4Cw5BsbMt3SyNYfMA/0"},{"phone":"","headline":"","sex":2,"nick":"糖￡糖","create_time":"2017-09-28 15:38:07","room_id":212,"modify_time":"2017-09-28 15:38:14","online":true,"fullname":"425062","id":181,"avatar":"https://q.qlogo.cn/qqapp/1106308148/E2DCAEFD6D35A8EE983E42F09EE30763/100"}],"creator_id":424980,"create_time":"2017-09-09 17:31:53","room_id":"R1504949513564645","modify_time":"2017-09-20 22:05:04","progress":0,"id":212,"creator_nick":"JHGH","numbers":2},{"index":0,"name":"欢","cover":"/s/image/upload/150607485762avatar.jpg","participants":[{"phone":"13888888886","headline":"test","sex":1,"nick":"test-6","create_time":"2017-09-22 18:07:37","room_id":308,"modify_time":"2017-09-28 17:27:04","online":true,"fullname":"425028","id":147,"avatar":"/s/image/upload/150607485762avatar.jpg"}],"creator_id":425028,"create_time":"2017-09-28 15:44:15","room_id":"R1506584655948579","modify_time":"2017-09-28 15:44:15","progress":0,"id":308,"creator_nick":"test-6","numbers":1},{"index":0,"name":"啊","cover":"https://wx.qlogo.cn/mmopen/SCug0ESSOHicuqjFbRF9R3Ls4OEYtekrmicCXicgQWjWUw02DfQeZLchibAzPy24IJPyR92pp2Fd3CpoANCwia3K6NQ/0","participants":[{"phone":"","headline":"","sex":2,"nick":"跑快快的草莓酱","create_time":"2017-08-18 18:11:28","room_id":314,"modify_time":"2017-09-28 18:15:13","online":true,"fullname":"425008","id":127,"avatar":"https://wx.qlogo.cn/mmopen/SCug0ESSOHicuqjFbRF9R3Ls4OEYtekrmicCXicgQWjWUw02DfQeZLchibAzPy24IJPyR92pp2Fd3CpoANCwia3K6NQ/0"},{"phone":"","headline":"","sex":1,"nick":"小演员皮皮","create_time":"2017-08-18 17:56:13","room_id":314,"modify_time":"2017-09-28 18:15:13","online":true,"fullname":"425007","id":126,"avatar":"https://wx.qlogo.cn/mmopen/f6MM9ynM620jjD4icZSRiabDxUy3FJVCmkTmCduicia32w0ILXoGibPjI7o60icp1uWfTdWYMtXs4ETSsE1bYTZgZIuOtffz48jQ5K/0"}],"creator_id":425008,"create_time":"2017-09-28 17:58:31","room_id":"R1506592711198418","modify_time":"2017-09-28 17:58:31","progress":0,"id":314,"creator_nick":"跑快快的草莓酱","numbers":2},{"index":0,"name":"我来给你伴奏","cover":"http://cdn.fibar.cn/gougou2.png","participants":[{"phone":"18511033324","headline":"","sex":1,"nick":"皮皮鲁","create_time":"2017-08-17 22:02:57","room_id":230,"modify_time":"2017-09-18 18:58:39","online":true,"fullname":"425002","id":119,"avatar":"/s/image/upload/150297857813avatar.jpg"}],"creator_id":425002,"create_time":"2017-09-18 17:08:11","room_id":"R1505725691862830","modify_time":"2017-09-20 22:05:55","progress":0,"id":230,"creator_nick":"皮皮鲁","numbers":1}]}
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
         * page_obj : {}
         * is_paginated : false
         * room_list : [{"index":0,"name":"哈哈哈","cover":"/s/image/upload/150607477969avatar.jpg","participants":[{"phone":"17600772135","headline":"","sex":0,"nick":"哈哈哈","create_time":"2017-09-27 11:20:12","room_id":289,"modify_time":"2017-09-28 18:38:23","online":true,"fullname":"425054","id":173,"avatar":null},{"phone":"15010814396","headline":"","sex":0,"nick":"哈哈哈","create_time":"2017-09-26 20:15:00","room_id":289,"modify_time":"2017-09-28 15:43:43","online":true,"fullname":"425045","id":164,"avatar":null},{"phone":"13888888887","headline":"test","sex":2,"nick":"test-7","create_time":"2017-09-22 18:06:19","room_id":289,"modify_time":"2017-09-28 20:56:59","online":true,"fullname":"425027","id":146,"avatar":"/s/image/upload/150607477969avatar.jpg"}],"creator_id":425027,"create_time":"2017-09-25 00:57:57","room_id":"R1506272277548245","modify_time":"2017-09-25 00:57:57","progress":0,"id":289,"creator_nick":"test-7","numbers":3},{"index":0,"name":"唱给你听","cover":"http://cdn.fibar.cn/xiaoxiong1.png","participants":[{"phone":"","headline":"好的","sex":1,"nick":"朱健","create_time":"2017-08-17 22:15:25","room_id":212,"modify_time":"2017-09-20 10:49:41","online":true,"fullname":"425003","id":120,"avatar":"https://wx.qlogo.cn/mmopen/Q3auHgzwzM5UGialn88ibZFWhFJanNTsllJia4qWmogddcBBAVC8G52XrpaFqTkdic4ZR0Z2ic4Cw5BsbMt3SyNYfMA/0"},{"phone":"","headline":"","sex":2,"nick":"糖￡糖","create_time":"2017-09-28 15:38:07","room_id":212,"modify_time":"2017-09-28 15:38:14","online":true,"fullname":"425062","id":181,"avatar":"https://q.qlogo.cn/qqapp/1106308148/E2DCAEFD6D35A8EE983E42F09EE30763/100"}],"creator_id":424980,"create_time":"2017-09-09 17:31:53","room_id":"R1504949513564645","modify_time":"2017-09-20 22:05:04","progress":0,"id":212,"creator_nick":"JHGH","numbers":2},{"index":0,"name":"欢","cover":"/s/image/upload/150607485762avatar.jpg","participants":[{"phone":"13888888886","headline":"test","sex":1,"nick":"test-6","create_time":"2017-09-22 18:07:37","room_id":308,"modify_time":"2017-09-28 17:27:04","online":true,"fullname":"425028","id":147,"avatar":"/s/image/upload/150607485762avatar.jpg"}],"creator_id":425028,"create_time":"2017-09-28 15:44:15","room_id":"R1506584655948579","modify_time":"2017-09-28 15:44:15","progress":0,"id":308,"creator_nick":"test-6","numbers":1},{"index":0,"name":"啊","cover":"https://wx.qlogo.cn/mmopen/SCug0ESSOHicuqjFbRF9R3Ls4OEYtekrmicCXicgQWjWUw02DfQeZLchibAzPy24IJPyR92pp2Fd3CpoANCwia3K6NQ/0","participants":[{"phone":"","headline":"","sex":2,"nick":"跑快快的草莓酱","create_time":"2017-08-18 18:11:28","room_id":314,"modify_time":"2017-09-28 18:15:13","online":true,"fullname":"425008","id":127,"avatar":"https://wx.qlogo.cn/mmopen/SCug0ESSOHicuqjFbRF9R3Ls4OEYtekrmicCXicgQWjWUw02DfQeZLchibAzPy24IJPyR92pp2Fd3CpoANCwia3K6NQ/0"},{"phone":"","headline":"","sex":1,"nick":"小演员皮皮","create_time":"2017-08-18 17:56:13","room_id":314,"modify_time":"2017-09-28 18:15:13","online":true,"fullname":"425007","id":126,"avatar":"https://wx.qlogo.cn/mmopen/f6MM9ynM620jjD4icZSRiabDxUy3FJVCmkTmCduicia32w0ILXoGibPjI7o60icp1uWfTdWYMtXs4ETSsE1bYTZgZIuOtffz48jQ5K/0"}],"creator_id":425008,"create_time":"2017-09-28 17:58:31","room_id":"R1506592711198418","modify_time":"2017-09-28 17:58:31","progress":0,"id":314,"creator_nick":"跑快快的草莓酱","numbers":2},{"index":0,"name":"我来给你伴奏","cover":"http://cdn.fibar.cn/gougou2.png","participants":[{"phone":"18511033324","headline":"","sex":1,"nick":"皮皮鲁","create_time":"2017-08-17 22:02:57","room_id":230,"modify_time":"2017-09-18 18:58:39","online":true,"fullname":"425002","id":119,"avatar":"/s/image/upload/150297857813avatar.jpg"}],"creator_id":425002,"create_time":"2017-09-18 17:08:11","room_id":"R1505725691862830","modify_time":"2017-09-20 22:05:55","progress":0,"id":230,"creator_nick":"皮皮鲁","numbers":1}]
         */

        private PageObjBean page_obj;
        private boolean is_paginated;
        private List<RoomListBean> room_list;

        public PageObjBean getPage_obj() {
            return page_obj;
        }

        public void setPage_obj(PageObjBean page_obj) {
            this.page_obj = page_obj;
        }

        public boolean isIs_paginated() {
            return is_paginated;
        }

        public void setIs_paginated(boolean is_paginated) {
            this.is_paginated = is_paginated;
        }

        public List<RoomListBean> getRoom_list() {
            return room_list;
        }

        public void setRoom_list(List<RoomListBean> room_list) {
            this.room_list = room_list;
        }

        public static class PageObjBean {
        }

        public static class RoomListBean {
            /**
             * index : 0
             * name : 哈哈哈
             * cover : /s/image/upload/150607477969avatar.jpg
             * participants : [{"phone":"17600772135","headline":"","sex":0,"nick":"哈哈哈","create_time":"2017-09-27 11:20:12","room_id":289,"modify_time":"2017-09-28 18:38:23","online":true,"fullname":"425054","id":173,"avatar":null},{"phone":"15010814396","headline":"","sex":0,"nick":"哈哈哈","create_time":"2017-09-26 20:15:00","room_id":289,"modify_time":"2017-09-28 15:43:43","online":true,"fullname":"425045","id":164,"avatar":null},{"phone":"13888888887","headline":"test","sex":2,"nick":"test-7","create_time":"2017-09-22 18:06:19","room_id":289,"modify_time":"2017-09-28 20:56:59","online":true,"fullname":"425027","id":146,"avatar":"/s/image/upload/150607477969avatar.jpg"}]
             * creator_id : 425027
             * create_time : 2017-09-25 00:57:57
             * room_id : R1506272277548245
             * modify_time : 2017-09-25 00:57:57
             * progress : 0
             * id : 289
             * creator_nick : test-7
             * numbers : 3
             */

            private int index;
            private String name;
            private String cover;
            private int creator_id;
            private String create_time;
            private String room_id;
            private String modify_time;
            private int progress;
            private int id;
            private String creator_nick;
            private int numbers;
            private List<ParticipantsBean> participants;

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

            public int getCreator_id() {
                return creator_id;
            }

            public void setCreator_id(int creator_id) {
                this.creator_id = creator_id;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getRoom_id() {
                return room_id;
            }

            public void setRoom_id(String room_id) {
                this.room_id = room_id;
            }

            public String getModify_time() {
                return modify_time;
            }

            public void setModify_time(String modify_time) {
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

            public int getNumbers() {
                return numbers;
            }

            public void setNumbers(int numbers) {
                this.numbers = numbers;
            }

            public List<ParticipantsBean> getParticipants() {
                return participants;
            }

            public void setParticipants(List<ParticipantsBean> participants) {
                this.participants = participants;
            }

            public static class ParticipantsBean {
                /**
                 * phone : 17600772135
                 * headline :
                 * sex : 0
                 * nick : 哈哈哈
                 * create_time : 2017-09-27 11:20:12
                 * room_id : 289
                 * modify_time : 2017-09-28 18:38:23
                 * online : true
                 * fullname : 425054
                 * id : 173
                 * avatar : null
                 */

                private String phone;
                private String headline;
                private int sex;
                private String nick;
                private String create_time;
                private int room_id;
                private String modify_time;
                private boolean online;
                private String fullname;
                private int id;
                private Object avatar;

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

                public String getCreate_time() {
                    return create_time;
                }

                public void setCreate_time(String create_time) {
                    this.create_time = create_time;
                }

                public int getRoom_id() {
                    return room_id;
                }

                public void setRoom_id(int room_id) {
                    this.room_id = room_id;
                }

                public String getModify_time() {
                    return modify_time;
                }

                public void setModify_time(String modify_time) {
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

                public Object getAvatar() {
                    return avatar;
                }

                public void setAvatar(Object avatar) {
                    this.avatar = avatar;
                }
            }
        }
    }
}
