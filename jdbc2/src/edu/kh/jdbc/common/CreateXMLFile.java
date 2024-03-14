package edu.kh.jdbc.common;

import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Scanner;

public class CreateXMLFile {
	
	public static void main(String[] args) {
		
		// 데이터 저장하고 사람이 읽기 쉽게 기계가 분석하기 쉽게 텍스트 기반의 형식
		// HTML도 마크업 language
		// XML도 태그 <> 적용
		// <ex key="key">value</ex>
		// 데이터 저장하기 용이한 형태
		// DB 정보, SQL 담아두고 사용
		
		// XML(eXtensible Markup Language) :단순화된 데이터 기술 형식
		
		// XML에 저장되는 데이터 형식은 Key : Value 형식
		// -> Key, Value 모두 String(문자열) 형식
		
		// XML 파일을 읽고, 쓰기 위한 IO 관련 클래스 필요
		
		// 키와 값을 String 타입으로 제한한 Map 컬렉션
		// 주로 Properties 는 프로퍼티(*.properties)파일을 읽어 들일 때 주로 사용

		// * Properties 컬렉션 객체 *
		// - Map 후손 클래스
		// - Key, Value 모두 String(문자열)
		// - XML 파일을 읽고, 쓰는데 특화된 메서드 제공
		
		// 파일 입출력 시에는 IO 관련 Exception 이 많이 발생하므로
		// try catch 구문 작성
		try {
			
			Scanner sc = new Scanner(System.in);
			
			// Properties 객체 생성
			Properties prop = new Properties();
			
			System.out.print("생성할 파일 이름 : ");
			String fileName = sc.next();
			
			// FileOutputStream 생성
			FileOutputStream fos = new FileOutputStream(fileName + ".xml");
			// -> XML 확장자가 붙어서 현재 프로젝트 안에 생성됨
			
			// Properties 객체를 이용해서 XML 파일 생성
			prop.storeToXML(fos, fileName + ".xml file!!!");
			// XML 파일 안에 <comment> fileName.xml file!!! </comment> 내부에 작성되는 comment
			// 이렇게 들어감 이 구문을 지나가면 XML 파일 만들어지는 거
			
			System.out.println(fileName + ".xml 파일 생성 완료");
			
//			생성할 파일 이름 : test
//			test.xml 파일 생성 완료

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
