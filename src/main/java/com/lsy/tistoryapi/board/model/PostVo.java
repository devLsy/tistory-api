package com.lsy.tistoryapi.board.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostVo {

    private String postId;  //포스트 아이디
    private String title;   //제목
    private String postUrl; //포스트 링크
    //댓글 리스트
    List<CommentsVo> commentsList = new ArrayList<>();
}
