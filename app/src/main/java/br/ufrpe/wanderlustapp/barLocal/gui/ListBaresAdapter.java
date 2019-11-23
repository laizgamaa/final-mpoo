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
import br.ufrpe.wanderlustapp.pontoTuristico.gui.OnItemClickListener;

public class ListBaresAdapter extends RecyclerView.Adapter<ListBaresAdapter.BarViewHolder>{

private final Context context;
private final List<BarLocal> bares;
private OnItemClickListener onItemClickListener;
private ListaBarActivity listaBar = new ListaBarActivity();

public ListBaresAdapter(Context context,List<BarLocal> bares) {
        this.context = context;
        this.bares = bares;
        }

public List<BarLocal> getList(){
        return this.bares;
        }

public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        }

@Override
public ListBaresAdapter.BarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context)
        .inflate(R.layout.item_bar, parent, false);
        return new BarViewHolder(viewCriada);
        }

@Override
public void onBindViewHolder(@NonNull BarViewHolder holder, int position) {
        BarLocal bar = bares.get(position);
        holder.vincula(bar);
        }

@Override
public int getItemCount() {
        return bares.size();
        }

public void adiciona(BarLocal bar){
        bares.add(bar);
        notifyDataSetChanged();
        }

class BarViewHolder extends RecyclerView.ViewHolder {

    private final TextView titulo;
    private final TextView descricao;
    private BarLocal bar;

    public BarViewHolder(@NonNull View itemView) {
        super(itemView);
        titulo = itemView.findViewById(R.id.item_bar_nome);
        descricao = itemView.findViewById(R.id.item_bar_descricao);
    }

    public void vincula(BarLocal bar) {
        this.bar = bar;
        titulo.setText(this.bar.getNome());
        descricao.setText(this.bar.getDescricao());
    }
}
}