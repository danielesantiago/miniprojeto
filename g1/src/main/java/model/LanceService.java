package model;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import database.LanceDao;

public class LanceService {
    private LanceDao lanceDao;
    public LanceService(){
        this.lanceDao = new LanceDao();
    }

    public boolean validarUsername(String username){
        try{
            List<String> usuarios = this.lanceDao.usuariosBd();
            for (String nome : usuarios){
                if(nome.equals(username)){
                    return true;
                }
            }
            return false;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        } 
    }

    public boolean validarCodigo(String codigo){
        try{
            List<String> produtos = this.lanceDao.produtosBd();
            for (String sku : produtos){
                if (sku.equals(codigo)){
                    return true;
                }
            }
            return false;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean validarValorLance(Lance lance) throws ClassNotFoundException {
        Float maiorLance = lanceDao.buscarMaiorLancePorProduto(lance.getProduto());
        // Se não houver nenhum lance anterior, aceita o novo lance
        if (maiorLance == null) {
            return true;
        }
        // Retorna true somente se o novo lance for estritamente maior que o atual
        return lance.getValor_lance() > maiorLance;
    }

        
}