package com.project.wall.data;

import com.project.wall.po.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author MercerJR
 * @Data 2020/6/3 19:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OneAppointmentResponse {

    private Appointment appointment;

    private List<CommentResponse<AComment, ACommentReply>> commentResponseList;

    private Integer joinCount;

    private boolean join;

}
