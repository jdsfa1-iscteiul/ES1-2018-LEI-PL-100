package Interface;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.util.List;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Group;
import com.restfb.types.Post;
import com.restfb.types.User;

import javafx.scene.control.TextField;

public class Controller {
	
	public Button search_button;
	public TextField text_box;
	public TextArea text_posts;
	private String idGrupoIscte = "";
	
	public void getUserData() {
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
				text_box.appendText(post.getMessage());
				//System.out.println(post.getMessage());
				//System.out.println(post.getUpdatedTime());
				//System.out.println("fb.com/"+ post.getId()+"\n");
			}
		}	
	}
	
	public void handleSearchButton () {
		System.out.println("Searching" + " for "+getBoxText() );
	}
	
	public void handleHomeButton() {
		text_posts.appendText("Ola, encontrámos estas mensagens nos teus grupos: \n");
		getUserData();
	}
	
	private String getBoxText() {
		return text_box.getText();
	}
}
