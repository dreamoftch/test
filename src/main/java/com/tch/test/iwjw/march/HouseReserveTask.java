package com.tch.test.iwjw.march;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class HouseReserveTask {
    private Long id;

    private Date createTime;

    private Date updateTime;

    private Long creatorId;

    private Integer status;

    private Long operatorId;

    private Long houseId;

    private Long publishId;

    private Long cityId;

    private Long districtId;

    private Long townId;

    private Long estateId;

    private Date publishTime;

    private Date followTime;

    private Integer publishSource;

    private Date makeTimeStart;

    private Date makeTimeEnd;

    private Date shootStartTime;

    private Date shootFinishTime;

    private Integer shootStatus;

    private Long shootOperatorId;

    private Long lastFailedTaskId;

    private String memo;

    private String shootMemo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public Long getPublishId() {
        return publishId;
    }

    public void setPublishId(Long publishId) {
        this.publishId = publishId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public Long getTownId() {
        return townId;
    }

    public void setTownId(Long townId) {
        this.townId = townId;
    }

    public Long getEstateId() {
        return estateId;
    }

    public void setEstateId(Long estateId) {
        this.estateId = estateId;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Date getFollowTime() {
        return followTime;
    }

    public void setFollowTime(Date followTime) {
        this.followTime = followTime;
    }

    public Integer getPublishSource() {
        return publishSource;
    }

    public void setPublishSource(Integer publishSource) {
        this.publishSource = publishSource;
    }

    public Date getMakeTimeStart() {
        return makeTimeStart;
    }

    public void setMakeTimeStart(Date makeTimeStart) {
        this.makeTimeStart = makeTimeStart;
    }

    public Date getMakeTimeEnd() {
        return makeTimeEnd;
    }

    public void setMakeTimeEnd(Date makeTimeEnd) {
        this.makeTimeEnd = makeTimeEnd;
    }

    public Date getShootStartTime() {
        return shootStartTime;
    }

    public void setShootStartTime(Date shootStartTime) {
        this.shootStartTime = shootStartTime;
    }

    public Date getShootFinishTime() {
        return shootFinishTime;
    }

    public void setShootFinishTime(Date shootFinishTime) {
        this.shootFinishTime = shootFinishTime;
    }

    public Integer getShootStatus() {
        return shootStatus;
    }

    public void setShootStatus(Integer shootStatus) {
        this.shootStatus = shootStatus;
    }

    public Long getShootOperatorId() {
        return shootOperatorId;
    }

    public void setShootOperatorId(Long shootOperatorId) {
        this.shootOperatorId = shootOperatorId;
    }

    public Long getLastFailedTaskId() {
        return lastFailedTaskId;
    }

    public void setLastFailedTaskId(Long lastFailedTaskId) {
        this.lastFailedTaskId = lastFailedTaskId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public String getShootMemo() {
        return shootMemo;
    }

    public void setShootMemo(String shootMemo) {
        this.shootMemo = shootMemo == null ? null : shootMemo.trim();
    }
}