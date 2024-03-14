package edu.kh.jdbc.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {

	/* DB 연결 (Connection 생성) + 자동 커밋 off ,
	 * JDBC 객체 자원 반환(close),
	 * 트랜잭션 제어
	 * 
	 * 이러한 JDBC에서 반보 사용되는 코드를 모아둔 클래스
	 * 
	 * * 모든 필드, 메서드가 static *
	 * -> 어디서든지 클래스명.필드명 / 클래스명.메서드명 호출 가능
	 * -> 별도 객체 생성 X
	 * */
	
	private static Connection conn = null;
	
	/** DB 연결 정보를 담고있는 Connection 객체 생성 및 반환 메서드
	 * @return conn
	 */
	public static Connection getConnection() {
		
		try {
			
			// 현재 커넥션 객체가 없을 경우 -> 새 커넥션 객체 생성
			if(conn == null || conn.isClosed()) {
				// conn.isClosed() : 커넥션이 close 상태면 true 반환
				
				Properties prop = new Properties();
				// Map<String, String> 형태의 객체, XML 입출력 특화
				
				// driver.xml 파일 읽어오기
				prop.loadFromXML(new FileInputStream("driver.xml"));
				// -> xml 파일에 작성된 내용이 Properties 객체에 모두 저장됨.
				
				String driver = prop.getProperty("driver");
				String url = prop.getProperty("url");
				String user = prop.getProperty("user");
				String password = prop.getProperty("password");
				
				// 커넥션 생성
				Class.forName(driver);
				
				// DriverManager 통해 Connection 객체 생성
				conn = DriverManager.getConnection(url, user, password);
				
				// + 자동 커밋 비활성화 Connection 이 해주는 일
				conn.setAutoCommit(false);
				
			} 
			
		} catch(Exception e) {
			System.out.println("[Connection 생성 중 예외 발생]");
			e.printStackTrace();
		}
		
		return conn; // 생성 및 설정된 Connection 객체 반환
		
	}

	/** Connection 객체를 자원 반환 메서드
	 * @param conn
	 */
	public static void close(Connection conn) {
		// 코드에서 사용하는 connection 을 받아서 닫아줌
		
		// close 도 Exception 발생함
		try {
			// 전달받은 conn 이
			// 참조하는 Connection 객체가 있고
			// 그 Connection 객체가 close 상태가 아니라면
			if(conn != null && !conn.isClosed()) conn.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/** Statement(부모), PreparedStatement(자식) 객체 자원 반환 메서드
	 * 자식 타입 다형성 upcasting 적용
	 * @param stmt
	 */
	public static void close(Statement stmt) {
		// 매개변수 타입 달라서 오버로딩 적용
		
		try {
			
			if(stmt != null && !stmt.isClosed()) stmt.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/** ResultSet 객체 자원 반환 메서드
	 * @param rs
	 */
	public static void close(ResultSet rs) {
		try {
			
			if(rs != null && !rs.isClosed()) rs.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/** 트랜잭션 commit 메서드
	 * @param conn
	 */
	public static void commit(Connection conn) {
		// commit rollback 도 Connection 통해서 함
		try {
			if(conn != null && !conn.isClosed()) conn.commit();
			// 커넥션 통해서 커밋
		} catch(Exception e) {
			e.printStackTrace();
			// SQLException 발생해서 try catch 구문 작성
		}
	}

	/** 트랜잭션 rollback 메서드
	 * @param conn
	 */
	public static void rollback(Connection conn) {
		// commit rollback 도 Connection 통해서 함
		try {
			if(conn != null && !conn.isClosed()) conn.rollback();
			// 커넥션 통해서 커밋
		} catch(Exception e) {
			e.printStackTrace();
			// SQLException 발생해서 try catch 구문 작성
		}
	}
}
