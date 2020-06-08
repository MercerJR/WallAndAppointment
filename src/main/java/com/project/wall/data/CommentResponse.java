package com.project.wall.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author MercerJR
 * @Data 2020/6/8 10:08
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse<T,P> {
    private T comment;

    private List<P> replyList;
}
