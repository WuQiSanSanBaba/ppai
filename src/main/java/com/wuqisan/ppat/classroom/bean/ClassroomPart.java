package com.wuqisan.ppat.classroom.bean;

import com.wuqisan.ppat.base.bean.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class ClassroomPart extends BaseBean {
    private Long partId;
    private Long groupId;
    private Long classroomId;
    private Long userId;
    private String userName;
    private String role;
    private Long subjectId;
    private String subjectName;
    private LocalDateTime effTime;
    private LocalDateTime expTime;
    private Long questionId;
    private String stage;
}
