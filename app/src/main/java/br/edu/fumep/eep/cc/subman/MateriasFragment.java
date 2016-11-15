package br.edu.fumep.eep.cc.subman;


import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import br.edu.fumep.eep.cc.subman.data.Materia;
import br.edu.fumep.eep.cc.subman.data.repository.Repositorio;
import br.edu.fumep.eep.cc.subman.data.repository.SqliteMateriaRepositorio;

/**
 * Created by arabasso on 09/11/2016.
 *
 */

public class MateriasFragment extends Fragment {

    private ListView listView;
    private MateriaAdapter adapter;

    public static MateriasFragment newInstance() {
        MateriasFragment fragment = new MateriasFragment();

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Repositorio<Materia> materiaRepositorio = new SqliteMateriaRepositorio(getActivity());

        adapter.setList(materiaRepositorio.listar());
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_materias, container, false);


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MateriasActivity.class);

                intent.putExtra("id", 0);

                startActivityForResult(intent, 0);
            }
        });

        listView = (ListView) view.findViewById(R.id.fragment_materias_list_view);

        adapter = new MateriaAdapter();

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), MateriasActivity.class);

                intent.putExtra("id", (int)id);

                startActivityForResult(intent, 1);
            }
        });

        Repositorio<Materia> materiaRepositorio = new SqliteMateriaRepositorio(getActivity());

        adapter.setList(materiaRepositorio.listar());

        return view;
    }

    public class MateriaAdapter extends BaseAdapter {
        private List<Materia> list = new ArrayList<>();

        public void setList(List<Materia> list) {
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
            convertView = getActivity().getLayoutInflater().inflate(R.layout.list_materias, parent, false);

            TextView nomeTextView = (TextView)convertView.findViewById(R.id.list_materias_nome_text_view);
            TextView professorTextView = (TextView)convertView.findViewById(R.id.list_materias_professor_text_view);
            TextView letraTextView = (TextView) convertView.findViewById(R.id.list_materias_letra_text_view);
            TextView notasTextView = (TextView) convertView.findViewById(R.id.list_materias_notas_text_view);
            TextView mediaTextView = (TextView) convertView.findViewById(R.id.list_materias_media_text_view);

            Materia m = (Materia)getItem(position);

            letraTextView.setBackground(getShapeDrawable(m));
            letraTextView.setText(m.getSigla());
            nomeTextView.setText(m.getNome());
            professorTextView.setText(m.getProfessor());
            notasTextView.setText("0, 0");
            mediaTextView.setText(NumberFormat.getInstance().format(m.calcularMedia()));

            return convertView;
        }

        @NonNull
        private ShapeDrawable getShapeDrawable(Materia m) {
            int indice = m.getId() % 10;

            ShapeDrawable background = new ShapeDrawable();
            background.setShape(new OvalShape());

            background.getPaint().setColor(ContextCompat.getColor(getActivity(), R.color.circulo_0 + indice));
            return background;
        }
    }
}
