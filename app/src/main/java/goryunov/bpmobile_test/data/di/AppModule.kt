package goryunov.bpmobile_test.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import goryunov.bpmobile_test.MyApplication
import goryunov.bpmobile_test.data.RepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): MyApplication {
        return app as MyApplication
    }

    @Singleton
    @Provides
    fun getRepository(): RepositoryImpl {
        return RepositoryImpl()
    }


}