package com.cargosmart.snakebox.entity;

import ch.qos.logback.core.status.Status;

import com.cargosmart.snakebox.utils.Datetime;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class News {

	private String key;
    private Status status = Status.received;
    private String heading;
    private String content;
    private String url;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishDt;
    private String timezone;
    private String sender;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date captureDt;
    private List<String> images = new ArrayList<String>();

    public String key() {
        return key = String.format("news:%s", status.name()+":"+
                Datetime.format(publishDt,"yyyyMMddHHmmss")+
                ":"+heading);
    }


//    @Override
//    public String toString() {
//        return "News{" +
//                ", heading='" + heading + '\'' +
//                ", content='" + content + '\'' +
//                ", url='" + url + '\'' +
//                ", publishDt=" + publishDt +
//                ", timezone='" + timezone + '\'' +
//                ", sender='" + sender + '\'' +
//                ", captureDt=" + captureDt +
//                '}';
//    }

    
    @Override
	public String toString() {
		return "News [key=" + key + ", status=" + status + ", heading="
				+ heading + ", content=" + content + ", url=" + url
				+ ", publishDt=" + publishDt + ", timezone=" + timezone
				+ ", sender=" + sender + ", captureDt=" + captureDt
				+ ", images=" + images + "]" + "/r/n";
	}


	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}


	public String getHeading() {
		return heading;
	}


	public void setHeading(String heading) {
		this.heading = heading;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public Date getPublishDt() {
		return publishDt;
	}


	public void setPublishDt(Date publishDt) {
		this.publishDt = publishDt;
	}


	public String getTimezone() {
		return timezone;
	}


	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}


	public String getSender() {
		return sender;
	}


	public void setSender(String sender) {
		this.sender = sender;
	}


	public Date getCaptureDt() {
		return captureDt;
	}


	public void setCaptureDt(Date captureDt) {
		this.captureDt = captureDt;
	}


	public List<String> getImages() {
		return images;
	}


	public void setImages(List<String> images) {
		this.images = images;
	}


	public enum Status{
        received, handled, archived
    }

}
