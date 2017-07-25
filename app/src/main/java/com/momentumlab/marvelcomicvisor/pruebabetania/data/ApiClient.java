package com.momentumlab.marvelcomicvisor.pruebabetania.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.modelosgeneradosretrofit.comic.Example;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.modelosgeneradosretrofit.creadores.CreadoresConsulta;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.modelosgeneradosretrofit.personajes.PersonajesConsulta;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.modelosgeneradosretrofit.series.SeriesConsulta;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiClient {

    String BASE_URL ="https://gateway.marvel.com/v1/public/";

    @GET("comics?apikey=264972ebd21bcb074a43bd64c6ed54a9&hash=74a25d99b1f4cd29d1688b25d4476519&ts=1492520601&limit=30")
    Call<Example> groupList(@Query("offset") String offset);

    @GET("comics?apikey=264972ebd21bcb074a43bd64c6ed54a9&hash=74a25d99b1f4cd29d1688b25d4476519&ts=1492520601&limit=1")
    Call<Example> getbasics();

    @GET("comics/{id}/creators?apikey=264972ebd21bcb074a43bd64c6ed54a9&hash=74a25d99b1f4cd29d1688b25d4476519&ts=1492520601")
    Call<CreadoresConsulta> getCreadores(@Path("id") String idComic);

    @GET("comics/{id}/characters?apikey=264972ebd21bcb074a43bd64c6ed54a9&hash=74a25d99b1f4cd29d1688b25d4476519&ts=1492520601")
    Call<PersonajesConsulta> getPersonajes(@Path("id") String idComic);

    @GET("comics/{id}/stories?apikey=264972ebd21bcb074a43bd64c6ed54a9&hash=74a25d99b1f4cd29d1688b25d4476519&ts=1492520601")
    Call<SeriesConsulta> getSeries(@Path("id") String idComic);




    class Factory {
        private static ApiClient service;

        public static ApiClient getIntance() {
            if (service == null) {
                //inicializacion Retrofit
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(120, TimeUnit.SECONDS)
                        .writeTimeout(120, TimeUnit.SECONDS)
                        .readTimeout(120, TimeUnit.SECONDS)
                        .build();

                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson)).baseUrl(BASE_URL).client(client).build();
                service = retrofit.create(ApiClient.class);
                return service;
            } else {
                return service;
            }
        }

    }
}
