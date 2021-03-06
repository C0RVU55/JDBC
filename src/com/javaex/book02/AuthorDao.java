package com.javaex.book02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDao {
	
	// 공통 코드 따로 빼기 : import(필드), 접속메소드 getConnection(), 자원정리 메소드 close()

	// 필드
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	// 생성자
	// 메소드 겟셋

	// 메소드 일반
	
	// *****DB접속*****
	private void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
	}
	
	// *****자원 정리 메소드*****
	private void close() {
		try {
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
	
	
	///////////////////////////////////////////////////
	
	
	// ************작가 등록************
	public int authorInsert(AuthorVo authorVo) {
		
		// 12. DB접속
		getConnection();
		
		int count = 0;

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " insert into author ";
			query += " values(seq_author_id.nextval, ?, ?) ";

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, authorVo.getAuthorName());
			pstmt.setString(2, authorVo.getAuthorDesc());

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println("[DAO] " + count + "건 등록");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		// 5. 자원 정리
		close();
		
		return count;

	}

	// ************작가 수정************
	public int authorUpdate(AuthorVo authorVo) {
		
		// 12. DB접속
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

			pstmt.setString(1, authorVo.getAuthorName());
			pstmt.setString(2, authorVo.getAuthorDesc());
			pstmt.setInt(3, authorVo.getAuthorId());

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println("[DAO] " + count + "건 수정");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		// 5. 자원 정리
		close();
		
		return count;

	}

	// ************작가 삭제************
	public int authorDelete(int authorId) {
		
		// 12. DB접속
		getConnection();

		int count = 0;

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " DELETE FROM author ";
			query += " where author_id = ? ";

			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, authorId);

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println("[DAO] " + count + "건 삭제");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		// 5. 자원 정리
		close();

		return count;
	}

	// ************작가 출력************
	public List<AuthorVo> getAuthorList() {
		
		// 12. DB접속
		getConnection();
		
		List<AuthorVo> authorList = new ArrayList<AuthorVo>();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " SELECT  author_id, ";
			query += "         author_name, ";
			query += "         author_desc ";
			query += " FROM author ";

			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery(query);

			// 4.결과처리
			while (rs.next()) {
				int authorId = rs.getInt("author_id"); // 코드 형태 기억하기 : 저장 클래스 파라미터에 넣을 변수 = rs.get자료형("컬럼명")
				String name = rs.getString("author_name");
				String desc = rs.getString("author_desc");

				AuthorVo vo = new AuthorVo(authorId, name, desc);
				authorList.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		// 5. 자원 정리
		close();

		return authorList;

	}

}
