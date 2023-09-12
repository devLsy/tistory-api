# [기획]
## api로 게시글 리스트를 조회한다.
## comments가 "0"이 아닌 것들의 id만 추출한다.
## 위에서 추출한 id를 api로 조회하되 parentId값이 없는 것들만 post-id, comments의 parentId, name을 가져와서 리스트에 담아둔다.
## 댓글 작성 vo에서 필요한 건 postId, comments[id(parentId), name]
## post로 댓글 작성 api를 호출한다.(위의 값들을 parameter로 넣어서)
## https://www.tistory.com/apis/comment/write?
##    access_token=토큰
## &output=json
## &blogName=yaga
## &postId={post-id} > 변수처리
## &parentId={parent-id} > 변수처리
## &content={name님 방문/댓글 감사합니다. 😃} 
## &secret=시크릿키