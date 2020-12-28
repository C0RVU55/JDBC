package com.javaex.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuthorUpdate {

	public static void main(String[] args) {
		// DB수정용 클래스
		
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		//ResultSet rs = null;

		try {
		    // 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");

		    // 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe"; 
			conn = DriverManager.getConnection(url, "webdb", "webdb"); 

		    // 3. SQL문 준비 / 바인딩 / 실행
			/*
			넣을 쿼리문 (sqldeveloper에서 테스트된 상태)
			update author
			set author_desc = '우리집'
			where author_id = 21;
		    */
			String query = "";
			query += " update author "; //계속 누적시키기 (들여쓰기 있으면 그대로 넣어주는 게 보기 편함)
			query += " set author_desc = ? ";
			query += " where author_id = ? ";
			// missing SET keyword 오류 : 쿼리문 각 줄을 ""안에 그대로 쓰면 3줄이 다 붙어서 set을 구분 못함. --> 공백 추가해야 됨. 처음부터 ""안 내용을 앞뒤로 띄어놓는 게 좋음.
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "우리집"); // 몇 번째인지는 1줄 안에서만 따지는 게 아니라 전체 쿼리문에서 따짐.
			pstmt.setInt(2, 21); 
			
			// 실행 (원하든 안 하든 값을 던져주니까 받는 거)
			int count = pstmt.executeUpdate();
			
		    // 4.결과처리
			System.out.println(count + "건이 수정되었습니다.");

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
