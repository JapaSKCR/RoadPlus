/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter.Activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.parse.ParseUser;
import com.parse.starter.R;
import com.parse.starter.adapter.TabsAdapter;
import com.parse.starter.util.SlidingTabLayout;



public class MainActivity extends AppCompatActivity {

  private Toolbar toolbar;
  private SlidingTabLayout slidingTabLayout;
  private ViewPager viewPager;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //configurando toolbar
    toolbar = findViewById(R.id.toolbarPrincipal);
    toolbar.setTitle(R.string.titulo);
    toolbar.setTitleTextAppearance(this, R.style.DaysOne);
    setSupportActionBar(toolbar);

    //configura abas
    slidingTabLayout = findViewById(R.id.sliding_tab_main);
    viewPager = findViewById(R.id.view_pager_main);

    //configura adapter e seta propriedades das abas
    TabsAdapter tabsAdapter = new TabsAdapter( getSupportFragmentManager(), this);
    viewPager.setAdapter(tabsAdapter);
    slidingTabLayout.setCustomTabView(R.layout.tab_view, R.id.text_item_tab);
    slidingTabLayout.setDistributeEvenly(true);
    slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.laranja));
    slidingTabLayout.setViewPager(viewPager);

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {

    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_main, menu);

    //define a cor dos icones
    for(int i = 0; i < menu.size(); i++){
      Drawable drawable = menu.getItem(i).getIcon();
      if(drawable != null) {
        drawable.mutate();
        drawable.setColorFilter(getResources().getColor(R.color.laranja), PorterDuff.Mode.SRC_ATOP);
      }
    }

    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    //menu do botao hamburguer
    switch (item.getItemId() ){
      case R.id.sair:

        deslogarUsuario();

        return true;
      case R.id.config:
        return true;

      default:
        return super.onOptionsItemSelected(item);
    }
    
  }


  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
  }




  private void deslogarUsuario() {

    ParseUser.logOut();
    finish();
    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
    startActivity(intent);

  }
}
