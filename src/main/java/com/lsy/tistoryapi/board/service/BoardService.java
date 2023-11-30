package com.lsy.tistoryapi.board.service;

import com.lsy.tistoryapi.board.model.CommentsVo;
import com.lsy.tistoryapi.board.model.PostResponse;
import com.lsy.tistoryapi.board.model.PostVo;
import com.lsy.tistoryapi.board.model.WriteCmmentVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    @Value("${open-api.app-id}")
    private String appId;

    @Value("${open-api.secret-key}")
    private String secretKey;

    @Value("${open-api.access-token}")
    private String accessToken;

    @Value("${open-api.blog-name}")
    private String blogName;

    private String apiUrl = "https://www.tistory.com/apis/";

    private final RestTemplate restTemplate;

    /**
     * 게시글 조회
     * @return
     */
    public ResponseEntity getPosts() {
        //resultMap
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            HttpEntity<String> entity = new HttpEntity<>(headers);
            //게시글 조회
            URI requestURI = new URI(apiUrl + "post/list?" + "access_token=" + accessToken + "&blogName=" + blogName + "&output=json" + "&page=1");
            
            ResponseEntity<PostResponse> responseEntity = restTemplate.exchange(
                               requestURI,
                               HttpMethod.GET,
                               entity,
                               PostResponse.class
                       );

            PostResponse postResponse = responseEntity.getBody();
            List<PostVo> posts = postResponse.getTistory().getItem().getPosts();

            List<WriteCmmentVo> topLevelComments = new ArrayList<>();

            if (posts != null) {
                //댓글이 있는 게시글 아이디만 추출
                List<String> commentedPostIds = getCommentedPostsId(posts);

                //댓글 존재 게시글 목록 개수만큼 루프 돌며 댓글 작성
                for (String commentedPostId : commentedPostIds) {
                    topLevelComments = getTopLevelComments(commentedPostId);
                }

                resultMap.put("topLevelComments", topLevelComments);
                resultMap.put("topLevelCommentsCount", topLevelComments.size());
                resultMap.put("code", HttpStatus.OK);
            }

        } catch (URISyntaxException ue) {
            resultMap.put("code", HttpStatus.BAD_REQUEST);
            log.info("URISyntaxException = []", ue.getMessage());
        }

        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    /**
     * 댓글이 있는 게시글 아이디 목록 추출
     * @param posts
     * @return
     */
    public List<String> getCommentedPostsId(List<PostVo> posts) {

        List<String> commentedPostIds = new ArrayList<>();

        for (PostVo post : posts) {

            if (!"0".equals(post.getComments())) {
                commentedPostIds.add(post.getId());
            }
        }
        return commentedPostIds;
    }

    /**
     * 댓글 목록 중 대댓글이 아닌 목록 추출(작성자가 답변 달지 않는 경우)
     * @param commentedPostIds
     * @return
     */
    public List<WriteCmmentVo> getTopLevelComments(String commentedPostIds) {

        List<WriteCmmentVo> writeCmmentVos = new ArrayList<>();

        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    
            HttpEntity<String> entity = new HttpEntity<>(headers);

            URI requestURI = new URI(apiUrl + "comment/list?" + "access_token=" + accessToken + "&blogName=" + blogName + "&output=json" + "&postId=" + commentedPostIds);

            ResponseEntity<PostResponse> responseEntity = restTemplate.exchange(
                               requestURI,
                               HttpMethod.GET,
                               entity,
                               PostResponse.class
                       );

            PostResponse postResponse = responseEntity.getBody();
            List<CommentsVo> comments = postResponse.getTistory().getItem().getComments();

            if (comments != null) {
                for (CommentsVo comment : comments) {
                    // parentId가 비어 있지 않은 경우를 필터링
                    if (comment.getParentId() != null && !comment.getParentId().isEmpty() && !"".equals(comment.getParentId())) {
                        continue;
                    }
                    WriteCmmentVo writeCmmentVo = new WriteCmmentVo();
                    writeCmmentVo.setPostId(commentedPostIds);
                    writeCmmentVo.setParentId(comment.getId());
                    writeCmmentVo.setContent(comment.getName() + "님 방문/댓글 감사합니다. 🙂");
                    writeCmmentVos.add(writeCmmentVo);
                }
            }

        } catch (URISyntaxException ue) {
            log.info("URISyntaxException = []", ue.getMessage());
        }
        
        return writeCmmentVos;
    }


}
