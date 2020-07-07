package entities;

import javax.persistence.*;

@Entity
@Table(name = "ACCOUNTS", schema = "PUBLIC", catalog = "DCDB")
public class AccountsEntity {
    private String address;
    private String secret;
    private String id;
    private Long lastLogin;
    private Long accountLastUpdate;
    private Double dc;
    private Integer dd;
    private Integer record;
    private Integer ap;
    private Integer op;
    private Double killMin;
    private Double killMax;
    private Double hackMin;
    private Double hackMax;
    private Double hackBounty;
    private Double opBounty;
    private Integer lotteryTickets;
    private Long historyLastUpdate;
    private Long mailLastUpdate;
    private String history;
    private String mail;

    @Id
    @Column(name = "ADDRESS", nullable = false, length = 100)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "SECRET", nullable = true, length = 100)
    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Basic
    @Column(name = "ID", nullable = true, length = 100)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "LAST_LOGIN", nullable = true)
    public Long getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Long lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Basic
    @Column(name = "ACCOUNT_LAST_UPDATE", nullable = true)
    public Long getAccountLastUpdate() {
        return accountLastUpdate;
    }

    public void setAccountLastUpdate(Long accountLastUpdate) {
        this.accountLastUpdate = accountLastUpdate;
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

    @Basic
    @Column(name = "RECORD", nullable = true)
    public Integer getRecord() {
        return record;
    }

    public void setRecord(Integer record) {
        this.record = record;
    }

    @Basic
    @Column(name = "AP", nullable = true)
    public Integer getAp() {
        return ap;
    }

    public void setAp(Integer ap) {
        this.ap = ap;
    }

    @Basic
    @Column(name = "OP", nullable = true)
    public Integer getOp() {
        return op;
    }

    public void setOp(Integer op) {
        this.op = op;
    }

    @Basic
    @Column(name = "KILL_MIN", nullable = true, precision = 0)
    public Double getKillMin() {
        return killMin;
    }

    public void setKillMin(Double killMin) {
        this.killMin = killMin;
    }

    @Basic
    @Column(name = "KILL_MAX", nullable = true, precision = 0)
    public Double getKillMax() {
        return killMax;
    }

    public void setKillMax(Double killMax) {
        this.killMax = killMax;
    }

    @Basic
    @Column(name = "HACK_MIN", nullable = true, precision = 0)
    public Double getHackMin() {
        return hackMin;
    }

    public void setHackMin(Double hackMin) {
        this.hackMin = hackMin;
    }

    @Basic
    @Column(name = "HACK_MAX", nullable = true, precision = 0)
    public Double getHackMax() {
        return hackMax;
    }

    public void setHackMax(Double hackMax) {
        this.hackMax = hackMax;
    }

    @Basic
    @Column(name = "HACK_BOUNTY", nullable = true, precision = 0)
    public Double getHackBounty() {
        return hackBounty;
    }

    public void setHackBounty(Double hackBounty) {
        this.hackBounty = hackBounty;
    }

    @Basic
    @Column(name = "OP_BOUNTY", nullable = true, precision = 0)
    public Double getOpBounty() {
        return opBounty;
    }

    public void setOpBounty(Double opBounty) {
        this.opBounty = opBounty;
    }

    @Basic
    @Column(name = "LOTTERY_TICKETS", nullable = true)
    public Integer getLotteryTickets() {
        return lotteryTickets;
    }

    public void setLotteryTickets(Integer lotteryTickets) {
        this.lotteryTickets = lotteryTickets;
    }

    @Basic
    @Column(name = "HISTORY_LAST_UPDATE", nullable = true)
    public Long getHistoryLastUpdate() {
        return historyLastUpdate;
    }

    public void setHistoryLastUpdate(Long historyLastUpdate) {
        this.historyLastUpdate = historyLastUpdate;
    }

    @Basic
    @Column(name = "MAIL_LAST_UPDATE", nullable = true)
    public Long getMailLastUpdate() {
        return mailLastUpdate;
    }

    public void setMailLastUpdate(Long mailLastUpdate) {
        this.mailLastUpdate = mailLastUpdate;
    }

    @Basic
    @Column(name = "HISTORY", nullable = true, length = 10000)
    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    @Basic
    @Column(name = "MAIL", nullable = true, length = 10000)
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountsEntity that = (AccountsEntity) o;

        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (secret != null ? !secret.equals(that.secret) : that.secret != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (lastLogin != null ? !lastLogin.equals(that.lastLogin) : that.lastLogin != null) return false;
        if (accountLastUpdate != null ? !accountLastUpdate.equals(that.accountLastUpdate) : that.accountLastUpdate != null)
            return false;
        if (dc != null ? !dc.equals(that.dc) : that.dc != null) return false;
        if (dd != null ? !dd.equals(that.dd) : that.dd != null) return false;
        if (record != null ? !record.equals(that.record) : that.record != null) return false;
        if (ap != null ? !ap.equals(that.ap) : that.ap != null) return false;
        if (op != null ? !op.equals(that.op) : that.op != null) return false;
        if (killMin != null ? !killMin.equals(that.killMin) : that.killMin != null) return false;
        if (killMax != null ? !killMax.equals(that.killMax) : that.killMax != null) return false;
        if (hackMin != null ? !hackMin.equals(that.hackMin) : that.hackMin != null) return false;
        if (hackMax != null ? !hackMax.equals(that.hackMax) : that.hackMax != null) return false;
        if (hackBounty != null ? !hackBounty.equals(that.hackBounty) : that.hackBounty != null) return false;
        if (opBounty != null ? !opBounty.equals(that.opBounty) : that.opBounty != null) return false;
        if (lotteryTickets != null ? !lotteryTickets.equals(that.lotteryTickets) : that.lotteryTickets != null)
            return false;
        if (historyLastUpdate != null ? !historyLastUpdate.equals(that.historyLastUpdate) : that.historyLastUpdate != null)
            return false;
        if (mailLastUpdate != null ? !mailLastUpdate.equals(that.mailLastUpdate) : that.mailLastUpdate != null)
            return false;
        if (history != null ? !history.equals(that.history) : that.history != null) return false;
        if (mail != null ? !mail.equals(that.mail) : that.mail != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = address != null ? address.hashCode() : 0;
        result = 31 * result + (secret != null ? secret.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (lastLogin != null ? lastLogin.hashCode() : 0);
        result = 31 * result + (accountLastUpdate != null ? accountLastUpdate.hashCode() : 0);
        result = 31 * result + (dc != null ? dc.hashCode() : 0);
        result = 31 * result + (dd != null ? dd.hashCode() : 0);
        result = 31 * result + (record != null ? record.hashCode() : 0);
        result = 31 * result + (ap != null ? ap.hashCode() : 0);
        result = 31 * result + (op != null ? op.hashCode() : 0);
        result = 31 * result + (killMin != null ? killMin.hashCode() : 0);
        result = 31 * result + (killMax != null ? killMax.hashCode() : 0);
        result = 31 * result + (hackMin != null ? hackMin.hashCode() : 0);
        result = 31 * result + (hackMax != null ? hackMax.hashCode() : 0);
        result = 31 * result + (hackBounty != null ? hackBounty.hashCode() : 0);
        result = 31 * result + (opBounty != null ? opBounty.hashCode() : 0);
        result = 31 * result + (lotteryTickets != null ? lotteryTickets.hashCode() : 0);
        result = 31 * result + (historyLastUpdate != null ? historyLastUpdate.hashCode() : 0);
        result = 31 * result + (mailLastUpdate != null ? mailLastUpdate.hashCode() : 0);
        result = 31 * result + (history != null ? history.hashCode() : 0);
        result = 31 * result + (mail != null ? mail.hashCode() : 0);
        return result;
    }
}
