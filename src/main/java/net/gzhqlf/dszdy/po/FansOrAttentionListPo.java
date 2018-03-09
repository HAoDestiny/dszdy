package net.gzhqlf.dszdy.po;

/**
 * Created by Destiny_hao on 2017/11/3.
 */
public class FansOrAttentionListPo {

    private Object fansOrAttentionPo;
    private int pageSize;
    private int page;
    private int pageTotal;

    public Object getFansOrAttentionPo() {
        return fansOrAttentionPo;
    }

    public void setFansOrAttentionPo(Object fansOrAttentionPo) {
        this.fansOrAttentionPo = fansOrAttentionPo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }
}
