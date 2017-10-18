package com.miku.ktv.miku_android.model.bean;

/**
 * Created by 焦帆 on 2017/10/18.
 */

public class DeleteBean {

    /**
     * body : {"page_obj":{}}
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
         */

        private PageObjBean page_obj;

        public PageObjBean getPage_obj() {
            return page_obj;
        }

        public void setPage_obj(PageObjBean page_obj) {
            this.page_obj = page_obj;
        }

        public static class PageObjBean {
        }
    }
}
