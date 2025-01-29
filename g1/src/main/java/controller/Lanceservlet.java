package controller;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Lance;
import database.LanceDao;

@WebServlet("/lanceservlet")
public class Lanceservlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    private LanceDao lanceDao;

    public void init(){
        lanceDao = new LanceDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        String nome = request.getParameter("nome");
        String produto = request.getParameter("produto");
        float valor_lance = Float.parseFloat(request.getParameter("lance"));

        Lance lance = new Lance();
        lance.setNome(nome);
        lance.setProduto(produto);
        lance.setValor_lance(valor_lance);

        try{
            /* Chama o método do DAO que insere os dados no BD e passa Bean encapsulando os dados */
            lanceDao.registraLance(lance);
        }catch(Exception e){
            e.printStackTrace();
        }

            //redireciona a execução para uma view
            response.sendRedirect("index.jsp");
    }
}

