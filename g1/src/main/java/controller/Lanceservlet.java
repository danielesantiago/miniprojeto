package controller;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Lance;
import model.LanceService;
import database.LanceDao;

@WebServlet("/lanceservlet")
public class Lanceservlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    private LanceDao lanceDao;

    public void init(){
        lanceDao = new LanceDao();
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        PrintWriter out = response.getWriter();
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
         
        String nome = request.getParameter("nome");
        String produto = request.getParameter("produto");
        float valor_lance = Float.parseFloat(request.getParameter("lance"));

        LanceService lanceService = new LanceService();
        if (!lanceService.validarUsername(nome)){
            out.write("Erro: esse usuário não está cadastrado.");
            return;
        }

        if (!lanceService.validarCodigo(produto))
        {
            out.write("Erro: esse produto não está cadastrado.");
            return;
        }
        Lance lance = new Lance();
        lance.setNome(nome);
        lance.setProduto(produto);
        lance.setValor_lance(valor_lance);

        try{
            /* Chama o método do DAO que insere os dados no BD e passa Bean encapsulando os dados */
            lanceDao.registraLance(lance);
            out.write("Lance registrado com sucesso.");
        }catch(Exception e){
            e.printStackTrace();
            out.write("Erro: Não foi possível registrar o lance.");
        }
    }
}

