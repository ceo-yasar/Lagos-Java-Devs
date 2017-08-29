package com.itquire.lagosjavadevs;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * A class to handle the network connection and parsing of JSON response from the GITHUB url
 */

public class UserUtils {
    private static final String LOG_TAG = UserUtils.class.getSimpleName();
    public static JSONObject baseJsonResponse;
    public static JSONObject currentUser;
    public static JSONArray userArray;
    public static String userName;
    public static String userImageUrl;
    public static String userProfileUrl;
    public static Users user;
    public static List<Users> userArrayList;

    /**
     * Create a private constructor  for {@link UserUtils}
     */
    private UserUtils() {
    }

    /**
     * Query the GITHUB site and return the {@link Users} objects
     */
    public static List<Users> fetchUsersData(String requestUrl){
        //create URL object
        URL url = createUrl(requestUrl);
        //Perform HTTP request to the the URL and receive a JSON response back
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e){
            Log.e(LOG_TAG, "Problem making the HTTP Request.", e);
        }

        /**Extract Relevant fields from the JSON response and create a list of {@link Users}
         * */
        List<Users> userList = extractFeatureFromJson(jsonResponse);

        /**Return the list of {@link Users}
         * */
        return userList;
    }

    /**
     * Returns new URL object from the given string URL
     * @param stringUrl
     * @return
     */
    private static URL createUrl(String stringUrl){
        URL url = null;
        try{
            url = new URL(stringUrl);
        } catch (MalformedURLException e){
            Log.e(LOG_TAG, "Problem building URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given url and return the result as String
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        //If the URL is null, then return early
        if (url==null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(1000/*milliseconds*/);
            urlConnection.setConnectTimeout(1500 /*milliseconds*/);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //If the request was successful  (response code 200)
            //then read the input stream and parse the response
            if (urlConnection.getResponseCode()==200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else{
                Log.e(LOG_TAG, "Error Response code: "+ urlConnection.getResponseCode());
            }
        } catch (IOException e){
            Log.e(LOG_TAG, "Problem retrieving the JSON result", e);
        } finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (inputStream != null){
                /**
                 * Closing the input stream could throw an exception, which is why
                 * the makeHttpRequest(URL url) method signature specifies that an IOException
                 * could be thrown
                 */
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the whole
     * JSON response from the server
     */
    private static String readFromStream (InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader((inputStreamReader));
            String line = reader.readLine();
            while (line != null){
                output.append(line);
                line=reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Users} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<Users> extractFeatureFromJson(String userJSON) {
        //If the JSON string is empty or null, then return early
        if (TextUtils.isEmpty(userJSON)){
            return null;
        }

        // Create an empty ArrayList that we can start adding users to
        userArrayList = new ArrayList<>();

        /** Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
         * is formatted, a JSONException exception object will be thrown.
         * Catch the exception so the app doesn't crash, and print the error message to the logs.
         */
        try {
            // Create a JSONObject from the JSON response string
            baseJsonResponse = new JSONObject(userJSON);

            //Extract the JSONArray associated with the called "items"
            //which represents a list of users
            userArray = baseJsonResponse.getJSONArray("items");
            /**
             * For each user in the userArray create an {@link Users} object
             *
             */
            for (int i = 0; i < userArray.length(); i++){
                //Get a single user at position i within the list of users
                currentUser = userArray.getJSONObject(i);

                //Extract the value for the key called "login"
                userName = currentUser.getString("login");

                //Extract the value for the key called "html_url"
                userProfileUrl = currentUser.getString("html_url");

                //Extract the user profile image value from the key called "avatar_url"
                userImageUrl = currentUser.getString("avatar_url");

                /**
                 * create a new {@link Users} object with the userName, userProfileUrl and userImageUrl
                 * from the JSON response
                 */
                user = new Users(userName, userImageUrl, userProfileUrl);

                //add the new {@link Users} to the list of users
                userArrayList.add(user);

            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("UserUtils", "Problem parsing the JSON results", e);
        }

        // Return the list of users
        return userArrayList;
    }




}
