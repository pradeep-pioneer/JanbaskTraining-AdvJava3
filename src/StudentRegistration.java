

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class StudentRegistration
 */
@WebServlet("/StudentRegistration")
public class StudentRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String html = "<html>"
				+ "<head>"
				+ "<title>Register Yourself</title>"
				+ "</head>"
				+ "<body BGCOLOR=\"#FDF5E6\" align=\"center\">"
				+ "<h1>Student Registration</h1>"
				+ "<form method=\"POST\" action=\"StudentRegistration\">"
				+ "<INPUT TYPE=\"TEXT\" NAME=\"firstName\" VALUE=\"First Name\"><BR>"
				+ "<INPUT TYPE=\"TEXT\" NAME=\"lastName\" VALUE=\"Last Name\"><BR>"
				+ "<INPUT TYPE=\"TEXT\" NAME=\"class\" VALUE=\"Class\"><BR>"
				+ "<INPUT TYPE=\"TEXT\" NAME=\"address\" VALUE=\"Address\"><BR>"
				+ "<INPUT TYPE=\"TEXT\" NAME=\"country\" VALUE=\"country\"><BR>"
				+ "<INPUT TYPE=\"TEXT\" NAME=\"postcode\" VALUE=\"Post Code\"><BR>"
				+ "<input type=\"SUBMIT\" value=\"Submit\"/>"
				+ "</form>"
				+ "</body></html>";
		PrintWriter output = response.getWriter();
		response.setContentType("text/html");
		output.write(html);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String classValue = request.getParameter("class");
		String address = request.getParameter("address");
		String country = request.getParameter("country");
		String postcode = request.getParameter("postcode");
		int id = submitRegistrationRequest(firstName, lastName, classValue, address, country, postcode);
		String result = "";
		if(id==-1)
			result = " Failed";
		else
			result = " Successful";
		String html = "<html>"
				+ "<head>"
				+ "<title>Register Yourself-Success</title>"
				+ "</head>"
				+ "<body>"
				+ "<h1>Student Registration:" + result + "</h1>"
				+ "<h2>Your Id: " + id + "</h2>"
				+ "</body></html>";
		PrintWriter output = response.getWriter();
		response.setContentType("text/html");
		output.write(html);
	}
	
	private int submitRegistrationRequest(String firstName, String lastName, String classValue, String address, String country, String postcode) {
		Connection connection = null;
		int registrationId=-1;
		try{
			Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DatabaseConfig.DATABASE_URL, DatabaseConfig.DATABASE_USERNAME, DatabaseConfig.DATABASE_PASSWORD);
            CallableStatement statement = connection.prepareCall(DatabaseConfig.STUDENT_REGISTER_PROC_CALL);
            statement.setString("firstName", firstName);
            statement.setString("lastName", lastName);
            statement.setString("class", classValue);
            statement.setString("address", address);
            statement.setString("country", country);
            statement.setString("postCode", postcode);
            ResultSet registrations = statement.executeQuery();
            while(registrations.next()){
                registrationId = registrations.getInt(1);
            }
            registrations.close();
            statement.close();
            connection.close();
        }catch (SQLException exception){
            exception.printStackTrace();
        }catch(ClassNotFoundException exception) {
        	exception.printStackTrace();
        }
		finally {
            try{
                if(connection!=null)
                    connection.close();
            }
            catch (SQLException exception){
                exception.printStackTrace();
            }
        }
		return registrationId;
	}

}
