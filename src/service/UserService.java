package service;

import controller.LoginUser;
import dao.UserDao;
import vo.User;

public class UserService {

	private UserDao dao = new UserDao();
	
	public void registerUser(User user, char type) {
		User savedUser = dao.getUserById(user.getId(), type);
		if (savedUser != null) {
			throw new RuntimeException("[" + user.getId() + "] 아이디는 이미 사용 중입니다.");
		} else {
			dao.insertUser(user, type);
		}
	}
	
//	public void registerStudent(User user) {
//		registerUser(user, 's');
//	}
//	
//	public void registerTeacher(User user) {
//		registerUser(user, 't');
//	}
	
	public LoginUser login(String id, String password, char type) {
		User savedUser = dao.getUserById(id, type);
		if (savedUser == null || savedUser.getInvalid() == "Y") {
			throw new RuntimeException("[" + id + "] 아이디는 유효하지 않습니다. 다른 아이디를 입력하세요.");
		} else if (!savedUser.getPassword().equals(password)) {
			throw new RuntimeException("비밀번호가 일치하지 않습니다.");
		}
		return new LoginUser(id, savedUser.getName(), (type == 't') ? "강사" : "학생");
	}
}
