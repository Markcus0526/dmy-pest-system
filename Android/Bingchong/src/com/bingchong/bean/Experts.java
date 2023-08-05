package com.bingchong.bean;

import com.bingchong.utils.QuickSetParcelableUtil;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description 专家实体类
 * @author 金铁钢
 *
 */
public class Experts implements Parcelable{
	
	private int id;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * anyChat返回的ID
	 */
	private int anyChatId;
	/**
	 * 密码
	 */
	private String userPassword;
	/**
	 * 专家头像
	 */
	private String expertImg;
	/**
	 * 专家姓名
	 */
	private String expertName;
	/**
	 * 性别
	 */
	private String expertSex;
	/**
	 * 单位
	 */
	private String expertUnit;
	/**
	 * 专家简介
	 */
	private String expertDescription;
	/**
	 * 在线状态
	 */
	private int expertOnlineState;
	
	/**
	 * 专家类别ID
	 */
	private int expertTypeId;
	
	/**
	 * 专家类别
	 */
	private String expertTypeName;
	
	/**
	 * 所在地区ID
	 */
	private int expertRegionId;
	
	/**
	 * 所在地区名称
	 */
	private String expertRegionName;
	
	private String phoneNum = "";
	
	private String birthday;
	private String address;
	private int departmentId;
	private String mail;
	private String wangpan;
	private String description;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getExpertName() {
		return expertName;
	}
	public void setExpertName(String expertName) {
		this.expertName = expertName;
	}
	public String getExpertSex() {
		return expertSex;
	}
	public void setExpertSex(String expertSex) {
		this.expertSex = expertSex;
	}
	public String getExpertUnit() {
		return expertUnit;
	}
	public void setExpertUnit(String expertUnit) {
		this.expertUnit = expertUnit;
	}
	public String getExpertDescription() {
		return expertDescription;
	}
	public void setExpertDescription(String expertDescription) {
		this.expertDescription = expertDescription;
	}
	
	public String getExpertImg() {
		return expertImg;
	}
	public void setExpertImg(String expertImg) {
		this.expertImg = expertImg;
	}
	public int getAnyChatId() {
		return anyChatId;
	}
	public void setAnyChatId(int anyChatId) {
		this.anyChatId = anyChatId;
	}
	public int getExpertOnlineState() {
		return expertOnlineState;
	}
	public void setExpertOnlineState(int expertOnlineState) {
		this.expertOnlineState = expertOnlineState;
	}
	public int getExpertTypeId() {
		return expertTypeId;
	}
	public void setExpertTypeId(int expertTypeId) {
		this.expertTypeId = expertTypeId;
	}
	public String getExpertTypeName() {
		return expertTypeName;
	}
	public void setExpertTypeName(String expertTypeName) {
		this.expertTypeName = expertTypeName;
	}
	public int getExpertRegionId() {
		return expertRegionId;
	}
	public void setExpertRegionId(int expertRegionId) {
		this.expertRegionId = expertRegionId;
	}
	public String getExpertRegionName() {
		return expertRegionName;
	}
	public void setExpertRegionName(String expertRegionName) {
		this.expertRegionName = expertRegionName;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getWangpan() {
		return wangpan;
	}
	public void setWangpan(String wangpan) {
		this.wangpan = wangpan;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		QuickSetParcelableUtil.write(dest, this);
	}
	
	public static final Parcelable.Creator<Experts> CREATOR = new Creator<Experts>() {

		@Override
		public Experts createFromParcel(Parcel source) {
			Experts weibo = (Experts) QuickSetParcelableUtil.read(source,
					Experts.class);
			return weibo;
		}

		@Override
		public Experts[] newArray(int size) {
			return new Experts[size];
		}

	};
	
}
