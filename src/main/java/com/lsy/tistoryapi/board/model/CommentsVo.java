package com.lsy.tistoryapi.board.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentsVo {

    private String id;  //댓글아이디
    private String date;
    private String name;
    private String parentId;
    private String homepage;
    private String visibility;
    private String comment;
    private String open;
}
