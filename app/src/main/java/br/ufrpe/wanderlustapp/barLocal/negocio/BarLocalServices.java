package br.ufrpe.wanderlustapp.barLocal.negocio;

import android.content.Context;
import java.util.List;
import br.ufrpe.wanderlustapp.barLocal.dominio.BarLocal;
import br.ufrpe.wanderlustapp.barLocal.persistencia.BarLocalDAO;


public class BarLocalServices {
    private BarLocalDAO barLocalDAO;

    public BarLocalServices(Context context) { barLocalDAO = new BarLocalDAO(context); }

    public void cadastrar(BarLocal barLocal) throws Exception {
        if (barLocalDAO.getBarLocalByNome(barLocal.getNome()) != null){
            throw new Exception();
        }
        long idBar = barLocalDAO.cadastrar(barLocal);
        barLocal.setId(idBar);
    }

    public List<BarLocal> getLista(){
        return barLocalDAO.getListBarLocal();
    }

}
