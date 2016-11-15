package br.edu.fumep.eep.cc.subman;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import org.joda.time.LocalDate;

import br.edu.fumep.eep.cc.subman.data.Avaliacao;
import br.edu.fumep.eep.cc.subman.data.repository.Repositorio;
import br.edu.fumep.eep.cc.subman.data.repository.SqliteAvaliacaoRepositorio;

/**
 * Created by arabasso on 09/11/2016.
 *
 */

public class AvaliacoesActivity extends AppCompatActivity {

    private int position;
    private int id;
    private EditText descricaoEditText;
    private DatePicker dataDatePicker;
    private EditText pesoEditText;
    private EditText notaEditText;
    private Spinner tiposSpinner;
    private CheckBox concluidoCheckBox;
    private Avaliacao avaliacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacoes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tiposSpinner = (Spinner) findViewById(R.id.activity_avaliacoes_tipos_spinner);

        tiposSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                boolean ehTrabalho = position == 1;

                concluidoCheckBox.setVisibility(ehTrabalho ? View.VISIBLE : View.GONE);

                if (!ehTrabalho){
                    concluidoCheckBox.setChecked(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.fragment_avaliacoes_tipos, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tiposSpinner.setAdapter(adapter);

        descricaoEditText = (EditText)findViewById(R.id.activity_avaliacoes_descricao_edit_text);
        dataDatePicker = (DatePicker)findViewById(R.id.activity_avaliacoes_data_date_picker);
        pesoEditText = (EditText)findViewById(R.id.activity_avaliacoes_peso_edit_text);
        notaEditText = (EditText)findViewById(R.id.activity_avaliacoes_nota_edit_text);
        concluidoCheckBox = (CheckBox)findViewById(R.id.activity_avaliacoes_concluido_check_box);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            id = bundle.getInt("id");

            if (id > 0){
                Repositorio<Avaliacao> avaliacaoRepositorio = new SqliteAvaliacaoRepositorio(this);

                avaliacao = avaliacaoRepositorio.carregar(id);

                tiposSpinner.setSelection(avaliacao.getTipo());
                descricaoEditText.setText(avaliacao.getDescricao());

                LocalDate data = avaliacao.getData();

                dataDatePicker.updateDate(data.getYear(), data.getMonthOfYear() - 1, data.getDayOfMonth());
                pesoEditText.setText(avaliacao.getPesoFormatado());
                notaEditText.setText(avaliacao.getNotaFormatada());
                concluidoCheckBox.setChecked(avaliacao.foiConcluido());
            } else{
                position = bundle.getInt("position");

                if (position >= 0){
                    tiposSpinner.setSelection(bundle.getInt("tipo"));
                    descricaoEditText.setText(bundle.getString("descricao"));

                    LocalDate data = (LocalDate)bundle.getSerializable("data");

                    dataDatePicker.updateDate(data.getYear(), data.getMonthOfYear() - 1, data.getDayOfMonth());
                    pesoEditText.setText(bundle.getString("peso"));
                    notaEditText.setText(bundle.getString("nota"));
                    concluidoCheckBox.setChecked(bundle.getBoolean("concluido"));
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_avaliacoes, menu);

        if (position < 0){
            menu.findItem(R.id.menu_avaliacoes_excluir).setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                break;

            case R.id.menu_avaliacoes_salvar: {
                Intent intent = null;

                if (avaliacao != null){
                    Repositorio<Avaliacao> avaliacaoRepositorio = new SqliteAvaliacaoRepositorio(this);

                    avaliacao.setTipo(tiposSpinner.getSelectedItemPosition());
                    avaliacao.setDescricao(descricaoEditText.getText().toString());

                    LocalDate data = new LocalDate(dataDatePicker.getYear(), dataDatePicker.getMonth() + 1, dataDatePicker.getDayOfMonth());

                    avaliacao.setData(data);
                    avaliacao.setConcluido(concluidoCheckBox.isChecked());
                    avaliacao.setPesoFormatado(pesoEditText.getText().toString());
                    avaliacao.setNotaFormatada(notaEditText.getText().toString());

                    avaliacaoRepositorio.salvar(avaliacao);
                } else {
                    intent = getResultIntent();
                }

                setResult(1, intent);
                finish();
                break;
            }

            case R.id.menu_avaliacoes_excluir: {
                Intent intent = null;

                if (avaliacao != null) {
                    Repositorio<Avaliacao> avaliacaoRepositorio = new SqliteAvaliacaoRepositorio(this);

                    avaliacaoRepositorio.excluir(avaliacao);
                } else {
                    intent = getResultIntent();
                }

                setResult(2, intent);

                finish();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @NonNull
    private Intent getResultIntent() {
        Intent intent = new Intent();

        intent.putExtra("id", id);
        intent.putExtra("tipo", tiposSpinner.getSelectedItemPosition());
        intent.putExtra("position", position);
        intent.putExtra("descricao", descricaoEditText.getText().toString());

        LocalDate data = new LocalDate(dataDatePicker.getYear(), dataDatePicker.getMonth() + 1, dataDatePicker.getDayOfMonth());

        intent.putExtra("data", data);
        intent.putExtra("peso", pesoEditText.getText().toString());
        intent.putExtra("nota", notaEditText.getText().toString());
        intent.putExtra("concluido", concluidoCheckBox.isChecked());

        return intent;
    }
}
