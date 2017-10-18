package com.miku.ktv.miku_android.model.bean;

import java.util.List;

/**
 * Created by 焦帆 on 2017/10/18.
 */

public class AddListBean {

    /**
     * body : {"is_paginated":false,"page_obj":{},"singer_list":[{"song_id":65680,"song":{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/congcongnanian.kas","hash":"","name":"匆匆那年","author":"王菲","song_type":1,"original":"http://cdn.fibar.cn/congcongnanian.mp3","create_time":"2017-08-17 11:00:07","link":"http://cdn.fibar.cn/congcongnanianbanzou.mp3","modify_time":"2017-08-25 12:00:09","dup":false,"hidden":false,"recommand":80,"id":65680},"creator":{"phone":"18507198446","forbid":false,"headline":"","sex":2,"nick":"，，我。。","create_time":"2017-10-14 20:00:17","last_login":null,"modify_time":"2017-10-15 20:38:25","online":true,"room_id":401,"fullname":"425103","id":222,"avatar":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLShoThFAr5sZd1z88RqL5iaZM3ttvLRQdrE49AXBfo2heU1I0Ok4v3ASD9wbZUnC4bylBI8BPfbJw/0"},"creator_id":222,"create_time":"2017-10-15 20:40:30","room_id":401,"modify_time":"2017-10-15 20:40:30","id":1124},{"song_id":100271,"song":{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/0AFCF23E75718F71D0B45CC01006DC4B.bph","hash":"0AFCF23E75718F71D0B45CC01006DC4B","name":"追光者","author":"岑宁儿","song_type":2,"original":"http://cdn.fibar.cn/0AFCF23E75718F71D0B45CC01006DC4B_ORG.mp3","create_time":"2017-10-18 14:42:03","link":"http://cdn.fibar.cn/0AFCF23E75718F71D0B45CC01006DC4B.mp3","modify_time":"2017-10-18 14:42:20","dup":false,"hidden":false,"recommand":200,"id":100271},"creator":{"phone":"17600772135","forbid":false,"headline":"","sex":0,"nick":"哈哈哈","create_time":"2017-09-27 11:20:12","last_login":null,"modify_time":"2017-10-18 17:54:47","online":true,"room_id":401,"fullname":"425054","id":173,"avatar":null},"creator_id":173,"create_time":"2017-10-18 17:55:05","room_id":401,"modify_time":"2017-10-18 17:55:05","id":1199},{"song_id":100265,"song":{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/63127091C51C9165D108046EFC70926F.bph","hash":"63127091C51C9165D108046EFC70926F","name":"小幸运 (电影《我的少女时代》主题曲)","author":"田馥甄","song_type":2,"original":"http://cdn.fibar.cn/63127091C51C9165D108046EFC70926F_ORG.mp3","create_time":"2017-10-18 14:40:10","link":"http://cdn.fibar.cn/63127091C51C9165D108046EFC70926F.mp3","modify_time":"2017-10-18 14:42:38","dup":false,"hidden":false,"recommand":200,"id":100265},"creator":{"phone":"15510600608","forbid":false,"headline":"","sex":0,"nick":"123","create_time":"2017-09-28 10:16:38","last_login":null,"modify_time":"2017-10-18 18:06:33","online":true,"room_id":402,"fullname":"425056","id":175,"avatar":null},"creator_id":175,"create_time":"2017-10-18 18:06:45","room_id":401,"modify_time":"2017-10-18 18:06:45","id":1200},{"song_id":100258,"song":{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/7FCEF4FA001B164F23D96847805F61D3.bph","hash":"7FCEF4FA001B164F23D96847805F61D3","name":"80000 ！ (prod.by DROCY原声版)","author":"PRC Killab","song_type":2,"original":"http://cdn.fibar.cn/7FCEF4FA001B164F23D96847805F61D3_ORG.mp3","create_time":"2017-10-18 14:39:53","link":"http://cdn.fibar.cn/7FCEF4FA001B164F23D96847805F61D3.mp3","modify_time":"2017-10-18 14:42:02","dup":false,"hidden":false,"recommand":210,"id":100258},"creator":{"phone":"15010814396","forbid":false,"headline":"","sex":0,"nick":"43964","create_time":"2017-09-26 20:17:50","last_login":null,"modify_time":"2017-10-18 18:30:06","online":true,"room_id":402,"fullname":"425047","id":166,"avatar":null},"creator_id":166,"create_time":"2017-10-18 18:30:10","room_id":401,"modify_time":"2017-10-18 18:30:10","id":1201}]}
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
         * is_paginated : false
         * page_obj : {}
         * singer_list : [{"song_id":65680,"song":{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/congcongnanian.kas","hash":"","name":"匆匆那年","author":"王菲","song_type":1,"original":"http://cdn.fibar.cn/congcongnanian.mp3","create_time":"2017-08-17 11:00:07","link":"http://cdn.fibar.cn/congcongnanianbanzou.mp3","modify_time":"2017-08-25 12:00:09","dup":false,"hidden":false,"recommand":80,"id":65680},"creator":{"phone":"18507198446","forbid":false,"headline":"","sex":2,"nick":"，，我。。","create_time":"2017-10-14 20:00:17","last_login":null,"modify_time":"2017-10-15 20:38:25","online":true,"room_id":401,"fullname":"425103","id":222,"avatar":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLShoThFAr5sZd1z88RqL5iaZM3ttvLRQdrE49AXBfo2heU1I0Ok4v3ASD9wbZUnC4bylBI8BPfbJw/0"},"creator_id":222,"create_time":"2017-10-15 20:40:30","room_id":401,"modify_time":"2017-10-15 20:40:30","id":1124},{"song_id":100271,"song":{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/0AFCF23E75718F71D0B45CC01006DC4B.bph","hash":"0AFCF23E75718F71D0B45CC01006DC4B","name":"追光者","author":"岑宁儿","song_type":2,"original":"http://cdn.fibar.cn/0AFCF23E75718F71D0B45CC01006DC4B_ORG.mp3","create_time":"2017-10-18 14:42:03","link":"http://cdn.fibar.cn/0AFCF23E75718F71D0B45CC01006DC4B.mp3","modify_time":"2017-10-18 14:42:20","dup":false,"hidden":false,"recommand":200,"id":100271},"creator":{"phone":"17600772135","forbid":false,"headline":"","sex":0,"nick":"哈哈哈","create_time":"2017-09-27 11:20:12","last_login":null,"modify_time":"2017-10-18 17:54:47","online":true,"room_id":401,"fullname":"425054","id":173,"avatar":null},"creator_id":173,"create_time":"2017-10-18 17:55:05","room_id":401,"modify_time":"2017-10-18 17:55:05","id":1199},{"song_id":100265,"song":{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/63127091C51C9165D108046EFC70926F.bph","hash":"63127091C51C9165D108046EFC70926F","name":"小幸运 (电影《我的少女时代》主题曲)","author":"田馥甄","song_type":2,"original":"http://cdn.fibar.cn/63127091C51C9165D108046EFC70926F_ORG.mp3","create_time":"2017-10-18 14:40:10","link":"http://cdn.fibar.cn/63127091C51C9165D108046EFC70926F.mp3","modify_time":"2017-10-18 14:42:38","dup":false,"hidden":false,"recommand":200,"id":100265},"creator":{"phone":"15510600608","forbid":false,"headline":"","sex":0,"nick":"123","create_time":"2017-09-28 10:16:38","last_login":null,"modify_time":"2017-10-18 18:06:33","online":true,"room_id":402,"fullname":"425056","id":175,"avatar":null},"creator_id":175,"create_time":"2017-10-18 18:06:45","room_id":401,"modify_time":"2017-10-18 18:06:45","id":1200},{"song_id":100258,"song":{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/7FCEF4FA001B164F23D96847805F61D3.bph","hash":"7FCEF4FA001B164F23D96847805F61D3","name":"80000 ！ (prod.by DROCY原声版)","author":"PRC Killab","song_type":2,"original":"http://cdn.fibar.cn/7FCEF4FA001B164F23D96847805F61D3_ORG.mp3","create_time":"2017-10-18 14:39:53","link":"http://cdn.fibar.cn/7FCEF4FA001B164F23D96847805F61D3.mp3","modify_time":"2017-10-18 14:42:02","dup":false,"hidden":false,"recommand":210,"id":100258},"creator":{"phone":"15010814396","forbid":false,"headline":"","sex":0,"nick":"43964","create_time":"2017-09-26 20:17:50","last_login":null,"modify_time":"2017-10-18 18:30:06","online":true,"room_id":402,"fullname":"425047","id":166,"avatar":null},"creator_id":166,"create_time":"2017-10-18 18:30:10","room_id":401,"modify_time":"2017-10-18 18:30:10","id":1201}]
         */

        private boolean is_paginated;
        private PageObjBean page_obj;
        private List<SingerListBean> singer_list;

        public boolean isIs_paginated() {
            return is_paginated;
        }

        public void setIs_paginated(boolean is_paginated) {
            this.is_paginated = is_paginated;
        }

        public PageObjBean getPage_obj() {
            return page_obj;
        }

        public void setPage_obj(PageObjBean page_obj) {
            this.page_obj = page_obj;
        }

        public List<SingerListBean> getSinger_list() {
            return singer_list;
        }

        public void setSinger_list(List<SingerListBean> singer_list) {
            this.singer_list = singer_list;
        }

        public static class PageObjBean {
        }

        public static class SingerListBean {
            /**
             * song_id : 65680
             * song : {"catch_lrc":false,"lrc":"http://cdn.fibar.cn/congcongnanian.kas","hash":"","name":"匆匆那年","author":"王菲","song_type":1,"original":"http://cdn.fibar.cn/congcongnanian.mp3","create_time":"2017-08-17 11:00:07","link":"http://cdn.fibar.cn/congcongnanianbanzou.mp3","modify_time":"2017-08-25 12:00:09","dup":false,"hidden":false,"recommand":80,"id":65680}
             * creator : {"phone":"18507198446","forbid":false,"headline":"","sex":2,"nick":"，，我。。","create_time":"2017-10-14 20:00:17","last_login":null,"modify_time":"2017-10-15 20:38:25","online":true,"room_id":401,"fullname":"425103","id":222,"avatar":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLShoThFAr5sZd1z88RqL5iaZM3ttvLRQdrE49AXBfo2heU1I0Ok4v3ASD9wbZUnC4bylBI8BPfbJw/0"}
             * creator_id : 222
             * create_time : 2017-10-15 20:40:30
             * room_id : 401
             * modify_time : 2017-10-15 20:40:30
             * id : 1124
             */

            private int song_id;
            private SongBean song;
            private CreatorBean creator;
            private int creator_id;
            private String create_time;
            private int room_id;
            private String modify_time;
            private int id;

            public int getSong_id() {
                return song_id;
            }

            public void setSong_id(int song_id) {
                this.song_id = song_id;
            }

            public SongBean getSong() {
                return song;
            }

            public void setSong(SongBean song) {
                this.song = song;
            }

            public CreatorBean getCreator() {
                return creator;
            }

            public void setCreator(CreatorBean creator) {
                this.creator = creator;
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

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public static class SongBean {
                /**
                 * catch_lrc : false
                 * lrc : http://cdn.fibar.cn/congcongnanian.kas
                 * hash :
                 * name : 匆匆那年
                 * author : 王菲
                 * song_type : 1
                 * original : http://cdn.fibar.cn/congcongnanian.mp3
                 * create_time : 2017-08-17 11:00:07
                 * link : http://cdn.fibar.cn/congcongnanianbanzou.mp3
                 * modify_time : 2017-08-25 12:00:09
                 * dup : false
                 * hidden : false
                 * recommand : 80
                 * id : 65680
                 */

                private boolean catch_lrc;
                private String lrc;
                private String hash;
                private String name;
                private String author;
                private int song_type;
                private String original;
                private String create_time;
                private String link;
                private String modify_time;
                private boolean dup;
                private boolean hidden;
                private int recommand;
                private int id;

                public boolean isCatch_lrc() {
                    return catch_lrc;
                }

                public void setCatch_lrc(boolean catch_lrc) {
                    this.catch_lrc = catch_lrc;
                }

                public String getLrc() {
                    return lrc;
                }

                public void setLrc(String lrc) {
                    this.lrc = lrc;
                }

                public String getHash() {
                    return hash;
                }

                public void setHash(String hash) {
                    this.hash = hash;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getAuthor() {
                    return author;
                }

                public void setAuthor(String author) {
                    this.author = author;
                }

                public int getSong_type() {
                    return song_type;
                }

                public void setSong_type(int song_type) {
                    this.song_type = song_type;
                }

                public String getOriginal() {
                    return original;
                }

                public void setOriginal(String original) {
                    this.original = original;
                }

                public String getCreate_time() {
                    return create_time;
                }

                public void setCreate_time(String create_time) {
                    this.create_time = create_time;
                }

                public String getLink() {
                    return link;
                }

                public void setLink(String link) {
                    this.link = link;
                }

                public String getModify_time() {
                    return modify_time;
                }

                public void setModify_time(String modify_time) {
                    this.modify_time = modify_time;
                }

                public boolean isDup() {
                    return dup;
                }

                public void setDup(boolean dup) {
                    this.dup = dup;
                }

                public boolean isHidden() {
                    return hidden;
                }

                public void setHidden(boolean hidden) {
                    this.hidden = hidden;
                }

                public int getRecommand() {
                    return recommand;
                }

                public void setRecommand(int recommand) {
                    this.recommand = recommand;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }
            }

            public static class CreatorBean {
                /**
                 * phone : 18507198446
                 * forbid : false
                 * headline :
                 * sex : 2
                 * nick : ，，我。。
                 * create_time : 2017-10-14 20:00:17
                 * last_login : null
                 * modify_time : 2017-10-15 20:38:25
                 * online : true
                 * room_id : 401
                 * fullname : 425103
                 * id : 222
                 * avatar : https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLShoThFAr5sZd1z88RqL5iaZM3ttvLRQdrE49AXBfo2heU1I0Ok4v3ASD9wbZUnC4bylBI8BPfbJw/0
                 */

                private String phone;
                private boolean forbid;
                private String headline;
                private int sex;
                private String nick;
                private String create_time;
                private Object last_login;
                private String modify_time;
                private boolean online;
                private int room_id;
                private String fullname;
                private int id;
                private String avatar;

                public String getPhone() {
                    return phone;
                }

                public void setPhone(String phone) {
                    this.phone = phone;
                }

                public boolean isForbid() {
                    return forbid;
                }

                public void setForbid(boolean forbid) {
                    this.forbid = forbid;
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

                public Object getLast_login() {
                    return last_login;
                }

                public void setLast_login(Object last_login) {
                    this.last_login = last_login;
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

                public int getRoom_id() {
                    return room_id;
                }

                public void setRoom_id(int room_id) {
                    this.room_id = room_id;
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

                public String getAvatar() {
                    return avatar;
                }

                public void setAvatar(String avatar) {
                    this.avatar = avatar;
                }
            }
        }
    }
}
