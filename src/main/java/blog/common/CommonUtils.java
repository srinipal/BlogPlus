package blog.common;

import java.util.List;

/**
 * Created by srinivas.g on 18/11/16.
 */
public class CommonUtils {



    public static void main(String[] args) {
        String str = "";
    }

    /**
     *
     * @param strList
     * @return
     */
    public static String strListToStr(List<String> strList){
        StringBuilder sb = new StringBuilder();
        if(strList != null && !strList.isEmpty()){
            //get the number of strList
            int numTags = strList.size();
            for(int i=0;i<numTags-1;i++){
                sb.append(strList.get(i));
                sb.append(", ");
            }

            //Append the last tag
            sb.append(strList.get(numTags-1));
            return sb.toString();
        }
        return "";
    }
}
