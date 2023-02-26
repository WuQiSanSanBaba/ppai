package com.wuqisan.ppai.operator.user.bean;

import com.wuqisan.ppai.base.bean.BaseBean;
import lombok.Data;

import lombok.EqualsAndHashCode;
/**
 * 操作员信息
 *
 * @author 573 931441227@qq.com
 * @since 1.0.0 2023-02-22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserInfo extends BaseBean {


	private Long id;
	/**
	* 姓名
	*/
	private String name;

	/**
	* 班级
	*/
	private String classId;

	/**
	* 用户名
	*/
	private String username;

	/**
	* 密码
	*/
	private String password;

	/**
	* 手机号
	*/
	private String phone;

	/**
	* 性别
	*/
	private String sex;

	/**
	* 身份证号
	*/
	private String idNumber;


}