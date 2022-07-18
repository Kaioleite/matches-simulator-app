package com.googlelabs.simulador.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Match (
    //@SerializeName - é a descrição de cada componente que criamos em nossas Classes!
    @SerializedName("descicao")
    val description: String,
    @SerializedName("local")
    val place: Place,
    @SerializedName("mandante")
    val homeTeam: Team,
    @SerializedName("visitante")
    val awayTeam:Team
        ) : Parcelable