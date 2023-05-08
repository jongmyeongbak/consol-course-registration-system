package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.ConnUtils;
import vo.User;

public class UserDao {
	
	private static UserDao instance = new UserDao();
	private UserDao() {}
	public static UserDao getInstance() {
		return instance;
	}
	
	public void insertUser(User user, char type) {
		String sql = null;
		if (type == 's') {
			sql = "INSERT INTO ACADEMY_STUDENTS"
					+ "(STUDENT_ID, STUDENT_PASSWORD, STUDENT_NAME, STUDENT_PHONE, STUDENT_EMAIL) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)";
		} else if (type == 't') {
			sql = "INSERT INTO ACADEMY_TEACHERS"
					+ "(TEACHER_ID, TEACHER_PASSWORD, TEACHER_NAME, TEACHER_PHONE, TEACHER_EMAIL, TEACHER_SALARY) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, 0)";
		}
		try (Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, user.getId());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getName());
			pstmt.setString(4, user.getPhone());
//			if (user.getEmail() != "" || type == 's') {
				pstmt.setString(5, user.getEmail());
//			}
//			else pstmt.setNull(5, java.sql.Types.VARCHAR);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
//	public void insertStudent(User user) {
//		String sql = "INSERT INTO ACADEMY_STUDENTS"
//				+ "(STUDENT_ID, STUDENT_PASSWORD, STUDENT_NAME, STUDENT_PHONE, STUDENT_EMAIL) "
//				+ "VALUES "
//				+ "(?, ?, ?, ?, ?)";
//		insertUser(user, sql);
//	}
//	
//	public void insertTeacher(User user) {
//		String sql = "INSERT INTO ACADEMY_TEACHERS"
//				+ "(TEACHER_ID, TEACHER_PASSWORD, TEACHER_NAME, TEACHER_PHONE, TEACHER_EMAIL) "
//				+ "VALUES "
//				+ "(?, ?, ?, ?, ?)";
//		insertUser(user, sql);
//	}
	
	public User getUserById(String id, char type) {
		String sql = null;
		if (type == 's') {
			sql = "SELECT * "
					+ "FROM ACADEMY_STUDENTS "
					+ "WHERE STUDENT_ID = ? ";
		} else if (type == 't') {
			sql = "SELECT * "
					+ "FROM ACADEMY_TEACHERS "
					+ "WHERE TEACHER_ID = ?";
		}
		User user = null;
		try (Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, id);
			
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					if (type == 's') {
						user = new User(rs.getString("student_id"),
								rs.getString("student_password"),
								rs.getString("student_name"),
								rs.getString("student_deleted"));
					} else if (type == 't') {
						user = new User(rs.getString("teacher_id"),
								rs.getString("teacher_password"),
								rs.getString("teacher_name"),
								rs.getString("teacher_retired"));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return user;
	}

//	public User getStudentById(String id) {
//		String sql = "SELECT * "
//				+ "FROM ACADEMY_STUDENTS "
//				+ "WHERE STUDENT_ID = ? "
//				+ "AND STUDENT_DELETED = 'N'";
//		return getUserById(id, sql, 's');
//	}
//	
//	public User getTeacherById(String id) {
//		String sql = "SELECT * "
//				+ "FROM ACADEMY_TEACHERS "
//				+ "WHERE TEACHER_ID = ?"
//				+ "AND TEACHER_RETIRED = 'N'";
//		return getUserById(id, sql, 't');
//	}
}
