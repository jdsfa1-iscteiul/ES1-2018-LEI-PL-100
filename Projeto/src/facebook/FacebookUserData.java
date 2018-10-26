package facebook;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import com.restfb.types.Group;
import com.restfb.types.Post;

public class FacebookUserData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String userId;
	private String userName;
	private List<Group> groupPages;
	private HashMap<String,List<Post>> groupPosts = new HashMap<>();
	
	
	public FacebookUserData(String userId, String userName, List<Group> groupPages) {
		this.userId = userId;
		this.userName = userName;
		this.groupPages = groupPages;
	}
	
	public void addGroupPosts(List<Group> listToAdd) {
		
		
	}
	
	

}