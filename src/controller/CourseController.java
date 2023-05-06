package controller;

import util.KeyboardReader;

public class CourseController {

	private KeyboardReader keyboard = new KeyboardReader();
	private LoginUser loginUser;
	
	public void menu() {
		
		try {
			if (loginUser == null) {
				System.out.println("-------------------------------------------------------------");
				System.out.println("1.로그인(학생)  2.로그인(강사)  3.가입(학생)  4.가입(강사)  0.종료");
				System.out.println("-------------------------------------------------------------");
			} else {
				if ("학생".equals(loginUser.getType())) {
					System.out.println("-------------------------------------------------------------");				
					System.out.println("1.과정조회  2.과정신청  3.등록취소  4.신청현황  0.종료");
					System.out.println("-------------------------------------------------------------");				
				} else if ("강사".equals(loginUser.getType())) {
					System.out.println("-------------------------------------------------------------");				
					System.out.println("1.과정조회  2.과정등록  3.과정취소  4.과정현황  0.종료");					
					System.out.println("-------------------------------------------------------------");				
				}
			}
			System.out.println();
			System.out.print("### 메뉴번호: ");
			int menu = keyboard.readInt();
			System.out.println();
			
			if (menu == 0) {
				System.out.println("<< 프로그램 종료 >>");
				System.out.println("### 프로그램을 종료합니다.");
				System.exit(0);
			}
			
			if (loginUser == null) {
				if (menu == 1) {
					학생로그인();
				} else if (menu == 2) {
					강사로그인();
				} else if (menu == 3) {
					학생회원가입();
				} else if (menu == 4) {
					강사회원가입();
				}
			} else {
				if ("학생".equals(loginUser.getType())) {
					if (menu == 1) {
						학생과정조회();
					} else if (menu == 2) {
						학생과정신청();
					} else if (menu == 3) {
						학생등록취소();
					} else if (menu == 4) {
						학생신청현황조회();
					}
				
				} else if ("강사".equals(loginUser.getType())) {
					if (menu == 1) {
						강사과정조회();
					} else if (menu == 2) {
						강사과정등록();
					} else if (menu == 3) {
						강사과정취소();
					} else if (menu == 4) {
						강사과정현황조회();
					}
				}
			}
			
			
			
		} catch (RuntimeException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace(System.out);
		}
		
		System.out.println();
		System.out.println();
		System.out.println();
		
		menu();
	}
}
