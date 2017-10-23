package com.miku.ktv.miku_android.model.bean;

/**
 * Created by 焦帆 on 2017/9/27.
 */

public class LoginCheckBean {


  /**
   * body : {"qq_open_id":"","phone":"15010814396","forbid":false,"headline":"","token":"aWbD84FbmVMju7kftoj96davNpehxxBnc5efwLohr1PnErzIQgsdRp0qlUcuAvZw","sex":0,"nick":"哈哈","wx_open_id":"","create_time":"2017-09-26 20:18:03","last_login":null,"modify_time":"2017-10-09 10:26:51","online":true,"room_id":212,"fullname":"425048","password":"","id":167,"avatar":"/s/image/upload/150720274614img-5jyf.jpg"}
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
     * qq_open_id :
     * phone : 15010814396
     * forbid : false
     * headline :
     * token : aWbD84FbmVMju7kftoj96davNpehxxBnc5efwLohr1PnErzIQgsdRp0qlUcuAvZw
     * sex : 0
     * nick : 哈哈
     * wx_open_id :
     * create_time : 2017-09-26 20:18:03
     * last_login : null
     * modify_time : 2017-10-09 10:26:51
     * online : true
     * room_id : 212
     * fullname : 425048
     * password :
     * id : 167
     * avatar : /s/image/upload/150720274614img-5jyf.jpg
     */

    private String qq_open_id;
    private String phone;
    private boolean forbid;
    private String headline;
    private String token;
    private int sex;
    private String nick;
    private String wx_open_id;
    private String create_time;
    private Object last_login;
    private String modify_time;
    private boolean online;
    private int room_id;
    private String fullname;
    private String password;
    private int id;
    private String avatar;

    public String getQq_open_id() {
      return qq_open_id;
    }

    public void setQq_open_id(String qq_open_id) {
      this.qq_open_id = qq_open_id;
    }

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

    public String getToken() {
      return token;
    }

    public void setToken(String token) {
      this.token = token;
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

    public String getWx_open_id() {
      return wx_open_id;
    }

    public void setWx_open_id(String wx_open_id) {
      this.wx_open_id = wx_open_id;
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

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
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