/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author Soft2
 */
public class Test2 {
    public static void main(String[] args) {
         try
        {
            Map<String,Integer> result=VoteDao.getResult();
            Set s=result.entrySet();
            HashMap<CandidateDetails,Integer> resultDetails=new HashMap<CandidateDetails,Integer>();
            Iterator it=s.iterator();
            while(it.hasNext())
            {
                Map.Entry<String,Integer> e=(Map.Entry)it.next();
                CandidateDetails c=CandidateDao.getDetailsById(e.getKey());
                resultDetails.put(c, e.getValue());
            }
            Iterator it2=resultDetails.entrySet().iterator();
            while(it2.hasNext())
            {
                Map.Entry<CandidateDetails,Integer> e=(Map.Entry)it2.next();
                System.out.println(e.getKey()+" "+e.getValue());
            }
        }
         catch(Exception e)
         {
             e.printStackTrace();
         }
    }
}
