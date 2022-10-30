package grid.bit.utils;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

import java.util.Locale;

public class RandomFakeDataUtils {

    private final static Faker faker = new Faker();


    private final static FakeValuesService fakeValuesService = new FakeValuesService(
            new Locale("en-GB"), new RandomService());


    public static String getRandomStr(String lengthString){

        return fakeValuesService.regexify("[a-z1-9]{"+lengthString+"}");
    }

}
