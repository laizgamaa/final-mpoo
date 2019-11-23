package br.ufrpe.wanderlustapp.pratoTipico.gui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.pessoaPrato.dominio.PessoaPrato;
import br.ufrpe.wanderlustapp.pratoImagem.negocio.PratoImagemServices;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;

public class ListaPratosFavoritosAdapter extends RecyclerView.Adapter<ListaPratosFavoritosAdapter.PratoViewHolder> {
    private final Context context;
    private List<Bitmap> listaDeImagens = new ArrayList<>();
    private final List<PessoaPrato> pessoaPratos;
    private PratoImagemServices pratoImagemServices;


    public ListaPratosFavoritosAdapter(Context context, List<PessoaPrato> pessoaPratos) {
        this.context = context;
        this.pessoaPratos = pessoaPratos;
        pratoImagemServices = new PratoImagemServices(this.context);
    }

    public List<PessoaPrato> getList(){
        return this.pessoaPratos;
    }

    @NonNull
    @Override
    public ListaPratosFavoritosAdapter.PratoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.item_prato_favoritos,parent,false);
        return new PratoViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull PratoViewHolder holder, int position) {
        PratoTipico prato = pessoaPratos.get(position).getPratoTipico();
        holder.vincula(prato);
    }

    @Override
    public int getItemCount() {
        return pessoaPratos.size();
    }

    class PratoViewHolder extends RecyclerView.ViewHolder{
        private final TextView titulo;
        private final TextView descricao;
        private final ImageView imagem;
        private PratoTipico prato;


        public PratoViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.item_prato_nome_favoritos);
            descricao = itemView.findViewById(R.id.item_prato_descricao_favoritos);
            imagem = itemView.findViewById(R.id.imagem_prato_favoritos);
        }

        public void vincula(PratoTipico prato){
            this.prato = prato;
            titulo.setText(this.prato.getNome());
            descricao.setText(this.prato.getDescricao());
            listaDeImagens = pratoImagemServices.geraImagens(prato);
            if (listaDeImagens.size()>0){
                Bitmap imagens = listaDeImagens.get(0);
                if (imagens != null){
                    imagem.setImageBitmap(imagens);
                }
            }
        }
    }

    public void remove(int posicao){
        pessoaPratos.remove(posicao);
        notifyDataSetChanged();
    }

}
