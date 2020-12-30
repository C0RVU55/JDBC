package com.javaex.book01;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookAuthorApp {

	public static void main(String[] args) {

		// book DAO 만들기 + 검색(추가하기)

		// 작가 6명 등록
		// AuthorDao, AuthorVo 이용해서 등록
		// 수정, 삭제 리스트
		// 작가 등록

		AuthorDao authorDao = new AuthorDao(); // 기능 클래스 먼저 new
		List<AuthorVo> authorList;
		// 리스트 선언. 처음에 오류났는데 author01패키지의 authorList가 자동으로 import돼서 이 패키지의 authorList랑
		// 충돌했기 때문.

		AuthorVo authorVo1 = new AuthorVo("이문열", "경북 영양");
		authorDao.authorInsert(authorVo1);

		AuthorVo authorVo2 = new AuthorVo("박경리", "경남 통영");
		authorDao.authorInsert(authorVo2);

		AuthorVo authorVo3 = new AuthorVo("유시민", "국회의원");
		authorDao.authorInsert(authorVo3);

		AuthorVo authorVo4 = new AuthorVo("기안84", "기안동 84년생");
		authorDao.authorInsert(authorVo4);

		AuthorVo authorVo5 = new AuthorVo("강풀", "1세대 웹툰작가");
		authorDao.authorInsert(authorVo5);

		AuthorVo authorVo6 = new AuthorVo("김영하", "알쓸신잡");
		authorDao.authorInsert(authorVo6);

		System.out.println("-----작가 리스트-----");
		authorList = authorDao.getAuthorList();
		for (int i = 0; i < authorList.size(); i++) {
			AuthorVo vo = authorList.get(i);
			System.out.println(vo.getAuthorId() + ". " + vo.getAuthorName() + " | " + vo.getAuthorDesc());
		}

		// 작가 수정
		AuthorVo authorVo11 = new AuthorVo(1, "천선란", "인천");
		authorDao.authorUpdate(authorVo11);

		System.out.println("-----작가 리스트(수정)-----");
		authorList = authorDao.getAuthorList();
		for (int i = 0; i < authorList.size(); i++) {
			AuthorVo vo = authorList.get(i);
			System.out.println(vo.getAuthorId() + ". " + vo.getAuthorName() + " | " + vo.getAuthorDesc());
		}

		// 작가 삭제
		authorDao.authorDelete(4);

		System.out.println("-----작가 리스트(삭제)-----");
		authorList = authorDao.getAuthorList();
		for (int i = 0; i < authorList.size(); i++) {
			AuthorVo vo = authorList.get(i);
			System.out.println(vo.getAuthorId() + ". " + vo.getAuthorName() + " | " + vo.getAuthorDesc());
		}

		// 책 등록
		// BookDao, BookVo 이용해서 등록
		BookDao bookDao = new BookDao();
		List<BookVo> bookList;

		BookVo bookVo1 = new BookVo("우리들의 일그러진 영웅", "다림", "98/02/22", 1);
		bookDao.bookInsert(bookVo1);

		BookVo bookVo2 = new BookVo("삼국지", "민음사", "02/03/01", 1);
		bookDao.bookInsert(bookVo2);

		BookVo bookVo3 = new BookVo("토지", "마로니에북스", "12/08/15", 2);
		bookDao.bookInsert(bookVo3);

		BookVo bookVo4 = new BookVo("유시민의 글쓰기 특강", "생각의길", "15/04/01", 3);
		bookDao.bookInsert(bookVo4);

		// BookVo bookVo5 = new BookVo("패션왕", "중앙북스(books)", "12/02/22", 4); --> 작가 테이블에서 기안84를 지워버렸기 때문에 제외함.
		// bookDao.bookInsert(bookVo5);

		BookVo bookVo6 = new BookVo("순정만화", "재미주의", "11/08/03", 5);
		bookDao.bookInsert(bookVo6);

		BookVo bookVo7 = new BookVo("오직두사람", "문학동네", "17/05/04", 6);
		bookDao.bookInsert(bookVo7);

		BookVo bookVo8 = new BookVo("26년", "재미주의", "12/02/04", 5);
		bookDao.bookInsert(bookVo8);

		System.out.println("-----책 리스트-----");
		bookList = bookDao.getBookList();
		for (int i = 0; i < bookList.size(); i++) {
			BookVo vo = bookList.get(i);
			System.out.println(vo.getBookId() + ". " + vo.getTitle() + " | " + vo.getPubs() + " | " + vo.getPubDate()
					+ " | " + vo.getAuthorId());
		}

		// 책 수정 : 책번호 제목 출판사 출판일 작가번호
		// (파라미터 순서는 BookVo에 맞춰야 함. DB들어 갈 때는 BookDao에 짠 대로 들어가서 순서 헷갈릴 필요X)
		BookVo bookVo11 = new BookVo(1, "천 개의 파랑", "허블", "20/08/19", 1);
		bookDao.bookUpdate(bookVo11);

		System.out.println("-----책 리스트(수정)-----");
		bookList = bookDao.getBookList();
		for (int i = 0; i < bookList.size(); i++) {
			BookVo vo = bookList.get(i);
			System.out.println(vo.getBookId() + ". " + vo.getTitle() + " | " + vo.getPubs() + " | " + vo.getPubDate()
					+ " | " + vo.getAuthorId());
		}

		// 책 삭제
		// ORA-00904: "BOOK_ID": invalid identifier 오류 났었는데 bookDelete에서 쿼리문을 "delete
		// from author"로 해놔서 그랬음.
		bookDao.bookDelete(7);

		System.out.println("-----책 리스트(삭제)-----");
		bookList = bookDao.getBookList();
		for (int i = 0; i < bookList.size(); i++) {
			BookVo vo = bookList.get(i);
			System.out.println(vo.getBookId() + ". " + vo.getTitle() + " | " + vo.getPubs() + " | " + vo.getPubDate()
					+ " | " + vo.getAuthorId());
		}

		// 책 검색
		System.out.println("-----책 검색-----");
		Scanner sc = new Scanner(System.in);
		System.out.print("검색어> ");
		String sch = sc.nextLine();

		List<BookVo> resultList = new ArrayList<BookVo>();
		resultList = bookDao.searching(sch);

		for (int i = 0; i < resultList.size(); i++) {
			BookVo vo = resultList.get(i);
			System.out.println(vo.getBookId() + ". " + vo.getTitle() + " | " + vo.getPubs() + " | " + vo.getPubDate()
					+ " | " + vo.getAuthorId() + " | " + vo.getAuthorName() + " | " + vo.getAuthorDesc());
		}

		sc.close();

		// 책 정보+작가 정보 전체 출력
		// BookVo --> 책정보+작가정보
		System.out.println("-----전체 리스트-----");
		List<BookVo> allList = bookDao.getAllList();
		for (int i = 0; i < allList.size(); i++) {
			BookVo vo = allList.get(i);
			System.out.println(vo.getBookId() + ". " + vo.getTitle() + " | " + vo.getPubs() + " | " + vo.getPubDate()
					+ " | " + vo.getAuthorId() + " | " + vo.getAuthorName() + " | " + vo.getAuthorDesc());
		}

	}

}
