package com.edu.controller

import com.edu.model.Account
import com.edu.service.AccountMsgService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import javax.annotation.Resource

@Controller
class UserContainer {
    @Resource
    private val accountMsgService: AccountMsgService? = null

    @RequestMapping("/userInfo/{userId}")
    @ResponseBody
    fun userInfo(@PathVariable("userId")userId : String) = accountMsgService?.getAccountById(userId)



}

