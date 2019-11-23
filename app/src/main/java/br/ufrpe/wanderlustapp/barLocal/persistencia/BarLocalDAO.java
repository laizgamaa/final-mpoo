package br.ufrpe.wanderlustapp.barLocal.persistencia;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.wanderlustapp.barLocal.dominio.BarLocal;
import br.ufrpe.wanderlustapp.cidade.persistencia.CidadeDAO;
import br.ufrpe.wanderlustapp.infra.persistencia.AbstractDAO;
import br.ufrpe.wanderlustapp.infra.persistencia.DBHelper;

public class BarLocalDAO extends AbstractDAO{
    private SQLiteDatabase db;
    private DBHelper helper;
    private Context context;

    public BarLocalDAO(Context context) {
        this.context = context;
        helper = new DBHelper(context);
    }

    private ContentValues getContentValues(BarLocal barLocal) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.CAMPO_FK_CIDADE_BAR, barLocal.getCidade().getId());
        values.put(DBHelper.CAMPO_NOME_BAR, barLocal.getNome());
        values.put(DBHelper.CAMPO_DESCRICAO_BAR, barLocal.getDescricao());
        return values;
    }

    private Cursor getCursor(List<BarLocal> barLocal, String sql) {
        Cursor cursor = db.rawQuery(sql, new String[]{});
        while (cursor.moveToNext()){
            barLocal.add(createBarLocal(cursor));
        }
        return cursor;
    }
    private void setsBarLocal(Cursor cursor, BarLocal barLocal, CidadeDAO cidadeDAO) {
        int columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_ID_BAR);
        barLocal.setId(Integer.parseInt(cursor.getString(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_NOME_BAR);
        barLocal.setNome(cursor.getString(columnIndex));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_DESCRICAO_BAR);
        barLocal.setDescricao(cursor.getString(columnIndex));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_FK_CIDADE_BAR);
        barLocal.setCidade(cidadeDAO.getCidade(cursor.getInt(columnIndex)));
    }

    public BarLocal getBarLocalById(long id) {
        BarLocal barLocal = null;
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_BAR + " WHERE " + DBHelper.CAMPO_ID_BAR + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(id)});
        barLocal = getBarLocal(barLocal, cursor);
        super.close(cursor, db);
        return barLocal;
    }

    private BarLocal getBarLocal(BarLocal barLocal, Cursor cursor) {
        if (cursor.moveToFirst()) {
            barLocal = createBarLocal(cursor);
        }
        return barLocal;
    }

    public BarLocal getBarLocalByNome(String nome) {
        BarLocal barLocal = null;
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_BAR + " WHERE " + DBHelper.CAMPO_NOME_BAR + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{nome});
        barLocal = getBarLocal(barLocal, cursor);
        super.close(cursor, db);
        return barLocal;
    }

    public List<BarLocal> getListBarLocal(){
        List<BarLocal> bares = new ArrayList<BarLocal>();
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_BAR;
        Cursor cursor = getCursor(bares, sql);
        cursor.close();
        db.close();
        return bares;
    }

    private BarLocal createBarLocal(Cursor cursor) {
        BarLocal barLocal = new BarLocal();
        CidadeDAO cidadeDAO = new CidadeDAO(context);
        setsBarLocal(cursor, barLocal, cidadeDAO);
        return barLocal;
    }

    public long cadastrar(BarLocal barLocal){
        db = helper.getWritableDatabase();
        ContentValues values = getContentValues(barLocal);
        long id = db.insert(DBHelper.TABELA_BAR,null,values);
        super.close(db);
        return id;
    }
}
