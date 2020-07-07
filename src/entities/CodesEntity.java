package entities;

import javax.persistence.*;

@Entity
@Table(name = "CODES", schema = "PUBLIC", catalog = "DCDB")
public class CodesEntity {
    private String code;
    private Integer usagesLeft;
    private Double dc;
    private Integer dd;

    @Id
    @Column(name = "CODE", nullable = false, length = 100)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "USAGES_LEFT", nullable = true)
    public Integer getUsagesLeft() {
        return usagesLeft;
    }

    public void setUsagesLeft(Integer usagesLeft) {
        this.usagesLeft = usagesLeft;
    }

    @Basic
    @Column(name = "DC", nullable = true, precision = 0)
    public Double getDc() {
        return dc;
    }

    public void setDc(Double dc) {
        this.dc = dc;
    }

    @Basic
    @Column(name = "DD", nullable = true)
    public Integer getDd() {
        return dd;
    }

    public void setDd(Integer dd) {
        this.dd = dd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CodesEntity that = (CodesEntity) o;

        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (usagesLeft != null ? !usagesLeft.equals(that.usagesLeft) : that.usagesLeft != null) return false;
        if (dc != null ? !dc.equals(that.dc) : that.dc != null) return false;
        if (dd != null ? !dd.equals(that.dd) : that.dd != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (usagesLeft != null ? usagesLeft.hashCode() : 0);
        result = 31 * result + (dc != null ? dc.hashCode() : 0);
        result = 31 * result + (dd != null ? dd.hashCode() : 0);
        return result;
    }
}
