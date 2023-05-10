package com.wuqisan.ppat.classroom.bean;

import com.wuqisan.ppat.base.bean.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author plusthree
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Classroom extends BaseBean {
    private Long classroomId;
    private String stage;
    private String categorizes;
    private Long teacherId;
    private String subjectId;
    private LocalDateTime effTime;
    private LocalDateTime expTime;
}
