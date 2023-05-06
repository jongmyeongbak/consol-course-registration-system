package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.ConnUtils;
import vo.Course;

public class CourseDao {

	public int getSequence() {
		String sql = "SELECT ACADEMY_COURSE_SEQ.NEXTVAL AS SEQ FROM DUAL";
		
		try {
			Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			
			rs.next();
			int seq = rs.getInt("seq");
			
			rs.close();
			pstmt.close();
			conn.close();
			return seq;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void insertCourse(Course course) {
		String sql = "INSERT INTO ACADEMY_COURSES"
				+ "(COURSE_NO, COURSE_NAME, COURSE_QUOTA, TEACHER_ID) "
				+ "VALUES "
				+ "(?, ?, ?, ?)";
		
		try {
			Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, course.getNo());
			pstmt.setString(2, course.getName());
			pstmt.setInt(3, course.getQuota());
			pstmt.setString(4, course.getTeacherIdOrName());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Course> getCourseByTeacherId(String id) {
		String sql = "SELECT COURSE_NO, COURSE_NAME, COURSE_QUOTA, COURSE_REG_CNT, COURSE_STATUS "
				+ "FROM ACADEMY_COURSES "
				+ "WHERE TEACHER_ID = ? "
				+ "ORDER BY 1";
		
		List<Course> courses = new ArrayList<>();
		try {
			Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				courses.add(new Course(rs.getInt("course_no"),
						rs.getString("course_name"),
						rs.getInt("course_quota"),
						rs.getInt("course_reg_cnt"),
						rs.getString("course_status")));
			}
			
			rs.close();
			pstmt.close();
			conn.close();
			return courses;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Course getCourseByNo(int no) {
		String sql = "SELECT COURSE_NAME, COURSE_QUOTA, COURSE_REG_CNT, COURSE_STATUS, COURSE_CREATE_DATE, TEACHER_ID "
				+ "FROM ACADEMY_COURSES "
				+ "WHERE COURSE_NO = ? "
				+ "AND ROWNUM = 1";
		
		Course course = null;
		try {
			Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				course = new Course(rs.getString("course_name"),
									rs.getInt("course_quota"),
									rs.getInt("course_reg_cnt"),
									rs.getString("course_status"),
									rs.getDate("course_create_date"),
									rs.getString("teacher_id"));
			}
			
			rs.close();
			pstmt.close();
			conn.close();
			return course;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Course> getAvailableCoursesWithTeacherName() {
		String sql = "SELECT C.COURSE_NO, C.COURSE_NAME, C.COURSE_QUOTA, C.COURSE_REG_CNT, T.TEACHER_NAME "
				+ "FROM ACADEMY_COURSES C, ACADEMY_TEACHERS T "
				+ "WHERE C.TEACHER_ID = T.TEACHER_ID "
				+ "AND COURSE_STATUS = '모집중' "
				+ "ORDER BY 1";
		
		List<Course> courses = new ArrayList<>();
		try {
			Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Course course = new Course(rs.getInt("course_no"),
										rs.getString("course_name"),
										rs.getInt("course_quota"),
										rs.getString("teacher_name"));
				course.setRegCnt(rs.getInt("course_reg_cnt"));
				courses.add(course);
			}
			
			rs.close();
			pstmt.close();
			conn.close();
			return courses;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void updateCourseRegCntAndStatus(int courseNo, int courseRegCnt, String status) {
		String sql = "UPDATE ACADEMY_COURSES "
				+ "SET "
				+ "COURSE_REG_CNT = ?,"
				+ "COURSE_STATUS = ? "
				+ "WHERE COURSE_NO = ?";
		
		try {
			Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, courseRegCnt);
			pstmt.setString(2, status);
			pstmt.setInt(3, courseNo);
			
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
