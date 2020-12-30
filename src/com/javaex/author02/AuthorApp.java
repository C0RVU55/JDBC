package com.javaex.author02;

import java.util.List;

public class AuthorApp {

	public static void main(String[] args) {
		// DAO(Data Access Object) : 공통되는 코드 같은 메소드로 묶어서 빼기

		AuthorDao authorDao = new AuthorDao();
		
		// authorDao.getConnection(); --> public으로 만들어 놔서 main에서 쓸 수도 있지만 오류날 수도 있으니까 private로 바꾸는 게 좋음.

		AuthorVo authorVo1 = new AuthorVo("이문열", "경북 영양"); 
		authorDao.authorInsert(authorVo1);
		AuthorVo authorVo2 = new AuthorVo("박경리", "경남 통영");
		authorDao.authorInsert(authorVo2);
		AuthorVo authorVo3 = new AuthorVo("유시민", "국회의원");
		authorDao.authorInsert(authorVo3);
		
		
		// 작가 리스트 가져오기
		List<AuthorVo> authorList = authorDao.getAuthorList();
		// for문 출력
		System.out.println("-----작가 리스트-----");
		for (int i = 0; i < authorList.size(); i++) {
			AuthorVo vo = authorList.get(i);
			System.out.println(vo.getAuthorId() + ". " + vo.getAuthorName() + ", " + vo.getAuthorDesc());
		}
		
		// 작가 삭제 (실제 DB에서 삭제)
		authorDao.authorDelete(2);
		
		// 리스트 재출력
		authorList = authorDao.getAuthorList();
		
		System.out.println("-----작가 리스트-----");
		for (int i = 0; i < authorList.size(); i++) {
			AuthorVo vo = authorList.get(i);
			System.out.println(vo.getAuthorId() + ". " + vo.getAuthorName() + ", " + vo.getAuthorDesc());
		}
		
		// 리스트 수정
		authorDao.authorUpdate(3, "천선란", "인천");

		// 재출력
		authorList = authorDao.getAuthorList();
		
		System.out.println("-----작가 리스트-----");
		for (int i = 0; i < authorList.size(); i++) {
			AuthorVo vo = authorList.get(i);
			System.out.println(vo.getAuthorId() + ". " + vo.getAuthorName() + ", " + vo.getAuthorDesc());
		}
		
	}

}
