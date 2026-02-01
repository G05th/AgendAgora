package com.example.agendagora.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.agendagora.data.local.dao.AgendamentoDao
import com.example.agendagora.data.local.dao.ServicoDao
import com.example.agendagora.data.local.entity.AgendamentoEntity
import com.example.agendagora.data.local.entity.ServicoEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [ServicoEntity::class, AgendamentoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun servicoDao(): ServicoDao
    abstract fun agendamentoDao(): AgendamentoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "agendagora.db")
                .fallbackToDestructiveMigration() // para protótipo; substitua por migrações em produção
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // pré-popular a base com serviços iniciais
                        // usamos INSTANCE após build para obter DAO
                        CoroutineScope(Dispatchers.IO).launch {
                            val instance = INSTANCE ?: return@launch
                            val dao = instance.servicoDao()
                            val initial = listOf(
                                ServicoEntity(id = "1", title = "Emissão de Bilhete de Identidade", description = "Serviço para emissão de documento de identificação."),
                                ServicoEntity(id = "2", title = "Pedido de Certidão", description = "Solicitação de certidões civis."),
                                ServicoEntity(id = "3", title = "Agendamento de Consulta Social", description = "Atendimento em serviços sociais municipais.")
                            )
                            dao.insertAll(initial)
                        }
                    }
                })
                .build()
        }
    }
}
