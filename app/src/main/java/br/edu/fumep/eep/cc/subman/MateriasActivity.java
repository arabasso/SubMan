package br.edu.fumep.eep.cc.subman;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import br.edu.fumep.eep.cc.subman.data.Avaliacao;
import br.edu.fumep.eep.cc.subman.data.Materia;
import br.edu.fumep.eep.cc.subman.data.repository.Repositorio;
import br.edu.fumep.eep.cc.subman.data.repository.SqliteMateriaRepositorio;

/**
 * Created by arabasso on 09/11/2016.
 *
 */

public class MateriasActivity extends AppCompatActivity {

    private ListView listView;
    private AvaliacaoAdapter adapter;
    private Materia materia;
    private AutoCompleteTextView materiaText;
    private AutoCompleteTextView professorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materias);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            int id = bundle.getInt("id");

            Repositorio<Materia> materiaRepositorio = new SqliteMateriaRepositorio(this);

            materia = materiaRepositorio.carregar(id);
        }

        if (materia != null) {
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        } else{
            materia = new Materia();
        }

        materiaText = (AutoCompleteTextView) findViewById(R.id.activity_materias_materia_text);

        materiaText.setText(materia.getNome());

        professorText = (AutoCompleteTextView) findViewById(R.id.activity_materias_professor_text);

        professorText.setText(materia.getProfessor());

        listView = (ListView) findViewById(R.id.activity_avaliacoes_list_view);

        adapter = new AvaliacaoAdapter();

        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return true;
            }
        });

        adapter.setList(new ArrayList<>(materia.getAvaliacoes()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_materias, menu);

        if (materia.getId() <= 0){
            menu.findItem(R.id.menu_materias_excluir).setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                break;

            case R.id.menu_materias_salvar: {
                Repositorio<Materia> materiaRepositorio = new SqliteMateriaRepositorio(this);

                materia.setNome(materiaText.getText().toString());
                materia.setProfessor(professorText.getText().toString());

                materiaRepositorio.salvar(materia);

                setResult(0);
                finish();
                break;
            }

            case R.id.menu_materias_excluir: {
                Repositorio<Materia> materiaRepositorio = new SqliteMateriaRepositorio(this);

                materiaRepositorio.excluir(materia);

                setResult(1);
                finish();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public class AvaliacaoAdapter extends BaseAdapter {
        private List<Avaliacao> list = new ArrayList<>();

        public void setList(List<Avaliacao> list) {
            this.list.clear();
            this.list.addAll(list);

            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return list.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.list_avaliacoes, parent, false);

            TextView tipoTextView = (TextView)convertView.findViewById(R.id.list_avaliacoes_tipo_text_view);
            TextView dataTextView = (TextView)convertView.findViewById(R.id.list_avaliacoes_data_text_view);

            Avaliacao avaliacao = (Avaliacao)getItem(position);

            tipoTextView.setText(avaliacao.getTipo() + "");

            DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());

            dataTextView.setText(dateFormat.format(avaliacao.getData()));

            return convertView;
        }
    }
}
