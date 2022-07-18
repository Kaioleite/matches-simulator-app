package com.googlelabs.simulador.data;

import com.googlelabs.simulador.domain.Match;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MatchesApi {

    //Camada de Acesso á dados pela API do Github ^_^
     @GET("teste.json")
    Call<List<Object>> getPlayer();
}
