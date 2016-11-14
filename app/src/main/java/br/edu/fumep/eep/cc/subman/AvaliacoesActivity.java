package br.edu.fumep.eep.cc.subman;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import org.joda.time.DateTime;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacoes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tiposSpinner = (Spinner) findViewById(R.id.activity_avaliacoes_tipos_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.fragment_avaliacoes_tipos, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tiposSpinner.setAdapter(adapter);

        descricaoEditText = (EditText)findViewById(R.id.activity_avaliacoes_descricao_edit_text);
        dataDatePicker = (DatePicker)findViewById(R.id.activity_avaliacoes_data_date_picker);
        pesoEditText = (EditText)findViewById(R.id.activity_avaliacoes_peso_edit_text);
        notaEditText = (EditText)findViewById(R.id.activity_avaliacoes_nota_edit_text);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            id = bundle.getInt("id");
            position = bundle.getInt("position");

            if (position >= 0){
                tiposSpinner.setSelection(bundle.getInt("tipo"));
                descricaoEditText.setText(bundle.getString("descricao"));

                DateTime data = (DateTime)bundle.getSerializable("data");

                dataDatePicker.updateDate(data.getYear(), data.getMonthOfYear(), data.getDayOfMonth());
                pesoEditText.setText(bundle.getString("peso"));
                notaEditText.setText(bundle.getString("nota"));
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
                Intent intent = getResultIntent();

                setResult(1, intent);
                finish();
                break;
            }

            case R.id.menu_avaliacoes_excluir: {
                Intent intent = getResultIntent();

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

        DateTime data = new DateTime(dataDatePicker.getYear(), dataDatePicker.getMonth(), dataDatePicker.getDayOfMonth(), 0, 0);

        intent.putExtra("data", data);
        intent.putExtra("peso", pesoEditText.getText().toString());
        intent.putExtra("nota", notaEditText.getText().toString());

        return intent;
    }
}
