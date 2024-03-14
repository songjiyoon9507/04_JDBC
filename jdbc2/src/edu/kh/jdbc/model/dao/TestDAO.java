package edu.kh.jdbc.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static edu.kh.jdbc.common.JDBCTemplate.*;
import edu.kh.jdbc.model.vo.TestVO;

public class TestDAO {

	// DAO가 생성될 때 기본적으로 해야할 일 작성
	// DAO (Data Access Object) : 데이터 접근 객체
	//                            데이터가 저장된 DB에 접근하는 객체
	//                            -> SQL 수행, 결과 반환 받는 기능을 수행
	
	// JDBC 객체를 참조하기 위한 참조변수 선언
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	// xml 로 SQL 을 다룰 것 -> Properties 객체 사용
	private Properties prop;
	// SQL 수행 SQL 쿼리 구문 작성할 때 xml 사용
	
	// 기본생성자
	public TestDAO() {
		// TestDAO 객체 생성시
		// test-query.xml 파일의 내용을 읽어와
		// Properties 객체에 저장
		
		try {
			
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("test-query.xml"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int insert(Connection conn, TestVO vo1) throws SQLException {
		
		// 1. 결과 저장용 변수 선언
		int result = 0;
		
		try {
			
			// 2. SQL 작성(test-query.xml에 작성된 SQL 얻어오기)
			String sql = prop.getProperty("insert");
			// INSERT INTO TB_TEST VALUES(?, ?, ?)
			
			// 3. PreparedStatement 객체 생성
			// ? placeHolder 때문에 버스 출발 할 수 없음
			pstmt = conn.prepareStatement(sql);
			// conn.createStatement(); 생성 후
			// stmt.excuteQuery(sql); 이렇게 sql 태워서 보냈음
			// preparedStatement는 생성하자마자 승객 태워서 보내야함
			// 구멍 난 승객으로는 출발 못 함
			
			// placeholder 위치홀더 채워줘야함
			// 4. 위치 홀더(?)에 알맞은 값 세팅해야함
			pstmt.setInt(1, vo1.getTestNo()); // 1
			pstmt.setString(2, vo1.getTestContent()); // "제목1"
			pstmt.setString(3, vo1.getTestContent()); // "내용1"
		
			// 5. SQL(INSERT) 수행 후 결과 반환받기
			result = pstmt.executeUpdate(); // DML 수행, 반영된 행의 개수(int) 반환
			// sql 담으면 안됨
			
			/* stmt = conn.createStatement();
			 * stmt.executeQuery(sql);
			 * 
			 * ------------------------------
			 * 
			 * pstmt = conn.prepareStatement(sql);
			 * pstmt.executeUpdate();
			 * sql 다시 넣으면 안됨
			 * sql 다시 넣으면 버스에서 승객 빠져나옴
			 * */
			
		} finally {
			// 6. 사용한 JDBC 객체 자원 반환
			// conn 은 닫으면 안됨
			close(pstmt);
		}
		
		// 7. SQL 수행 결과 반환
		return result;
		
	}

	/** 번호가 일치하는 행의 제목, 내용을 수정 DAO
	 * @param conn
	 * @param vo
	 * @return result
	 */
	public int update(Connection conn, TestVO vo) throws Exception{
		
		// 결과 저장용 변수
		int result = 0;
		
		try {
			
			String sql = prop.getProperty("update");
			/*
			UPDATE TB_TEST SET
			TEST_TITLE = ?,
			TEST_CONTENT = ?
			WHERE TEST_NO = ?
			 * */
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getTestTitle());
			pstmt.setString(2, vo.getTestContent());
			pstmt.setInt(3, vo.getTestNo());
			
			result = pstmt.executeUpdate();
			
			
		} finally {
			close(pstmt);
		}

		return result;

	}

	public int delete(Connection conn, int testNo) throws Exception {

		int result = 0;
		
		try {
			
			String sql = prop.getProperty("delete");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, testNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

}
