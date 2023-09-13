package com.lsy.tistoryapi.board.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostVo {

    private String id;       //포스트 아이디
    private String title;   //포스트 제목
    private String postUrl; //포스트 url
    private String visibility;  //글 공개 단계 (0: 비공개, 15: 보호, 20: 발행)
    private String categoryId;  //카테고리 ID
    private String comments;    //댓글 수
    private String trackbacks;  //트랙백 수
    private String date;        //날짜
}
