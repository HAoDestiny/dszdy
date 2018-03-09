package net.gzhqlf.dszdy.entity;

import javax.persistence.*;

/**
 * Created by Destiny_hao on 2017/11/22.
 */
@Entity
@Table(name = "admin_nav", schema = "dszdy", catalog = "")
public class AdminNavEntity {

    private int id;
    private String title;
    private String icon;
    private String href;
    private boolean spread;
    private Integer pro;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title", nullable = true, length = 255)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "icon", nullable = true, length = 255)
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Basic
    @Column(name = "href", nullable = true, length = 255)
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Basic
    @Column(name = "spread", nullable = true)
    public boolean isSpread() {
        return spread;
    }

    public void setSpread(boolean spread) {
        this.spread = spread;
    }

    @Basic
    @Column(name = "pro", nullable = true)
    public Integer getPro() {
        return pro;
    }

    public void setPro(Integer pro) {
        this.pro = pro;
    }

}
