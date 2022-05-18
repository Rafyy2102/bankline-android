package com.dio.bankline_android.ui.statement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dio.bankline_android.data.State
import com.dio.bankline_android.databinding.ActivityBankStatementBinding
import com.dio.bankline_android.domain.Correntista
import com.dio.bankline_android.domain.Movimentacao
import com.dio.bankline_android.domain.TipoMovimentacao
import com.google.android.material.snackbar.Snackbar
import java.lang.IllegalArgumentException

class BankStatementActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ACCOUNT_HOLDER =
            "com.dio.bankline_android.ui.statement.EXTRA_ACCOUNT_HOLDER"
    }

    private val binding by lazy {
        ActivityBankStatementBinding.inflate(layoutInflater)
    }

    private val accountHolder by lazy {
        intent.getParcelableExtra<Correntista>(EXTRA_ACCOUNT_HOLDER)
            ?: throw IllegalArgumentException()
    }

    private val viewModel by viewModels<BankStatementViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rvBankStatement.layoutManager = LinearLayoutManager(this)

        findBankStatement()

        binding.srlBankStatement.setOnRefreshListener { findBankStatement()  }
    }

    private fun findBankStatement() {
        /*val dataSet = ArrayList<Movimentacao>()
        dataSet.add(Movimentacao(1, "03/05/2018 10:50:50", "Exemplo receita", 7000.0, TipoMovimentacao.RECEITA, 1 ))
        dataSet.add(Movimentacao(1, "03/05/2018 10:30:50", "Exemplo despesa", 1000.0, TipoMovimentacao.DESPESA, 1 ))
        dataSet.add(Movimentacao(1, "03/05/2018 10:26:50", "Exemplo receita", 7000.0, TipoMovimentacao.RECEITA, 1 ))
        dataSet.add(Movimentacao(1, "03/05/2018 10:10:50", "Exemplo despesa", 5000.0, TipoMovimentacao.DESPESA, 1 ))
        binding.rvBankStatement.adapter = BankStatementAdapter(dataSet)*/

        viewModel.findBankStatement(accountHolder.id).observe(this) { state ->
            when (state) {
                is State.Success -> {
                    binding.rvBankStatement.adapter =
                        state.data?.let { BankStatementAdapter(it) }
                    binding.srlBankStatement.isRefreshing = false
                }
                is State.Error -> {
                    state.message?.let {
                        Snackbar.make(
                            binding.rvBankStatement,
                            it,
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    binding.srlBankStatement.isRefreshing = false
                }
                State.Wait -> binding.srlBankStatement.isRefreshing = true
            }
        }
    }
}