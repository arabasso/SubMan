package br.edu.fumep.eep.cc.subman;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;

import org.joda.time.LocalDate;

import java.text.NumberFormat;

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
        } else {
            materia = new Materia();
        }

        materiaText = (AutoCompleteTextView) findViewById(R.id.activity_materias_materia_text);

        materiaText.setText(materia.getNome());

        professorText = (AutoCompleteTextView) findViewById(R.id.activity_materias_professor_text);

        professorText.setText(materia.getProfessor());

        listView = (ListView) findViewById(R.id.activity_avaliacoes_list_view);

        adapter = new AvaliacaoAdapter(materia);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Avaliacao avaliacao = materia.getAvaliacoes().get(position);

                Intent intent = new Intent(view.getContext(), AvaliacoesActivity.class);

                intent.putExtra("position", position);
                intent.putExtra("id", 0);
                intent.putExtra("tipo", avaliacao.getTipo());
                intent.putExtra("descricao", avaliacao.getDescricao());
                intent.putExtra("data", avaliacao.getData());
                intent.putExtra("peso", avaliacao.getPesoFormatado());
                intent.putExtra("nota", avaliacao.getNotaFormatada());
                intent.putExtra("concluido", avaliacao.foiConcluido());

                startActivityForResult(intent, 0);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.activity_materias_adicionar_avaliacoes_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AvaliacoesActivity.class);

                intent.putExtra("id", 0);
                intent.putExtra("position", -1);

                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 1: {
                int position = data.getIntExtra("position", 0);

                Avaliacao avaliacao = materia.getAvaliacao(position);

                avaliacao.setTipo(data.getIntExtra("tipo", 0));
                avaliacao.setDescricao(data.getStringExtra("descricao"));
                avaliacao.setData((LocalDate) data.getSerializableExtra("data"));
                avaliacao.setNotaFormatada(data.getStringExtra("nota"));
                avaliacao.setPesoFormatado(data.getStringExtra("peso"));
                avaliacao.setConcluido(data.getBooleanExtra("concluido", false));

                materia.ordenarAvaliacoes();

                adapter.notifyDataSetChanged();

                break;
            }

            case 2: {
                int position = data.getIntExtra("position", -1);

                if (position >= 0){
                    materia.getAvaliacoes().remove(position);

                    materia.ordenarAvaliacoes();

                    adapter.notifyDataSetChanged();
                }

                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_materias, menu);

        if (materia.getId() <= 0) {
            menu.findItem(R.id.menu_materias_excluir).setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.menu_materias_salvar: {
                String nome = materiaText.getText().toString();

                if (nome == null || nome.trim().equals("")){
                    Toast.makeText(this, R.string.activity_materias_professor_error, Toast.LENGTH_SHORT).show();

                    return false;
                }

                Repositorio<Materia> materiaRepositorio = new SqliteMateriaRepositorio(this);

                materia.setNome(nome);
                materia.setProfessor(professorText.getText().toString());

                materiaRepositorio.salvar(materia);

                setResult(1);
                finish();
                break;
            }

            case R.id.menu_materias_excluir: {
                Dialogo.mostrar(this, R.string.dialog_janela_exclusao_titulo_text, R.string.dialog_janela_exclusao_mensagem_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Repositorio<Materia> materiaRepositorio = new SqliteMateriaRepositorio(MateriasActivity.this);

                        materiaRepositorio.excluir(materia);

                        setResult(2);
                        finish();
                    }
                });
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public class AvaliacaoAdapter extends BaseAdapter {
        Materia materia;

        public AvaliacaoAdapter(Materia materia) {
            this.materia = materia;
        }

        @Override
        public int getCount() {
            return materia.getAvaliacoes().size();
        }

        @Override
        public Object getItem(int position) {
            return materia.getAvaliacoes().get(position);
        }

        @Override
        public long getItemId(int position) {
            return materia.getAvaliacoes().get(position).getId();
        }

        private String [] tipos = new String[]{
                "P",
                "T"
        };

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.list_avaliacoes, parent, false);

            TextView tipoTextView = (TextView) convertView.findViewById(R.id.list_avaliacoes_tipo_text_view);
            TextView descricaoTextView = (TextView) convertView.findViewById(R.id.list_avaliacoes_descricao_text_view);
            TextView dataTextView = (TextView) convertView.findViewById(R.id.list_avaliacoes_data_text_view);
            TextView pesoTextView = (TextView) convertView.findViewById(R.id.list_avaliacoes_peso_text_view);
            TextView notaTextView = (TextView) convertView.findViewById(R.id.list_avaliacoes_nota_text_view);
            TextView mediaTextView = (TextView) convertView.findViewById(R.id.list_avaliacoes_media_text_view);

            Avaliacao avaliacao = (Avaliacao) getItem(position);

            tipoTextView.setBackground(getShapeDrawable(position, convertView));
            tipoTextView.setText(tipos[avaliacao.getTipo()]);

            descricaoTextView.setText(avaliacao.getDescricao());
            dataTextView.setText(avaliacao.getDataFormatada());
            pesoTextView.setText(avaliacao.getPesoFormatado());
            notaTextView.setText(avaliacao.getNotaFormatada());
            mediaTextView.setText(NumberFormat.getInstance().format(avaliacao.calcularNota()));

            return convertView;
        }

        @NonNull
        private ShapeDrawable getShapeDrawable(int posicao, View view) {
            int indice = posicao % 10;

            ShapeDrawable background = new ShapeDrawable();
            background.setShape(new OvalShape());

            background.getPaint().setColor(ContextCompat.getColor(view.getContext(), R.color.circulo_0 + indice));
            return background;
        }
    }
}
