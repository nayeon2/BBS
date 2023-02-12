package user;

import java.sql.*;

public class UserDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public UserDAO() {
		try {
			//1. JDBC 드라이버 로딩
			Class.forName("com.mysql.jdbc.Driver"); 
			String dbURL = "jdbc:mysql://localhost:3306/BBS";
			String dbID = "root";
			String dbPassword = "root";
			//2. 데이터베이스 커넥션 생성
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public int login(String userID, String userPassword) {
		String SQL = "SELECT userPassword from USER where userID = ?";
		try {
			//3. Statement 생성
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			//4. 쿼리 실행
			rs = pstmt.executeQuery();
			//5. 쿼리 실행 결과 사용
			if(rs.next()) {
				if(rs.getString(1).equals(userPassword)) {
					return 1;
				}else {
					return 0; //비밀번호 불일치
				}
			}
			return -1; //아이디가 없음
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -2; //데이터베이스 오류
	}
	
	public int join(User user) {
		String SQL = "insert into user values(?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserGender());
			pstmt.setString(5, user.getUserEmail());
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
