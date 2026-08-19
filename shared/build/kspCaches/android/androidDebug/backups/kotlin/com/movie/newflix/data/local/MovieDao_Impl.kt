package com.movie.newflix.`data`.local

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.EntityUpsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import javax.`annotation`.processing.Generated
import kotlin.Double
import kotlin.Int
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class MovieDao_Impl(
  __db: RoomDatabase,
) : MovieDao {
  private val __db: RoomDatabase

  private val __deleteAdapterOfMovieEntity: EntityDeleteOrUpdateAdapter<MovieEntity>

  private val __upsertAdapterOfMovieEntity: EntityUpsertAdapter<MovieEntity>
  init {
    this.__db = __db
    this.__deleteAdapterOfMovieEntity = object : EntityDeleteOrUpdateAdapter<MovieEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `movies` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: MovieEntity) {
        statement.bindLong(1, entity.id.toLong())
      }
    }
    this.__upsertAdapterOfMovieEntity = EntityUpsertAdapter<MovieEntity>(object :
        EntityInsertAdapter<MovieEntity>() {
      protected override fun createQuery(): String =
          "INSERT INTO `movies` (`id`,`title`,`overview`,`posterPath`,`backdropPath`,`voteAverage`,`releaseDate`) VALUES (?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: MovieEntity) {
        statement.bindLong(1, entity.id.toLong())
        statement.bindText(2, entity.title)
        statement.bindText(3, entity.overview)
        val _tmpPosterPath: String? = entity.posterPath
        if (_tmpPosterPath == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmpPosterPath)
        }
        val _tmpBackdropPath: String? = entity.backdropPath
        if (_tmpBackdropPath == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmpBackdropPath)
        }
        statement.bindDouble(6, entity.voteAverage)
        statement.bindText(7, entity.releaseDate)
      }
    }, object : EntityDeleteOrUpdateAdapter<MovieEntity>() {
      protected override fun createQuery(): String =
          "UPDATE `movies` SET `id` = ?,`title` = ?,`overview` = ?,`posterPath` = ?,`backdropPath` = ?,`voteAverage` = ?,`releaseDate` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: MovieEntity) {
        statement.bindLong(1, entity.id.toLong())
        statement.bindText(2, entity.title)
        statement.bindText(3, entity.overview)
        val _tmpPosterPath: String? = entity.posterPath
        if (_tmpPosterPath == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmpPosterPath)
        }
        val _tmpBackdropPath: String? = entity.backdropPath
        if (_tmpBackdropPath == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmpBackdropPath)
        }
        statement.bindDouble(6, entity.voteAverage)
        statement.bindText(7, entity.releaseDate)
        statement.bindLong(8, entity.id.toLong())
      }
    })
  }

  public override suspend fun deleteMovie(movie: MovieEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __deleteAdapterOfMovieEntity.handle(_connection, movie)
  }

  public override suspend fun upsertMovie(movie: MovieEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __upsertAdapterOfMovieEntity.upsert(_connection, movie)
  }

  public override fun getAllMovies(): Flow<List<MovieEntity>> {
    val _sql: String = "SELECT * FROM movies"
    return createFlow(__db, false, arrayOf("movies")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfOverview: Int = getColumnIndexOrThrow(_stmt, "overview")
        val _columnIndexOfPosterPath: Int = getColumnIndexOrThrow(_stmt, "posterPath")
        val _columnIndexOfBackdropPath: Int = getColumnIndexOrThrow(_stmt, "backdropPath")
        val _columnIndexOfVoteAverage: Int = getColumnIndexOrThrow(_stmt, "voteAverage")
        val _columnIndexOfReleaseDate: Int = getColumnIndexOrThrow(_stmt, "releaseDate")
        val _result: MutableList<MovieEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: MovieEntity
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpOverview: String
          _tmpOverview = _stmt.getText(_columnIndexOfOverview)
          val _tmpPosterPath: String?
          if (_stmt.isNull(_columnIndexOfPosterPath)) {
            _tmpPosterPath = null
          } else {
            _tmpPosterPath = _stmt.getText(_columnIndexOfPosterPath)
          }
          val _tmpBackdropPath: String?
          if (_stmt.isNull(_columnIndexOfBackdropPath)) {
            _tmpBackdropPath = null
          } else {
            _tmpBackdropPath = _stmt.getText(_columnIndexOfBackdropPath)
          }
          val _tmpVoteAverage: Double
          _tmpVoteAverage = _stmt.getDouble(_columnIndexOfVoteAverage)
          val _tmpReleaseDate: String
          _tmpReleaseDate = _stmt.getText(_columnIndexOfReleaseDate)
          _item =
              MovieEntity(_tmpId,_tmpTitle,_tmpOverview,_tmpPosterPath,_tmpBackdropPath,_tmpVoteAverage,_tmpReleaseDate)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getMovieById(id: Int): MovieEntity? {
    val _sql: String = "SELECT * FROM movies WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id.toLong())
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfOverview: Int = getColumnIndexOrThrow(_stmt, "overview")
        val _columnIndexOfPosterPath: Int = getColumnIndexOrThrow(_stmt, "posterPath")
        val _columnIndexOfBackdropPath: Int = getColumnIndexOrThrow(_stmt, "backdropPath")
        val _columnIndexOfVoteAverage: Int = getColumnIndexOrThrow(_stmt, "voteAverage")
        val _columnIndexOfReleaseDate: Int = getColumnIndexOrThrow(_stmt, "releaseDate")
        val _result: MovieEntity?
        if (_stmt.step()) {
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpOverview: String
          _tmpOverview = _stmt.getText(_columnIndexOfOverview)
          val _tmpPosterPath: String?
          if (_stmt.isNull(_columnIndexOfPosterPath)) {
            _tmpPosterPath = null
          } else {
            _tmpPosterPath = _stmt.getText(_columnIndexOfPosterPath)
          }
          val _tmpBackdropPath: String?
          if (_stmt.isNull(_columnIndexOfBackdropPath)) {
            _tmpBackdropPath = null
          } else {
            _tmpBackdropPath = _stmt.getText(_columnIndexOfBackdropPath)
          }
          val _tmpVoteAverage: Double
          _tmpVoteAverage = _stmt.getDouble(_columnIndexOfVoteAverage)
          val _tmpReleaseDate: String
          _tmpReleaseDate = _stmt.getText(_columnIndexOfReleaseDate)
          _result =
              MovieEntity(_tmpId,_tmpTitle,_tmpOverview,_tmpPosterPath,_tmpBackdropPath,_tmpVoteAverage,_tmpReleaseDate)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
