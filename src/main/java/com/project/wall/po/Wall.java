package com.project.wall.po;

import java.io.Serializable;

import com.project.wall.data.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @Author MercerJR
 * @Data 2020/5/15 12:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wall implements Serializable {
    private String wallId;

    private String accountId;

    private String title;

    private String content;

    private String img;

    private String label;

    private Long gmtCreat;

    private Long gmtModified;

    private static final long serialVersionUID = 1L;
}