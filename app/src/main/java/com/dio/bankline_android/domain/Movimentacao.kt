package com.dio.bankline_android.domain

data class Movimentacao(
    val id: Int,
    val dataHora: String,
    val descricao: String,
    val valor: Double,
    val tipo: TipoMovimentacao,
    //todo maper idConta com idCorrentista "idConta -> idCorrentista"
    val idCorrentista: Int
)