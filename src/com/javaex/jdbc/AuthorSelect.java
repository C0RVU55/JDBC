package com.javaex.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorSelect {

	public static void main(String[] args) {
		// DB출력용 클래스
		/*
		SELECT  author_id,
        author_name,
        author_desc
        FROM author;
        */
		
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; // 결과값 가져오는 변수

		try {
		    // 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");

		    // 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe"; 
			conn = DriverManager.getConnection(url, "webdb", "webdb");

		    // 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " SELECT  author_id, ";
			query += "         author_name, ";
			query += "         author_desc ";
			query += " FROM author ";
			// 따로 ?로 변환해줄 데이터가 없는 상황 / 컬럼명에 alias 주면 자바 쿼리변수에도 alias로 넣어야 됨.
			
			System.out.println(query);
			
			pstmt = conn.prepareStatement(query);
			
			// 실행 : ResultSet rs = null; 이거 씀. 숫자나 문자열 하나가 오는 게 아니라 db로 나오는 걸 출력할 수 있어야 되니까 이게 되는 ResultSet을 씀.
			rs = pstmt.executeQuery();
			
			/*
			 데이터를 가져오면 커서라는 가상의 개념이 1줄을 훑어서 각 컬럼마다 숫자 매기고 컬럼값의 자료형에 따라 getInt(1) getString(2)...이렇게 데이터를 가져옴.
			 getString(author_desc) 이렇게 컬럼명을 쓰는 게 정확하긴 함. 
			 */
			
		    // 4.결과처리
			while(rs.next()) { // next()는 커서를 이동시킴. rs.next() 결과가 참거짓이라 거짓 나올 때까지 돌림.
				int authorId = rs.getInt("author_id");
				String authorName = rs.getString("author_name");
				String authorDesc = rs.getString("author_desc");
				
				System.out.println(authorId + ", " + authorName + ", " + authorDesc);

			}
			

		} catch (ClassNotFoundException e) {
		    System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		} finally {
		   
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

	}

}
