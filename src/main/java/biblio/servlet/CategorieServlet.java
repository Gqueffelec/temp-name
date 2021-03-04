package biblio.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import biblio.controller.CategorieController;
import biblio.model.Categorie;

/**
 * Servlet implementation class CategorieServlet
 */
@WebServlet("/CategorieServlet")
public class CategorieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CategorieController controller;

	@Override
	public void init() throws ServletException {
		controller = new CategorieController();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("actionCategorie");
		System.out.println("asset :"+action);
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		switch (action) {
		case "create":
			if (request.getParameter("nom").equals("")||request.getParameter("label").equals("")||request.getParameter("info").equals("")){
				response.getWriter().write("Veuillez remplir tous les champs");
				break;
			}
			List<Categorie> listeTemp = controller.getAll();
			if (listeTemp.stream().anyMatch(e -> e.getNom().equalsIgnoreCase(request.getParameter("nom")))) {
				response.getWriter().write("Ce nom �xiste d�j�");
				break;
			}
			if (listeTemp.stream().anyMatch(e -> e.getLabel().equalsIgnoreCase(request.getParameter("label")))) {
				response.getWriter().write("Ce label �xiste d�j�");
				break;
			}
			Categorie temp = controller.create(Categorie.builder().nom(request.getParameter("nom")).label(request.getParameter("label")).information_technique(request.getParameter("info")).build());
			System.out.println(temp);
			if (temp.getId()!=0) {
				response.getWriter().write("Cat�gorie ajout�e");
			} else {
				response.getWriter().write("Probl�me d'ajout");
			}
			break;
		case "remove":
			if (request.getParameter("nom").equals("")||request.getParameter("label").equals("")||request.getParameter("info").equals("")){
				response.getWriter().write("Veuillez remplir tous les champs");
			}
			break;
		case "update":
			if (request.getParameter("nom").equals("")||request.getParameter("label").equals("")||request.getParameter("info").equals("")){
				response.getWriter().write("Veuillez remplir tous les champs");
			}
			System.out.println(request.getParameter("nom"));
			//controller.update(null);
			break;
		case "getid":
			
			break;
		case "getall":
			List<Categorie> liste = controller.getAll();
			String json = new Gson().toJson(liste);
			response.setContentType("application/json");
			response.getWriter().write(json);
			break;
		default:
			response.sendRedirect("accueil");
			break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
