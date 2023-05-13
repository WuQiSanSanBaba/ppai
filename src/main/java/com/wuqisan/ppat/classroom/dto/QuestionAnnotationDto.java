package com.wuqisan.ppat.classroom.dto;

import com.wuqisan.ppat.classroom.bean.AnnotationBatch;
import com.wuqisan.ppat.classroom.bean.Question;
import lombok.Data;

import java.util.List;

@Data
public class QuestionAnnotationDto {
    private Question question;
    private List<AnnotationBatch> annotationBatches;
}
