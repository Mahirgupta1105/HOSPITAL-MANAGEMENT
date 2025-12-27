

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Discharge
 */
@WebServlet("/Discharge")
public class Discharge extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Discharge() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		
<<<<<<< HEAD
	double total = 0;
	Connection c = GetConnection.getConnection();
	PrintWriter out = response.getWriter();
	String pid = request.getParameter("pid");
	int days = Integer.valueOf(request.getParameter("days"));
	int daycost = Integer.valueOf(request.getParameter("daycost"));
	String medData = request.getParameter("mc");
	String[] mcs = medData.split(";");
	
	// Use PreparedStatement to prevent SQL injection
	String sq1 = "delete from patient where pid = ?";
	try (PreparedStatement ps1 = c.prepareStatement(sq1)) {
		ps1.setInt(1, Integer.valueOf(pid));
		ps1.executeUpdate();
		
		// Calculate medicine costs
		for(String mc:mcs){
			if(mc != null && !mc.trim().isEmpty()) {
				String[] parts = mc.split(",");
				if(parts.length == 2) {
					String mid = parts[0];
					int count = Integer.valueOf(parts[1]);
					
					// Use PreparedStatement for medicine query
					String sq2 = "select price from medicine where mid = ?";
					try (PreparedStatement ps2 = c.prepareStatement(sq2)) {
						ps2.setInt(1, Integer.valueOf(mid));
						try (ResultSet rs = ps2.executeQuery()) {
							if(rs.next()) {
								double price = rs.getDouble("price");
								total += (price * count);
							}
						}
					}
				}
			}
		}
		
		total += (days * daycost);
		response.setContentType("text/html");
		out.println("<h1>TOTAL MONEY TO PAY IS:<br><br><br></h1>");
		out.println("<h3>= "+total+"</h3>");
		
	} catch (SQLException e) {
		out.println("<br><br><br><h1 align=center><font color=\"red\">TRY AGAIN! Some problem<br></font></h1>");  
		e.printStackTrace();
	} catch (NumberFormatException e) {
		out.println("<br><br><br><h1 align=center><font color=\"red\">Invalid input format<br></font></h1>");  
		e.printStackTrace();
	}
=======
		double total = 0;
		Connection c = GetConnection.getConnection();
		PrintWriter out = response.getWriter();
		String pid = request.getParameter("pid");
		int days = Integer.valueOf(request.getParameter("days"));
		int daycost = Integer.valueOf(request.getParameter("daycost"));
		String medData = request.getParameter("mc");
		String[] mcs = medData.split(";");
		String sq1 = "delete from patient where pid = "+pid;
		try {
			c.createStatement().executeUpdate(sq1);
			for(String mc:mcs){
				String mid = mc.split(",")[0];
				int count = Integer.valueOf(mc.split(",")[1]);
				
				String sq2 = "select price from medicine where mid = "+mid;
				ResultSet rs = c.createStatement().executeQuery("select price from medicine where mid = "+mid);
				rs.next();
				double price = Double.valueOf(rs.getString("price"));
				total += (price * count);
			}
			
			total += (days * daycost);
			response.setContentType("text/html");
			out.println("<h1>TOTAL MONEY TO PAY IS:<br><br><br></h1>");
			out.println("<h3>= "+total+"</h3>");
			
			
		} catch (SQLException e) {
			out.println("<br><br><br><h1 align=center><font color=\"red\">TRY AGAIN! Some problem<br></font></h1>");  
			e.printStackTrace();}
>>>>>>> 097da98d41bf87d6e4bf7a3701e59bea6a3a0510
	}

}
