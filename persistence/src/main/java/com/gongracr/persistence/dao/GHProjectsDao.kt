package com.gongracr.persistence.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gongracr.persistence.model.project.GHProjectEntity

@Dao
interface GHProjectsDao {
    @Query("SELECT * FROM repositories ORDER BY stargazersCount DESC")
    fun getAllProjects(): PagingSource<Int, GHProjectEntity>

    @Query("SELECT * FROM repositories WHERE name LIKE '%' || :searchQuery || '%' OR fullname LIKE '%' || :searchQuery || '%'")
    fun searchPaginatedProjects(searchQuery: String): PagingSource<Int, GHProjectEntity>

    @Query("SELECT * FROM repositories WHERE id = :id")
    fun findProjectById(id: String): GHProjectEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(projects: List<GHProjectEntity>)

    @Query("DELETE FROM repositories")
    fun deleteAllData()

    @Query("DELETE FROM repositories WHERE id = :id")
    fun deleteProject(id: Long)
}