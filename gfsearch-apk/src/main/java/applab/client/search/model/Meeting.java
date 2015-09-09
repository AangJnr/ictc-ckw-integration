package applab.client.search.model;

import java.util.Date;

/**
 * Created by skwakwa on 8/30/15.
 */
public class Meeting {
    private String id;
    private String type;//G/I
    private String title;
    private Date scheduledDate;
    private Date meetingDate;
    private int attended;
    private int meetingIndex;
    private String farmer;
    private String remark;



    public Meeting(String id, String type, String title, Date scheduledDate, Date meetingDate, int attended, int meetingIndex, String farmer, String remark) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.scheduledDate = scheduledDate;
        this.meetingDate = meetingDate;
        this.attended = attended;
        this.meetingIndex = meetingIndex;
        this.farmer = farmer;
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public Date getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Date meetingDate) {
        this.meetingDate = meetingDate;
    }

    public int getAttended() {
        return attended;
    }

    public void setAttended(int attended) {
        this.attended = attended;
    }

    public int getMeetingIndex() {
        return meetingIndex;
    }

    public void setMeetingIndex(int meetingIndex) {
        this.meetingIndex = meetingIndex;
    }

    public String getFarmer() {
        return farmer;
    }

    public void setFarmer(String farmer) {
        this.farmer = farmer;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}