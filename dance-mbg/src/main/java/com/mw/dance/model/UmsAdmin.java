package com.mw.dance.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class UmsAdmin implements Serializable {
  private Long id;

  private String username;

  private String password;

  @ApiModelProperty(value = "头像")
  private String avatar;

  @ApiModelProperty(value = "邮箱")
  private String email;

  @ApiModelProperty(value = "昵称")
  private String nickname;

  @ApiModelProperty(value = "创建时间")
  private Date createTime;

  @ApiModelProperty(value = "最后登录时间")
  private Date loginTime;

  @ApiModelProperty(value = "帐号启用状态：0->禁用；1->启用'")
  private Integer status;

  @ApiModelProperty(value = "手机号码")
  private String telephone;

  private static final long serialVersionUID = 1L;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getLoginTime() {
    return loginTime;
  }

  public void setLoginTime(Date loginTime) {
    this.loginTime = loginTime;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getClass().getSimpleName());
    sb.append(" [");
    sb.append("Hash = ").append(hashCode());
    sb.append(", id=").append(id);
    sb.append(", username=").append(username);
    sb.append(", password=").append(password);
    sb.append(", avatar=").append(avatar);
    sb.append(", email=").append(email);
    sb.append(", nickname=").append(nickname);
    sb.append(", createTime=").append(createTime);
    sb.append(", loginTime=").append(loginTime);
    sb.append(", status=").append(status);
    sb.append(", telephone=").append(telephone);
    sb.append(", serialVersionUID=").append(serialVersionUID);
    sb.append("]");
    return sb.toString();
  }
}