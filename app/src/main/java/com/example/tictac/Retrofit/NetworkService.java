package com.example.tictac.Retrofit;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class NetworkService {
    private static NetworkService mInstance;
    private static final String BASE_URL = "http://37.57.197.237:5000";
    private Retrofit mRetrofit;

    private NetworkService() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }


    public JSONPlaceHolderApi getJSONApi() {
        return mRetrofit.create(JSONPlaceHolderApi.class);
    }

    public interface JSONPlaceHolderApi {
        @GET("/user") // get gameId 
        Call<Post> getPostWithName(@Query("userName") String userName);

        @GET("/session") // get information about game: coordinates, move
        Call<Post> getPostWithGameID(@Query("gameId") String gameId);

        @POST("/register") // registration player on "base"
        Call<Post> postDataRegister(@Body JsonObject data);

        @POST("/matchmaking") // finding enemy for game
        Call<Post> postMatchmaking(@Body JsonObject data);

        @POST("/coordinator") // send coordinates to api
        Call<Post> postCoordinates(@Body JsonObject data);
    }

    public class Post {
        @SerializedName("Status")
        @Expose
        private String status;
        @SerializedName("userName")
        @Expose
        private String userName;
        @SerializedName("x")
        @Expose
        private String x;
        @SerializedName("y")
        @Expose
        private String y;
        @SerializedName("gameId")
        @Expose
        private String gameId;
        @SerializedName("move")
        @Expose
        private String move;

        @SerializedName("message")
        @Expose
        private String message;

        public String getStatus() {
            return status;
        }

        public void setStatus(String title) {
            this.status = title;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String message) {
            this.userName = message;
        }

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }

        public String getY() {
            return y;
        }

        public void setY(String y) {
            this.y = y;
        }

        public String getGameId() {
            return gameId;
        }

        public void setGameId(String gameId) {
            this.gameId = gameId;
        }

        public String getMove() {
            return move;
        }

        public void setMove(String move) {
            this.move = move;
        }

        public String getMessage() {
            return message;
        }
    }
}




