package com.project.wall.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.wall.po.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author MercerJR
 * @Data 2020/5/18 17:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WallResponse {

    private List wallList;

    private Set likeSet;

    private List hotList;

}
