package com.lsy.tistoryapi.board.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
//게시글 작성용 Vo
public class WriteCmmentVo {

    private String postId;
    private String parentId;
    private String content;
}
