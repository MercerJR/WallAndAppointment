package com.project.wall.data;

import com.project.wall.po.WComment;
import com.project.wall.po.WCommentReply;
import com.project.wall.po.Wall;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author MercerJR
 * @Data 2020/6/3 19:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OneWallResponse {

    private Wall wall;

    private List<CommentResponse<WComment, WCommentReply>> commentResponseList;

    private Integer likeCount;

    private boolean like;

}
