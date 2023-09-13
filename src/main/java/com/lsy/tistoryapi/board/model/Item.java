package com.lsy.tistoryapi.board.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {

    private String url;
    private String secondaryUrl;
    private String page;
    private String count;
    private String totalCount;

    List<PostVo> posts = new ArrayList<>();

}
