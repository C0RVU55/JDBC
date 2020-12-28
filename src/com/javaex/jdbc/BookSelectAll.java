package com.javaex.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookSelectAll {

	public static void main(String[] args) {
		// book 테이블과 author 테이블 조인하기
		
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
		    // 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");

		    // 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe"; 
			conn = DriverManager.getConnection(url, "webdb", "webdb");

		    // 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " SELECT  b.book_id, ";
			query += "         b.title, ";
			query += "         b.pubs, ";
			query += "         to_char(b.pub_date, 'YY/MM/DD') pub_date, ";
			query += "         b.author_id, ";
			query += "         a.author_name, ";
			query += "         a.author_desc";
			query += " FROM author a, book b ";
			query += " where a.author_id = b.author_id ";
			
			pstmt = conn.prepareStatement(query);
			
			rs = pstmt.executeQuery();
		    
		    // 4.결과처리
			while(rs.next()) { 
				int bookId = rs.getInt("book_id"); // 이미 어느 테이블의 book_id인지 쓴 다음 query에 넣은 거라 출력할 때는 b.book_id라고 안 써도 됨.
				String title = rs.getString("title");
				String pubs = rs.getString("pubs");
				String pubDate = rs.getString("pub_date");
				int authorId = rs.getInt("author_id");
				String authorName = rs.getString("author_name");
				String authorDesc = rs.getString("author_desc");
				
				System.out.println(bookId + " | " + title + " | " + pubs + " | " + pubDate + " | " + authorId + " | " + authorName + " | " + authorDesc);
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
