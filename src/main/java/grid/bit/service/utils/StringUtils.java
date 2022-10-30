package grid.bit.service.utils;

import java.util.List;

public class StringUtils {

    /**
     * 1. There is has gotten a string collection.
     * If a collection has not a element, then there is returned  a `string empty`.
     * 2. First, There is gotten a string and the string becomes  as a prefix.
     * str.indexOf(prefix) - this the method equals two strings to find a index.
     * If both lines are identical, then the processed line is skipped.
     * 3. If the strings are different, then we go into the while loop
     * and until a substring (prefix variable) is obtained,
     * which is as a prefix in the current string being checked,
     * as a sample, in each cycle we will shorten the value in the prefix variable,
     * through the substring() method.
     * 4. As soon as after the reduction of the substring in the prefix variable,
     * there will be a substring that will correspond to some part of it
     * in the checked string (prefix part), then the while () loop ends
     * and the transition to another string in the checked collection occurs.
     * If in this checked string, the previously received prefix does not fit,
     * then the base prefix is shortened through the subString() method.
     * 5. Thus, all strings will be checked and a common prefix will be found for them,
     * which will be the maximum length and common for the entire collection of processed strings.
     */
    public static String findLongestCommonPrefix(List<String> stringList){

        String emptyStr = "";

        if (stringList.size() == 0){
            return emptyStr;
        }

        String prefix = stringList.get(0);
        for (var str : stringList) {

            while (str.indexOf(prefix) != 0) {

                prefix = prefix.substring(0, prefix.length() - 1);

                if (prefix.isEmpty()){
                    return emptyStr;
                }
            }
        }
        return prefix;
    }
}
