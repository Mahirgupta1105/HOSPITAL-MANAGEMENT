

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Adds new doctor in doctor Table<br> CreateDoctor
 */
@WebServlet("/CreateDoctor")
public class CreateDoctor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateDoctor() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	doGet(request, response);
	PrintWriter out = response.getWriter();
	
	// Get parameters
	String  name = request.getParameter("name");
	String  email = request.getParameter("email");
	String  phone = request.getParameter("phone");
	String  age = request.getParameter("age");
	String  sal = request.getParameter("sal");
	String  spec = request.getParameter("spec");
	
	// Validate inputs
	ValidationUtils.ValidationResult nameResult = ValidationUtils.validateName(name);
	if (!nameResult.isValid()) {
		response.setContentType("text/html");  
		out.println("<br><br><br><h1 align=center><font color=\"red\">" + nameResult.getErrorMessage() + "</font></h1>");
		return;
	}
	
	ValidationUtils.ValidationResult emailResult = ValidationUtils.validateEmail(email);
	if (!emailResult.isValid()) {
		response.setContentType("text/html");  
		out.println("<br><br><br><h1 align=center><font color=\"red\">" + emailResult.getErrorMessage() + "</font></h1>");
		return;
	}
	
	ValidationUtils.ValidationResult phoneResult = ValidationUtils.validatePhone(phone);
	if (!phoneResult.isValid()) {
		response.setContentType("text/html");  
		out.println("<br><br><br><h1 align=center><font color=\"red\">" + phoneResult.getErrorMessage() + "</font></h1>");
		return;
	}
	
	ValidationUtils.ValidationResult ageResult = ValidationUtils.validateAge(age);
	if (!ageResult.isValid()) {
		response.setContentType("text/html");  
		out.println("<br><br><br><h1 align=center><font color=\"red\">" + ageResult.getErrorMessage() + "</font></h1>");
		return;
	}
	
	ValidationUtils.ValidationResult salResult = ValidationUtils.validateSalary(sal);
	if (!salResult.isValid()) {
		response.setContentType("text/html");  
		out.println("<br><br><br><h1 align=center><font color=\"red\">" + salResult.getErrorMessage() + "</font></h1>");
		return;
	}
	
	ValidationUtils.ValidationResult specResult = ValidationUtils.validateNotEmpty(spec, "Specialization");
	if (!specResult.isValid()) {
		response.setContentType("text/html");  
		out.println("<br><br><br><h1 align=center><font color=\"red\">" + specResult.getErrorMessage() + "</font></h1>");
		return;
	}
	
	try {
		Connection c = GetConnection.getConnection();
		String joindate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			String patients = "-1";    // initial is -1 always for every doctor
			String sql = "insert into doctor(name,email,phone,age,joindate,salary,specialist,patients) values(?,?,?,?,?,?,?,?)";
	
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, email);
			ps.setString(3, phone);
			ps.setInt(4, Integer.valueOf(age));
			ps.setString(5, joindate);
			ps.setLong(6,Long.valueOf(sal));
			ps.setString(7, spec);
			ps.setString(8, patients);
			ps.addBatch();
			
			int successCount = 0;
			successCount += ps.executeBatch()[0];
			ps.clearBatch();
			
			if(successCount == 1) {
				response.setContentType("text/html");  
				out.println("<br><br><br><h1 align=center><font color=\"green\">SUCCESSFUL<br></font></h1><script type=\"text/javascript\">");  
//				out.println("redirectURL = \"welcome.html\";setTimeout(\"location.href = redirectURL;\",\"5000\");");  
				out.println("</script>");
			}
			else {
				response.setContentType("text/html");  
				out.println("<br><br><br><h1 align=center><font color=\"red\">THERE IS SOME PROBLEM<br></font></h1><script type=\"text/javascript\">");  
//				out.println("redirectURL = \"welcome.html\";setTimeout(\"location.href = redirectURL;\",\"5000\");");  
				out.println("</script>");
			}
		} catch (SQLException e) { 
			e.printStackTrace();
		}
	}

}
