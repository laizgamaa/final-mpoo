package br.ufrpe.wanderlustapp.barLocal.gui;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.barLocal.dominio.BarLocal;
import br.ufrpe.wanderlustapp.cidade.dominio.Cidade;
import br.ufrpe.wanderlustapp.cidade.negocio.CidadeServices;
import br.ufrpe.wanderlustapp.infra.Sessao;
import br.ufrpe.wanderlustapp.pais.dominio.Pais;
import br.ufrpe.wanderlustapp.pais.negocio.PaisServices;
import br.ufrpe.wanderlustapp.pontoTuristico.dominio.PontoTuristico;

public class CadastraBarActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR_INSERE = "Cadastrar bar";
    CidadeServices cidadeServices = new CidadeServices(this);
    PaisServices paisServices = new PaisServices(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra_bares);
        setTitle(TITULO_APPBAR_INSERE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_ponto_salva, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_formulario_ponto_ic_salva){
            BarLocal barLocal = criaBarLocal();
            if(verficaCampos()) {
                Sessao.instance.setBarLocal(barLocal);
            }
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private BarLocal criaBarLocal() {
        BarLocal barLocal = new BarLocal();
        if (verficaCampos()){
            preencheAtributosBar(barLocal);
        }
        return barLocal;
    }

    private boolean verficaCampos(){
        EditText nome = findViewById(R.id.formulario_bar_nome);
        EditText descricao = findViewById(R.id.formulario_bar_descricao);
        return nome.length() > 0 && descricao.length() > 0;
    }

    private void preencheAtributosBar(BarLocal barLocal) {
        EditText nome = findViewById(R.id.formulario_bar_nome);
        EditText descricao = findViewById(R.id.formulario_bar_descricao);
        barLocal.setNome(nome.getText().toString());
        barLocal.setDescricao(descricao.getText().toString());
        barLocal.setCidade(createCidadePadrao());
    }

    private Cidade createCidadePadrao() {
        Pais pais = new Pais();
        pais.setNome("Brasil");
        paisServices.cadastrar(pais);
        Cidade cidade = new Cidade();
        cidade.setNome("Recife");
        cidade.setPais(pais);
        cidadeServices.cadastrar(cidade);
        return cidade;
    }
}
