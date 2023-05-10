package com.wuqisan.ppat.manage.bean;

import com.wuqisan.ppat.base.bean.BaseBean;
import com.wuqisan.ppat.classroom.bean.Classroom;
import com.wuqisan.ppat.classroom.bean.ClassroomPart;
import com.wuqisan.ppat.classroom.bean.Question;
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
public class User extends BaseBean {


	private Long userId;
	/**
	* 姓名
	*/
	private String name;

	/**
	* 班级
	*/
	private String classId;

	/**
	 * 种类
	 */
	private String type;

	/**
	* 用户名
	*/
	private String useraccount;

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


	private String idNumber;
	private ClassroomPart classroomPart;

	private Classroom classroom;


}