package com.example.Matcho.Utils.Dto;

public class AuthenticationResponse {

    private final String jwt;
     private String UserName ;
      private String Rolename; 
 

    public AuthenticationResponse(String jwt, Long UserID, String userName, String rolename) {
		super();
		this.jwt = jwt;
		UserName = userName;
		Rolename = rolename;
	}
    
    
    


	public String getUserName() {
		return UserName;
	}





	public void setUserName(String userName) {
		UserName = userName;
	}





	public String getRolename() {
		return Rolename;
	}





	public void setRolename(String rolename) {
		Rolename = rolename;
	}





	public String getJwt() {
        return jwt;
    }
}