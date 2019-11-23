package br.ufrpe.wanderlustapp.pratoTipico.gui;

import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;

public interface OnItemClickListener {
    void onItemClick(PratoTipico pratoTipico, int posicao);
    void onItemClick(PratoTipico pratoTipico, int posicao, boolean isChecked);
    void onItemClick(PratoTipico pratoTipico, int posicao, boolean likeChecked, boolean dislikeChecked);

}