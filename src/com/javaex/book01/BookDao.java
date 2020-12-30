package com.javaex.book01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

	// 필드
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";

	// 생성자
	// 메소드 겟셋

	// 메소드 일반
	// **********책 등록**********
	public int bookInsert(BookVo bookVo) {
		int count = 0;

		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " insert into book ";
			query += " values(seq_book_id.nextval, ?, ?, ?, ?) ";

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, bookVo.getTitle());
			pstmt.setString(2, bookVo.getPubs());
			pstmt.setString(3, bookVo.getPubDate());
			pstmt.setInt(4, bookVo.getAuthorId());

			count = pstmt.executeUpdate(); // ***************주의*************** excute에는 암것도 넣으면 안 됨.........

			// 4.결과처리
			System.out.println("[DAO] " + count + "건 등록");

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {

			// 5. 자원정리
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

		return count;

	}

	// **********책 수정**********
	public int bookUpdate(BookVo bookVo) {
		int count = 0;
		
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " update book ";
			query += " set title = ?, ";
			query += "     pubs = ?, ";
			query += "     pub_date = ?, ";
			query += "     author_id = ? ";
			query += " where book_id = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, bookVo.getTitle());
			pstmt.setString(2, bookVo.getPubs());
			pstmt.setString(3, bookVo.getPubDate());
			pstmt.setInt(4, bookVo.getAuthorId());
			pstmt.setInt(5, bookVo.getBookId());
			
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println("[DAO] " + count + "건 수정");

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {

			// 5. 자원정리
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
		
		return count;

	}

	// **********책 삭제**********
	public int bookDelete(int bookId) {
		int count = 0;
		
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " DELETE FROM book "; // ORA-00904: "BOOK_ID": invalid identifier 오류 : author테이블로 돼 있었음...
			query += " where book_id = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, bookId);
			
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println("[DAO] " + count + "건 삭제");

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {

			// 5. 자원정리
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

		return count;
		
	}
	
	// **********책 검색**********
	public List<BookVo> searching(String sch){
		List<BookVo> resultList = new ArrayList<BookVo>();
		
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
			query += " SELECT  book_id, ";
			query += "         title, ";
			query += "         pubs, ";
			query += "         to_char(pub_date, 'YY/MM/DD') pub_date, ";
			query += "         b.author_id, ";
			query += "         author_name, ";
			query += "         author_desc";
			query += " FROM author a, book b ";
			query += " where a.author_id = b.author_id ";
			query += " and (title like ? ";
			query += " or pubs like ? ";
			query += " or author_name like ? ";
			query += " or author_desc like ?) ";
			
			pstmt = conn.prepareStatement(query); // 순서 유의. pstmt에 먼저 conn 대입한 다음에 set자료형 가능.
			
			pstmt.setString(1, "%" + sch + "%");
			pstmt.setString(2, "%" + sch + "%");
			pstmt.setString(3, "%" + sch + "%");
			pstmt.setString(4, "%" + sch + "%");
			
			rs = pstmt.executeQuery();

			// 4.결과처리
			while(rs.next()) {
				int bookId = rs.getInt("book_id");
				String title = rs.getString("title");
				String pubs = rs.getString("pubs");
				String pubDate = rs.getString("pub_date");
				int authorId = rs.getInt("author_id");	
				String authorName = rs.getString("author_name");
				String authorDesc = rs.getString("author_desc");
				
				BookVo vo = new BookVo(bookId, title, pubs, pubDate, authorId, authorName, authorDesc);
				resultList.add(vo);		
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

		return resultList;
	}

	// **********책 출력**********
	public List<BookVo> getBookList() {
		List<BookVo> bookList = new ArrayList<BookVo>();
		
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
			query += " SELECT  book_id, ";
			query += "         title, ";
			query += "         pubs, ";
			query += "         to_char(pub_date, 'YY/MM/DD') pub_date, ";
			query += "         author_id ";
			query += " FROM book ";

			
			pstmt = conn.prepareStatement(query);
			
			rs = pstmt.executeQuery();

			// 4.결과처리
			while(rs.next()) {
				int bookId = rs.getInt("book_id");
				String title = rs.getString("title");
				String pubs = rs.getString("pubs");
				String pubDate = rs.getString("pub_date");
				int authorId = rs.getInt("author_id");	
				
				BookVo vo = new BookVo(bookId, title, pubs, pubDate, authorId);
				bookList.add(vo);		
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

		return bookList;
		
	}
	
	// **********전체 출력**********
		public List<BookVo> getAllList() {
			List<BookVo> allList = new ArrayList<BookVo>();
			
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
				query += " SELECT  book_id, ";
				query += "         title, ";
				query += "         pubs, ";
				query += "         to_char(pub_date, 'YY/MM/DD') pub_date, ";
				query += "         b.author_id, ";
				query += "         author_name, ";
				query += "         author_desc";
				query += " FROM author a, book b ";
				query += " where a.author_id = b.author_id ";
				
				pstmt = conn.prepareStatement(query);
				
				rs = pstmt.executeQuery();

				// 4.결과처리
				while(rs.next()) {
					int bookId = rs.getInt("book_id");
					String title = rs.getString("title");
					String pubs = rs.getString("pubs");
					String pubDate = rs.getString("pub_date");
					int authorId = rs.getInt("author_id");	
					String authorName = rs.getString("author_name");
					String authorDesc = rs.getString("author_desc");
					
					BookVo vo = new BookVo(bookId, title, pubs, pubDate, authorId, authorName, authorDesc);
					allList.add(vo);		
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

			return allList;
			
		}

}
