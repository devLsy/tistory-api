package com.lsy.tistoryapi.board.model;

import lombok.Data;

@Data
public class CommentsVo {
    
    private String commentsId;  //댓글작성자 아이디
    private String name;        //댓글작성자 닉네임
}
