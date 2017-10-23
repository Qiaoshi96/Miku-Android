package com.miku.ktv.miku_android.model.bean;

/**
 * Created by 焦帆 on 2017/10/9.
 */

public class NickBean {

  /**
   * body : {"nick":"111","fullname":"222","id":182,"phone":"18810979594"}
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
     * nick : 111
     * fullname : 222
     * id : 182
     * phone : 18810979594
     */

    private String nick;
    private String fullname;
    private int id;
    private String phone;

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

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getPhone() {
      return phone;
    }

    public void setPhone(String phone) {
      this.phone = phone;
    }
  }
}