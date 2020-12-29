package com.javaex.author01;

import java.util.List;

public class AuthorApp {

	public static void main(String[] args) {
		// DAO(Data Access Object)

		/*
		 * com.javaex.jdbc처럼 만들면 각각이 다 프로그램이라 너무 많음. 
		 * 스위치문처럼 1하면 insert 2하면 select 이런 식으로 만들고 싶음 --> 전체 코드를 관리하는 클래스를 만듦.
		 */
		/*
		 * AuthorDao : 기능용 클래스. DB와 관련된 일을 전문적으로 하는 클래스. 
		 * AuthorVo : 데이터 저장용 클래스. value object. 이 클래스 모양으로 리스트에 넣어서 관리할 거임.
		 */

		AuthorDao authorDao = new AuthorDao();

		// 작가테이블에 데이터 등록
		//authorDao.authorInsert("이문열", "경북 영양");
		//authorDao.authorInsert("박경리", "경남 통영");
		//authorDao.authorInsert("유시민", "국회의원");
		// System.out.println(count + "건 등록되었습니다."); 위 코드를 int count로 정해서 이용자에게 이렇게 알려줄 수도 있음.
		
		// 아니면 변수 지정해서 넣을 수도 있음. 클래스도 모든 파라미터를 정해서 넣는 게 아니라 변수로 묶어서 넣음. 
		AuthorVo authorVo1 = new AuthorVo("이문열", "경북 영양"); // 파라미터 2개인 생성자를 AuthorVo에 추가해야 됨.
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
		
		// 리스트를 다시 불러와야 변경사항이 반영된 리스트를 출력할 수 있음.
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
