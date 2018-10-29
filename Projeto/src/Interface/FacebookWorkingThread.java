package Interface;

import java.util.ArrayList;
import java.util.List;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Group;
import com.restfb.types.Post;
import com.restfb.types.User;

import javafx.scene.control.TextArea;

public class FacebookWorkingThread extends Thread {
	
	private String idGrupoIscte = "";
	private TextArea tx;
	
	public FacebookWorkingThread(TextArea tx) {
		this.tx = tx;
	}
	
	public void start() {
		String at = "EAAdoDZCjMNoEBABEtZCdVZC8yMsOZAyBOl2VzJHruDxn4IIxw1sDxvtFgneEzNSwBH1q9dk6qS1UBDfZCbZCN7ZBjqqv2ZArbp1u4baADCdl64pZBrrSsFaNYRqAT85PnOOCoDZBsNZCqPotx4zO6E9IfMb6G1NlYYDo1EZD";

		@SuppressWarnings("deprecation")
		FacebookClient fb = new DefaultFacebookClient(at);

		User me = fb.fetchObject("me", User.class);
		System.out.println("Utilizador: \n" + me.getName() +"\n");


		Connection<Group> groups = fb.fetchConnection("me/groups", Group.class);

		for(List<Group> groupPages: groups) {
			for(Group g: groupPages) {
				if (g.getName().equals("TURMA LEI PL")) {
					idGrupoIscte = g.getId();
					System.out.println("Grupo: " + g.getName() + "\n");
				}
			}
		}

		Connection<Post> posts = fb.fetchConnection(idGrupoIscte+"/feed", Post.class);
		
		for (List<Post> postPages: posts) {
			for(Post post: postPages) {
				tx.appendText(post.getMessage());
			}
					
		}	
	}

}
