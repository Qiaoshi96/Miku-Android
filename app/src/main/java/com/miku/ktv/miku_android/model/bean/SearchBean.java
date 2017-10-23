package com.miku.ktv.miku_android.model.bean;

import java.util.List;

/**
 * Created by 焦帆 on 2017/10/17.
 */

public class SearchBean {

  /**
   * body : {"is_paginated":true,"song_list":[{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/123guoxintong.kas","hash":"","name":"123","author":"郭欣桐","song_type":1,"original":"http://cdn.fibar.cn/123guoxintong.mp3","create_time":"2017-08-30 11:35:08","link":"http://cdn.fibar.cn/123guoxintongbanzou.mp3","modify_time":"2017-08-30 11:35:08","dup":false,"hidden":false,"recommand":80,"id":65853},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/1182CBEFC23CF754B04C1AD80A2103BC.bph","hash":"1182CBEFC23CF754B04C1AD80A2103BC","name":"100KM","author":"王梵瑞","song_type":2,"original":"http://cdn.fibar.cn/1182CBEFC23CF754B04C1AD80A2103BC_ORG.mp3","create_time":"2017-10-15 09:51:00","link":"http://cdn.fibar.cn/1182CBEFC23CF754B04C1AD80A2103BC.mp3","modify_time":"2017-10-15 09:51:00","dup":false,"hidden":false,"recommand":0,"id":94916},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/C4B20734D62190201279567B243BE07A.bph","hash":"C4B20734D62190201279567B243BE07A","name":"1到10=我和你 (Live)","author":"黄义达","song_type":2,"original":"http://cdn.fibar.cn/C4B20734D62190201279567B243BE07A_ORG.mp3","create_time":"2017-10-15 19:26:10","link":"http://cdn.fibar.cn/C4B20734D62190201279567B243BE07A.mp3","modify_time":"2017-10-15 19:26:10","dup":false,"hidden":false,"recommand":0,"id":97849},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/DDFD632351D2C775F0DBC63287D8F27E.bph","hash":"DDFD632351D2C775F0DBC63287D8F27E","name":"3851_20795_国语-东方传奇-财源滚滚来","author":"d","song_type":2,"original":"http://cdn.fibar.cn/DDFD632351D2C775F0DBC63287D8F27E_ORG.mp3","create_time":"2017-10-15 10:15:52","link":"http://cdn.fibar.cn/DDFD632351D2C775F0DBC63287D8F27E.mp3","modify_time":"2017-10-15 10:15:52","dup":false,"hidden":false,"recommand":0,"id":95403},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/561ADF2CE8EC3E3F37931E1156391827.bph","hash":"561ADF2CE8EC3E3F37931E1156391827","name":"牡丹赞 (1990版)","author":"程琳","song_type":2,"original":"http://cdn.fibar.cn/561ADF2CE8EC3E3F37931E1156391827_ORG.mp3","create_time":"2017-10-15 09:55:24","link":"http://cdn.fibar.cn/561ADF2CE8EC3E3F37931E1156391827.mp3","modify_time":"2017-10-15 09:55:24","dup":false,"hidden":false,"recommand":0,"id":94993},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/100fendewenxiaoyaxuan.txt","hash":"","name":"100分的吻","author":"萧亚轩","song_type":1,"original":"http://cdn.fibar.cn/100fendewenxiaoyaxuan.mp3","create_time":"2017-09-20 07:41:04","link":"http://cdn.fibar.cn/100fendewenxiaoyaxuanbanzou.mp3","modify_time":"2017-09-20 07:41:04","dup":false,"hidden":false,"recommand":0,"id":66801},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/B04022FA81C6A439C1BED5D6F321D38F.bph","hash":"B04022FA81C6A439C1BED5D6F321D38F","name":"恋曲1990","author":"刘芳","song_type":2,"original":"http://cdn.fibar.cn/B04022FA81C6A439C1BED5D6F321D38F_ORG.mp3","create_time":"2017-10-15 10:32:47","link":"http://cdn.fibar.cn/B04022FA81C6A439C1BED5D6F321D38F.mp3","modify_time":"2017-10-15 10:32:47","dup":false,"hidden":false,"recommand":0,"id":95741},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/ECB794DBE3B2E1FE20449E56E4C1899E.bph","hash":"ECB794DBE3B2E1FE20449E56E4C1899E","name":"唯思葵花(10进9 live版)","author":"贾盛强","song_type":2,"original":"http://cdn.fibar.cn/ECB794DBE3B2E1FE20449E56E4C1899E_ORG.mp3","create_time":"2017-10-17 10:14:24","link":"http://cdn.fibar.cn/ECB794DBE3B2E1FE20449E56E4C1899E.mp3","modify_time":"2017-10-17 10:14:24","dup":false,"hidden":false,"recommand":0,"id":98551},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/C8C83E3EBBC211E8EFB56C9407DC95A4.bph","hash":"C8C83E3EBBC211E8EFB56C9407DC95A4","name":"放手2012","author":"李志洲","song_type":2,"original":"http://cdn.fibar.cn/C8C83E3EBBC211E8EFB56C9407DC95A4_ORG.mp3","create_time":"2017-10-15 03:11:31","link":"http://cdn.fibar.cn/C8C83E3EBBC211E8EFB56C9407DC95A4.mp3","modify_time":"2017-10-15 03:11:31","dup":false,"hidden":false,"recommand":0,"id":94456},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/C300D5FC8E869BB9C5A4FD4B572699A1.bph","hash":"C300D5FC8E869BB9C5A4FD4B572699A1","name":"放手2012","author":"DJ何鹏/李志洲","song_type":2,"original":"http://cdn.fibar.cn/C300D5FC8E869BB9C5A4FD4B572699A1_ORG.mp3","create_time":"2017-10-15 03:12:24","link":"http://cdn.fibar.cn/C300D5FC8E869BB9C5A4FD4B572699A1.mp3","modify_time":"2017-10-15 03:54:01","dup":false,"hidden":false,"recommand":0,"id":94470},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/0BAB4E136EAB37DB82EF9142BF529945.bph","hash":"0BAB4E136EAB37DB82EF9142BF529945","name":"85_381_国语-中国娃娃-路边野花不要采","author":"`","song_type":2,"original":"http://cdn.fibar.cn/0BAB4E136EAB37DB82EF9142BF529945_ORG.mp3","create_time":"2017-10-15 10:24:22","link":"http://cdn.fibar.cn/0BAB4E136EAB37DB82EF9142BF529945.mp3","modify_time":"2017-10-15 10:24:22","dup":false,"hidden":false,"recommand":0,"id":95578},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/6B7BC5E6AE169D1C21D8DEE1F7D8CFAE.bph","hash":"6B7BC5E6AE169D1C21D8DEE1F7D8CFAE","name":"Voices Of 512","author":"黄义达","song_type":2,"original":"http://cdn.fibar.cn/6B7BC5E6AE169D1C21D8DEE1F7D8CFAE_ORG.mp3","create_time":"2017-10-17 12:33:53","link":"http://cdn.fibar.cn/6B7BC5E6AE169D1C21D8DEE1F7D8CFAE.mp3","modify_time":"2017-10-17 12:33:53","dup":false,"hidden":false,"recommand":0,"id":98908},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/E981A10F0DDBF597D3E8FC59BC6C137F.bph","hash":"E981A10F0DDBF597D3E8FC59BC6C137F","name":"1.时光谣-王梵瑞","author":"是","song_type":2,"original":"http://cdn.fibar.cn/E981A10F0DDBF597D3E8FC59BC6C137F_ORG.mp3","create_time":"2017-10-15 09:50:10","link":"http://cdn.fibar.cn/E981A10F0DDBF597D3E8FC59BC6C137F.mp3","modify_time":"2017-10-15 09:50:10","dup":false,"hidden":false,"recommand":0,"id":94904},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/1874cehnyixun.txt","hash":"","name":"1874","author":"陈奕迅","song_type":1,"original":"http://cdn.fibar.cn/1874cehnyixun.mp3","create_time":"2017-09-11 10:56:39","link":"http://cdn.fibar.cn/1874cehnyixunbanzou.mp3","modify_time":"2017-09-11 10:56:39","dup":false,"hidden":false,"recommand":0,"id":65984},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/dijige100tianlinjunjie.txt","hash":"","name":"第几个100天","author":"林俊杰","song_type":1,"original":"http://cdn.fibar.cn/dijige100tianlinjunjie.mp3","create_time":"2017-09-11 16:30:52","link":"http://cdn.fibar.cn/dijige100tianlinjunjiebanzou.mp3","modify_time":"2017-09-11 16:30:52","dup":false,"hidden":false,"recommand":0,"id":66058},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/6D35E28C3F360DC43D30E218E76F2F2F.bph","hash":"6D35E28C3F360DC43D30E218E76F2F2F","name":"127日","author":"吴建豪/安七炫","song_type":2,"original":"http://cdn.fibar.cn/6D35E28C3F360DC43D30E218E76F2F2F_ORG.mp3","create_time":"2017-10-16 21:19:13","link":"http://cdn.fibar.cn/6D35E28C3F360DC43D30E218E76F2F2F.mp3","modify_time":"2017-10-17 15:27:21","dup":false,"hidden":false,"recommand":0,"id":98275},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/462F8AD973B974D8FCB00A92DC4708D5.bph","hash":"462F8AD973B974D8FCB00A92DC4708D5","name":"恋曲2012","author":"陈玉建/陈美惠","song_type":2,"original":"http://cdn.fibar.cn/462F8AD973B974D8FCB00A92DC4708D5_ORG.mp3","create_time":"2017-10-15 11:01:31","link":"http://cdn.fibar.cn/462F8AD973B974D8FCB00A92DC4708D5.mp3","modify_time":"2017-10-16 00:41:17","dup":false,"hidden":false,"recommand":0,"id":96306},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/8770B456C50A51D1FB888E6A2BEEB0C8.bph","hash":"8770B456C50A51D1FB888E6A2BEEB0C8","name":"忘记2011","author":"曾春年","song_type":2,"original":"http://cdn.fibar.cn/8770B456C50A51D1FB888E6A2BEEB0C8_ORG.mp3","create_time":"2017-10-15 19:16:10","link":"http://cdn.fibar.cn/8770B456C50A51D1FB888E6A2BEEB0C8.mp3","modify_time":"2017-10-15 19:16:10","dup":false,"hidden":false,"recommand":0,"id":97640},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/BDBC799BA6303B98DFC7CB81362C9473.bph","hash":"BDBC799BA6303B98DFC7CB81362C9473","name":"Namaste1","author":"张蕙兰","song_type":2,"original":"http://cdn.fibar.cn/BDBC799BA6303B98DFC7CB81362C9473_ORG.mp3","create_time":"2017-10-15 10:45:37","link":"http://cdn.fibar.cn/BDBC799BA6303B98DFC7CB81362C9473.mp3","modify_time":"2017-10-15 10:45:37","dup":false,"hidden":false,"recommand":0,"id":95984},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/3FCE6964A4B68B339B0B390D391E5F1D.bph","hash":"3FCE6964A4B68B339B0B390D391E5F1D","name":"献给邓小平诞辰100周年","author":"汤灿","song_type":2,"original":"http://cdn.fibar.cn/3FCE6964A4B68B339B0B390D391E5F1D_ORG.mp3","create_time":"2017-10-17 10:49:56","link":"http://cdn.fibar.cn/3FCE6964A4B68B339B0B390D391E5F1D.mp3","modify_time":"2017-10-17 10:49:56","dup":false,"hidden":false,"recommand":0,"id":98686}],"page_obj":{"current":1,"next":2,"total":5,"page_range":[{"page":1},{"page":2},{"page":3},{"page":4},{"page":5}],"previous":null}}
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
     * is_paginated : true
     * song_list : [{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/123guoxintong.kas","hash":"","name":"123","author":"郭欣桐","song_type":1,"original":"http://cdn.fibar.cn/123guoxintong.mp3","create_time":"2017-08-30 11:35:08","link":"http://cdn.fibar.cn/123guoxintongbanzou.mp3","modify_time":"2017-08-30 11:35:08","dup":false,"hidden":false,"recommand":80,"id":65853},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/1182CBEFC23CF754B04C1AD80A2103BC.bph","hash":"1182CBEFC23CF754B04C1AD80A2103BC","name":"100KM","author":"王梵瑞","song_type":2,"original":"http://cdn.fibar.cn/1182CBEFC23CF754B04C1AD80A2103BC_ORG.mp3","create_time":"2017-10-15 09:51:00","link":"http://cdn.fibar.cn/1182CBEFC23CF754B04C1AD80A2103BC.mp3","modify_time":"2017-10-15 09:51:00","dup":false,"hidden":false,"recommand":0,"id":94916},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/C4B20734D62190201279567B243BE07A.bph","hash":"C4B20734D62190201279567B243BE07A","name":"1到10=我和你 (Live)","author":"黄义达","song_type":2,"original":"http://cdn.fibar.cn/C4B20734D62190201279567B243BE07A_ORG.mp3","create_time":"2017-10-15 19:26:10","link":"http://cdn.fibar.cn/C4B20734D62190201279567B243BE07A.mp3","modify_time":"2017-10-15 19:26:10","dup":false,"hidden":false,"recommand":0,"id":97849},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/DDFD632351D2C775F0DBC63287D8F27E.bph","hash":"DDFD632351D2C775F0DBC63287D8F27E","name":"3851_20795_国语-东方传奇-财源滚滚来","author":"d","song_type":2,"original":"http://cdn.fibar.cn/DDFD632351D2C775F0DBC63287D8F27E_ORG.mp3","create_time":"2017-10-15 10:15:52","link":"http://cdn.fibar.cn/DDFD632351D2C775F0DBC63287D8F27E.mp3","modify_time":"2017-10-15 10:15:52","dup":false,"hidden":false,"recommand":0,"id":95403},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/561ADF2CE8EC3E3F37931E1156391827.bph","hash":"561ADF2CE8EC3E3F37931E1156391827","name":"牡丹赞 (1990版)","author":"程琳","song_type":2,"original":"http://cdn.fibar.cn/561ADF2CE8EC3E3F37931E1156391827_ORG.mp3","create_time":"2017-10-15 09:55:24","link":"http://cdn.fibar.cn/561ADF2CE8EC3E3F37931E1156391827.mp3","modify_time":"2017-10-15 09:55:24","dup":false,"hidden":false,"recommand":0,"id":94993},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/100fendewenxiaoyaxuan.txt","hash":"","name":"100分的吻","author":"萧亚轩","song_type":1,"original":"http://cdn.fibar.cn/100fendewenxiaoyaxuan.mp3","create_time":"2017-09-20 07:41:04","link":"http://cdn.fibar.cn/100fendewenxiaoyaxuanbanzou.mp3","modify_time":"2017-09-20 07:41:04","dup":false,"hidden":false,"recommand":0,"id":66801},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/B04022FA81C6A439C1BED5D6F321D38F.bph","hash":"B04022FA81C6A439C1BED5D6F321D38F","name":"恋曲1990","author":"刘芳","song_type":2,"original":"http://cdn.fibar.cn/B04022FA81C6A439C1BED5D6F321D38F_ORG.mp3","create_time":"2017-10-15 10:32:47","link":"http://cdn.fibar.cn/B04022FA81C6A439C1BED5D6F321D38F.mp3","modify_time":"2017-10-15 10:32:47","dup":false,"hidden":false,"recommand":0,"id":95741},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/ECB794DBE3B2E1FE20449E56E4C1899E.bph","hash":"ECB794DBE3B2E1FE20449E56E4C1899E","name":"唯思葵花(10进9 live版)","author":"贾盛强","song_type":2,"original":"http://cdn.fibar.cn/ECB794DBE3B2E1FE20449E56E4C1899E_ORG.mp3","create_time":"2017-10-17 10:14:24","link":"http://cdn.fibar.cn/ECB794DBE3B2E1FE20449E56E4C1899E.mp3","modify_time":"2017-10-17 10:14:24","dup":false,"hidden":false,"recommand":0,"id":98551},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/C8C83E3EBBC211E8EFB56C9407DC95A4.bph","hash":"C8C83E3EBBC211E8EFB56C9407DC95A4","name":"放手2012","author":"李志洲","song_type":2,"original":"http://cdn.fibar.cn/C8C83E3EBBC211E8EFB56C9407DC95A4_ORG.mp3","create_time":"2017-10-15 03:11:31","link":"http://cdn.fibar.cn/C8C83E3EBBC211E8EFB56C9407DC95A4.mp3","modify_time":"2017-10-15 03:11:31","dup":false,"hidden":false,"recommand":0,"id":94456},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/C300D5FC8E869BB9C5A4FD4B572699A1.bph","hash":"C300D5FC8E869BB9C5A4FD4B572699A1","name":"放手2012","author":"DJ何鹏/李志洲","song_type":2,"original":"http://cdn.fibar.cn/C300D5FC8E869BB9C5A4FD4B572699A1_ORG.mp3","create_time":"2017-10-15 03:12:24","link":"http://cdn.fibar.cn/C300D5FC8E869BB9C5A4FD4B572699A1.mp3","modify_time":"2017-10-15 03:54:01","dup":false,"hidden":false,"recommand":0,"id":94470},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/0BAB4E136EAB37DB82EF9142BF529945.bph","hash":"0BAB4E136EAB37DB82EF9142BF529945","name":"85_381_国语-中国娃娃-路边野花不要采","author":"`","song_type":2,"original":"http://cdn.fibar.cn/0BAB4E136EAB37DB82EF9142BF529945_ORG.mp3","create_time":"2017-10-15 10:24:22","link":"http://cdn.fibar.cn/0BAB4E136EAB37DB82EF9142BF529945.mp3","modify_time":"2017-10-15 10:24:22","dup":false,"hidden":false,"recommand":0,"id":95578},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/6B7BC5E6AE169D1C21D8DEE1F7D8CFAE.bph","hash":"6B7BC5E6AE169D1C21D8DEE1F7D8CFAE","name":"Voices Of 512","author":"黄义达","song_type":2,"original":"http://cdn.fibar.cn/6B7BC5E6AE169D1C21D8DEE1F7D8CFAE_ORG.mp3","create_time":"2017-10-17 12:33:53","link":"http://cdn.fibar.cn/6B7BC5E6AE169D1C21D8DEE1F7D8CFAE.mp3","modify_time":"2017-10-17 12:33:53","dup":false,"hidden":false,"recommand":0,"id":98908},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/E981A10F0DDBF597D3E8FC59BC6C137F.bph","hash":"E981A10F0DDBF597D3E8FC59BC6C137F","name":"1.时光谣-王梵瑞","author":"是","song_type":2,"original":"http://cdn.fibar.cn/E981A10F0DDBF597D3E8FC59BC6C137F_ORG.mp3","create_time":"2017-10-15 09:50:10","link":"http://cdn.fibar.cn/E981A10F0DDBF597D3E8FC59BC6C137F.mp3","modify_time":"2017-10-15 09:50:10","dup":false,"hidden":false,"recommand":0,"id":94904},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/1874cehnyixun.txt","hash":"","name":"1874","author":"陈奕迅","song_type":1,"original":"http://cdn.fibar.cn/1874cehnyixun.mp3","create_time":"2017-09-11 10:56:39","link":"http://cdn.fibar.cn/1874cehnyixunbanzou.mp3","modify_time":"2017-09-11 10:56:39","dup":false,"hidden":false,"recommand":0,"id":65984},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/dijige100tianlinjunjie.txt","hash":"","name":"第几个100天","author":"林俊杰","song_type":1,"original":"http://cdn.fibar.cn/dijige100tianlinjunjie.mp3","create_time":"2017-09-11 16:30:52","link":"http://cdn.fibar.cn/dijige100tianlinjunjiebanzou.mp3","modify_time":"2017-09-11 16:30:52","dup":false,"hidden":false,"recommand":0,"id":66058},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/6D35E28C3F360DC43D30E218E76F2F2F.bph","hash":"6D35E28C3F360DC43D30E218E76F2F2F","name":"127日","author":"吴建豪/安七炫","song_type":2,"original":"http://cdn.fibar.cn/6D35E28C3F360DC43D30E218E76F2F2F_ORG.mp3","create_time":"2017-10-16 21:19:13","link":"http://cdn.fibar.cn/6D35E28C3F360DC43D30E218E76F2F2F.mp3","modify_time":"2017-10-17 15:27:21","dup":false,"hidden":false,"recommand":0,"id":98275},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/462F8AD973B974D8FCB00A92DC4708D5.bph","hash":"462F8AD973B974D8FCB00A92DC4708D5","name":"恋曲2012","author":"陈玉建/陈美惠","song_type":2,"original":"http://cdn.fibar.cn/462F8AD973B974D8FCB00A92DC4708D5_ORG.mp3","create_time":"2017-10-15 11:01:31","link":"http://cdn.fibar.cn/462F8AD973B974D8FCB00A92DC4708D5.mp3","modify_time":"2017-10-16 00:41:17","dup":false,"hidden":false,"recommand":0,"id":96306},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/8770B456C50A51D1FB888E6A2BEEB0C8.bph","hash":"8770B456C50A51D1FB888E6A2BEEB0C8","name":"忘记2011","author":"曾春年","song_type":2,"original":"http://cdn.fibar.cn/8770B456C50A51D1FB888E6A2BEEB0C8_ORG.mp3","create_time":"2017-10-15 19:16:10","link":"http://cdn.fibar.cn/8770B456C50A51D1FB888E6A2BEEB0C8.mp3","modify_time":"2017-10-15 19:16:10","dup":false,"hidden":false,"recommand":0,"id":97640},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/BDBC799BA6303B98DFC7CB81362C9473.bph","hash":"BDBC799BA6303B98DFC7CB81362C9473","name":"Namaste1","author":"张蕙兰","song_type":2,"original":"http://cdn.fibar.cn/BDBC799BA6303B98DFC7CB81362C9473_ORG.mp3","create_time":"2017-10-15 10:45:37","link":"http://cdn.fibar.cn/BDBC799BA6303B98DFC7CB81362C9473.mp3","modify_time":"2017-10-15 10:45:37","dup":false,"hidden":false,"recommand":0,"id":95984},{"catch_lrc":false,"lrc":"http://cdn.fibar.cn/3FCE6964A4B68B339B0B390D391E5F1D.bph","hash":"3FCE6964A4B68B339B0B390D391E5F1D","name":"献给邓小平诞辰100周年","author":"汤灿","song_type":2,"original":"http://cdn.fibar.cn/3FCE6964A4B68B339B0B390D391E5F1D_ORG.mp3","create_time":"2017-10-17 10:49:56","link":"http://cdn.fibar.cn/3FCE6964A4B68B339B0B390D391E5F1D.mp3","modify_time":"2017-10-17 10:49:56","dup":false,"hidden":false,"recommand":0,"id":98686}]
     * page_obj : {"current":1,"next":2,"total":5,"page_range":[{"page":1},{"page":2},{"page":3},{"page":4},{"page":5}],"previous":null}
     */

    private boolean is_paginated;
    private PageObjBean page_obj;
    private List<SongListBean> song_list;

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

    public List<SongListBean> getSong_list() {
      return song_list;
    }

    public void setSong_list(List<SongListBean> song_list) {
      this.song_list = song_list;
    }

    public static class PageObjBean {
      /**
       * current : 1
       * next : 2
       * total : 5
       * page_range : [{"page":1},{"page":2},{"page":3},{"page":4},{"page":5}]
       * previous : null
       */

      private int current;
      private int next;
      private int total;
      private Object previous;
      private List<PageRangeBean> page_range;

      public int getCurrent() {
        return current;
      }

      public void setCurrent(int current) {
        this.current = current;
      }

      public int getNext() {
        return next;
      }

      public void setNext(int next) {
        this.next = next;
      }

      public int getTotal() {
        return total;
      }

      public void setTotal(int total) {
        this.total = total;
      }

      public Object getPrevious() {
        return previous;
      }

      public void setPrevious(Object previous) {
        this.previous = previous;
      }

      public List<PageRangeBean> getPage_range() {
        return page_range;
      }

      public void setPage_range(List<PageRangeBean> page_range) {
        this.page_range = page_range;
      }

      public static class PageRangeBean {
        /**
         * page : 1
         */

        private int page;

        public int getPage() {
          return page;
        }

        public void setPage(int page) {
          this.page = page;
        }
      }
    }

    public static class SongListBean {
      /**
       * catch_lrc : false
       * lrc : http://cdn.fibar.cn/123guoxintong.kas
       * hash :
       * name : 123
       * author : 郭欣桐
       * song_type : 1
       * original : http://cdn.fibar.cn/123guoxintong.mp3
       * create_time : 2017-08-30 11:35:08
       * link : http://cdn.fibar.cn/123guoxintongbanzou.mp3
       * modify_time : 2017-08-30 11:35:08
       * dup : false
       * hidden : false
       * recommand : 80
       * id : 65853
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
  }
}