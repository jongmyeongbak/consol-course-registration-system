package controller;

import java.util.List;

import service.CourseService;
import service.UserService;
import util.KeyboardReader;
import vo.Course;
import vo.CourseRegistration;
import vo.User;

public class CourseController {

	private KeyboardReader keyboard = new KeyboardReader();
	private CourseService courseService = new CourseService();
	private UserService userService = new UserService();
	private LoginUser loginUser;
	
	public static void main(String[] args) {
		new CourseController().menu();
	}
	
	public void menu() {
		
		try {
			if (loginUser == null) {
				System.out.println("-------------------------------------------------------------");
				System.out.println("1.로그인(학생)  2.로그인(강사)  3.가입(학생)  4.가입(강사)  0.종료");
				System.out.println("-------------------------------------------------------------");
			} else {
				if ("학생".equals(loginUser.getType())) {
					System.out.println("-------------------------------------------------------------");				
					System.out.println("1.과정조회  2.수강신청  3.신청취소  4.신청현황  0.종료");
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
						학생수강신청();
					} else if (menu == 3) {
						학생신청취소();
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

	private void 학생과정조회() {
		System.out.println("<< 과정 조회 >>");
		List<Course> courses = courseService.availableCourses();
		System.out.println("-------------------------------------------------------------");
		System.out.println("과정번호\t모집정원\t신청자수\t담당강사\t과목명");
		System.out.println("-------------------------------------------------------------");
		for (Course course : courses) {
			System.out.println(course.getNo() + "\t"
					+ course.getQuota() + "\t"
					+ course.getRegCnt() + "\t"
					+ course.getTeacherIdOrName() + "\t"
					+ course.getName());
		}
		System.out.println("-------------------------------------------------------------");
	}

	private void 학생수강신청() {
		System.out.println("<< 수강 신청 >>");
		System.out.println("### 모집 중인 과정 번호를 입력하여 수강 신청하세요.");
		System.out.print("### 과정번호:");
		int courseNo = keyboard.readInt();
		System.out.println(courseService.registerCourse(loginUser.getId(), courseNo));
	}

	private void 학생신청취소() {
		System.out.println("<< 수강 신청 취소 >>");
		System.out.println("### 본인이 신청한 과정의 등록번호를 입력하여 취소하세요.");
		System.out.print("### 등록번호: ");
		int regNo = keyboard.readInt();
		System.out.println(courseService.dropCourse(loginUser.getId(), regNo));
	}

	private void 학생신청현황조회() {
		System.out.println("<< 수강 신청 현황 >>");
		List<CourseRegistration> courseRegistrations = courseService.getCourseRegistrationsByStudent(loginUser.getId());
		if (courseRegistrations.isEmpty()) {
			System.out.println("개설하신 과정이 없습니다.");
		} else {
			System.out.println("-------------------------------------------------------------");
			System.out.println("등록번호\t등록일자      취소여부\t과정번호/과목명");
			System.out.println("-------------------------------------------------------------");
			for (CourseRegistration courseRegistration : courseRegistrations) {
				System.out.println(courseRegistration.getRegNo() + "\t"
						+ courseRegistration.getCreateDate() + "  "
						+ ("N".equals(courseRegistration.getCanceled()) ? "정상등록" : "취소됨") + "\t"
						+ courseRegistration.getCourseNo() + "/"
						+ courseRegistration.getCourseName());
			}
			System.out.println("-------------------------------------------------------------");
		}
	}

	private void 강사과정조회() {
		System.out.println("<< 과정 조회 >>");
		List<Course> courses = courseService.getCoursesByTeacher(loginUser.getId());
		if (courses.isEmpty()) {
			System.out.println("개설하신 과정이 없습니다.");
		} else {
			System.out.println("-------------------------------------------------------------");
			System.out.println("과정번호\t모집정원\t신청자수\t과정상태\t과목명");
			System.out.println("-------------------------------------------------------------");
			for (Course course : courses) {
				System.out.println(course.getNo() + "\t"
								+ course.getQuota() + "\t"
								+ course.getRegCnt() + "\t"
								+ course.getStatus() + "\t"
								+ course.getName());
			}
			System.out.println("-------------------------------------------------------------");
		}
	}

	private void 강사과정등록() {
		System.out.println("<< 과정 등록 >>");
		System.out.println("과목명, 모집정원을 입력하여 과정을 개설하세요.");
		System.out.print("### 과목명:");
		String name = keyboard.readString();
		System.out.print("### 모집정원:");
		int quota = keyboard.readInt();
		
		int no = courseService.createCourse(name, quota, loginUser.getId());
		System.out.println("### " + no + "번 과정이 개설되었습니다.");
	}

	private void 강사과정취소() {
		System.out.println("<< 과정 개설 취소 >>");
		System.out.println("과정번호를 입력하여 과정을 폐쇄하세요.");
		System.out.print("### 과정번호:");
		System.out.println(courseService.closeCourse(keyboard.readInt(), loginUser.getId()));
	}

	private void 강사과정현황조회() {
		System.out.println("<< 과정 현황 조회 >>");
		System.out.println("조회할 본인의 과정번호를 입력하세요.");
		System.out.print("### 과정번호:");
		Object[] courseDto = courseService.getCourseDetail(keyboard.readInt(), loginUser.getId());
		if (courseDto == null) {
			System.out.println("본인의 과정번호가 아닙니다.");
			return;
		}
		System.out.println("모집정원\t신청자수\t과정상태\t개설일        과목명");
		System.out.println("-------------------------------------------------------------");
		Course course = (Course) courseDto[0];
		System.out.println(course.getQuota() + "\t"
				+ course.getRegCnt() + "\t"
				+ course.getStatus() + "\t"
				+ course.getCreateDate() + "  "
				+ course.getName());
		System.out.println("-------------------------------------------------------------");
		System.out.println();
		System.out.println("등록번호\t학생이름\t취소여부\t신청일");
		System.out.println("-------------------------------------------------------------");
		@SuppressWarnings("unchecked")
		List<CourseRegistration> courseRegistrations = (List<CourseRegistration>) courseDto[1];
		for (CourseRegistration courseRegistration : courseRegistrations) {
			System.out.println(courseRegistration.getRegNo() + "\t"
					+ courseRegistration.getStudentIdOrName() + "\t"
					+ ("N".equals(courseRegistration.getCanceled()) ? "정상등록" : "취소됨") + "\t"
					+ courseRegistration.getCreateDate());
		}
		System.out.println("-------------------------------------------------------------");
	}

	private void 학생로그인() {
		System.out.println("<< 학생 로그인 >>");
		System.out.println("아이디, 비밀번호를 입력하여 로그인하세요.");
		System.out.print("### 아이디:");
		String id = keyboard.readString();
		System.out.print("### 비밀번호:");
		String password = keyboard.readString();
		
		loginUser = userService.login(id, password, 's');
		System.out.println("### 로그인되었습니다.");
	}

	private void 강사로그인() {
		System.out.println("<< 강사 로그인 >>");
		System.out.println("아이디, 비밀번호를 입력하여 로그인하세요.");
		System.out.print("### 아이디:");
		String id = keyboard.readString();
		System.out.print("### 비밀번호:");
		String password = keyboard.readString();
		
		loginUser = userService.login(id, password, 't');
		System.out.println("### 로그인되었습니다.");
	}

	private void 학생회원가입() {
		System.out.println("<< 학생 등록 >>");
		userService.registerUser(가입정보입력(), 's');
		System.out.println("### 신규 학생 등록이 완료되었습니다.");
	}

	private void 강사회원가입() {
		System.out.println("<< 강사 등록 >>");
		userService.registerUser(가입정보입력(), 't');
		System.out.println("### 신규 강사 등록이 완료되었습니다.");
	}
	
	private User 가입정보입력() {
		System.out.println("### 아이디, 비밀번호, 이름, 전화번호, 이메일을 입력하여 가입하세요.");
		System.out.print("### 아이디:");
		String id = keyboard.readString();
		System.out.print("### 비밀번호:");
		String password = keyboard.readString();
		System.out.print("### 이름:");
		String name = keyboard.readString();
		System.out.print("### 전화번호:");
		String phone = keyboard.readString();
		System.out.print("### 이메일:");
		String email = keyboard.readString();
		return new User(id, password, name, phone, email);
	}
}
