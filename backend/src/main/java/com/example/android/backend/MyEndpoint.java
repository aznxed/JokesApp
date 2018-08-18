package com.example.android.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;

/** An endpoint class we are exposing */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.android.example.com",
                ownerName = "backend.android.example.com",
                packagePath = ""
        )
)
public class MyEndpoint {
    public static String[] DOG_JOKES = {"What do chemist's dogs do with their bone? -They barium",
            "What do you call a frozen dog? -A pupsicle",
            "What do you call a dog with a surround system? -A sub-woofer",
            "What did the dog say to the sandpaper? -Ruff",
            "What did the dog say to the tree? -Bark"};
    public static String[] CAT_JOKES = {"What is a cat's favorite book? -The Great Catsby",
            "Why don't cats play poker? -Too many cheetahs",
            "What do you call a pile of kittens? -A meowntain",
            "What do cats love to read? -Catalogs",
            "How do felines maintain law and order? -Claw Enforcement"};
    public static String[] MED_JOKES = {"I've got very bad news. You've got cancer and Alzheimer\'s -Well... at least I don't have cancer",
            "I went to the doctor and he told me I had acute appendicitis. -and I said \"compared to who?\"",
            "Did you hear about the guy who lost his whole left side? -He's all right now",
            "What do you call a doctor that fixes websites? -A URLologist",
            "What happened when the man tried to search for information about impotence on the Internet? -Nothing came up."};

    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String type) {
        MyBean response = new MyBean();
        switch (type){
            case "DOG":
                response.setData(jokesToString(DOG_JOKES));
                break;
            case "CAT":
                response.setData(jokesToString(CAT_JOKES));
                break;
            case "MED":
                response.setData(jokesToString(MED_JOKES));
                break;
            default:
                response.setData("");

        }
        return response;
    }

    public static String jokesToString(String[] jokes){
        StringBuilder stringBuilder = new StringBuilder();
        for(String joke:jokes){
            stringBuilder.append(joke);
            stringBuilder.append(",");
        }
        return stringBuilder.toString();
    }


}
