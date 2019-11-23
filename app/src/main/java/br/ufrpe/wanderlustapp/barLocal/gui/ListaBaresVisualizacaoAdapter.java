package br.ufrpe.wanderlustapp.barLocal.gui;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.barLocal.dominio.BarLocal;
import br.ufrpe.wanderlustapp.infra.Sessao;
import br.ufrpe.wanderlustapp.pessoa.dominio.Pessoa;

public class ListaBaresVisualizacaoAdapter extends RecyclerView.Adapter<br.ufrpe.wanderlustapp.barLocal.gui.ListaBaresVisualizacaoAdapter.BarViewHolder> {

    private final Context context;
    private final List<BarLocal> baresAvaliacao;

    public ListaBaresVisualizacaoAdapter(Context context, List<BarLocal> bares) {
        this.context = context;
        this.baresAvaliacao = bares;
    }

    @NonNull
    @Override
    public ListaBaresVisualizacaoAdapter.BarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.item_ponto_avaliacao,parent,false);
        return new ListaBaresVisualizacaoAdapter.BarViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaBaresVisualizacaoAdapter.BarViewHolder holder, int position) {
        BarLocal bar = baresAvaliacao.get(position);
        holder.vincula(bar);
    }

    @Override
    public int getItemCount() {
        return baresAvaliacao.size();
    }

    class BarViewHolder extends RecyclerView.ViewHolder {
        private final TextView titulo;
        private final TextView descricao;
        private BarLocal bar;
        private Pessoa pessoa = Sessao.instance.getUsuario().getPessoa();

        public BarViewHolder(@NonNull final View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.item_bar_nome_avaliacao);
            descricao = itemView.findViewById(R.id.item_bar_descricao_avaliacao);

        }

        public void vincula(BarLocal bar) {
            this.bar = bar;
            titulo.setText(this.bar.getNome());
            descricao.setText(this.bar.getDescricao());
        }

    }
}

