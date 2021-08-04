/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.dao;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import static java.util.stream.Collectors.toMap;

/**
 *
 * @author Soft2
 */
public class Test {
    public static void main(String[] args) {
        try
        {
            Map<String,Integer> result=VoteDao.getResult().entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
.collect(toMap(Map.Entry::getKey,
               Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));;
            Set r=result.entrySet();
           Iterator it=r.iterator();
           while(it.hasNext())
           {
               Map.Entry<String,Integer> e=(Map.Entry<String,Integer>)it.next();
               System.out.println(e.getKey()+" "+e.getValue());
           }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
