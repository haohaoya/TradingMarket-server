package top.hhduan.market.bo;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/12/25 14:28
 * @description: 基础入参
 * @version: 1.0
 */
public class BaseBO implements Serializable {

    private static final long serialVersionUID = -5146126799389106360L;
    /** 用户令牌 */
    private String token;

    /** 当前页 从1开始 */
    private Integer pageNum;

    /** 页大小 */
    private Integer pageSize;



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getPageNum() {
        if (this.pageNum == null) {
            this.pageNum = 1;
        }
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        if (this.pageSize == null) {
            this.pageSize = 10;
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "BaseBO{" +
                "token='" + token + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
