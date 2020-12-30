package com.javaex.author02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDao {

	// 기능용 클래스 : 공통부분 따로 빼기

	// 필드 (중복되는 코드가 많으니까 수정을 대비해서 필드로 뺌)
	// 0. import java.sql.*;
	Connection conn = null; 
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";

	// 생성자
	// 기본생성자 생략 (다른 생성자 없음)

	// 메소드 겟셋

	// 메소드 일반
	
	// DB접속 메소드로 따로 빼기
	private void getConnection() {
		try {
			// 1. JDBC 드라이버(Oracle) 로딩
			Class.forName(driver);
			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
			
			System.out.println("[접속성공]");
		}catch (ClassNotFoundException e) { // 오류나서 예외처리해야 됨
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	
	// 자원 정리 : finally 부분을 따로 뺌.
	public void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
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
	
	
	// 작가 수정하기
	public int authorUpdate(int authorId, String name, String desc) {

		// DB접속
		getConnection();
		
		int count = 0;

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " update author ";
			query += " set author_name = ?, ";
			query += "     author_desc = ? ";
			query += " where author_id = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, desc);
			pstmt.setInt(3, authorId);

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println("[dao] " + count + " 건 수정");

		} catch (SQLException e) { // catch (ClassNotFoundException e)는 로딩에 관한 거니까 getConnetion()에서 처리하게 뺌
			System.out.println("error:" + e);
		} 
		
		// 자원 정리
		close();
		
		return count;
	}

	// 작가 삭제하기
	public int authorDelete(int authorId) {

		// DB접속
		getConnection();
		
		int count = 0;

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " delete from author "; // 또 += 안 함...주의하기. 이거 안 하면 sql 어쩌구 오류 뜸.
			query += " where author_id = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, authorId);

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println("[dao] " + count + "건 삭제");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		close();

		return count;
	}

	// 작가 리스트 가져오기
	public List<AuthorVo> getAuthorList() {
		// 반환 받을 리스트(인터페이스 섞어쓰기)를 먼저 씀.
		List<AuthorVo> authorList = new ArrayList<AuthorVo>();

		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " select author_id, ";
			query += "	      author_name, ";
			query += "        author_desc ";
			query += " from author ";

			System.out.println(query);

			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();

			// 4.결과처리 : rs에 있는 데이터를 List<AuthorVo>로 구성해야 함
			while (rs.next()) {
				int authorId = rs.getInt("author_id");
				String authorName = rs.getString("author_name");
				String authorDesc = rs.getString("author_desc");

				AuthorVo vo = new AuthorVo(authorId, authorName, authorDesc); // AuthorVo클래스가 데이터 저장용 클래스인 게 여기서 데이터 담는 용도로 써서 그럼.
				authorList.add(vo); // ***리스트에 데이터 추가하는 거 잊지 말기*** 이거 안 해서 아무것도 출력 안 됨.
			}

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		close();

		return authorList;
	}

	// 작가 저장 기능
	public int authorInsert(AuthorVo authorVo) {

		// DB접속
		getConnection();

		int count = 0; // count를 반환해야 되는데 try문에만 있으니까 밖으로 빼줌.

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " insert into author ";
			query += " values(SEQ_AUTHOR_ID.nextval, ?, ?) ";

			System.out.println(query);

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, authorVo.getAuthorName()); // 메소드 파라미터 정했으니까 그걸로 넣음 --> 클래스 변수로 바꿔서 한번에 담고 꺼낼 때 getter 메소드 사용.
			pstmt.setString(2, authorVo.getAuthorDesc());

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println("[dao] " + count + "건 저장");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		close();

		return count;
	}
}
