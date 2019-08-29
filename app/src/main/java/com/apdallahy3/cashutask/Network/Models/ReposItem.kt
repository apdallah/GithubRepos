package com.apdallahy3.cashutask.Network.Models

import java.util.*

class ReposItem(
    val id: Int,
    val name: String,
    val full_name: String,
    val private: Boolean,
    val created_at: Date,
    val updated_at: Date,
    val git_url: String
)