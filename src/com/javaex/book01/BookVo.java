package com.javaex.book01;

public class BookVo extends AuthorVo { 
	// book이 author의 author_id를 참조하니까 author 상속하기 --> 문법은 맞지만 책을 작가라고 부를 수는 없음
	// 분류상 맞지 않아서 작가에 여러 개 상속할 경우 이상해짐. 문법뿐만 아니라 의미상으로도 맞아야 됨.
	// 그냥 필드에 모든 컬럼명 넣으면 됨. (수정하고 검색 만들기. 검색은 오라클에서 처리 가능)

	// 필드 (접근제한자 주의. private 아니면 g/s 쓸 필요가 없어짐.)
	private int bookId;
	private String title;
	private String pubs;
	private String pubDate;
	private int authorId;

	// 생성자
	public BookVo() {
	}

	public BookVo(int bookId, String title, String pubs, String pubDate, int authorId, String authorName, String authorDesc) {
		super.authorName = authorName;
		super.authorDesc = authorDesc;
		this.bookId = bookId;
		this.title = title;
		this.pubs = pubs;
		this.pubDate = pubDate;
		this.authorId = authorId;
	}

	public BookVo(String title, String pubs, String pubDate, int authorId) {
		this.title = title;
		this.pubs = pubs;
		this.pubDate = pubDate;
		this.authorId = authorId;
	}
	
	public BookVo(int bookId, String title, String pubs, String pubDate, int authorId) {
		this.bookId = bookId;
		this.title = title;
		this.pubs = pubs;
		this.pubDate = pubDate;
		this.authorId = authorId;
	}

	// 메소드 겟셋
	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPubs() {
		return pubs;
	}

	public void setPubs(String pubs) {
		this.pubs = pubs;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	// 메소드 일반
	@Override
	public String toString() {
		return "BookVo [bookId=" + bookId + ", title=" + title + ", pubs=" + pubs + ", pubDate=" + pubDate
				+ ", authorId=" + authorId + "]";
	}

}
