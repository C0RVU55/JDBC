package com.javaex.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookInsert {

	public static void main(String[] args) {
		// Book 입력 클래스
		
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
			String query = "insert into book values(seq_book_author_id.nextval, ?, ?, ?, ?)"; 
			pstmt = conn.prepareStatement(query); 
		    
			// 제목 출판사 출판일 작가번호
			pstmt.setNString(1, "우리들의 일그러진 영웅");
			pstmt.setNString(2, "다림");
			pstmt.setString(3, "98/02/22");
			pstmt.setInt(4, 1);
			
			pstmt.setNString(1, "삼국지");
			pstmt.setNString(2, "민음사");
			pstmt.setString(3, "02/03/01");
			pstmt.setInt(4, 1);
			
			pstmt.setNString(1, "토지");
			pstmt.setNString(2, "마로니에북스");
			pstmt.setString(3, "12/08/15");
			pstmt.setInt(4, 2);
			
			pstmt.setNString(1, "유시민의 글쓰기 특강");
			pstmt.setNString(2, "생각의길");
			pstmt.setString(3, "15/04/01");
			pstmt.setInt(4, 3);
			
			pstmt.setNString(1, "패션왕");
			pstmt.setNString(2, "중앙북스(books)");
			pstmt.setString(3, "12/02/22");
			pstmt.setInt(4, 23);
			
			pstmt.setNString(1, "순정만화");
			pstmt.setNString(2, "재미주의");
			pstmt.setString(3, "11/08/03");
			pstmt.setInt(4, 5);
			
			pstmt.setNString(1, "오직두사람");
			pstmt.setNString(2, "문학동네");
			pstmt.setString(3, "17/05/04");
			pstmt.setInt(4, 6);
			
			pstmt.setNString(1, "26년");
			pstmt.setNString(2, "재미주의");
			pstmt.setString(3, "12/02/04");
			pstmt.setInt(4, 5);
			
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
