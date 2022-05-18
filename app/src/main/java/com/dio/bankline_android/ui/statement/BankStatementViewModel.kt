package com.dio.bankline_android.ui.statement

import androidx.lifecycle.ViewModel
import com.dio.bankline_android.data.BanklineRepository

class BankStatementViewModel : ViewModel(){
    fun findBankStatement(accountHolderId: Int) = BanklineRepository.findBankStatement(accountHolderId)
}