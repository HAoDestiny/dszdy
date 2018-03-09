package net.gzhqlf.dszdy.entity;

import javax.persistence.*;

/**
 * Created by DESTINY on 2017/10/24.
 */
@Entity
@Table(name = "file", schema = "dszdy", catalog = "")
public class FileEntity {
    private int id;
    private String fileUri;
    private Integer createTime;
    private Integer deleteTag;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "file_uri")
    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }

    @Basic
    @Column(name = "create_time")
    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "delete_tag")
    public Integer getDeleteTag() {
        return deleteTag;
    }

    public void setDeleteTag(Integer deleteTag) {
        this.deleteTag = deleteTag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileEntity that = (FileEntity) o;

        if (id != that.id) return false;
        if (fileUri != null ? !fileUri.equals(that.fileUri) : that.fileUri != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (deleteTag != null ? !deleteTag.equals(that.deleteTag) : that.deleteTag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (fileUri != null ? fileUri.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (deleteTag != null ? deleteTag.hashCode() : 0);
        return result;
    }
}
