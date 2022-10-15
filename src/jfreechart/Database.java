package jfreechart;

import java.sql.*;
import java.util.*;

public class Database {

	String URL = "jdbc:oracle:thin:@192.168.0.50:1521:xe";
	String USER ="scott";
	String PASS = "tiger";

	public ArrayList<ArrayList> getData() {

		ArrayList<ArrayList> data = new ArrayList<ArrayList>();
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(URL, USER , PASS);	
			
			//**********************************************************************************************
			/* 1) 업무별 평균 월급
			String sql = "SELECT        NVL(JOB, '미정') JOB, ROUND(AVG(SAL)) AVG\r\n"
									 + "FROM         EMP\r\n"
									 + "GROUP BY  JOB ";
			
			2) 월급을 많이 받는 10명
			String sql = "SELECT       ENAME, NVL(SAL, 0)  SAL "
								+ "   FROM        (SELECT ENAME, NVL(SAL, 0) SAL FROM EMP ORDER BY SAL DESC)"
								+ "   WHERE       ROWNUM < 11";
			
			3) 월별 입사한 인원 수 */
			String sql = 	"  SELECT         TO_CHAR(HIREDATE, 'MM') MONTH, COUNT(*) COUNT"
								+ "  FROM           EMP  "
								+ "  WHERE         HIREDATE IS NOT NULL  "
								+ "  GROUP BY    TO_CHAR(HIREDATE, 'MM')  " // 별칭 부여하면 정상 출력 안 됨
								+ "  ORDER BY    TO_CHAR(HIREDATE, 'MM')";   // 별칭 부여하면 정상 출력 안 됨
			//**********************************************************************************************
			
			PreparedStatement stmt = con.prepareStatement( sql );	

			ResultSet rset = stmt.executeQuery();

			while( rset.next() ){
				ArrayList temp = new ArrayList();
				temp.add( rset.getInt("COUNT"));				//****************
				temp.add( rset.getString("MONTH"));			//****************		
				data.add(temp);
			}
			rset.close();
			stmt.close();
			con.close();
		} catch(Exception ex){
			System.out.println("에러 : " + ex.getMessage() );
		}
		return data;
	}
}
