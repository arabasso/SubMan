package br.edu.fumep.eep.cc.subman;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by arabasso on 09/11/2016.
 *
 */

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;
    private MateriasFragment materiasFragment;
    private DatasFragment datasFragment;
    private MenuItem adicionarMateriaMenuItem;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.main_view_pager_container);
        viewPager.setAdapter(sectionsPagerAdapter);

        context = this;

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch(position){
                    case 0:
                        materiasFragment.listar();
                        break;

                    case 1:
                        datasFragment.listar();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.main_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_subject_white_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_date_range_white_24dp);

        materiasFragment = MateriasFragment.newInstance();
        datasFragment = DatasFragment.newInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        adicionarMateriaMenuItem = menu.findItem(R.id.menu_main_adicionar_materia);

        adicionarMateriaMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(materiasFragment.getContext(), MateriasActivity.class);

                intent.putExtra("id", 0);

                startActivityForResult(intent, 0);

                return true;
            }
        });

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        materiasFragment.listar();
        datasFragment.listar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return materiasFragment;
                case 1:
                    return datasFragment;
            }

            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        private int[] imageResId = {
                R.drawable.ic_subject_white_24dp,
                R.drawable.ic_date_range_white_24dp,
        };

        private int[] textResId = {
                R.string.activity_main_materias_text,
                R.string.activity_main_datas_text,
        };

        @Override
        public CharSequence getPageTitle(int position) {
            return context.getResources().getString(textResId[position]);
//            Drawable image = context.getResources().getDrawable(imageResId[position]);
//
//            image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
//
//            SpannableString sb = new SpannableString(context.getResources().getString(textResId[position]));
//
//            ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
//
//            sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            sb.setSpan(new Rela);
//
//            return sb;
        }
    }
}
