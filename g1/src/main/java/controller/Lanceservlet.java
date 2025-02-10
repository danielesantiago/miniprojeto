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
public class Lanceservlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private LanceDao lanceDao;

    @Override
    public void init() throws ServletException {
        lanceDao = new LanceDao();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            // Validação dos parâmetros
            String nome = request.getParameter("nome");
            String produto = request.getParameter("produto");
            String lanceParam = request.getParameter("lance");

            if (nome == null || nome.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("Erro: parâmetro 'nome' é obrigatório.");
                return;
            }

            if (produto == null || produto.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("Erro: parâmetro 'produto' é obrigatório.");
                return;
            }

            if (lanceParam == null || lanceParam.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("Erro: parâmetro 'lance' é obrigatório.");
                return;
            }

            float valorLance;
            try {
                valorLance = Float.parseFloat(lanceParam);
            } catch (NumberFormatException ex) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("Erro: parâmetro 'lance' inválido. Deve ser um número.");
                return;
            }

            // Validação de regras de negócio
            LanceService lanceService = new LanceService();
            if (!lanceService.validarUsername(nome)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("Erro: esse usuário não está cadastrado.");
                return;
            }

            if (!lanceService.validarCodigo(produto)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("Erro: esse produto não está cadastrado.");
                return;
            }

            // Monta o objeto Lance com os dados validados
            Lance lance = new Lance();
            lance.setNome(nome);
            lance.setProduto(produto);
            lance.setValor_lance(valorLance);

            // Validação: o valor do novo lance deve ser maior que o maior lance já existente.
            try {
                if (!lanceService.validarValorLance(lance)) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.write("Erro: o valor do lance deve ser maior que o lance atual.");
                    return;
                }
            } catch (ClassNotFoundException e) {
                log("Erro na validação do lance", e);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.write("Erro interno: não foi possível validar o lance.");
                return;
            }            

            // Tenta registrar o lance no banco de dados
            try {
                int result = lanceDao.registraLance(lance);
                if (result > 0) {
                    out.write("Lance registrado com sucesso.");
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    out.write("Erro: Nenhum registro foi inserido.");
                }
            } catch (Exception e) {
                // Registra o erro no log do servidor
                log("Erro ao registrar lance", e);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.write("Erro: Não foi possível registrar o lance devido a um erro interno.");
            }
        }
    }
}
