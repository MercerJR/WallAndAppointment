package com.project.wall.po;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author MercerJR
 * @Data 2020/6/13 12:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotWall implements Serializable {
    private String wallId;

    private String hotImg;

    private static final long serialVersionUID = 1L;
}