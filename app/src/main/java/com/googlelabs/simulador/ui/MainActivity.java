package com.googlelabs.simulador.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.util.Log;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.googlelabs.simulador.R;
import com.googlelabs.simulador.data.MatchesApi;
import com.googlelabs.simulador.databinding.ActivityMainBinding;
import com.googlelabs.simulador.domain.Match;
import com.googlelabs.simulador.ui.adapter.MatchesAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MatchesApi matchesApi;
    private MatchesAdapter matchesAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Setup do Retrofit
        setuphttpClient();

        setupMatchesList();
        setupMatchesRefresh();
        setupFloatingActionButton();

    }

    private void setuphttpClient() {
        //Instanciando o Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                // Url Base é o mais importante
                .baseUrl("https://sportsmatch.com.br/teste/")

                // Biblioteca responsavel por fazer a transição para Json
                .addConverterFactory(GsonConverterFactory.create())
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .build();
        //Criando o retrofit a partir da interface
        matchesApi = retrofit.create(MatchesApi.class);

        Call<List<Object>>call=matchesApi.getPlayer();
        call.enqueue(new Callback<List<Object>>() {
            @Override
            public void onResponse(Call<List<Object>> call, Response<List<Object>> response) {
                if(response.isSuccessful()) {
                    List<Object> list = new ArrayList<>();
                    list = response.body();
                    Log.i("Sucesso", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<List<Object>> call, Throwable t) {

            }
        });

            }

    private void setupMatchesList() {
        binding.rvMatches.setHasFixedSize(true);
        binding.rvMatches.setLayoutManager(new LinearLayoutManager(this));
        findMatchesFromApi();
    }

    private void setupMatchesRefresh() {
        binding.srMatches.setOnRefreshListener(this::findMatchesFromApi);
    }

    private void setupFloatingActionButton() {
        binding.fabSimulate.setOnClickListener(view -> {
    view.animate().rotationBy(360).setDuration(500).setListener(new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            Random random = new Random();
            for (int i = 0; i < matchesAdapter.getItemCount(); i++) {
                Match match = matchesAdapter.getMatches().get(i);
                match.getHomeTeam().setScore(random.nextInt(match.getHomeTeam().getStars() + 1));
                match.getAwayTeam().setScore(random.nextInt(match.getAwayTeam().getStars() + 1));
                matchesAdapter.notifyDataSetChanged(i);
                }
             }
           });
       });

      }

    private void findMatchesFromApi() {
        binding.srMatches.setRefreshing(true);
//        matchesApi.getMatches().enqueue(new Callback<List<Match>>() {
//            @Override
//            public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {
//                if (response.isSuccessful()) {
//                    List<Match> matches = response.body();
//                    matchesAdapter = new MatchesAdapter(matches);
//                    binding.rvMatches.setAdapter(matchesAdapter);
//                    Log.i("Sucesso", response.body().toString());
//                } else {
//                    showErrorMessage();
//                    Log.i("ERROR", response.body().toString());
//                }
//                binding.srMatches.setRefreshing(false);
//            }
//            @Override
//            public void onFailure(Call<List<Match>> call, Throwable t) {
//                showErrorMessage();
//                binding.srMatches.setRefreshing(false);
//                Log.i("ERROR", call.toString());
//            }
//        });
    }
    private void showErrorMessage() {
        Snackbar.make(binding.fabSimulate, R.string.error_api, Snackbar.LENGTH_LONG).show();

    }
}
