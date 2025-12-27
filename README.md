# 개발 목록
DAY1 구현할 기능 목록
✅ 글 등록 - 이름, 제목, 암호, 본문 입력 - 등록일, ID 자동 생성
✅글 목록 보기 - 최신글 우선 표시 - ID, 제목, 이름, 등록일(YYYY/MM/DD) 표시
✅ 글 상세 조회 - 암호는 숨김 처리 - 등록일 상세 표시 (YYYY/MM/DD HH:mm)
✅ 글 수정 - 이름, 제목, 본문 수정 가능 - 암호 확인 필수 - 수정일 자동 저장
✅ 글 삭제 - 암호 확인 필수

DAY2 구현할 기능 목록
 로그인 - 이름, 제목, 암호, 본문 입력 - 등록일, ID 자동 생성
 로그아웃  - 최신글 우선 표시 - ID, 제목, 이름, 등록일(YYYY/MM/DD) 표시
 회원가입 - 암호는 숨김 처리 - 등록일 상세 표시 (YYYY/MM/DD HH:mm)
 회원 탈퇴 - 이름, 제목, 본문 수정 가능 - 암호 확인 필수 - 수정일 자동 저장

DAY3 구현할 기능 목록
배포


# DAY 1 - 게시판 기능 구현하기 
## 1. 글 등록 - 이름, 제목, 암호, 본문 입력 - 등록일, ID 자동 생성
1) Repository 인터페이스 = DB에 데이터를 집어넣는 역할
2) Service 클래스 = 사용자가 보낸 데이터를 받아서 등록일을 채워넣고 레포지토리에게 넘겨주는 역할
3) 
> 현재 상황 1 = GET: 단순히 return "문자열"을 하고 있어서 고정된 글자만 보임

### 게시판 목록 만들기
1) BoardController의 list() 메서드가 진짜 데이터 리스트를 반환하도록 수정해야 한다
2) 'public class BoardService {}' 을 통해 db와 연결되도록 한다
3) `test.http`에서 내용을 입력해줄 수 있다 
4) mysql을 사용하는 이유는 저장해주기 위함.
> 현재 상황 2 = 데이터 저장(Create)과 조회(Read) 가능한 상태가 되었음
> `[{"id":1,"name":"홍길동","title":"테스트","content":"내용","password":"1234","createTime":"2025-12-21T18:53:31.901459","updatedAt":null}]`

### html로 사용자가 보게 될 화면 만들기
1) 화면을 보여줄 컨트롤러 추가 (BoardViewController)
2) 기존에 만든 @RestController는 데이터(JSON)만 보내는 용도니까 일반 @Controller로 html을 보내준다
3) templates -> `list.html` 에서 HTML 만들기
> 버튼 활성화하기 = 사용자가 글쓰기 버튼을 누른다 -> 글쓰기 페이지 이동 -> 내용 작성 -> DB 저장

### 첫 번째 게시글 등록하기
1) 사용자가 버튼을 누르면 write.html 화면을 띄워줘야함 -> BoardViewController 역할 
2) list.html을 다음과 같이 설정하면 해당 버튼을 클릭했을때, 필요 페이지로 넘어감
    `<a href="/board/write" class="btn-write">글쓰기</a>`
> 문제 상황 발생 = 새로고침을 해도 넘어가지 않는다
> 원인은 수정된 파일이 실제 실행 중인 서버에 반영되지 않았기 때문
> 즉, 인텔리제이가 바뀐 HTML을 서버로 새로 보내주지 않고 있는 상황
Build -> Rebuild Project 해서 해결함
3) 글쓰기 전용 `wirte.html` 설정
#### 문제 상황 발생: `Ambiguous mapping` 에러
똑같은 주소(/board/write)로 처리하는 기능이 두 개를 써버려서 스프링이 누굴 써야 할지 몰라 다운되어버림
-> 하나 지우고 해결함

### 여기까지의 흐름 정리
글쓰기: 사용자가 write.html 화면에 글을 적습니다.
전송: '발행' 버튼을 누르면 데이터가 서버의 writeProcess 메서드로 날아갑니다.
저장: 서버가 그 데이터를 받아서 MySQL DB에 한 줄(Row)을 새로 만듭니다.
조회: 저장이 끝나면 서버가 다시 목록(list.html)으로 사용자를 보냅니다.
확인: 목록 페이지가 열릴 때 서버가 DB에서 최신 목록을 싹 긁어와서 화면에 뿌려줍니다.

## 2. 글 목록에 '상세 페이지' 링크 걸기
1) `list.html`에서 특정 글을 클릭하면 그 글의 상세 페이지로 이동하게 만들기
    주소에 id를 담아 보내야함
2) <a> 를 넣어서 클릭하면 넘어가게 함
    `(id=${board.id})`로 ID를 전달함
```
<div class="post-title">
    <a th:href="@{/board/view(id=${board.id})}" th:text="${board.title}" style="text-decoration: none; color: inherit;">
        게시글 제목
    </a>
</div>
```
3) BoardViewController에서 관련 매서드를 추가해준다
4) `view.html`만들기
`@RequestParam` 이란, 예를 들어 브라우저 주소 .../view?id=5에서 5라는 숫자를 자바 변수 Long id에 넣어줌
> 에러 발생 : 대/소문자 구별 = 자바에서 BoardRepository는 설계도 이름이고, boardRepository는 실제 일을 하는 일꾼(객체)임
>           데이터 타입 불일치 = id를 Long으로 통일함
5) 정리 = `list html` DB에 저장된 모든 데이터를 흝어봄 - 제목 클릭시 상세로 연결
        `write.html` DB에 새로운 데이터를 집어넣기 위한 빈 양식 페이지 - 새로운 글 데이터 입력받아서 post함
        `view.html` 특정 ID를 가진 글 하나만 자세히 읽는 페이지 - 수정/삭제 버튼이 포함됨
6) 삭제시에 list로 넘어가도록 설정 (BoardViewController <-> BoardService)

## 3. 수정 로직
기존 내용을 불러오는 화면 (GET) + 바뀐 내용을 저장하는 로직 (POST) 그리고 `edit.html` 설정
1. `edit.html`은 기존 내용이 입력창에 적혀있고, 글번호(id)를 hidden 하게 들고 있어야함


# DAY2 - 회원관리 기능 만들기
## 1. 회원 가입 기능
