package com.lmh.minhhoang.movieapp.movieList.data.local.movie

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MovieDao  {
    @Upsert
    //upsert có nghĩa là nó sẽ cập nhật dữ liệu nếu đã tồn tại hoặc chèn mới nếu chưa tồn tại
    suspend fun upsertMovieList(movieList: List<movieEntity>)
    //Phương thức này thực hiện một truy vấn để lấy ra một đối tượng movieEntity dựa trên id
    @Query("SELECT * FROM movieEntity WHERE id =  :id")
    suspend fun getMovieById(id:Int):movieEntity
//Phương thức này thực hiện một truy vấn để lấy ra một danh sách các đối tượng movieEntity dựa trên category
    @Query("SELECT * FROM movieEntity WHERE category =  :category")
    suspend fun getMovieListByCategory(category:String):List<movieEntity>
}