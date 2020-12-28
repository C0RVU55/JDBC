package com.javaex.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuthorInsert {

	public static void main(String[] args) {
		/* 지난 시퀀스 보충
		insert into board VALUES (1, '제목', '글쓴이', sysdate); 
		-->sysdate는 이 쿼리문이 작동된 시간(글 올린 시간)이 입력됨
		*기능 : 쿼리문 여러 개를 드래그한 후 실행시키면 *순서대로* 실행됨
		*/
		
		// JDBC(Java database connectivity) ***오라클과 자바 연결하기***
		/*
		이거를 자바에 넣을 거임 --> insert into author values(seq_author_id.nextval, '이다현', '수원 영통구');
		자바에서 일하는 순서 정해줌 : 피피티 빨간 부분(select일 때만 필요) 빼고 다 똑같이 씀
		*/
		
		// 0. 프로젝트 > properties > java build path > add external JARs > 오라클 라이브러리의 ojdbc6 드라이버 추가
		
		// 0. import java.sql.*; 드라이브에 있는 라이브러리(클래스)를 갖다 씀. *해도 되는데 다 딸려오니까 ctrl+shift+o
		Connection conn = null;
		PreparedStatement pstmt = null;
		//ResultSet rs = null;
		

		try {
		    // 1. JDBC 드라이버 (Oracle) 로딩 (피피티 복붙. 오라클 아니고 mysql이면 mysql에서 주는 이름으로 써야 됨.)
			Class.forName("oracle.jdbc.driver.OracleDriver");

		    // 2. Connection 얻어오기 (소켓통신 스트림 필요)
			String url = "jdbc:oracle:thin:@localhost:1521:xe"; 
			//locahost는 외부 접속이면 IP 들어갈 자리고 1521은 포트번호
			conn = DriverManager.getConnection(url, "webdb", "webdb"); 
			//DriverManager는 대문자니까 스태틱클래스일 가능성 있음. "webdb"는 아이디, 비밀번호 가리킴.

			
		    // 3. SQL문 준비 / 바인딩 / 실행 --> 이 순서대로 코드 짜면 됨
			/* 
			쿼리문에 따라 쓰이는 자바코드가 다름. 리턴되는 자료형이 다르기 때문.
			
			int count = pstmt.executeUpdate();      //insert, update, delete
			ResultSet rs = pstmt.executeQuery();    //select
			*/
			
			/*
			자바에서는 쿼리마침표 안 붙여도 됨. 실행할 때 자동으로 붙음. 또 변하는 값은 ?로 써둠. 나중에 ?마다 값 매칭시켜야 됨.
			***String 쿼리문을 여기서 짜지 말고 꼭 sqldeveloper에서 테스트해 본 다음에 이걸 복붙하는 게 안전함. 또 여기서는 insert+commit까지 됨. 옵션에서 변경 가능.***
			실행하면 sqldeveloper에서 author테이블 출력했을 때 이다현 추가된 거 확인 가능
			*/
			
			String query = "insert into author values(seq_author_id.nextval, ?, ?)"; 
			pstmt = conn.prepareStatement(query); //위의 문자열을 쿼리로 만들고 아래처럼 ?에 값 입력함. 
		    
			pstmt.setNString(1, "기안84");
			pstmt.setNString(2, "기안동 84년생");
			// pstmt.setInt(3, 10000); 자료형과 쿼리문에 맞게 써야 됨. 3번째 데이터는 없기 때문에 넣으면 안 됨.
			
			// 실행
			int count = pstmt.executeUpdate(); //성공한 개수 체크용
			
		    // 4.결과처리
			System.out.println(count + "건이 저장됐습니다");

		} catch (ClassNotFoundException e) {
		    System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		} finally {
		   
		    // 5. 자원정리
		    try {
		    	/*
		        if (rs != null) {
		            rs.close();
		        }
		        */                
		        if (pstmt != null) {
		            pstmt.close();
		        }
		        if (conn != null) {
		            conn.close();
		        }
		    } catch (SQLException e) {
		        System.out.println("error:" + e);
		    }

		}
	}

}
