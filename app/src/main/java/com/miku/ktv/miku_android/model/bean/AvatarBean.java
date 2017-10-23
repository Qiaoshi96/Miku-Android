package com.miku.ktv.miku_android.model.bean;

/**
 * Created by 焦帆 on 2017/10/5.
 */

public class AvatarBean {

  /**
   * body : {"phone":"15010814396","nick":"123","create_time":1506477934,"avatar":"/s/image/upload/150719948972Ezreal_Splash_8.jpg","fullname":"425050","id":169}
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
     * phone : 15010814396
     * nick : 123
     * create_time : 1506477934
     * avatar : /s/image/upload/150719948972Ezreal_Splash_8.jpg
     * fullname : 425050
     * id : 169
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