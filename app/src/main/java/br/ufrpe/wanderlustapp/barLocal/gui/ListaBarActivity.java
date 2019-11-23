package br.ufrpe.wanderlustapp.barLocal.gui;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.barLocal.dominio.BarLocal;
import br.ufrpe.wanderlustapp.barLocal.negocio.BarLocalServices;
import br.ufrpe.wanderlustapp.infra.Sessao;


public class ListaBarActivity extends AppCompatActivity {

    BarLocalServices barLocalServices = new BarLocalServices(this);
    public static final String TITULO_APPBAR_LISTA = "Lista de bares";
    private ListBaresAdapter adapter;
    private int posicaoEnviada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_bares);
        setTitle(TITULO_APPBAR_LISTA);
        configuraRecyclerview();
        configuraBtnInsereBar();
    }

    private void configuraBtnInsereBar() {
        TextView btnInsereBar = findViewById(R.id.lista_bares_insere_bar);
        btnInsereBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListaBarActivity.this, CadastraBarActivity.class));
            }
        });
    }

    private void insereBar(BarLocal barLocal) {
        try {
            barLocalServices.cadastrar(barLocal);
            adapter.adiciona(barLocal);
            Toast.makeText(getApplicationContext(), "Bar cadastrado", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Bar já cadastrado", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        BarLocal barLocal = Sessao.instance.getBarLocal();
        if (barLocal != null){
            if (barLocal.getId() == 0){
                insereBar(barLocal);
                Sessao.instance.resetBar();
            }else{
                Sessao.instance.resetBar();
            }
        }

        super.onResume();
    }

    private List<BarLocal> geraLista(){
        return barLocalServices.getLista();
    }

    private void setAdapter (RecyclerView recyclerView){
        adapter = new ListBaresAdapter(this,geraLista());
        recyclerView.setAdapter(adapter);
    }

    private void configuraRecyclerview() {
        RecyclerView listaBares = findViewById(R.id.lista_bares_recyclerview);
        setAdapter(listaBares);
    }

}
