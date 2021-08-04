package evoting.dao;

import evoting.dbutil.DBConnection;
import evoting.dto.CandidateDto;
import evoting.dto.UserDetails;
import evoting.dto.UserDto;
import evoting.dto.VoteDto;
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

public class UserDao {
    private static Statement st,st1;
    private static PreparedStatement ps,ps1,ps2,ps3,ps4,ps5,ps6,ps7;
    static {
        try {
            st=DBConnection.getConnection().createStatement();
            st1=DBConnection.getConnection().createStatement();
            ps=DBConnection.getConnection().prepareStatement("select * from user_details where adhar_no=?");
            ps1= DBConnection.getConnection().prepareStatement("insert into user_details values(?,?,?,?,?,?,?,?)");
            ps2=DBConnection.getConnection().prepareStatement("select * from user_details where adhar_no=? and password=?");
            ps3=DBConnection.getConnection().prepareStatement("select * from voting where user_id=?");
            ps4=DBConnection.getConnection().prepareStatement("insert into voting values(?,?)");
            ps5=DBConnection.getConnection().prepareStatement("select  candidate_id,username,party,election_symbol from candidate inner join user_details on candidate.user_id=user_details.adhar_no where user_details.city=(select city from user_details where adhar_no=?)");
            ps6=DBConnection.getConnection().prepareStatement("select * from user_details where adhar_no=?");
            ps7=DBConnection.getConnection().prepareStatement("delete from user_details where adhar_no=?");
        } catch (SQLException e) {
            if(DBConnection.getConnection()!=null)
                System.out.println("not null");
            e.printStackTrace();
        }
    }
    public static boolean addUser(UserDetails user) throws SQLException {
        ps.setString(1, user.getUserid());
        ResultSet rs=ps.executeQuery();
        if(rs.next())
            return false;
        ps1.setString(1,user.getUserid());
        ps1.setString(3,user.getUsername());
        ps1.setString(2,user.getPassword());
        ps1.setString(4,user.getAddress());
        ps1.setString(5,user.getCity());
        ps1.setString(6,user.getEmail());
        ps1.setString(7,user.getMobile()+"");
        ps1.setString(8, "Voter");
        return (ps1.executeUpdate()!=0);
    }
    public static String validateUser(UserDto user) throws SQLException {
        ps2.setString(1,user.getUserid());
        ps2.setString(2,user.getPassword());
        ResultSet rs=ps2.executeQuery();
        if(rs.next())
            return rs.getString(8);
        else
            return null;
    }
    public static boolean validateVote(String userid)throws SQLException
    {
        ps3.setString(1, userid);
        return ps3.executeQuery().next();
    }
    public static boolean addVote(VoteDto vote)throws SQLException
    {
        if(validateVote(vote.getVoterId())==false)
        {
            ps4.setString(1, vote.getCandidateId());
            ps4.setString(2, vote.getVoterId());
            return (ps4.executeUpdate()!=0);
        }
        return false;
    }
    public static ArrayList<CandidateDto> viewCandidate(String userId)throws SQLException, IOException
    {
        ArrayList<CandidateDto> candidate=new ArrayList<>();
        ps5.setString(1, userId);
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
             base64Image = Base64.getEncoder().encodeToString(imageBytes);
          // candidate.add(new CandidateDto(rs.getString(1),rs.getString(2),rs.getString(3),base64Image));
        }
        return candidate;
    }
    public static ArrayList<UserDetails> showAllUsers()throws Exception
    {
        ResultSet rs=st.executeQuery("select * from user_details");
        ArrayList<UserDetails> users=new ArrayList<>();
        UserDetails u;
        while(rs.next())
        {
            u=new UserDetails();
            u.setUserid(rs.getString(1));
            u.setUsername(rs.getString(2));
            u.setAddress(rs.getString(4));
            u.setCity(rs.getString(5));
            u.setEmail(rs.getString(6));
            u.setMobile(Long.parseLong(rs.getString(7)));
            users.add(u);
        }
        return users;
    }
    public static ArrayList<String> getUserId()throws Exception
    {
        ResultSet rs=st1.executeQuery("select adhar_no from user_details");
        ArrayList<String> userid=new ArrayList<>();
        while(rs.next())
        {
            userid.add(rs.getString(1));
        }
        return userid;
    }
    public static UserDetails getDetailsById(String uid)throws SQLException
    {
        ps6.setString(1, uid);
        UserDetails user=new UserDetails();
        ResultSet rs=ps6.executeQuery();
        if(rs.next())
        {
            user.setUserid(uid);
            user.setUsername(rs.getString(2));
            user.setAddress(rs.getString(4));
            user.setCity(rs.getString(5));
            user.setEmail(rs.getString(6));
            user.setMobile(Long.parseLong(rs.getString(7)));
        }
        return user;
    }
    public static boolean removeUser(String uid)throws SQLException
    {
        ps7.setString(1, uid);
        return (ps7.executeUpdate()!=0);
    }
}
