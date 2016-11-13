package br.edu.fumep.eep.cc.subman;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import br.edu.fumep.eep.cc.subman.data.Avaliacao;

/**
 * Created by arabasso on 09/11/2016.
 *
 */

public class DatasFragment extends Fragment {

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

        return view;
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

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();

            convertView = inflater.inflate(R.layout.list_datas, parent,false);

            return convertView;
        }
    }
}
