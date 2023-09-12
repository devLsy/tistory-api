package com.lsy.tistoryapi.board.service;

import com.lsy.tistoryapi.board.model.PostVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 게시글 조회
     * @return
     */
    public ResponseEntity getPosts() {
        //resultMap
        Map<String, Object> resultMap = new HashMap<String, Object>();
        RestTemplate restTemplate;

        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setAcceptCharset(Collections.singletonList(Charset.forName("UTF-8")));

            URI requestURI = new URI(apiUrl + "post/list?" + "access_token=" + accessToken + "&blogName=" + blogName + "&output=json");
            restTemplate = new RestTemplate();

            ResponseEntity<String> response = restTemplate.getForEntity(requestURI, String.class);
            String responseBody = response.getBody();
            String decodedResponse = new String(responseBody.getBytes("ISO-8859-1"), "UTF-8");

            log.info("status = [{}]", response.getStatusCode());
            log.info("response = [{}]", response.getBody());
            resultMap.put("code", response.getStatusCode());
            resultMap.put("list", decodedResponse);

        } catch (URISyntaxException | UnsupportedEncodingException ue) {
            resultMap.put("code", HttpStatus.BAD_REQUEST);
            log.info("URISyntaxException = []", ue.getMessage());
        }

        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
}
