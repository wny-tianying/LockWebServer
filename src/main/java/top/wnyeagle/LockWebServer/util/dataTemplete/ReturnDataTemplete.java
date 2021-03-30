package top.wnyeagle.LockWebServer.util.dataTemplete;

/**
 * @Description
 * @ClassName ReturnDataTemplete
 * @Author wny
 * @Date 2021/3/6 16:52
 * @Version 1.0
 */

public class ReturnDataTemplete {
//    是否发生错误
    private Boolean happenError;
//    是否跳转
    private Boolean isSkip;
//    跳转的路径
    private String skipUrl;
//    接收的数据
    private String data;

    public ReturnDataTemplete() {
    }

    public ReturnDataTemplete(Boolean happenError, Boolean isSkip, String skipUrl, String data) {
        this.happenError = happenError;
        this.isSkip = isSkip;
        this.skipUrl = skipUrl;
        this.data = data;
    }

    public Boolean getHappenError() {
        return happenError;
    }

    public void setHappenError(Boolean happenError) {
        this.happenError = happenError;
    }

    public Boolean getSkip() {
        return isSkip;
    }

    public void setSkip(Boolean skip) {
        isSkip = skip;
    }

    public String getSkipUrl() {
        return skipUrl;
    }

    public void setSkipUrl(String skipUrl) {
        this.skipUrl = skipUrl;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ReturnDataTemplete{" +
                "happenError=" + happenError +
                ", isSkip=" + isSkip +
                ", skipUrl='" + skipUrl + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
