package service;

import java.util.List;

import dao.CourseDao;
import dao.CourseRegistrationDao;
import vo.Course;
import vo.CourseRegistration;

public class CourseService {

	private CourseDao courseDao = CourseDao.getInstance();
	private CourseRegistrationDao courseRegistrationDao = CourseRegistrationDao.getInstance();
	
	public int createCourse(String name, int quota, String teacherId) {
		int no = courseDao.getSequence();
		courseDao.insertCourse(new Course(no, name, quota, teacherId));
		return no;
	}
	
	public List<Course> getCoursesByTeacher(String teacherId) {
		return courseDao.getCourseByTeacherId(teacherId);
	}
	
	public String closeCourse(int no, String teacherId) {
		Course course = courseDao.getCourseByNo(no);
		if (course == null || !course.getTeacherIdOrName().equals(teacherId)) {
			return "본인의 과정번호가 아닙니다.";
		}
		courseDao.updateCourseRegCntAndStatus(no, 0, "과정취소");
		if (course.getRegCnt() != 0) {
			courseRegistrationDao.updateCourseCanceled(no);
		}
		return "과정이 폐쇄되었습니다.";
	}
	
	public Object[] getCourseDetail(int no, String teacherId) {
		Course course = courseDao.getCourseByNo(no);
		if (course == null || !course.getTeacherIdOrName().equals(teacherId)) {
			return null;
		}
		List<CourseRegistration> courseRegistrations
//		= new ArrayList<>()
		;
//		if (!course.getStatus().equals("과정취소")) {
			courseRegistrations = courseRegistrationDao.getCourseRegistrationsByCourseNo(no);
//		}
		return new Object[] {course, courseRegistrations};
	}
	
	public List<Course> availableCourses() {
		return courseDao.getAvailableCoursesWithTeacherName("모집중");
	}
	
	public String registerCourse(String studentId, int courseNo) {
		if (courseRegistrationDao.countValidCourseRegistrationWithStudentAndCourseNo(studentId, courseNo) > 0) {
			return "이미 신청한 과정입니다.";
		}
		Course course = courseDao.getCourseByNo(courseNo);
		if (course == null) {
			return "과정번호가 존재하지 않습니다.";
		}
		if (!"모집중".equals(course.getStatus())) {
			return "신청할 수 없습니다. 과정이 취소되었거나 정원 초과입니다.";
		}
		int regCnt = course.getRegCnt() + 1;
		String status = (regCnt < course.getQuota()) ? "모집중" : "모집완료";
		courseDao.updateCourseRegCntAndStatus(courseNo, regCnt, status);
		courseRegistrationDao.insertCourseRegistration(studentId, courseNo);
		return "### 신청하신 과정이 등록되었습니다.";
	}
	
	public String dropCourse(String studentId, int regNo) {
		List<CourseRegistration> courseRegistrations = courseRegistrationDao.getCourseRegistrationsByStudentId(studentId);
		for (CourseRegistration courseRegistration : courseRegistrations) {
			if (courseRegistration.getRegNo() == regNo && "N".equals(courseRegistration.getCanceled())) {
				int courseNo = courseRegistration.getCourseNo();
				Course course = courseDao.getCourseByNo(courseNo);
				int regCnt = course.getRegCnt() - 1;
				String status = (regCnt < course.getQuota()) ? "모집중" : "모집완료";
				courseRegistrationDao.updateCourseRegCanceled(regNo);
				courseDao.updateCourseRegCntAndStatus(courseNo, regCnt, status);
				return "[" + course.getName() + "] 수강신청이 취소되었습니다.";
			}
		}
		return "본인의 등록번호가 아니거나 이미 취소되었습니다.";
	}
	
	public List<CourseRegistration> getCourseRegistrationsByStudent(String studentId) {
		return courseRegistrationDao.getCourseRegistrationsByStudentId(studentId);
	}
}
