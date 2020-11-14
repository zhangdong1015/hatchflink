package com.dz.entity;

/**
 * @ClassName Record
 * @Description TODO
 * @Author zhangdong
 * @Date 2020/11/3 13:29
 * @Version 1.0
 */
public class Record
{
    private String uuid;
    private String opType;
    private long  producerTime;
    private String content;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public long getProducerTime() {
        return producerTime;
    }

    public void setProducerTime(long producerTime) {
        this.producerTime = producerTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Record(String uuid, String opType, long producerTime, String content) {
        this.uuid = uuid;
        this.opType = opType;
        this.producerTime = producerTime;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Record{" +
                "uuid='" + uuid + '\'' +
                ", opType='" + opType + '\'' +
                ", producerTime=" + producerTime +
                ", content='" + content + '\'' +
                '}';
    }
}
