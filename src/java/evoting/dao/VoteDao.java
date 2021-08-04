/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.dao;

import evoting.dbutil.DBConnection;
import evoting.dto.CandidateInfo;
import evoting.dto.VoteDto;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Soft2
 */
public class VoteDao {
    private static PreparedStatement ps,ps1,ps2,ps3,ps4;
    private static Statement st;
    static
    {
        try {
            st=DBConnection.getConnection().createStatement();
            ps=DBConnection.getConnection().prepareStatement("select candidate_id from voting where voter_id=?");
            ps2=DBConnection.getConnection().prepareStatement("select candidate_id,username,party,symbol from candidate,user_details where candidate.user_id=user_details.adhar_no and candidate.candidate_id=?");
            ps3=DBConnection.getConnection().prepareStatement("select candidate_id,count(*) as votes_obt from voting group by candidate_id order by votes_obt desc");
            ps1=DBConnection.getConnection().prepareStatement("insert into voting values(?,?)"); 
           // ps4=DBConnection.getConnection().prepareStatement("select ")
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public static String getCandidateId(String userid)throws SQLException
    {
        ps.setString(1, userid);
        ResultSet rs=ps.executeQuery();
        if(rs.next())
            return rs.getString(1);
        return null;
    }
    public static CandidateInfo getVote(String candidateid)throws Exception
    {
        CandidateInfo cd=new CandidateInfo();
        ps2.setString(1,candidateid);
        ResultSet rs=ps2.executeQuery();
       
        Blob blob ;
        InputStream inputStream;
                ByteArrayOutputStream outputStream;
                byte[] buffer;
                int bytesRead;
                byte[] imageBytes;
                String base64Image;
        if(rs.next())
        {
            blob=rs.getBlob(4);
            inputStream = blob.getBinaryStream();
            outputStream = new ByteArrayOutputStream();
            buffer = new byte[4096];
            bytesRead = -1;
             while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);                  
                }
             imageBytes = outputStream.toByteArray();
            // Base64.Encoder en=Base64.getEncoder();
             base64Image = Base64.getEncoder().encodeToString(imageBytes);
             cd.setSymbol(base64Image);
             cd.setCandidateId(candidateid);
             cd.setParty(rs.getString(3));
             cd.setCandidateName(rs.getString(2));
        }
        return cd;
        
    }
    public static Map<String,Integer> getResult()throws Exception
    {
        Map<String,Integer> result=new LinkedHashMap<>();
        ResultSet rs=ps3.executeQuery();
        while(rs.next())
            result.put(rs.getString(1), rs.getInt(2));
        return result;
    }
    public static int getVoteCount()throws SQLException
    {
        ResultSet rs=st.executeQuery("select count(*) from voting");
        if(rs.next())
            return rs.getInt(1);
        return 0;
    }
    public static boolean addVote(VoteDto obj)throws SQLException
    {
        ps1.setString(1,obj.getCandidateId());
        ps1.setString(2,obj.getVoterId());
        return (ps1.executeUpdate()!=0);
    }
}
