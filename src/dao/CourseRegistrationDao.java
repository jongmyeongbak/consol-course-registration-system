package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.ConnUtils;
import vo.CourseRegistration;

public class CourseRegistrationDao {
	
	private static final CourseRegistrationDao instance = new CourseRegistrationDao();
	private CourseRegistrationDao() {}
	public static CourseRegistrationDao getInstance() {
		return instance;
	}

	public void insertCourseRegistration(String studentId, int courseNo) {
		String sql = "INSERT INTO ACADEMY_COURSE_REGISTRATIONS "
				+ "(STUDENT_ID, COURSE_NO) "
				+ "VALUES "
				+ "(?, ?)";
		
		try (Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, studentId);
			pstmt.setInt(2, courseNo);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<CourseRegistration> getCourseRegistrationsByStudentId(String studentId) {
		String sql = "SELECT R.REG_NO, R.COURSE_NO, R.REG_CANCELED, R.REG_CREATE_DATE, C.COURSE_NAME "
				+ "FROM ACADEMY_COURSE_REGISTRATIONS R, ACADEMY_COURSES C "
				+ "WHERE R.COURSE_NO = C.COURSE_NO "
				+ "AND STUDENT_ID = ? "
				+ "ORDER BY 3, 1";
		
		List<CourseRegistration> courseRegistrations = new ArrayList<>();
		try (Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, studentId);
			
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					courseRegistrations.add(new CourseRegistration(rs.getInt("reg_no"),
							rs.getInt("course_no"),
							rs.getString("reg_canceled"),
							rs.getDate("reg_create_date"),
							rs.getString("course_name")));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return courseRegistrations;
	}
	
	public List<CourseRegistration> getCourseRegistrationsByCourseNo(int courseNo) {
		String sql = "SELECT R.REG_NO, S.STUDENT_NAME, R.REG_CANCELED, R.REG_CREATE_DATE "
				+ "FROM ACADEMY_COURSE_REGISTRATIONS R, ACADEMY_STUDENTS S "
				+ "WHERE R.STUDENT_ID = S.STUDENT_ID "
				+ "AND R.COURSE_NO = ? "
				+ "ORDER BY 3, 1";
		
		List<CourseRegistration> courseRegistrations = new ArrayList<>();
		try (Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, courseNo);
			
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					courseRegistrations.add(new CourseRegistration(rs.getInt("reg_no"),
							rs.getString("student_name"),
							rs.getString("reg_canceled"),
							rs.getDate("reg_create_date")));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return courseRegistrations;
	}
	
	public int countValidCourseRegistrationWithStudentAndCourseNo(String studentId, int courseNo) {
		String sql = "SELECT COUNT(*) AS CNT "
				+ "FROM ACADEMY_COURSE_REGISTRATIONS "
				+ "WHERE STUDENT_ID = ? "
				+ "AND COURSE_NO = ? "
				+ "AND REG_CANCELED = 'N'"
				+ "AND ROWNUM = 1";

		try (Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, studentId);
			pstmt.setInt(2, courseNo);
			
			try (ResultSet rs = pstmt.executeQuery()) {
				rs.next();
				return rs.getInt("cnt");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void updateCourseCanceled(int courseNo) {
		String sql = "UPDATE ACADEMY_COURSE_REGISTRATIONS "
				+ "SET "
				+ "REG_CANCELED = 'Y' "
				+ "WHERE COURSE_NO = ?";
		
		try (Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, courseNo);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void updateCourseRegCanceled(int regNo) {
		String sql = "UPDATE ACADEMY_COURSE_REGISTRATIONS "
				+ "SET "
				+ "REG_CANCELED = 'Y' "
				+ "WHERE REG_NO = ?";
		
		try (Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, regNo);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
