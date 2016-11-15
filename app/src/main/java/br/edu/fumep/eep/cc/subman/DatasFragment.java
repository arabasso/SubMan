package br.edu.fumep.eep.cc.subman;


import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;
import java.util.List;

import br.edu.fumep.eep.cc.subman.data.Avaliacao;
import br.edu.fumep.eep.cc.subman.data.repository.AvaliacaoRepositorio;
import br.edu.fumep.eep.cc.subman.data.repository.SqliteAvaliacaoRepositorio;

/**
 * Created by arabasso on 09/11/2016.
 *
 */

public class DatasFragment extends Fragment {

    private DatasAdapter adapter;

    public static DatasFragment newInstance() {
        DatasFragment fragment = new DatasFragment();

        Bundle args = new Bundle();

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_datas, container, false);

        ListView datasListView = (ListView) view.findViewById(R.id.fragment_datas_list_view);

        adapter = new DatasAdapter();

        datasListView.setAdapter(adapter);

        datasListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), AvaliacoesActivity.class);

                intent.putExtra("id", (int)id);

                startActivityForResult(intent, 0);
            }
        });

        listar();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        listar();
    }

    public void listar() {
        AvaliacaoRepositorio avaliacaoRepositorio = new SqliteAvaliacaoRepositorio(getActivity());

        adapter.setList(avaliacaoRepositorio.listarPendentes());
    }

    public class DatasAdapter extends BaseAdapter {
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

        private String [] tipos = new String[]{
                "P",
                "T"
        };

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getActivity().getLayoutInflater().inflate(R.layout.list_datas, parent, false);

            TextView materiaTextView = (TextView) convertView.findViewById(R.id.list_datas_materia_text_view);
            TextView tipoTextView = (TextView) convertView.findViewById(R.id.list_datas_tipo_text_view);
            TextView descricaoTextView = (TextView) convertView.findViewById(R.id.list_datas_descricao_text_view);
            TextView dataTextView = (TextView) convertView.findViewById(R.id.list_datas_data_text_view);

            Avaliacao avaliacao = (Avaliacao) getItem(position);

            tipoTextView.setBackground(getShapeDrawable(position, convertView));
            tipoTextView.setText(tipos[avaliacao.getTipo()]);

            materiaTextView.setText(avaliacao.getMateria().getNome());
            descricaoTextView.setText(avaliacao.getDescricao());
            dataTextView.setText(avaliacao.getData(DateTimeFormat.forPattern("dd/MM")));

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
