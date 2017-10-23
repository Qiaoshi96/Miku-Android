package com.miku.ktv.miku_android.model.bean;

/**
 * Created by 焦帆 on 2017/9/27.
 */

public class LoginCodeBean {

  /**
   * body : {}
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
  }
}