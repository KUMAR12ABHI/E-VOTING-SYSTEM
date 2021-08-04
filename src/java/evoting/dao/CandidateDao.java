
package evoting.dao;

import evoting.dbutil.DBConnection;
import evoting.dto.AddCandidateDto;
import evoting.dto.CandidateDto;
import evoting.dto.CandidateInfo;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;

public class CandidateDao {
    private static Statement st,st2,st3;
    private static PreparedStatement ps,ps1,ps2,ps3,ps4,ps5;
    static
    {
        try {
            st=DBConnection.getConnection().createStatement();
            st2=DBConnection.getConnection().createStatement();
            st3=DBConnection.getConnection().createStatement();
            ps=DBConnection.getConnection().prepareStatement("select username from user_details where adhar_no=?");
            ps1=DBConnection.getConnection().prepareStatement("insert into candidate values(?,?,?,?,?)");
            ps2=DBConnection.getConnection().prepareStatement("select * from candidate where candidate_id=?");
            ps3=DBConnection.getConnection().prepareStatement("update candidate set party=?,symbol=?,city=? where candidate_id=?");
            ps4=DBConnection.getConnection().prepareStatement("delete from candidate where candidate_id=?");
            ps5=DBConnection.getConnection().prepareStatement("select candidate_id,username,party,symbol from candidate,user_details where candidate.user_id=user_details.adhar_no and candidate.city=(select city from user_details where adhar_no=?)");
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public static String getNewCandidateId()throws SQLException
    {
        ResultSet rs=st.executeQuery("select count(*) from candidate");
        if(rs.next())
            return "C"+(100+(rs.getInt(1)+1));
        return null;
    }
    public static String getUsernameById(String uid)throws SQLException
    {
        ps.setString(1, uid);
        ResultSet rs=ps.executeQuery();
        if(rs.next())
            return rs.getString(1);
        return null;
    }
    public static ArrayList<String> getCity()throws SQLException
    {
        ArrayList<String> city=new ArrayList<>();
        ResultSet rs=st2.executeQuery("select distinct city from user_details");
        while(rs.next())
            city.add(rs.getString(1));
        return city;
    }
    
    public static boolean addCandidate(CandidateDto obj)throws SQLException,IOException
    {
       // System.out.println("Called AddCandidate method");
        ps1.setString(1,obj.getCandidateId());
       // System.out.println("1setter");
        ps1.setString(2,obj.getParty());
       // System.out.println("2nd setter");
        ps1.setString(3,obj.getUserId());
       // System.out.println("3rd setter");
       ps1.setBinaryStream(4,obj.getSymbol(),obj.getSymbol().available());
        //System.out.println("4th setter");
      ps1.setString(5,obj.getCity());
       // System.out.println("Called end");
        int res=ps1.executeUpdate();
       // System.out.println("Result Add Candidate Method:"+res);
      if(res==1)
          return true;
      return false;
    }
    public static ArrayList<String> getCandidateId()throws SQLException
    {
        ResultSet rs=st3.executeQuery("select candidate_id from candidate");
        ArrayList<String> id=new ArrayList<>();
        while(rs.next())
        {
            id.add(rs.getString(1));
        }
        return id;
    }
    public static CandidateDetails getDetailsById(String cid)throws Exception
    {
        ps2.setString(1, cid);
        ResultSet rs=ps2.executeQuery();
      CandidateDetails cd=new CandidateDetails();
       
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
             cd.setCity(rs.getString(5));
             cd.setCandidateId(rs.getString(1));
             cd.setParty(rs.getString(2));
             cd.setUserId(rs.getString(3));
             cd.setCandidateName(getUsernameById(rs.getString(3)));
        }
        return cd;
    }
    public static boolean updateCandidate(evoting.dto.CandidateDto candidate)throws Exception
    {
        ps3.setString(1, candidate.getParty());
         ps3.setBinaryStream(2,candidate.getSymbol(),candidate.getSymbol().available());
         ps3.setString(3, candidate.getCity());
         ps3.setString(4, candidate.getCandidateId());
        return (ps3.executeUpdate()!=0);
    }
    public static boolean removeCandidate(String cid)throws SQLException
    {
        ps4.setString(1, cid);
        return (ps4.executeUpdate()!=0);
    }
    public static ArrayList<CandidateInfo> viewCandidate(String adhar_id)throws Exception
    {
        ArrayList<CandidateInfo> candidateList=new ArrayList<CandidateInfo>();
        ps5.setString(1,adhar_id);
        ResultSet rs=ps5.executeQuery();
         Blob blob ;
        InputStream inputStream;
                ByteArrayOutputStream outputStream;
                byte[] buffer;
                int bytesRead;
                byte[] imageBytes;
                String base64Image;
                while(rs.next())
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
                    CandidateInfo cd=new CandidateInfo();
                    cd.setSymbol(base64Image);
                    cd.setCandidateId(rs.getString(1));
                    cd.setParty(rs.getString(3));
                    cd.setCandidateName(rs.getString(2));
                    candidateList.add(cd);
                }
                return candidateList;
    }
}


