package com.wolking.fortnite.domain.friends.repository

import com.wolking.fortnite.data.friends.data_source.Friend

interface FriendsRepository {
    fun insert(friend: Friend): Boolean

    fun getAll(): List<Friend>

    fun findByName(nickName: String): Friend?

    fun delete(friend: Friend): Boolean

    fun edit(friend: Friend): Boolean
}